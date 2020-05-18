package es.uca.automaticfoodlist.repositories;

import es.uca.automaticfoodlist.entities.Intolerancia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IntoleranciaRepository extends JpaRepository<Intolerancia, Long> {
    List<Intolerancia> findByOrderByIdAsc();

}
