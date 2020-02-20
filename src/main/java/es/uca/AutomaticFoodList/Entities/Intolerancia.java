package es.uca.AutomaticFoodList.Entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Intolerancia {
    @Id //esto sirve para decir cual es el id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //esto para que se genere aleatorio
    private Long id;
    @NotEmpty(message = "Este campo es obligatorio")
    @Column(unique = true)
    private String nombre = "";
    @ManyToMany(mappedBy = "intolerancias")
    private Set<Usuario> usuarios = new HashSet<>();

    public String getNombre() {
        return nombre;
    }

    public Long getId() {
        return id;
    }

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
