package es.uca.automaticfoodlist.repositories;

import es.uca.automaticfoodlist.entities.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {
    List<Ingrediente> findByNombreStartsWithIgnoreCase(String ingrediente);

    Ingrediente findByNombre(String ingrediente);

    Ingrediente findByIdApi(String idApi);
}