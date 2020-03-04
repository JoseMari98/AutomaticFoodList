package es.uca.AutomaticFoodList.Views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import es.uca.AutomaticFoodList.Entities.ListaComida;
import es.uca.AutomaticFoodList.Entities.ListaCompra;
import es.uca.AutomaticFoodList.Entities.Producto;
import es.uca.AutomaticFoodList.Entities.Usuario;
import es.uca.AutomaticFoodList.Forms.ListaComidasForm;
import es.uca.AutomaticFoodList.Forms.SeleccionPlatoForm;
import es.uca.AutomaticFoodList.Services.ListaComidaService;
import es.uca.AutomaticFoodList.Services.ListaCompraService;
import es.uca.AutomaticFoodList.Services.RecetaIngredienteService;
import es.uca.AutomaticFoodList.Services.RecetaService;
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

    @Autowired
    public ListaCompraView(ListaComidaService listaComidaService, RecetaService recetaService, RecetaIngredienteService recetaIngredienteService, ListaCompraService listaCompraService) {
        this.listaComidaService = listaComidaService;
        this.listaCompraService = listaCompraService;

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
        grid.addColumn(ListaCompra -> ListaCompra.getCantidad() + " " + ListaCompra.getProducto().getUnidad()).setHeader("Cantidad").setSortable(true);

        // Sets the max number of items to be rendered on the grid for each page
        grid.setPageSize(9);

        // Sets how many pages should be visible on the pagination before and/or after the current selected page
        grid.setPaginatorSize(3);
        grid.setSelectionMode(Grid.SelectionMode.MULTI);

        delete.addClickListener(e -> {
            Set<ListaCompra> listaCompras = grid.asMultiSelect().getSelectedItems();
            if(!listaCompras.isEmpty())
                delete(listaCompras);
        });

        VerticalLayout mainContent = new VerticalLayout(filterText, grid, delete); //metemos en un objeto el grid y formulario
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
