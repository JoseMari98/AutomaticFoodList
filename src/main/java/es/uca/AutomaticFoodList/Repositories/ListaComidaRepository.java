package es.uca.AutomaticFoodList.Repositories;

import es.uca.AutomaticFoodList.Entities.Comida;
import es.uca.AutomaticFoodList.Entities.ListaComida;
import es.uca.AutomaticFoodList.Entities.Plato;
import es.uca.AutomaticFoodList.Entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ListaComidaRepository extends JpaRepository<ListaComida, Long> {
    List<ListaComida> findByUsuario(Usuario usuario);
    Optional<ListaComida> findByUsuarioAndComidaAndFechaAndPlato(Usuario usuario, Comida comida, LocalDate fecha, Plato plato);
    List<ListaComida> findByUsuarioAndComida(Usuario usuario, Comida comida);

}
