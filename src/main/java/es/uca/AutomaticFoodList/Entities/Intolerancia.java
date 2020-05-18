package es.uca.AutomaticFoodList.Entities;

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
