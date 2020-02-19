package es.uca.AutomaticFoodList.Entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

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
    private UnidadMedida unidad;
    @NotEmpty(message = "Este campo es obligatorio")
    private double peso;

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
