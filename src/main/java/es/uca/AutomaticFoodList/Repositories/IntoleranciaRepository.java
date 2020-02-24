package es.uca.AutomaticFoodList.Repositories;

import es.uca.AutomaticFoodList.Entities.Intolerancia;
import es.uca.AutomaticFoodList.Entities.IntoleranciaUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IntoleranciaRepository extends JpaRepository<Intolerancia, Long> {
    List<Intolerancia> findByOrderByIdAsc();

}
