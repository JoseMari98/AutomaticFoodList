package es.uca.AutomaticFoodList.Repositories;

import es.uca.AutomaticFoodList.Entities.Ingrediente;
import es.uca.AutomaticFoodList.Entities.PreferenciaIngrediente;
import es.uca.AutomaticFoodList.Entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferenciaIngredienteRepository extends JpaRepository<PreferenciaIngrediente, Long> {
    PreferenciaIngrediente findByUsuarioAndIngrediente(Usuario usuario, Ingrediente ingrediente);
}
