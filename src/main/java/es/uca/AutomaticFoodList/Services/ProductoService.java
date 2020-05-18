package es.uca.AutomaticFoodList.Services;

import es.uca.AutomaticFoodList.Entities.Ingrediente;
import es.uca.AutomaticFoodList.Entities.Producto;
import es.uca.AutomaticFoodList.Repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ProductoService {
    private ProductoRepository productoRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public Producto create(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto update(Producto producto) {
        return productoRepository.save(producto);
    }

    public Optional<Producto> buscarId(Long id) {
        return productoRepository.findById(id);
    }

    public Producto findByIngrediente(Ingrediente ingrediente){
        return productoRepository.findByIngrediente(ingrediente);
    }

    public List<Producto> findByProducto(String nombre){
        return productoRepository.findByNombreStartsWithIgnoreCase(nombre);
    }

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public void delete(Producto producto) {
        productoRepository.delete(producto);
    }
}
