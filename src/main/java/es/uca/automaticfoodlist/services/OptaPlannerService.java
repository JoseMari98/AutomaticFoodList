package es.uca.automaticfoodlist.services;

import es.uca.automaticfoodlist.entities.HorarioComidas;
import es.uca.automaticfoodlist.entities.Usuario;
import org.optaplanner.core.api.score.ScoreManager;
import org.optaplanner.core.api.solver.SolverManager;
import org.optaplanner.core.api.solver.SolverStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OptaPlannerService {

    @Autowired
    private HorarioComidasService horarioComidasService;
    @Autowired
    private SolverManager<HorarioComidas, Usuario> solverManager;
    @Autowired
    private ScoreManager<HorarioComidas> scoreManager;



    public HorarioComidas getTimeTable(Usuario usuario) {
        // Get the solver status before loading the solution
        // to avoid the race condition that the solver terminates between them
        SolverStatus solverStatus = getSolverStatus(usuario);
        HorarioComidas solution = horarioComidasService.findByUsuario(usuario);
        scoreManager.updateScore(solution); // Sets the score
        solution.setSolverStatus(solverStatus);
        return solution;
    }

    public void solve(Usuario usuario) {
        horarioComidasService.usuario = usuario;
        solverManager.solveAndListen(usuario, horarioComidasService::findByUsuario, horarioComidasService::save);
    }

    public SolverStatus getSolverStatus(Usuario usuario) {
        return solverManager.getSolverStatus(usuario);
    }

    public void stopSolving(Usuario usuario) {
        solverManager.terminateEarly(usuario);
    }
}
