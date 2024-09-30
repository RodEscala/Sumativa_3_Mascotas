package com.mascotas.venta.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name="venta")
public class Venta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min=1, max=30 )
    @Pattern(regexp = "[A-Za-z0-9 ]+")
    private String estado;

    @NotNull
    @Column
    @Size(min = 1, max = 30)
    @Pattern(regexp = "[A-Za-z0-9 ]+")
    private String producto;

    @NotNull
    @Column
    // @Size(min = 1, max = 8)
    @Min(value = 1, message = "El valor debe ser al menos 1")
    private int valor;




    //Join a otra tabla
    // Uno a muchos
    // @OneToMany
    // Uno a uno
    // @OneToOne
    // Etc
    // por ejemplo seria de esta forma para una 
    // tabla(imaginaria)

    // se explica solo.
    // @ManyToOne(cascade = CascadeType.All)
    // @JoinColumn(name = "hotel_id",referencedColumnName="id")
    // private Hotel hotel;

    //de esta forma seria en la otra tabla
    // @OneToMany(mappedBy = "Hotel",cascade = CascadeType.ALL, FetchType.LAZY)
    // @JsonIgnore
    // @private List<Reserva> reservas


}
