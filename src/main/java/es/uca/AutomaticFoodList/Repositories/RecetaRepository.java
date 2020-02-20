package es.uca.AutomaticFoodList.Repositories;

import es.uca.AutomaticFoodList.Entities.Receta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecetaRepository extends JpaRepository<Receta, Long> {
}
