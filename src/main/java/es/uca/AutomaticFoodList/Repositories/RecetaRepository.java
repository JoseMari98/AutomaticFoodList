package es.uca.AutomaticFoodList.Repositories;

import es.uca.AutomaticFoodList.Entities.Receta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecetaRepository extends JpaRepository<Receta, Long> {
    Optional<Receta> findById(Long id);
}
