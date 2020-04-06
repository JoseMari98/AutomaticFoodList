package es.uca.AutomaticFoodList.Repositories;

import es.uca.AutomaticFoodList.Entities.Receta;
import es.uca.AutomaticFoodList.Entities.ValoresNutricionales;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ValoresNutricionalesRepository extends JpaRepository<ValoresNutricionales, Long> {
    List<ValoresNutricionales> findByReceta_NombreStartsWithIgnoreCase(String receta);
    Optional<ValoresNutricionales> findByReceta(Receta receta);
}
