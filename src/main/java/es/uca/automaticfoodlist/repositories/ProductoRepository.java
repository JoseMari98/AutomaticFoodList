package es.uca.automaticfoodlist.repositories;

import es.uca.automaticfoodlist.entities.Ingrediente;
import es.uca.automaticfoodlist.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Producto findByIngrediente(Ingrediente ingrediente);

    List<Producto> findByNombreStartsWithIgnoreCase(String nombre);

    Producto findByNombre(String nombre);
}
