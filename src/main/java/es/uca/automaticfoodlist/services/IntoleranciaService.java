package es.uca.automaticfoodlist.services;

import es.uca.automaticfoodlist.entities.Intolerancia;
import es.uca.automaticfoodlist.repositories.IntoleranciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
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

    public List<Intolerancia> findAllOrderById(){
        return intoleranciaRepository.findByOrderByIdAsc();
    }

    public void delete(Intolerancia intolerancia) {
        intoleranciaRepository.delete(intolerancia);
    }
}
