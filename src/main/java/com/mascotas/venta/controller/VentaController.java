package com.mascotas.venta.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mascotas.venta.api.request.VentaUpdateRequest;
import com.mascotas.venta.model.Venta;
import com.mascotas.venta.service.VentaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/venta")
@RequiredArgsConstructor
public class VentaController {
    
    private static final Logger logger = LoggerFactory.getLogger(VentaController.class);

    private final VentaService ventaService;

    @PostMapping
    public ResponseEntity<EntityModel<Venta>> createVenta(@RequestBody Venta venta) {
        logger.info("Creando una Venta con request: {} ",venta);
        Venta savedVenta = ventaService.saveVenta(venta);
        logger.info("Venta creada con request: {} ",savedVenta.getId());
        
        EntityModel<Venta> ventaModel = EntityModel.of(savedVenta);
        ventaModel.add(linkTo(methodOn(VentaController.class).getVentaById(savedVenta.getId())).withSelfRel());



        return ResponseEntity.created(
            linkTo(methodOn(VentaController.class).getVentaById(savedVenta.getId())).toUri())
            .body(ventaModel);
    }
        
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Venta>> getVentaById(@PathVariable Long id) {
        logger.info("Creando una Venta con request: {} ",id);
        Optional<Venta> venta = ventaService.findVentaById(id);

        if (venta.isPresent()) {
            EntityModel<Venta> ventaModel = EntityModel.of(venta.get());
            ventaModel.add(linkTo(methodOn(VentaController.class).getVentaById(id)).withSelfRel());

            return ResponseEntity.ok(ventaModel);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<CollectionModel<EntityModel<Venta>>> getAllVentas() {
        logger.info("Obteniendo todas las Ventas: {} ");
        // List<Venta> venta = ventaService.findAllVentas();

        List<EntityModel<Venta>> ventas = ventaService.findAllVentas().stream()
                .map(venta -> {
                    EntityModel<Venta> ventaModel = EntityModel.of(venta);
                    ventaModel.add(linkTo(methodOn(VentaController.class).getVentaById(venta.getId())).withSelfRel());
                    return ventaModel;
                }).toList();
        logger.info("Encontro {} Ventas  ",ventas.size());
        // return new ResponseEntity<>(venta,HttpStatus.OK);
        
        return ResponseEntity.ok(CollectionModel.of(ventas,
                linkTo(methodOn(VentaController.class).getAllVentas()).withSelfRel()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Venta>> updateReserva(@PathVariable Long id, @RequestBody VentaUpdateRequest updateRequest) {
        logger.info("actualizando una Venta por el ID: {} y request {}",id,updateRequest);
        Venta updateVenta = ventaService.updateVenta(id, updateRequest);
        logger.info("Venta actualizada exitosamente. Venta ID: {} ",updateVenta.getId());
        EntityModel<Venta> ventaModel = EntityModel.of(updateVenta);
        ventaModel.add(linkTo(methodOn(VentaController.class).getVentaById(id)).withSelfRel());

        return ResponseEntity.ok(ventaModel);
    }


    
    @DeleteMapping("/{id}")
    public ResponseEntity<Venta> deleteReserva(@PathVariable Long id) {
        logger.info("Eliminando una Venta por el ID: {} y request {}",id);
        ventaService.deleteVenta(id);
        logger.info("Venta eliminada exitosamente. Venta ID: {} ",id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
