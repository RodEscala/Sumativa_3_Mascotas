package com.mascotas.venta.controller;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mascotas.venta.api.request.VentaUpdateRequest;
import com.mascotas.venta.model.Venta;
import com.mascotas.venta.service.VentaService;

@ExtendWith(MockitoExtension.class)
public class VentaControllerTest {

    @Mock
    private VentaService ventaService;

    @InjectMocks
    private VentaController ventaController;

    private Venta venta;
    private VentaUpdateRequest updateRequest;

    @BeforeEach
    void setUp() {
        venta = new Venta();
        venta.setId(1L);
        venta.setEstado("Entregado");
        venta.setProducto("Anti-pulgas");
        venta.setValor(3000);

        updateRequest = new VentaUpdateRequest();
        updateRequest.setEstado("En camino");
        updateRequest.setProducto("Correa");
        updateRequest.setValor(7000);
    }


    @Test
    void createVenta() {

        when(ventaService.saveVenta(any(Venta.class))).thenReturn(venta);


        ResponseEntity<EntityModel<Venta>> response = ventaController.createVenta(venta);


        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        EntityModel<Venta> savedVentaModel = response.getBody();
        assertNotNull(savedVentaModel);
        assertEquals(venta.getId(), savedVentaModel.getContent().getId());
        assertTrue(savedVentaModel.hasLink("self"));
        verify(ventaService).saveVenta(any(Venta.class));
    }

    @Test
    void getUserById_found() {

        when(ventaService.findVentaById(1L)).thenReturn(Optional.of(venta));


        ResponseEntity<EntityModel<Venta>> response = ventaController.getVentaById(1L);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        EntityModel<Venta> foundUserModel = response.getBody();
        assertNotNull(foundUserModel);
        assertEquals(venta.getId(), foundUserModel.getContent().getId());
        assertTrue(foundUserModel.hasLink("self"));
        verify(ventaService).findVentaById(1L);
    }

    @Test
    void getUserById_notFound() {

        when(ventaService.findVentaById(1L)).thenReturn(Optional.empty());


        ResponseEntity<EntityModel<Venta>> response = ventaController.getVentaById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(ventaService).findVentaById(1L);
    }

    @Test
    void patchUser() {

        when(ventaService.updateVenta(eq(1L), any(VentaUpdateRequest.class))).thenReturn(venta);


        ResponseEntity<EntityModel<Venta>> response = ventaController.updateReserva(1L, updateRequest);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        EntityModel<Venta> updatedVentaModel = response.getBody();
        assertNotNull(updatedVentaModel);
        assertEquals(venta.getId(), updatedVentaModel.getContent().getId());
        assertTrue(updatedVentaModel.hasLink("self"));
        verify(ventaService).updateVenta(eq(1L), any(VentaUpdateRequest.class));
    }
}
