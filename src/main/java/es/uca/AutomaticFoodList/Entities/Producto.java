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
    private double precio;
    @Enumerated(EnumType.STRING)
    private UnidadMedida unidad;
    private double peso;
    @OneToMany(mappedBy = "producto")
    private Set<ListaCompra> listaCompras = new HashSet<>();
    @OneToOne
    private Ingrediente ingrediente;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

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

    public Categoria getCategoria() {
        return categoria;
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public Set<ListaCompra> getListaCompras() {
        return listaCompras;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

    public void setListaCompras(Set<ListaCompra> listaCompras) {
        this.listaCompras = listaCompras;
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
