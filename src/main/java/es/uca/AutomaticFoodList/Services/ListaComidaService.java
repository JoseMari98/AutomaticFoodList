package es.uca.AutomaticFoodList.Services;

import es.uca.AutomaticFoodList.Entities.*;
import es.uca.AutomaticFoodList.Repositories.ListaComidaRepository;
import es.uca.AutomaticFoodList.Repositories.ListaCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ListaComidaService {
    private ListaComidaRepository listaComidaRepository;

    @Autowired
    public ListaComidaService(ListaComidaRepository listaComidaRepository) {
        this.listaComidaRepository = listaComidaRepository;
    }

    public ListaComida create(ListaComida listaComida) {
        return listaComidaRepository.save(listaComida);
    }

    public ListaComida update(ListaComida listaComida) {
        return listaComidaRepository.save(listaComida);
    }

    public Optional<ListaComida> buscarId(Long id) {
        return listaComidaRepository.findById(id);
    }

    public Optional<ListaComida> findByUsuarioAndComidaAndPlatoAndFecha(Usuario usuario, Comida comida, Plato plato, LocalDate fecha){
        return listaComidaRepository.findByUsuarioAndComidaAndFechaAndPlato(usuario, comida, fecha, plato);
    }

    public List<ListaComida> findByUsuario(Usuario usuario){
        return listaComidaRepository.findByUsuario(usuario);
    }

    public List<ListaComida> findAll() {
        return listaComidaRepository.findAll();
    }

    public void delete(ListaComida listaComida) {
        listaComidaRepository.delete(listaComida);
    }
}
