package es.uca.automaticfoodlist.views;

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
import es.uca.automaticfoodlist.entities.Usuario;
import es.uca.automaticfoodlist.entities.UsuarioProducto;
import es.uca.automaticfoodlist.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.klaudeta.PaginatedGrid;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Set;

@Route(value = "ListaCompraView", layout = MainView.class)
@Secured({"User", "Admin", "Gerente"})
public class ListaCompraView extends AbstractView {
    private PaginatedGrid<UsuarioProducto> grid = new PaginatedGrid<>();
    private UsuarioProductoService usuarioProductoService;
    private TextField filterText = new TextField();
    private Button delete = new Button("Borrar seleccion");
    private Button borrarLista = new Button("Borrar lista");
    private double precioTotal = 0;
    private H6 precio = new H6();

    @Autowired
    public ListaCompraView(UsuarioRecetaService usuarioRecetaService, RecetaService recetaService, RecetaIngredienteService recetaIngredienteService,
                           UsuarioProductoService usuarioProductoService, ProductoService productoService, IngredienteService ingredienteService) {
        this.usuarioProductoService = usuarioProductoService;
        if (usuarioProductoService.findByUsuario(UI.getCurrent().getSession().getAttribute(Usuario.class)).isEmpty()) {
            if (!usuarioRecetaService.findByUsuario(UI.getCurrent().getSession().getAttribute(Usuario.class)).isEmpty()) {
                Dialog dialog = new Dialog();

                Label label = new Label("Oh, parece que no tienes lista de compra, quieres generar una?");
                Button confirmButton = new Button("Confirmar", event -> {
                    usuarioProductoService.generarListaCompra(UI.getCurrent().getSession().getAttribute(Usuario.class), usuarioRecetaService, recetaIngredienteService, productoService, usuarioProductoService, ingredienteService);
                    List<UsuarioProducto> compraList = usuarioProductoService.findByUsuario(UI.getCurrent().getSession().getAttribute(Usuario.class));
                    for (UsuarioProducto listaCompra : compraList)
                        precioTotal += listaCompra.getProducto().getPrecio() * listaCompra.getCantidad();
                    BigDecimal redondeado = new BigDecimal(precioTotal)
                            .setScale(2, RoundingMode.HALF_EVEN);
                    precioTotal = redondeado.doubleValue();
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
        } else {
            List<UsuarioProducto> compraList = usuarioProductoService.findByUsuario(UI.getCurrent().getSession().getAttribute(Usuario.class));
            for (UsuarioProducto usuarioProducto : compraList)
                precioTotal += usuarioProducto.getProducto().getPrecio() * usuarioProducto.getCantidad();
            BigDecimal redondeado = new BigDecimal(precioTotal)
                    .setScale(2, RoundingMode.HALF_EVEN);
            precioTotal = redondeado.doubleValue();
            precio.removeAll();
            precio.add("Precio total de: " + precioTotal + "€");
        }
        borrarLista.addThemeVariants(ButtonVariant.LUMO_ERROR);
        borrarLista.addClickListener(e -> {
            for (UsuarioProducto usuarioProducto : usuarioProductoService.findByUsuario(UI.getCurrent().getSession().getAttribute(Usuario.class))) {
                usuarioProductoService.delete(usuarioProducto);
            }
            updateList();
        });

        filterText.setPlaceholder("Filtrar por producto"); //poner el campo
        filterText.setClearButtonVisible(true); //poner la cruz para borrar
        filterText.setValueChangeMode(ValueChangeMode.EAGER); //que se hagan los cambios cuando se escriba
        filterText.addValueChangeListener(event -> {
            if (usuarioProductoService.findByProducto(filterText.getValue()) != null)
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
        grid.setPageSize(15);

        // Sets how many pages should be visible on the pagination before and/or after the current selected page
        grid.setPaginatorSize(3);
        grid.setSelectionMode(Grid.SelectionMode.MULTI);

        delete.addClickListener(e -> {
            Set<UsuarioProducto> usuarioProductos = grid.asMultiSelect().getSelectedItems();
            for (UsuarioProducto usuarioProducto : usuarioProductos)
                precioTotal -= usuarioProducto.getProducto().getPrecio() * usuarioProducto.getCantidad();
            BigDecimal redondeado = new BigDecimal(precioTotal)
                    .setScale(2, RoundingMode.HALF_EVEN);
            precioTotal = redondeado.doubleValue();
            precio.removeAll();
            precio.add("Precio total de: " + precioTotal + "€");
            if (!usuarioProductos.isEmpty())
                delete(usuarioProductos);
        });

        HorizontalLayout toolbar = new HorizontalLayout(filterText, delete, borrarLista, precio);

        VerticalLayout mainContent = new VerticalLayout(toolbar, grid); //metemos en un objeto el grid y formulario
        mainContent.setSizeFull();
        grid.setSizeFull();
        add(mainContent);

        setSizeFull();

        updateList();
    }

    public void delete(Set<UsuarioProducto> usuarioProductos) {
        for (UsuarioProducto usuarioProducto : usuarioProductos)
            usuarioProductoService.delete(usuarioProducto);
        updateList();
        Notification.show("Borrado con exito", 2000, Notification.Position.MIDDLE);
    }

    public void updateList() {
        if (filterText.isEmpty())
            grid.setItems(usuarioProductoService.findByUsuario(UI.getCurrent().getSession().getAttribute(Usuario.class)));
        else {
            grid.setItems(usuarioProductoService.findByProducto(filterText.getValue()));
        }
    }
}
