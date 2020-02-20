package es.uca.AutomaticFoodList.Repositories;

import es.uca.AutomaticFoodList.Entities.RecetaIngrediente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecetaIngredienteRepository extends JpaRepository<RecetaIngrediente, Long> {
}