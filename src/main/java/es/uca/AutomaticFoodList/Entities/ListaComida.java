package es.uca.AutomaticFoodList.Entities;

import javax.persistence.*;

@Entity
public class ListaComida {
    @Id //esto sirve para decir cual es el id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //esto para que se genere aleatorio
    private Long id;
    @Enumerated(EnumType.STRING)
    private Comida comida;
    @Enumerated(EnumType.STRING)
    private Plato plato;
    @ManyToOne
    private Receta receta;
    @ManyToOne
    private Usuario usuario;

    public Long getId() {
        return id;
    }

    public Comida getComida() {
        return comida;
    }

    public Plato getPlato() {
        return plato;
    }

    public Receta getReceta() {
        return receta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setComida(Comida comida) {
        this.comida = comida;
    }

    public void setPlato(Plato plato) {
        this.plato = plato;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
