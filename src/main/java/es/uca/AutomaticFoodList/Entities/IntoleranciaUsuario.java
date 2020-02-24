package es.uca.AutomaticFoodList.Entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class IntoleranciaUsuario {
     //esto sirve para decir cual es el id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //esto para que se genere aleatorio
    private Long id;
    @ManyToOne
    private Usuario usuario;
    @ManyToOne
    private Intolerancia intolerancia;

    public Intolerancia getIntolerancia() {
        return intolerancia;
    }

    public Long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIntolerancia(Intolerancia intolerancia) {
        this.intolerancia = intolerancia;
    }

}
