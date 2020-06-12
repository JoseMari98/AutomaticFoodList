package es.uca.automaticfoodlist.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Este campo es obligatorio")
    private String nombre = "";
    private double precio;
    @Enumerated(EnumType.STRING)
    private UnidadMedida unidad;
    private double peso;
    @OneToMany(mappedBy = "producto")
    private Set<UsuarioProducto> usuarioProductos = new HashSet<>();
    @ManyToOne
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

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public Set<UsuarioProducto> getUsuarioProductos() {
        return usuarioProductos;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

    public void setUsuarioProductos(Set<UsuarioProducto> usuarioProductos) {
        this.usuarioProductos = usuarioProductos;
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
