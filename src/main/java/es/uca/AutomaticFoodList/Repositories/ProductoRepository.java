package es.uca.AutomaticFoodList.Repositories;

import es.uca.AutomaticFoodList.Entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
