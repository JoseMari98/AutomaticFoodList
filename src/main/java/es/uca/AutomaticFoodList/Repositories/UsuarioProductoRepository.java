package es.uca.AutomaticFoodList.Repositories;

import es.uca.AutomaticFoodList.Entities.UsuarioProducto;
import es.uca.AutomaticFoodList.Entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioProductoRepository extends JpaRepository<UsuarioProducto, Long> {
    List<UsuarioProducto> findByProducto_NombreStartsWithIgnoreCase(String producto);
    List<UsuarioProducto> findByUsuario(Usuario usuario);
}
