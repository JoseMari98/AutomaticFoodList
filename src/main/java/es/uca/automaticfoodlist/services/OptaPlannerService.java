package es.uca.automaticfoodlist.services;

import es.uca.automaticfoodlist.entities.*;
import org.optaplanner.core.api.score.ScoreManager;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;
import org.optaplanner.core.api.solver.SolverManager;
import org.optaplanner.core.api.solver.SolverStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OptaPlannerService {
    public static IntoleranciaUsuarioService intoleranciaUsuarioService;
    public static IntoleranciaRecetaService intoleranciaRecetaService;

    @Autowired
    private HorarioComidasService horarioComidasService;
    //@Autowired
    private SolverManager<HorarioComidas, Usuario> solverManager;
    //@Autowired
    private ScoreManager<HorarioComidas> scoreManager;

    public class HorarioComidasConstraintProvider implements ConstraintProvider {
        @Override
        public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
            return new Constraint[]{
                    //Hard
                    conflictoAlmuerzo(constraintFactory),
                    conflictoComidaRepetida(constraintFactory),
                    conflictoComidaFecha(constraintFactory),
                    conflictoIntolerancia(constraintFactory),
                    conflictoCalorias(constraintFactory),
                    conflictoHidratos(constraintFactory),
                    conflictoGrasa(constraintFactory),
                    conflictoPresupuesto(constraintFactory),
                    conflictoProteina(constraintFactory),
                    conflictoCena(constraintFactory),
                    conflictoDesayuno(constraintFactory),
                    //Soft
                    conflictoComidasRepetidas(constraintFactory)
            };
        }

        //Condicion para que no haya una comida y fecha iguales
        private Constraint conflictoComidaFecha(ConstraintFactory constraintFactory) {
            return constraintFactory.fromUniquePair(UsuarioReceta.class,
                    Joiners.equal(UsuarioReceta::getFecha),
                    Joiners.equal(UsuarioReceta::getComida))
                    .penalize("Conflicto fecha y comida", HardSoftScore.ONE_HARD);
        }

        //Condicion para que no haya una comida dos veces el mismo dia
        private Constraint conflictoComidaRepetida(ConstraintFactory constraintFactory) {
            return constraintFactory.fromUniquePair(UsuarioReceta.class,
                    Joiners.equal(UsuarioReceta::getFecha),
                    Joiners.equal(UsuarioReceta::getReceta))
                    .penalize("Comida repetida mismo dia", HardSoftScore.ONE_HARD);
        }

        //Condicion para que no se repitan mucho las recetas durante la semana
        private Constraint conflictoComidasRepetidas(ConstraintFactory constraintFactory) {
            return constraintFactory.fromUniquePair(UsuarioReceta.class,
                    Joiners.equal(UsuarioReceta::getReceta))
                    .filter((usuarioReceta1, usuarioReceta2) -> usuarioReceta1.getFecha() == usuarioReceta2.getFecha())
                    .penalize("Receta repetida distinto dia", HardSoftScore.ONE_SOFT);
        }

        //Comprobar que no se mete comidas con intolerancias en el usuario
        private Constraint conflictoIntolerancia(ConstraintFactory constraintFactory) {
            return constraintFactory.fromUnfiltered(UsuarioReceta.class)
                    .filter((usuarioReceta -> {
                        boolean valido = true;
                        for (IntoleranciaUsuario intoleranciaUsuario : intoleranciaUsuarioService.buscarPorUsuario(usuarioReceta.getUsuario())) {
                            for (IntoleranciaReceta intoleranciaReceta : intoleranciaRecetaService.buscarPorReceta(usuarioReceta.getReceta())) {
                                if (intoleranciaUsuario.getIntolerancia() == intoleranciaReceta.getIntolerancia()) {
                                    valido = false;
                                    break;
                                }
                            }
                        }

                        return !valido;
                    }))
                    .penalize("No cumple intolerancia", HardSoftScore.ONE_HARD);
        }

        private Constraint conflictoCalorias(ConstraintFactory constraintFactory) {
            return constraintFactory.fromUnfiltered(UsuarioReceta.class)
                    .filter((usuarioReceta -> {
                        String[] signos = usuarioReceta.getUsuario().getSignosValoresNutrcionales().split(",");
                        if (signos[0].equals("Menor"))
                            return usuarioReceta.getUsuario().getValoresNutricionales().getCaloriasPlato() < usuarioReceta.getReceta().getValoresNutricionales().getCaloriasPlato();
                        else
                            return usuarioReceta.getUsuario().getValoresNutricionales().getCaloriasPlato() >= usuarioReceta.getReceta().getValoresNutricionales().getCaloriasPlato();

                    }))
                    .penalize("No cumple calorias", HardSoftScore.ONE_HARD);
        }

        private Constraint conflictoGrasa(ConstraintFactory constraintFactory) {
            return constraintFactory.fromUnfiltered(UsuarioReceta.class)
                    .filter((usuarioReceta -> {
                        String[] signos = usuarioReceta.getUsuario().getSignosValoresNutrcionales().split(",");
                        if (signos[1].equals("Menor"))
                            return usuarioReceta.getUsuario().getValoresNutricionales().getGrasaPlato() < usuarioReceta.getReceta().getValoresNutricionales().getGrasaPlato();
                        else
                            return usuarioReceta.getUsuario().getValoresNutricionales().getGrasaPlato() >= usuarioReceta.getReceta().getValoresNutricionales().getGrasaPlato();

                    }))
                    .penalize("No cumple calorias", HardSoftScore.ONE_HARD);
        }

        private Constraint conflictoHidratos(ConstraintFactory constraintFactory) {
            return constraintFactory.fromUnfiltered(UsuarioReceta.class)
                    .filter((usuarioReceta -> {
                        String[] signos = usuarioReceta.getUsuario().getSignosValoresNutrcionales().split(",");
                        if (signos[2].equals("Menor"))
                            return usuarioReceta.getUsuario().getValoresNutricionales().getHidratosPlato() < usuarioReceta.getReceta().getValoresNutricionales().getHidratosPlato();
                        else
                            return usuarioReceta.getUsuario().getValoresNutricionales().getHidratosPlato() >= usuarioReceta.getReceta().getValoresNutricionales().getHidratosPlato();

                    }))
                    .penalize("No cumple calorias", HardSoftScore.ONE_HARD);
        }

        private Constraint conflictoProteina(ConstraintFactory constraintFactory) {
            return constraintFactory.fromUnfiltered(UsuarioReceta.class)
                    .filter((usuarioReceta -> {
                        String[] signos = usuarioReceta.getUsuario().getSignosValoresNutrcionales().split(",");
                        if (signos[3].equals("Menor"))
                            return usuarioReceta.getUsuario().getValoresNutricionales().getProteinaPlato() < usuarioReceta.getReceta().getValoresNutricionales().getProteinaPlato();
                        else
                            return usuarioReceta.getUsuario().getValoresNutricionales().getProteinaPlato() >= usuarioReceta.getReceta().getValoresNutricionales().getProteinaPlato();

                    }))
                    .penalize("No cumple calorias", HardSoftScore.ONE_HARD);
        }

        //Conflicto de presupuesto del usuario con el plato
        private Constraint conflictoPresupuesto(ConstraintFactory constraintFactory) {
            return constraintFactory.fromUnfiltered(UsuarioReceta.class)
                    .filter((usuarioReceta -> usuarioReceta.getUsuario().getPresupuestoPlato() < usuarioReceta.getReceta().getPrecioAproximado()))
                    .penalize("No cumple presupuesto", HardSoftScore.ONE_HARD);
        }

        /*
        private Constraint conflictoIngredienteNoGusta(ConstraintFactory constraintFactory){

        }

        private Constraint conflictoIngredienteGustaPoco(ConstraintFactory constraintFactory){

        }

        private Constraint premioIngredienteGustaMucho(ConstraintFactory constraintFactory){

        }*/

        private Constraint conflictoDesayuno(ConstraintFactory constraintFactory) {
            return constraintFactory.fromUnfiltered(UsuarioReceta.class)
                    .filter((usuarioReceta -> {
                        String[] booleanos = usuarioReceta.getReceta().getComidaAdecuada().split(",");
                        boolean valido = true;
                        if (booleanos[0].equals("false") && usuarioReceta.getComida() == Comida.Desayuno)
                            valido = false;
                        return !valido;
                    }))
                    .penalize("No adecuada para desayunar", HardSoftScore.ONE_HARD);
        }

        private Constraint conflictoAlmuerzo(ConstraintFactory constraintFactory) {
            return constraintFactory.fromUnfiltered(UsuarioReceta.class)
                    .filter((usuarioReceta -> {
                        String[] booleanos = usuarioReceta.getReceta().getComidaAdecuada().split(",");
                        boolean valido = true;
                        if (booleanos[1].equals("false") && usuarioReceta.getComida() == Comida.Almuerzo)
                            valido = false;
                        return !valido;
                    }))
                    .penalize("No adecuada para desayunar", HardSoftScore.ONE_HARD);
        }

        private Constraint conflictoCena(ConstraintFactory constraintFactory) {
            return constraintFactory.fromUnfiltered(UsuarioReceta.class)
                    .filter((usuarioReceta -> {
                        String[] booleanos = usuarioReceta.getReceta().getComidaAdecuada().split(",");
                        boolean valido = true;
                        if (booleanos[2].equals("false") && usuarioReceta.getComida() == Comida.Cena)
                            valido = false;
                        return !valido;
                    }))
                    .penalize("No adecuada para desayunar", HardSoftScore.ONE_HARD);
        }
    }


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
