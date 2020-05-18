package es.uca.AutomaticFoodList.Entities;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import javax.persistence.*;

@PlanningEntity
@Entity
public class UsuarioReceta {
    @PlanningId
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @PlanningVariable
    @Enumerated(EnumType.STRING)
    private Comida comida;
    @PlanningVariable
    @Enumerated(EnumType.STRING)
    private Plato plato;
    @PlanningVariable
    @ManyToOne
    private Receta receta;
    @ManyToOne
    private Usuario usuario;
    @PlanningVariable
    @Enumerated(EnumType.STRING)
    private FechaSemana fechaSemana;

    public Long getId() {
        return id;
    }

    public Comida getComida() {
        return comida;
    }

    public FechaSemana getFecha() {
        return fechaSemana;
    }

    public Plato getPlato() {
        return plato;
    }

    public Receta getReceta() {
        return receta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setComida(Comida comida) {
        this.comida = comida;
    }

    public void setFecha(FechaSemana fechaSemana) {
        this.fechaSemana = fechaSemana;
    }

    public void setPlato(Plato plato) {
        this.plato = plato;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
