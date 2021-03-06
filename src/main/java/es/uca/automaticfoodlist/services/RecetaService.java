package es.uca.automaticfoodlist.services;

import es.uca.automaticfoodlist.entities.Receta;
import es.uca.automaticfoodlist.entities.Usuario;
import es.uca.automaticfoodlist.entities.ValoresNutricionales;
import es.uca.automaticfoodlist.repositories.RecetaRepository;
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

    public List<Receta> findByValores(ValoresNutricionales valoresNutricionales){
        return recetaRepository.findByValoresNutricionales(valoresNutricionales);
    }

    public List<Receta> findByNombreList(String nombre){
        return recetaRepository.findByNombreStartsWithIgnoreCase(nombre);
    }

    public List<Receta> findByNombreAndUsuario(String nombre, Usuario usuario){
        return recetaRepository.findByNombreStartsWithIgnoreCaseAndUsuario(nombre, usuario);
    }

    public List<Receta> findByUsuario(Usuario usuario){
        return recetaRepository.findByUsuario(usuario);
    }

    public Optional<Receta> findByNombre(String nombre){
        return recetaRepository.findByNombre(nombre);
    }

    public List<Receta> findAll() {
        return recetaRepository.findAllByOrderByNombreAsc();
    }

    public Optional<Receta> findById(Receta receta){
        return recetaRepository.findById(receta.getId());
    }

    public void delete(Receta receta) {
        recetaRepository.delete(receta);
    }
}