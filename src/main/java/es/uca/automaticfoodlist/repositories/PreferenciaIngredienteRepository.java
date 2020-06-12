package es.uca.automaticfoodlist.repositories;

import es.uca.automaticfoodlist.entities.Ingrediente;
import es.uca.automaticfoodlist.entities.PreferenciaIngrediente;
import es.uca.automaticfoodlist.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PreferenciaIngredienteRepository extends JpaRepository<PreferenciaIngrediente, Long> {
    PreferenciaIngrediente findByUsuarioAndIngrediente(Usuario usuario, Ingrediente ingrediente);
    List<PreferenciaIngrediente> findByIngrediente(Ingrediente ingrediente);

    List<PreferenciaIngrediente> findByUsuario(Usuario usuario);
}
