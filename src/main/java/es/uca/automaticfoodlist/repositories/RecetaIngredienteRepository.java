package es.uca.automaticfoodlist.repositories;

import es.uca.automaticfoodlist.entities.Ingrediente;
import es.uca.automaticfoodlist.entities.Receta;
import es.uca.automaticfoodlist.entities.RecetaIngrediente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecetaIngredienteRepository extends JpaRepository<RecetaIngrediente, Long> {
    List<RecetaIngrediente> findByReceta(Receta receta);
    List<RecetaIngrediente> findByIngrediente(Ingrediente ingrediente);
    List<RecetaIngrediente> findByIngrediente_NombreStartsWithIgnoreCase(String ingrediente);
}