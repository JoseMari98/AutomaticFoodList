package es.uca.automaticfoodlist.solver;

import es.uca.automaticfoodlist.entities.UsuarioReceta;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;

public class HorarioComidasConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
                //Hard
                conflictoComidaRepetida(constraintFactory),
                conflictoComidaFecha(constraintFactory),
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
                .penalize("Receta repetida distinto dia", HardSoftScore.ONE_SOFT);
    }
}
