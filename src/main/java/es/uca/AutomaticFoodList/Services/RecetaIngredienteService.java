package es.uca.AutomaticFoodList.Services;

import es.uca.AutomaticFoodList.Entities.RecetaIngrediente;
import es.uca.AutomaticFoodList.Repositories.RecetaIngredienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecetaIngredienteService {
    private RecetaIngredienteRepository recetaIngredienteRepository;

    @Autowired
    public RecetaIngredienteService(RecetaIngredienteRepository recetaIngredienteRepository) {
        this.recetaIngredienteRepository = recetaIngredienteRepository;
    }

    public RecetaIngrediente create(RecetaIngrediente recetaIngrediente) {
        return recetaIngredienteRepository.save(recetaIngrediente);
    }

    public RecetaIngrediente update(RecetaIngrediente recetaIngrediente) {
        return recetaIngredienteRepository.save(recetaIngrediente);
    }

    public Optional<RecetaIngrediente> buscarId(Long id) {
        return recetaIngredienteRepository.findById(id);
    }

    public List<RecetaIngrediente> findAll() {
        return recetaIngredienteRepository.findAll();
    }

    public void delete(RecetaIngrediente recetaIngrediente) {
        recetaIngredienteRepository.delete(recetaIngrediente);
    }
}