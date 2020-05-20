package es.uca.automaticfoodlist.repositories;

import es.uca.automaticfoodlist.entities.Intolerancia;
import es.uca.automaticfoodlist.entities.IntoleranciaReceta;
import es.uca.automaticfoodlist.entities.Receta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IntoleranciaRecetaRepository extends JpaRepository<IntoleranciaReceta, Long> {
    List<IntoleranciaReceta> findByReceta(Receta receta);

    IntoleranciaReceta findByRecetaAndIntolerancia(Receta receta, Intolerancia intolerancia);

    List<IntoleranciaReceta> findByOrderByIdAsc();

    List<IntoleranciaReceta> findByIntolerancia(Intolerancia intolerancia);
}
