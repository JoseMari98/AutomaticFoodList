package es.uca.AutomaticFoodList.Views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import es.uca.AutomaticFoodList.Entities.Producto;
import es.uca.AutomaticFoodList.Forms.ProductoForm;
import es.uca.AutomaticFoodList.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

@Route(value = "GestionProducto", layout = MainView.class)
@Secured({"Admin", "Gerente"})
public class ProductoView extends AbstractView {
    private Grid<Producto> grid = new Grid<>(Producto.class);
    private TextField filterText = new TextField();
    private ProductoService productoService;
    private ProductoForm productoForm;

    @Autowired
    public ProductoView(ProductoService productoService, IngredienteService ingredienteService, ListaCompraService listaCompraService) {
        this.productoService = productoService;
        this.productoForm = new ProductoForm(this, productoService, ingredienteService, listaCompraService);

        filterText.setPlaceholder("Filtrar por nombre"); //poner el campo
        filterText.setClearButtonVisible(true); //poner la cruz para borrar
        filterText.setValueChangeMode(ValueChangeMode.EAGER); //que se hagan los cambios cuando se escriba
        filterText.addValueChangeListener(event -> {
            if (productoService.findByProducto(filterText.getValue()) != null)
                updateList();
            else {
                filterText.clear();
                Notification.show("No hay ningun ingrediente con ese nombre", 2000, Notification.Position.MIDDLE);
            }

        });

        Button addProductoBtn = new Button("Añade un producto");
        addProductoBtn.addClickListener(e -> {
            grid.asSingleSelect().clear(); //clear para que borre si habia algo antes
            productoForm.setProducto(new Producto()); //instancia un nuevo customer
        });

        HorizontalLayout toolbar = new HorizontalLayout(filterText,
                addProductoBtn);

        grid.setColumns("nombre", "categoria", "peso", "unidad");
        grid.addColumn(Producto -> Producto.getPrecio() + "€").setSortable(true).setHeader("Precio");
        grid.addColumn(Producto -> Producto.getIngrediente() == null ? "Sin ingrediente" : Producto.getIngrediente().getNombre()).setHeader("Ingrediente").setSortable(true);

        HorizontalLayout mainContent = new HorizontalLayout(grid, productoForm); //metemos en un objeto el grid y formulario
        mainContent.setSizeFull();
        grid.setSizeFull();

        add(toolbar, mainContent);

        setSizeFull();

        updateList();

        productoForm.setProducto(null);

        grid.asSingleSelect().addValueChangeListener(event -> productoForm.setProducto(grid.asSingleSelect().getValue()));
    }

    public void updateList() {
        if (filterText.isEmpty())
            grid.setItems(productoService.findAll());
        else
            grid.setItems(productoService.findByProducto(filterText.getValue()));
    }
}
