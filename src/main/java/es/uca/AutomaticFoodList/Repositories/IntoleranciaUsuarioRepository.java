package es.uca.AutomaticFoodList.Repositories;

import es.uca.AutomaticFoodList.Entities.IntoleranciaUsuario;
import es.uca.AutomaticFoodList.Entities.Intolerancia;
import es.uca.AutomaticFoodList.Entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IntoleranciaUsuarioRepository extends JpaRepository<IntoleranciaUsuario, Long> {
    List<IntoleranciaUsuario> findByUsuario(Usuario usuario);
    IntoleranciaUsuario findByUsuarioAndIntolerancia(Usuario usuario, Intolerancia intolerancia);
    List<IntoleranciaUsuario> findByOrderByIdAsc();
}