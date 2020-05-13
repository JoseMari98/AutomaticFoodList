package es.uca.AutomaticFoodList.Views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import es.uca.AutomaticFoodList.Entities.ListaCompra;
import es.uca.AutomaticFoodList.Entities.Usuario;
import es.uca.AutomaticFoodList.GenerarListaCompra;
import es.uca.AutomaticFoodList.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.klaudeta.PaginatedGrid;

import java.util.List;
import java.util.Set;

@Route(value = "ListaCompraView", layout = MainView.class)
@Secured({"User", "Admin", "Gerente"})
public class ListaCompraView extends AbstractView{
    private PaginatedGrid<ListaCompra> grid = new PaginatedGrid<>();
    private ListaComidaService listaComidaService;
    private RecetaService recetaService;
    private ListaCompraService listaCompraService;
    private TextField filterText = new TextField();
    private Button delete = new Button("Borrar seleccion");
    private double precioTotal = 0;
    private H6 precio = new H6();

    @Autowired
    public ListaCompraView(ListaComidaService listaComidaService, RecetaService recetaService, RecetaIngredienteService recetaIngredienteService,
                           ListaCompraService listaCompraService, ProductoService productoService, IngredienteService ingredienteService) {
        this.listaComidaService = listaComidaService;
        this.listaCompraService = listaCompraService;
        if(listaCompraService.findByUsuario(UI.getCurrent().getSession().getAttribute(Usuario.class)).isEmpty()){
            if(!listaComidaService.findByUsuario(UI.getCurrent().getSession().getAttribute(Usuario.class)).isEmpty()){
                Dialog dialog = new Dialog();

                Label label = new Label("Oh, parece que no tienes lista de compra, quieres generar una?");
                Button confirmButton = new Button("Confirmar", event -> {
                    GenerarListaCompra.generadorListaCompra(UI.getCurrent().getSession().getAttribute(Usuario.class), listaComidaService, recetaIngredienteService, productoService, listaCompraService, ingredienteService);
                    List<ListaCompra> compraList = listaCompraService.findByUsuario(UI.getCurrent().getSession().getAttribute(Usuario.class));
                    for(ListaCompra listaCompra : compraList)
                        precioTotal += listaCompra.getProducto().getPrecio() * listaCompra.getCantidad();
                    precio.removeAll();
                    precio.add("Precio total de: " + precioTotal + "€");
                    updateList();
                    dialog.close();
                });
                confirmButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
                Button cancelButton = new Button("Cancelar", event -> {
                    dialog.close();
                    UI.getCurrent().navigate("Inicio");
                });
                dialog.add(label, cancelButton, confirmButton);
                dialog.open();
            } else {
                UI.getCurrent().navigate("ListaComidasView");
            }
        } else{
            List<ListaCompra> compraList = listaCompraService.findByUsuario(UI.getCurrent().getSession().getAttribute(Usuario.class));
            for(ListaCompra listaCompra : compraList)
                precioTotal += listaCompra.getProducto().getPrecio() * listaCompra.getCantidad();
            precio.removeAll();
            precio.add("Precio total de: " + precioTotal + "€");
        }

        filterText.setPlaceholder("Filtrar por producto"); //poner el campo
        filterText.setClearButtonVisible(true); //poner la cruz para borrar
        filterText.setValueChangeMode(ValueChangeMode.EAGER); //que se hagan los cambios cuando se escriba
        filterText.addValueChangeListener(event -> {
            if(listaCompraService.findByProducto(filterText.getValue()) != null)
                updateList();
            else {
                filterText.clear();
                Notification.show("No hay ningun producto con ese nombre", 2000, Notification.Position.MIDDLE);
            }
        });
        grid.addColumn(ListaCompra -> ListaCompra.getProducto().getNombre()).setHeader("Producto").setSortable(true);
        grid.addColumn(ListaCompra -> ListaCompra.getProducto().getPrecio() + "€").setHeader("Precio/Ud").setSortable(true);
        grid.addColumn(ListaCompra -> ListaCompra.getCantidad()).setHeader("Cantidad de producto").setSortable(true);

        // Sets the max number of items to be rendered on the grid for each page
        grid.setPageSize(9);

        // Sets how many pages should be visible on the pagination before and/or after the current selected page
        grid.setPaginatorSize(3);
        grid.setSelectionMode(Grid.SelectionMode.MULTI);

        delete.addClickListener(e -> {
            Set<ListaCompra> listaCompras = grid.asMultiSelect().getSelectedItems();
            for(ListaCompra listaCompra : listaCompras)
                precioTotal -= listaCompra.getProducto().getPrecio() * listaCompra.getCantidad();
            precio.removeAll();
            precio.add("Precio total de: " + precioTotal + "€");
            if(!listaCompras.isEmpty())
                delete(listaCompras);
        });

        HorizontalLayout toolbar = new HorizontalLayout(filterText, delete, precio);

        VerticalLayout mainContent = new VerticalLayout(toolbar, grid); //metemos en un objeto el grid y formulario
        mainContent.setSizeFull();
        grid.setSizeFull();
        add(mainContent);

        setSizeFull();

        updateList();
    }

    public void delete(Set<ListaCompra> listaCompras){
        for(ListaCompra listaCompra : listaCompras)
            listaCompraService.delete(listaCompra);
        updateList();
        Notification.show("Borrado con exito", 2000, Notification.Position.MIDDLE);
    }

    public void updateList(){
        if(filterText.isEmpty())
            grid.setItems(listaCompraService.findByUsuario(UI.getCurrent().getSession().getAttribute(Usuario.class)));
        else{
            grid.setItems(listaCompraService.findByProducto(filterText.getValue()));
        }
    }
}
