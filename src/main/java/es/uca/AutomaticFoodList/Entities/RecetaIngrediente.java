package es.uca.AutomaticFoodList.Entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class RecetaIngrediente {
    @Id //esto sirve para decir cual es el id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //esto para que se genere aleatorio
    private Long id;
    @Enumerated(EnumType.STRING)
    private UnidadMedida unidadMedida;
    private double cantidad;
    @ManyToOne
    private Receta receta;
    @ManyToOne
    private Ingrediente ingrediente;

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public double getCantidad() {
        return cantidad;
    }

    public Receta getReceta() {
        return receta;
    }

    public UnidadMedida getUnidadMedida() {
        return unidadMedida;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }

    public void setUnidadMedida(UnidadMedida unidadMedida) {
        this.unidadMedida = unidadMedida;
    }
}
