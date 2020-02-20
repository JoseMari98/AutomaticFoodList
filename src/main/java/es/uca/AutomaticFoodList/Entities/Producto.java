package es.uca.AutomaticFoodList.Entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Producto {
    @Id //esto sirve para decir cual es el id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //esto para que se genere aleatorio
    private Long id;
    @NotEmpty(message = "Este campo es obligatorio")
    private String nombre = "";
    @NotEmpty(message = "Este campo es obligatorio")
    private double precio;
    @NotEmpty(message = "Este campo es obligatorio")
    @Enumerated(EnumType.STRING)
    private UnidadMedida unidad;
    @NotEmpty(message = "Este campo es obligatorio")
    private double peso;
    @OneToMany(mappedBy = "producto")
    private Set<ListaCompra> listaCompras = new HashSet<>();
    @OneToOne
    private Ingrediente ingrediente;

    public double getPeso() {
        return peso;
    }

    public double getPrecio() {
        return precio;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public UnidadMedida getUnidad() {
        return unidad;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setUnidad(UnidadMedida unidad) {
        this.unidad = unidad;
    }
}
