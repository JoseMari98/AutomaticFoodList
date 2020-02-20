package es.uca.AutomaticFoodList.Services;

import es.uca.AutomaticFoodList.Entities.Ingrediente;
import es.uca.AutomaticFoodList.Repositories.IngredienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

public class IngredienteService {
    private IngredienteRepository ingredienteRepository;

    @Autowired
    public IngredienteService(IngredienteRepository ingredienteRepository) {
        this.ingredienteRepository = ingredienteRepository;
    }

    public Ingrediente create(Ingrediente ingrediente) {
        return ingredienteRepository.save(ingrediente);
    }

    public Ingrediente update(Ingrediente ingrediente) {
        return ingredienteRepository.save(ingrediente);
    }
    public Optional<Ingrediente> buscarId(Long id) {
        return ingredienteRepository.findById(id);
    }

    public List<Ingrediente> findAll() {
        return ingredienteRepository.findAll();
    }

    public void delete(Ingrediente ingrediente) {
        ingredienteRepository.delete(ingrediente);
    }
}
