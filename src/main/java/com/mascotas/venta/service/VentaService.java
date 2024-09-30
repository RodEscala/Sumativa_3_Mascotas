package com.mascotas.venta.service;
import java.util.List;
import java.util.Optional;

import com.mascotas.venta.api.request.VentaUpdateRequest;
import com.mascotas.venta.model.Venta;


public interface VentaService {
    Venta saveVenta(Venta venta);
    Optional<Venta> findVentaById(Long id);
    Venta updateVenta(Long id,VentaUpdateRequest updateRequest);
    void deleteVenta(Long id);
    List<Venta> findAllVentas();

}
