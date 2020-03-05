package es.uca.AutomaticFoodList;

import com.vaadin.flow.component.UI;
import es.uca.AutomaticFoodList.Entities.*;
import es.uca.AutomaticFoodList.Services.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GenerarListaCompra {
    public static void generadorListaCompra(Usuario usuario, ListaComidaService listaComidaService, RecetaIngredienteService recetaIngredienteService, ProductoService productoService, ListaCompraService listaCompraService, IngredienteService ingredienteService) {
        List<ListaComida> platosSemanales = listaComidaService.findByUsuario(usuario); //lista de todos las recetas del usuario
        List<Receta> recetaList = new LinkedList<>();
        Map<Ingrediente, Map<UnidadMedida, Double>> ingredienteMap = new HashMap<>();

        for(ListaComida listaComida : platosSemanales){ //meto en una lista todas las recetas de la semana
            recetaList.add(listaComida.getReceta());
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
            ListaCompra listaCompra = new ListaCompra();
            listaCompra.setCantidad(0);
            Producto producto = productoService.findByIngrediente(ingrediente);
            for(UnidadMedida unidadMedida : ingredienteMap.get(ingrediente).keySet()){
                double cantidad = 0;
                switch (unidadMedida){
                    case Gramos:
                        cantidad = listaCompra.getCantidad();
                        cantidad += ingredienteMap.get(ingrediente).get(unidadMedida);
                        listaCompra.setCantidad(cantidad);
                        listaCompra.setProducto(producto);
                        listaCompra.setUsuario(UI.getCurrent().getSession().getAttribute(Usuario.class));
                    break;

                    case Unidad:
                        cantidad = listaCompra.getCantidad();
                        double unidades = ingredienteMap.get(ingrediente).get(unidadMedida);
                        cantidad += producto.getPeso() * unidades;
                        listaCompra.setCantidad(cantidad);
                        listaCompra.setProducto(producto);
                        listaCompra.setUsuario(UI.getCurrent().getSession().getAttribute(Usuario.class));
                    break;
                }
            }
            double cantidadComprar = Math.ceil(listaCompra.getCantidad() / producto.getPeso());
            listaCompra.setCantidad(cantidadComprar);
            listaCompraService.create(listaCompra);
        }
    }
}
