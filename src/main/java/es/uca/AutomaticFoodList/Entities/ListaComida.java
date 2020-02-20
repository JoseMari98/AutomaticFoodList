package es.uca.AutomaticFoodList.Entities;

import javax.persistence.*;

@Entity
public class ListaComida {
    @Id //esto sirve para decir cual es el id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //esto para que se genere aleatorio
    private Long id;
    @Enumerated(EnumType.STRING)
    private Comida comida;
    @Enumerated(EnumType.STRING)
    private Plato plato;
    @ManyToOne
    private Receta receta;
    @ManyToOne
    private Usuario usuario;
}
