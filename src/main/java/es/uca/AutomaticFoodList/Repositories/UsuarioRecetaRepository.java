package es.uca.AutomaticFoodList.Repositories;

import es.uca.AutomaticFoodList.Entities.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRecetaRepository extends JpaRepository<UsuarioReceta, Long> {
    List<UsuarioReceta> findByUsuario(Usuario usuario);

    Optional<UsuarioReceta> findByUsuarioAndComidaAndFechaSemanaAndPlato(Usuario usuario, Comida comida, FechaSemana fechaSemana, Plato plato);

    List<UsuarioReceta> findByUsuarioAndComida(Usuario usuario, Comida comida);

    List<UsuarioReceta> findByUsuarioAndComidaAndPlato(Usuario usuario, Comida comida, Plato plato);

    List<UsuarioReceta> findByReceta(Receta receta);
}
