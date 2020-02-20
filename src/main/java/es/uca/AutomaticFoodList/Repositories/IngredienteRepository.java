package es.uca.AutomaticFoodList.Repositories;

import es.uca.AutomaticFoodList.Entities.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {
}