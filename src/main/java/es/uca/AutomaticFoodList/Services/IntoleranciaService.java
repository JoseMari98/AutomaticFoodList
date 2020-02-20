package es.uca.AutomaticFoodList.Services;

import es.uca.AutomaticFoodList.Entities.Intolerancia;
import es.uca.AutomaticFoodList.Repositories.IntoleranciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

public class IntoleranciaService {
    private IntoleranciaRepository intoleranciaRepository;

    @Autowired
    public IntoleranciaService(IntoleranciaRepository intoleranciaRepository) {
        this.intoleranciaRepository = intoleranciaRepository;
    }

    public Intolerancia create(Intolerancia intolerancia) {
        return intoleranciaRepository.save(intolerancia);
    }

    public Intolerancia update(Intolerancia intolerancia) {
        return intoleranciaRepository.save(intolerancia);
    }

    public Optional<Intolerancia> buscarId(Long id) {
        return intoleranciaRepository.findById(id);
    }

    public List<Intolerancia> findAll() {
        return intoleranciaRepository.findAll();
    }

    public void delete(Intolerancia intolerancia) {
        intoleranciaRepository.delete(intolerancia);
    }
}
