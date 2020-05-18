package es.uca.automaticfoodlist.services;

import es.uca.automaticfoodlist.entities.Ingrediente;
import es.uca.automaticfoodlist.entities.Receta;
import es.uca.automaticfoodlist.entities.RecetaIngrediente;
import es.uca.automaticfoodlist.repositories.RecetaIngredienteRepository;
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

    public List<RecetaIngrediente> findByReceta(Receta receta){
        return recetaIngredienteRepository.findByReceta(receta);
    }

    public List<RecetaIngrediente> findByIngredienteNombre(String ingrediente){
        return recetaIngredienteRepository.findByIngrediente_NombreStartsWithIgnoreCase(ingrediente);
    }

    public List<RecetaIngrediente> findByIngrediente(Ingrediente ingrediente){
        return recetaIngredienteRepository.findByIngrediente(ingrediente);
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