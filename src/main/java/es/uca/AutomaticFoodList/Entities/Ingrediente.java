package es.uca.AutomaticFoodList.Entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Ingrediente {
    @Id //esto sirve para decir cual es el id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //esto para que se genere aleatorio
    private Long id;
    @NotEmpty(message = "Este campo es obligatorio")
    @Column(unique = true)
    private String id_api = "", nombre = "";
    @OneToMany(mappedBy = "ingrediente") //esto para decir la cardinalidad y a que variable se asocia
    private Set<PreferenciasIngrediente> preferenciasIngredienteSet = new HashSet<>();
    @OneToMany(mappedBy = "ingrediente")
    private Set<RecetaIngrediente> recetaIngredientes = new HashSet<>();
    @OneToOne(mappedBy = "ingrediente", cascade=CascadeType.ALL)
    private Producto producto;
}
