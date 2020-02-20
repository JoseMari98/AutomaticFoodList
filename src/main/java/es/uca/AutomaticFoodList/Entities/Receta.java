package es.uca.AutomaticFoodList.Entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Receta {
    @Id //esto sirve para decir cual es el id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //esto para que se genere aleatorio
    private Long id;
    @NotEmpty(message = "Este campo es obligatorio")
    @Column(unique = true)
    private String nombre = "", id_api = "";
    @OneToOne
    private ValoresNutricionales valoresNutricionales;
    private double precioAproximado;
    @OneToMany(mappedBy = "receta") //esto para decir la cardinalidad y a que variable se asocia
    private Set<RecetaIngrediente> recetaIngredientes = new HashSet<>();
}
