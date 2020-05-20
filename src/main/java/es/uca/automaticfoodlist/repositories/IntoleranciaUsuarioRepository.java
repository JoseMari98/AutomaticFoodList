package es.uca.automaticfoodlist.repositories;

import es.uca.automaticfoodlist.entities.Intolerancia;
import es.uca.automaticfoodlist.entities.IntoleranciaUsuario;
import es.uca.automaticfoodlist.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IntoleranciaUsuarioRepository extends JpaRepository<IntoleranciaUsuario, Long> {
    List<IntoleranciaUsuario> findByUsuario(Usuario usuario);
    IntoleranciaUsuario findByUsuarioAndIntolerancia(Usuario usuario, Intolerancia intolerancia);
    List<IntoleranciaUsuario> findByOrderByIdAsc();

    List<IntoleranciaUsuario> findByIntolerancia(Intolerancia intolerancia);
}