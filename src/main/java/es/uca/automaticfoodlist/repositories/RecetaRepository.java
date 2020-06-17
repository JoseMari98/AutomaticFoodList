package es.uca.automaticfoodlist.repositories;

import es.uca.automaticfoodlist.entities.Receta;
import es.uca.automaticfoodlist.entities.Usuario;
import es.uca.automaticfoodlist.entities.ValoresNutricionales;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecetaRepository extends JpaRepository<Receta, Long> {
    List<Receta> findByValoresNutricionales(ValoresNutricionales valoresNutricionales);

    List<Receta> findAllByOrderByNombreAsc();

    Optional<Receta> findById(Long id);
    Optional<Receta> findByNombre(String nombre);
    List<Receta> findByNombreStartsWithIgnoreCase(String nombre);
    List<Receta> findByNombreStartsWithIgnoreCaseAndUsuario(String nombre, Usuario usuario);
    List<Receta> findByUsuario(Usuario usuario);
}
