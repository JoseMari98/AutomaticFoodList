package es.uca.AutomaticFoodList.Repositories;

import es.uca.AutomaticFoodList.Entities.ListaCompra;
import es.uca.AutomaticFoodList.Entities.Producto;
import es.uca.AutomaticFoodList.Entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListaCompraRepository extends JpaRepository<ListaCompra, Long> {
    List<ListaCompra> findByProducto_NombreStartsWithIgnoreCase(String producto);
    List<ListaCompra> findByUsuario(Usuario usuario);
}
