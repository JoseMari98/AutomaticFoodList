package es.uca.AutomaticFoodList.Entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Receta {
    @Id //esto sirve para decir cual es el id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //esto para que se genere aleatorio
    private Long id;
    @NotEmpty(message = "Este campo es obligatorio")
    @Column(unique = true)
    private String nombre = "";
    @Column(unique = true)
    private String id_api = "";
    @OneToOne
    private ValoresNutricionales valoresNutricionales;
    private double precioAproximado;
    @OneToMany(mappedBy = "receta") //esto para decir la cardinalidad y a que variable se asocia
    private Set<RecetaIngrediente> recetaIngredientes = new HashSet<>();
    @OneToOne
    private Usuario usuario;

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getId_api() {
        return id_api;
    }

    public Set<RecetaIngrediente> getRecetaIngredientes() {
        return recetaIngredientes;
    }

    public double getPrecioAproximado() {
        return precioAproximado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public ValoresNutricionales getValoresNutricionales() {
        return valoresNutricionales;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setRecetaIngredientes(Set<RecetaIngrediente> recetaIngredientes) {
        this.recetaIngredientes = recetaIngredientes;
    }

    public void setId_api(String id_api) {
        this.id_api = id_api;
    }

    public void setPrecioAproximado(double precioAproximado) {
        this.precioAproximado = precioAproximado;
    }

    public void setValoresNutricionales(ValoresNutricionales valoresNutricionales) {
        this.valoresNutricionales = valoresNutricionales;
    }
}
