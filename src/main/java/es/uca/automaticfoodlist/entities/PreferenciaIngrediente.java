package es.uca.automaticfoodlist.entities;

import javax.persistence.*;

@Entity
public class PreferenciaIngrediente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Usuario usuario;
    @ManyToOne
    private Ingrediente ingrediente;
    @Enumerated(EnumType.STRING)
    private Gusto gusto;

    public Long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Gusto getGusto() {
        return gusto;
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setGusto(Gusto gusto) {
        this.gusto = gusto;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }
}
