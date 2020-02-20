package es.uca.AutomaticFoodList.Entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ValoresNutricionales {
    @Id //esto sirve para decir cual es el id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //esto para que se genere aleatorio
    private Long id;
    private double caloriasPlato, grasaPlato, hidratosPlato, proteinaPlato;
    @OneToOne(mappedBy = "valoresNutricionales", cascade=CascadeType.ALL)
    private Usuario usuario;
    @OneToOne(mappedBy = "valoresNutricionales", cascade=CascadeType.ALL)
    private Receta receta;
}
