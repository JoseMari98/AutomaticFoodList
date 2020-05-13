package es.uca.AutomaticFoodList.Entities;

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
    private List<Usuario> usuarioList;

    @ProblemFactCollectionProperty
    private List<Receta> recetaList;

    @PlanningEntityCollectionProperty
    private List<ListaComida> listaComidas;

    @PlanningScore
    private HardSoftScore score;

    // Ignored by OptaPlanner, used by the UI to display solve or stop solving button
    private SolverStatus solverStatus;

    private HorarioComidas() {
    }

    public HorarioComidas(List<Usuario> usuarioList, List<Receta> recetaList,
                     List<ListaComida> listaComidas) {
        this.listaComidas = listaComidas;
        this.recetaList = recetaList;
        this.usuarioList = usuarioList;
    }


    public List<ListaComida> getListaComidas() {
        return listaComidas;
    }

    public List<Receta> getRecetaList() {
        return recetaList;
    }

    public List<Usuario> getUsuarioList() {
        return usuarioList;
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
