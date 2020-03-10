package es.uca.AutomaticFoodList.Repositories;

import es.uca.AutomaticFoodList.Entities.Ingrediente;
import es.uca.AutomaticFoodList.Entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Producto findByIngrediente(Ingrediente ingrediente);
    List<Producto> findByNombreStartsWithIgnoreCase(String nombre);
}
