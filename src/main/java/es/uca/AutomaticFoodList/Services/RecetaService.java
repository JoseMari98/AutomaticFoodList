package es.uca.AutomaticFoodList.Services;

import es.uca.AutomaticFoodList.Entities.Receta;
import es.uca.AutomaticFoodList.Repositories.RecetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecetaService {
    private RecetaRepository recetaRepository;

    @Autowired
    public RecetaService(RecetaRepository recetaRepository) {
        this.recetaRepository = recetaRepository;
    }

    public Receta create(Receta receta) {
        return recetaRepository.save(receta);
    }

    public Receta update(Receta receta) {
        return recetaRepository.save(receta);
    }

    public Optional<Receta> buscarId(Long id) {
        return recetaRepository.findById(id);
    }

    public List<Receta> findAll() {
        return recetaRepository.findAll();
    }

    public void delete(Receta receta) {
        recetaRepository.delete(receta);
    }
}