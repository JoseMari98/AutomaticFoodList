package es.uca.automaticfoodlist.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Ingrediente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Este campo es obligatorioo")
    @Column(unique = true)
    private String nombre = "";
    @Column(unique = true)
    private String idApi = "";
    @OneToMany(mappedBy = "ingrediente")
    private Set<PreferenciaIngrediente> preferenciaIngredienteSet = new HashSet<>();
    @OneToMany(mappedBy = "ingrediente")
    private Set<RecetaIngrediente> recetaIngredientes = new HashSet<>();
    @OneToMany(mappedBy = "ingrediente", cascade = CascadeType.ALL)
    private Set<Producto> productos;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Set<Producto> getProducto() {
        return productos;
    }

    public Set<PreferenciaIngrediente> getPreferenciaIngredienteSet() {
        return preferenciaIngredienteSet;
    }

    public Set<RecetaIngrediente> getRecetaIngredientes() {
        return recetaIngredientes;
    }

    public String getIdApi() {
        return idApi;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setIdApi(String id_api) {
        this.idApi = id_api;
    }

    public void setPreferenciaIngredienteSet(Set<PreferenciaIngrediente> preferenciaIngredienteSet) {
        this.preferenciaIngredienteSet = preferenciaIngredienteSet;
    }

    public Set<Producto> getProductos() {
        return productos;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingrediente that = (Ingrediente) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
