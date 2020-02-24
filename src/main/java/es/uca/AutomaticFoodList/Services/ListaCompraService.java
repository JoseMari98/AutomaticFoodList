package es.uca.AutomaticFoodList.Services;

import es.uca.AutomaticFoodList.Entities.ListaCompra;
import es.uca.AutomaticFoodList.Repositories.ListaCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ListaCompraService {
    private ListaCompraRepository listaCompraRepository;

    @Autowired
    public ListaCompraService(ListaCompraRepository listaCompraRepository) {
        this.listaCompraRepository = listaCompraRepository;
    }

    public ListaCompra create(ListaCompra listaCompra) {
        return listaCompraRepository.save(listaCompra);
    }

    public ListaCompra update(ListaCompra listaCompra) {
        return listaCompraRepository.save(listaCompra);
    }

    public Optional<ListaCompra> buscarId(Long id) {
        return listaCompraRepository.findById(id);
    }

    public List<ListaCompra> findAll() {
        return listaCompraRepository.findAll();
    }

    public void delete(ListaCompra listaCompra) {
        listaCompraRepository.delete(listaCompra);
    }
}

