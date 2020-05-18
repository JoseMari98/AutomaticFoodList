package es.uca.AutomaticFoodList.Entities;

import org.optaplanner.core.api.domain.lookup.PlanningId;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Receta {
    @PlanningId
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Este campo es obligatorio")
    @Column(unique = true)
    private String nombre = "";
    @Column(unique = true)
    private String idApi = "";
    @OneToOne
    private ValoresNutricionales valoresNutricionales;
    private double precioAproximado;
    @OneToMany(mappedBy = "receta")
    private Set<RecetaIngrediente> recetaIngredientes = new HashSet<>();
    @OneToOne
    private Usuario usuario;
    @OneToMany(mappedBy = "receta")
    private Set<IntoleranciaReceta> intoleranciaRecetaSet;

    //tomara valores verdadero o falso separado por |, ejemplo t|f|t
    private String comidaAdecuada = "";

    private String platoAdecuado = "";

    public Long getId() {
        return id;
    }

    public String getComidaAdecuada() {
        return comidaAdecuada;
    }

    public String getPlatoAdecuado() {
        return platoAdecuado;
    }

    public Set<IntoleranciaReceta> getIntoleranciaRecetaSet() {
        return intoleranciaRecetaSet;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIdApi() {
        return idApi;
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

    public void setComidaAdecuada(String comidaAdecuada) {
        this.comidaAdecuada = comidaAdecuada;
    }

    public void setIntoleranciaRecetaSet(Set<IntoleranciaReceta> intoleranciaRecetaSet) {
        this.intoleranciaRecetaSet = intoleranciaRecetaSet;
    }

    public void setPlatoAdecuado(String platoAdecuado) {
        this.platoAdecuado = platoAdecuado;
    }

    public void setIdApi(String id_api) {
        this.idApi = id_api;
    }

    public void setPrecioAproximado(double precioAproximado) {
        this.precioAproximado = precioAproximado;
    }

    public void setValoresNutricionales(ValoresNutricionales valoresNutricionales) {
        this.valoresNutricionales = valoresNutricionales;
    }
}
