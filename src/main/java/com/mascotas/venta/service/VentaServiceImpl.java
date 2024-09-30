package com.mascotas.venta.service;


import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mascotas.venta.api.request.VentaUpdateRequest;
import com.mascotas.venta.exceptionhandler.DatabaseTransactionException;
import com.mascotas.venta.exceptionhandler.ResourceNotFoundException;
import com.mascotas.venta.model.Venta;
import com.mascotas.venta.repository.VentaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VentaServiceImpl implements  VentaService{

    private final VentaRepository ventaRepository;

    private static final Logger logger = LoggerFactory.getLogger(VentaService.class);


    @Override
    public Venta saveVenta(Venta ventaToSave) {
        logger.info("Creando Venta con Request: {} - metodo saveVenta",ventaToSave);
        try {
            Venta savedVenta = ventaRepository.save(ventaToSave);
            logger.info("Venta creada satisfactoriamente. Reserva ID: {} - metodo saveVenta",savedVenta.getId());

            return savedVenta;
        } catch (Exception e) {
            logger.error("Error creando Venta - metodo ventaToSave",e);

            throw new DatabaseTransactionException("Error creando Venta",e);
        }
    }

    @Override
    public Optional<Venta> findVentaById(Long id){
        logger.info("Encontrando la Venta por ID {} - metodo findVentaById",id);
        return ventaRepository.findById(id);
    }

    @Override
    public List<Venta> findAllVentas(){
        logger.info("Buscar todas las Ventas - metodo findAllVentas");
        return ventaRepository.findAll();

    }

    @Override
    public void deleteVenta(Long id){
        logger.info("Borrar una Venta por el ID. {} - metodo deleteVenta",id);
        Optional<Venta> reserva = ventaRepository.findById(id);
        if (reserva.isEmpty()){
            logger.info("Venta no encontrada. {} - metodo deleteReserva");
            throw new ResourceNotFoundException("Venta no encontrada");
        }
        logger.info("Borrando Venta. - metodo deleteVenta");
        ventaRepository.deleteById(id);
        logger.info("Venta borrada exitosamente. {} - metodo deleteVenta");
        
    }



    @Override 
    public Venta updateVenta(Long id, VentaUpdateRequest updateRequest){
        logger.info("actualizando Venta por el ID: {} - metodo updateReserva",id, updateRequest);
        Venta venta = ventaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada"));
        if (updateRequest.getEstado() != null){
            logger.info("actualizando Estado de Venta: {} - metodo updateVenta", updateRequest.getEstado());
            venta.setEstado(updateRequest.getEstado());

        }
        if(updateRequest.getProducto() != null){
            logger.info("actualizando Producto de Venta: {} - metodo updateVenta", updateRequest.getProducto());
            venta.setProducto(updateRequest.getProducto());
        }
        if(updateRequest.getValor() > 0){
            logger.info("actualizando Venta: {} - metodo updateVenta", updateRequest.getValor());
            venta.setValor(updateRequest.getValor());
            // venta.setValor(updateRequest.getValor());
        }
        Venta updateVenta = ventaRepository.save(venta);
        return updateVenta;
    }
}
