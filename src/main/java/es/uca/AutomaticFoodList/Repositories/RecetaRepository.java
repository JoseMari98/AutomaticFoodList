package es.uca.AutomaticFoodList.Repositories;

import es.uca.AutomaticFoodList.Entities.Receta;
import es.uca.AutomaticFoodList.Entities.ValoresNutricionales;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecetaRepository extends JpaRepository<Receta, Long> {
    List<Receta> findByValoresNutricionales(ValoresNutricionales valoresNutricionales);
    Optional<Receta> findById(Long id);
    Optional<Receta> findByNombre(String nombre);
}
