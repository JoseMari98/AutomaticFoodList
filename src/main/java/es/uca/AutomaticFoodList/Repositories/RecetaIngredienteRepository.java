package es.uca.AutomaticFoodList.Repositories;

import es.uca.AutomaticFoodList.Entities.Ingrediente;
import es.uca.AutomaticFoodList.Entities.Receta;
import es.uca.AutomaticFoodList.Entities.RecetaIngrediente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecetaIngredienteRepository extends JpaRepository<RecetaIngrediente, Long> {
    List<RecetaIngrediente> findByReceta(Receta receta);
    List<RecetaIngrediente> findByIngrediente(Ingrediente ingrediente);
    List<RecetaIngrediente> findByIngrediente_NombreStartsWithIgnoreCase(String ingrediente);
}