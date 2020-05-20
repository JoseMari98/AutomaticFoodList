package es.uca.automaticfoodlist.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
public class Intolerancia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Este campo es obligatorio")
    @Column(unique = true)
    private String intolerancia = "";
    @OneToMany(mappedBy = "intolerancia")
    private Set<IntoleranciaUsuario> intoleranciaUsuarioSet;
    @OneToMany(mappedBy = "intolerancia")
    private Set<IntoleranciaReceta> intoleranciaRecetaSet;

    public void setIntoleranciaRecetaSet(Set<IntoleranciaReceta> intoleranciaRecetaSet) {
        this.intoleranciaRecetaSet = intoleranciaRecetaSet;
    }

    public Set<IntoleranciaReceta> getIntoleranciaRecetaSet() {
        return intoleranciaRecetaSet;
    }

    public Set<IntoleranciaUsuario> getIntoleranciaUsuarioSet() {
        return intoleranciaUsuarioSet;
    }

    public void setIntoleranciaUsuarioSet(Set<IntoleranciaUsuario> intoleranciaUsuarioSet) {
        this.intoleranciaUsuarioSet = intoleranciaUsuarioSet;
    }

    public void setIntolerancia(String intolerancia) {
        this.intolerancia = intolerancia;
    }

    public Long getId() {
        return id;
    }

    public String getIntolerancia() {
        return intolerancia;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
