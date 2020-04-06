package es.uca.AutomaticFoodList.Repositories;

import es.uca.AutomaticFoodList.Entities.Ingrediente;
import es.uca.AutomaticFoodList.Entities.Receta;
import es.uca.AutomaticFoodList.Entities.Usuario;
import es.uca.AutomaticFoodList.Entities.ValoresNutricionales;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecetaRepository extends JpaRepository<Receta, Long> {
    List<Receta> findByValoresNutricionales(ValoresNutricionales valoresNutricionales);
    Optional<Receta> findById(Long id);
    Optional<Receta> findByNombre(String nombre);
    List<Receta> findByNombreStartsWithIgnoreCase(String nombre);
    List<Receta> findByNombreStartsWithIgnoreCaseAndUsuario(String nombre, Usuario usuario);
    List<Receta> findByUsuario(Usuario usuario);
}
