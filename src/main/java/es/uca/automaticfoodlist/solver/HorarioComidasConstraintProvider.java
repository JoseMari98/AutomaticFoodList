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
                //conflictoAlmuerzo(constraintFactory),
                conflictoComidaRepetida(constraintFactory),
                conflictoComidaFecha(constraintFactory),
                //conflictoIntolerancia(constraintFactory),
                //conflictoCalorias(constraintFactory),
                //conflictoHidratos(constraintFactory),
                //conflictoGrasa(constraintFactory),
                //conflictoPresupuesto(constraintFactory),
                //conflictoProteina(constraintFactory),
                //conflictoCena(constraintFactory),
                //conflictoDesayuno(constraintFactory),
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
                .penalize("Receta repetida distinto dia", HardSoftScore.ONE_SOFT);
    }

    //Comprobar que no se mete comidas con intolerancias en el usuario
    /*private Constraint conflictoIntolerancia(ConstraintFactory constraintFactory) {
        return constraintFactory.from(UsuarioReceta.class)
                .filter((usuarioReceta -> {
                    int contUsuario = 0;
                    int cont = 0;
                    if(intoleranciaUsuarioService.buscarPorUsuario(usuarioReceta.getUsuario()).size() != 0){
                        contUsuario = intoleranciaUsuarioService.buscarPorUsuario(usuarioReceta.getUsuario()).size();
                        for (IntoleranciaUsuario intoleranciaUsuario : intoleranciaUsuarioService.buscarPorUsuario(usuarioReceta.getUsuario())) {
                            for (IntoleranciaReceta intoleranciaReceta : intoleranciaRecetaService.buscarPorReceta(usuarioReceta.getReceta())) {
                                if (intoleranciaUsuario.getIntolerancia() == intoleranciaReceta.getIntolerancia()) {
                                    cont++;
                                }
                            }
                        }
                    }
                    return (contUsuario > cont);
                }))
                .penalize("No cumple intolerancia", HardSoftScore.ONE_HARD);
    }*/

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
                .penalize("No cumple grasa", HardSoftScore.ONE_HARD);
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
                .penalize("No cumple hidratos", HardSoftScore.ONE_HARD);
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
                .penalize("No cumple proteina", HardSoftScore.ONE_HARD);
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
}
