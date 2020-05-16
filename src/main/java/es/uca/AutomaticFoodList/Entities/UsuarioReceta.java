package es.uca.AutomaticFoodList.Entities;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningEntityProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.SolverStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@PlanningEntity
@Entity
public class UsuarioReceta {
    @PlanningId
    @Id //esto sirve para decir cual es el id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //esto para que se genere aleatorio
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
    @Column(nullable = false)
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
