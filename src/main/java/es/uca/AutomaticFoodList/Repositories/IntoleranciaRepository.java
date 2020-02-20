package es.uca.AutomaticFoodList.Repositories;

import es.uca.AutomaticFoodList.Entities.Intolerancia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IntoleranciaRepository extends JpaRepository<Intolerancia, Long> {
}