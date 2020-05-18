package es.uca.automaticfoodlist.services;

import com.vaadin.flow.component.UI;
import es.uca.automaticfoodlist.entities.*;
import es.uca.automaticfoodlist.repositories.UsuarioProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsuarioProductoService {
    private UsuarioProductoRepository usuarioProductoRepository;

    @Autowired
    public UsuarioProductoService(UsuarioProductoRepository usuarioProductoRepository) {
        this.usuarioProductoRepository = usuarioProductoRepository;
    }

    public UsuarioProducto create(UsuarioProducto usuarioProducto) {
        return usuarioProductoRepository.save(usuarioProducto);
    }

    public UsuarioProducto update(UsuarioProducto usuarioProducto) {
        return usuarioProductoRepository.save(usuarioProducto);
    }

    public List<UsuarioProducto> findByProducto(String producto){
        return usuarioProductoRepository.findByProducto_NombreStartsWithIgnoreCase(producto);
    }

    public List<UsuarioProducto> findByUsuario(Usuario usuario){
        return usuarioProductoRepository.findByUsuario(usuario);
    }

    public Optional<UsuarioProducto> buscarId(Long id) {
        return usuarioProductoRepository.findById(id);
    }

    public List<UsuarioProducto> findAll() {
        return usuarioProductoRepository.findAll();
    }

    public void delete(UsuarioProducto usuarioProducto) {
        usuarioProductoRepository.delete(usuarioProducto);
    }

    public void generarListaCompra(Usuario usuario, UsuarioRecetaService usuarioRecetaService, RecetaIngredienteService recetaIngredienteService, ProductoService productoService, UsuarioProductoService usuarioProductoService, IngredienteService ingredienteService) {
        List<UsuarioReceta> platosSemanales = usuarioRecetaService.findByUsuario(usuario); //lista de todos las recetas del usuario
        List<Receta> recetaList = new LinkedList<>();
        Map<Ingrediente, Map<UnidadMedida, Double>> ingredienteMap = new HashMap<>();

        for(UsuarioReceta usuarioReceta : platosSemanales){ //meto en una lista todas las recetas de la semana
            recetaList.add(usuarioReceta.getReceta());
        }

        for(Receta receta : recetaList){ //loop para todas las recetas
            List<RecetaIngrediente> recetaIngredienteList = recetaIngredienteService.findByReceta(receta); //lista con todos los ingredientes de la receta
            for(RecetaIngrediente recetaIngrediente : recetaIngredienteList){
                Ingrediente ingrediente = ingredienteService.findByNombre(recetaIngrediente.getIngrediente().getNombre());
                if(ingredienteMap.containsKey(ingrediente)){ //si esta el ingrediente en el map
                    if(ingredienteMap.get(recetaIngrediente.getIngrediente()).containsKey(recetaIngrediente.getUnidadMedida())){ //si esta la unidad de medida
                        double cantidad = ingredienteMap.get(recetaIngrediente.getIngrediente()).get(recetaIngrediente.getUnidadMedida());
                        cantidad += recetaIngrediente.getCantidad();
                        ingredienteMap.get(recetaIngrediente.getIngrediente()).put(recetaIngrediente.getUnidadMedida(), cantidad);
                    } else{ //nueva unidad de medida
                        ingredienteMap.get(recetaIngrediente.getIngrediente()).put(recetaIngrediente.getUnidadMedida(), recetaIngrediente.getCantidad());
                    }
                } else{ //meto un nuevo ingrediente en el map
                    Map<UnidadMedida, Double> unidadMedidaDoubleMap = new HashMap<>();
                    unidadMedidaDoubleMap.put(recetaIngrediente.getUnidadMedida(), recetaIngrediente.getCantidad());
                    ingredienteMap.put(recetaIngrediente.getIngrediente(), unidadMedidaDoubleMap);
                }
            }
        }

        for(Ingrediente ingrediente : ingredienteMap.keySet()){
            UsuarioProducto usuarioProducto = new UsuarioProducto();
            usuarioProducto.setCantidad(0);
            Producto producto = productoService.findByIngrediente(ingrediente);
            for(UnidadMedida unidadMedida : ingredienteMap.get(ingrediente).keySet()){
                double cantidad;
                switch (unidadMedida){
                    case Gramos:
                        cantidad = usuarioProducto.getCantidad();
                        cantidad += ingredienteMap.get(ingrediente).get(unidadMedida);
                        usuarioProducto.setCantidad(cantidad);
                        usuarioProducto.setProducto(producto);
                        usuarioProducto.setUsuario(UI.getCurrent().getSession().getAttribute(Usuario.class));
                        break;

                    case Unidad:
                        cantidad = usuarioProducto.getCantidad();
                        double unidades = ingredienteMap.get(ingrediente).get(unidadMedida);
                        cantidad += producto.getPeso() * unidades;
                        usuarioProducto.setCantidad(cantidad);
                        usuarioProducto.setProducto(producto);
                        usuarioProducto.setUsuario(UI.getCurrent().getSession().getAttribute(Usuario.class));
                        break;
                }
            }
            double cantidadComprar = Math.ceil(usuarioProducto.getCantidad() / producto.getPeso());
            usuarioProducto.setCantidad(cantidadComprar);
            usuarioProductoService.create(usuarioProducto);
        }
    }
}

