package es.uca.automaticfoodlist.repositories;

import es.uca.automaticfoodlist.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRecetaRepository extends JpaRepository<UsuarioReceta, Long> {
    List<UsuarioReceta> findByUsuario(Usuario usuario);

    Optional<UsuarioReceta> findByUsuarioAndComidaAndFechaSemana(Usuario usuario, Comida comida, FechaSemana fechaSemana);

    List<UsuarioReceta> findByUsuarioAndComida(Usuario usuario, Comida comida);

    List<UsuarioReceta> findByReceta(Receta receta);
}
