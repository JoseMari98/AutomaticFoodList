package es.uca.automaticfoodlist.entities;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.SolverStatus;

import java.util.List;

@PlanningSolution
public class HorarioComidas {
    @ProblemFactCollectionProperty
    private List<Receta> recetaList;

    @PlanningEntityCollectionProperty
    private List<UsuarioReceta> usuarioRecetas;

    @PlanningScore
    private HardSoftScore score;

    // Ignored by OptaPlanner, used by the UI to display solve or stop solving button
    private SolverStatus solverStatus;

    private HorarioComidas() {
    }

    public HorarioComidas(List<Receta> recetaList,
                          List<UsuarioReceta> usuarioRecetas) {
        this.usuarioRecetas = usuarioRecetas;
        this.recetaList = recetaList;
    }


    public List<UsuarioReceta> getUsuarioRecetas() {
        return usuarioRecetas;
    }

    public List<Receta> getRecetaList() {
        return recetaList;
    }


    public HardSoftScore getScore() {
        return score;
    }

    public SolverStatus getSolverStatus() {
        return solverStatus;
    }

    public void setSolverStatus(SolverStatus solverStatus) {
        this.solverStatus = solverStatus;
    }

}
