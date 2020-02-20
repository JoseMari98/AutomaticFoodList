package es.uca.AutomaticFoodList.Repositories;

import es.uca.AutomaticFoodList.Entities.ListaCompra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListaCompraRepository extends JpaRepository<ListaCompra, Long> {
}
