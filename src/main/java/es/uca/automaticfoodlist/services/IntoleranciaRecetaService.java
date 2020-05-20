package es.uca.automaticfoodlist.services;

import es.uca.automaticfoodlist.entities.Intolerancia;
import es.uca.automaticfoodlist.entities.IntoleranciaReceta;
import es.uca.automaticfoodlist.entities.Receta;
import es.uca.automaticfoodlist.repositories.IntoleranciaRecetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IntoleranciaRecetaService {
    private IntoleranciaRecetaRepository intoleranciaRecetaRepository;

    @Autowired
    public IntoleranciaRecetaService(IntoleranciaRecetaRepository intoleranciaRecetaRepository) {
        this.intoleranciaRecetaRepository = intoleranciaRecetaRepository;
    }

    public IntoleranciaReceta create(IntoleranciaReceta intolerancia) {
        return intoleranciaRecetaRepository.save(intolerancia);
    }

    public IntoleranciaReceta update(IntoleranciaReceta intolerancia) {
        return intoleranciaRecetaRepository.save(intolerancia);
    }

    public Optional<IntoleranciaReceta> buscarId(Long id) {
        return intoleranciaRecetaRepository.findById(id);
    }

    public List<IntoleranciaReceta> buscarPorReceta(Receta receta) {
        return intoleranciaRecetaRepository.findByReceta(receta);
    }

    public List<IntoleranciaReceta> buscarIntolerancia(Intolerancia intolerancia) {
        return intoleranciaRecetaRepository.findByIntolerancia(intolerancia);
    }

    public List<IntoleranciaReceta> findAllOrderById() {
        return intoleranciaRecetaRepository.findByOrderByIdAsc();
    }

    public IntoleranciaReceta buscarPorRecetaEIntolerancia(Receta receta, Intolerancia intolerancia) {
        return intoleranciaRecetaRepository.findByRecetaAndIntolerancia(receta, intolerancia);
    }

    public List<IntoleranciaReceta> findAll() {
        return intoleranciaRecetaRepository.findAll();
    }

    public void delete(IntoleranciaReceta intolerancia) {
        intoleranciaRecetaRepository.delete(intolerancia);
    }
}
