package es.uca.AutomaticFoodList.Entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class PreferenciasIngrediente {
    @Id //esto sirve para decir cual es el id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //esto para que se genere aleatorio
    private Long id;
    @ManyToOne
    private Usuario usuario;
    @ManyToOne
    private Ingrediente ingrediente;
    @NotEmpty(message = "Este campo es obligatorio")
    private Gusto gusto;
}
