package es.uca.AutomaticFoodList.Repositories;

import es.uca.AutomaticFoodList.Entities.ValoresNutricionales;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ValoresNutricionalesRepository extends JpaRepository<ValoresNutricionales, Long> {
    List<ValoresNutricionales> findByReceta_NombreStartsWithIgnoreCase(String receta);
}
