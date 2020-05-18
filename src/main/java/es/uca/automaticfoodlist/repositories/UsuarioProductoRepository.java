package es.uca.automaticfoodlist.repositories;

import es.uca.automaticfoodlist.entities.Usuario;
import es.uca.automaticfoodlist.entities.UsuarioProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioProductoRepository extends JpaRepository<UsuarioProducto, Long> {
    List<UsuarioProducto> findByProducto_NombreStartsWithIgnoreCase(String producto);
    List<UsuarioProducto> findByUsuario(Usuario usuario);
}
