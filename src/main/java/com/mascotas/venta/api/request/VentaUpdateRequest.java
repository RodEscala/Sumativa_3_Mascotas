package com.mascotas.venta.api.request;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class VentaUpdateRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min=1, max=30 )
    @Pattern(regexp = "[A-Za-z0-9 ]+")
    @NotBlank(message= "El campo estado no puede estar vacio")
    private String estado;

    @NotNull
    @Column
    @Size(min = 1, max = 30)
    // @Pattern(regexp = "[A-Za-z0-9 ]+")
    @NotBlank(message ="El campo producto no puede estar vacio")
    private String producto;

    @NotNull
    @Column
    // @Size(min = 1, max = 8)
    // @Pattern(regexp = "[A-Za-z0-9 ]+")
    @NotBlank(message ="El campo de valor no puede estar vacio")
    private int valor;


}
