package es.uca.automaticfoodlist.repositories;

import es.uca.automaticfoodlist.entities.ValoresNutricionales;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ValoresNutricionalesRepository extends JpaRepository<ValoresNutricionales, Long> {
    List<ValoresNutricionales> findByReceta_NombreStartsWithIgnoreCase(String receta);

    Optional<ValoresNutricionales> findByReceta_Id(Long id);
}
