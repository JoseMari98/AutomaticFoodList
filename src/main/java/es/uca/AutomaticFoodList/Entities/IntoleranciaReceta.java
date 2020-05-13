package es.uca.AutomaticFoodList.Entities;

import javax.persistence.*;

@Entity
public class IntoleranciaReceta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //esto para que se genere aleatorio
    private Long id;
    @ManyToOne
    private Receta receta;
    @ManyToOne
    private Intolerancia intolerancia;

    public Intolerancia getIntolerancia() {
        return intolerancia;
    }

    public Long getId() {
        return id;
    }

    public Receta getReceta() {
        return receta;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIntolerancia(Intolerancia intolerancia) {
        this.intolerancia = intolerancia;
    }
}
