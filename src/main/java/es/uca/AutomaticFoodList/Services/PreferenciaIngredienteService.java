package es.uca.AutomaticFoodList.Services;

import es.uca.AutomaticFoodList.Entities.Ingrediente;
import es.uca.AutomaticFoodList.Entities.PreferenciaIngrediente;
import es.uca.AutomaticFoodList.Entities.Usuario;
import es.uca.AutomaticFoodList.Repositories.PreferenciaIngredienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PreferenciaIngredienteService {
    private PreferenciaIngredienteRepository preferenciaIngredienteRepository;

    @Autowired
    public PreferenciaIngredienteService(PreferenciaIngredienteRepository preferenciaIngredienteRepository) {
        this.preferenciaIngredienteRepository = preferenciaIngredienteRepository;
    }

    public PreferenciaIngrediente create(PreferenciaIngrediente preferenciaIngrediente) {
        return preferenciaIngredienteRepository.save(preferenciaIngrediente);
    }

    public PreferenciaIngrediente update(PreferenciaIngrediente preferenciaIngrediente) {
        return preferenciaIngredienteRepository.save(preferenciaIngrediente);
    }

    public PreferenciaIngrediente findByUsuarioAndIngrediente(Usuario usuario, Ingrediente ingrediente){
        return preferenciaIngredienteRepository.findByUsuarioAndIngrediente(usuario, ingrediente);
    }

    public List<PreferenciaIngrediente> findByIngrediente(Ingrediente ingrediente){
        return preferenciaIngredienteRepository.findByIngrediente(ingrediente);
    }

    public Optional<PreferenciaIngrediente> buscarId(Long id) {
        return preferenciaIngredienteRepository.findById(id);
    }

    public List<PreferenciaIngrediente> findAll() {
        return preferenciaIngredienteRepository.findAll();
    }

    public void delete(PreferenciaIngrediente preferenciaIngrediente) {
        preferenciaIngredienteRepository.delete(preferenciaIngrediente);
    }
}
