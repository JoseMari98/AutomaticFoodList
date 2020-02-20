package es.uca.AutomaticFoodList.Entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
public class RecetaIngrediente {
    @Id //esto sirve para decir cual es el id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //esto para que se genere aleatorio
    private Long id;
    @NotEmpty(message = "Este campo es obligatorio")
    @Enumerated(EnumType.STRING)
    private UnidadMedida unidadMedida;
    private double cantidad;
    @ManyToOne
    private Receta receta;
    @ManyToOne
    private Ingrediente ingrediente;
}
