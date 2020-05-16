package es.uca.AutomaticFoodList.Services;

import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;

public class OptaPlannerServices {
    public class ListaComidaConstraintProvider implements ConstraintProvider {
        @Override
        public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
            return new Constraint[]{
                    //Hard
                    //Soft
            };
        }

        //poner casos de que no se pisen en fechas etc

    /*private Constraint conflictoIntolerancia(ConstraintFactory constraintFactory){
        return constraintFactory.fromUniquePair(ListaComida.class, Joiners.equal(ListaComida));
    }

    private Constraint conflictoValoresNutricionales(ConstraintFactory constraintFactory){

    }

    private Constraint conflictoPresupuesto(ConstraintFactory constraintFactory){
        return constraintFactory.fromUniquePair(ListaComida.class, )
    }

    private Constraint conflictoIngredienteNoGusta(ConstraintFactory constraintFactory){

    }

    private Constraint conflictoIngredienteGustaPoco(ConstraintFactory constraintFactory){

    }

    private Constraint premioIngredienteGustaMucho(ConstraintFactory constraintFactory){

    }

    private Constraint conflictoComidaNoAdecuada(ConstraintFactory constraintFactory){

    }

    private Constraint conflictoPlatoNoAdecuado(ConstraintFactory constraintFactory){

    }*/
    }
}
