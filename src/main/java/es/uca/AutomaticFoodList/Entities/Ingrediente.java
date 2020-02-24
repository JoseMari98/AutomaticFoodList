package es.uca.AutomaticFoodList.Entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Ingrediente {
    @Id //esto sirve para decir cual es el id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //esto para que se genere aleatorio
    private Long id;
    @NotEmpty(message = "Este campo es obligatorio")
    @Column(unique = true)
    private String id_api = "", nombre = "";
    @OneToMany(mappedBy = "ingrediente") //esto para decir la cardinalidad y a que variable se asocia
    private Set<PreferenciaIngrediente> preferenciaIngredienteSet = new HashSet<>();
    @OneToMany(mappedBy = "ingrediente")
    private Set<RecetaIngrediente> recetaIngredientes = new HashSet<>();
    @OneToOne(mappedBy = "ingrediente", cascade=CascadeType.ALL)
    private Producto producto;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Producto getProducto() {
        return producto;
    }

    public Set<PreferenciaIngrediente> getPreferenciaIngredienteSet() {
        return preferenciaIngredienteSet;
    }

    public Set<RecetaIngrediente> getRecetaIngredientes() {
        return recetaIngredientes;
    }

    public String getId_api() {
        return id_api;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setId_api(String id_api) {
        this.id_api = id_api;
    }

    public void setPreferenciaIngredienteSet(Set<PreferenciaIngrediente> preferenciaIngredienteSet) {
        this.preferenciaIngredienteSet = preferenciaIngredienteSet;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public void setRecetaIngredientes(Set<RecetaIngrediente> recetaIngredientes) {
        this.recetaIngredientes = recetaIngredientes;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
