package es.uca.AutomaticFoodList.Repositories;

import es.uca.AutomaticFoodList.Entities.ListaComida;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListaComidaRepository extends JpaRepository<ListaComida, Long> {
}
