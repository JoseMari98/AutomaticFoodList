package es.uca.automaticfoodlist.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import es.uca.automaticfoodlist.entities.Producto;
import es.uca.automaticfoodlist.forms.ProductoForm;
import es.uca.automaticfoodlist.services.IngredienteService;
import es.uca.automaticfoodlist.services.ProductoService;
import es.uca.automaticfoodlist.services.UsuarioProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.klaudeta.PaginatedGrid;

@Route(value = "GestionProducto", layout = MainView.class)
@Secured({"Admin", "Gerente"})
public class ProductoView extends AbstractView {
    private PaginatedGrid<Producto> grid = new PaginatedGrid<>();
    private TextField filterText = new TextField();
    private ProductoService productoService;
    private ProductoForm productoForm;

    @Autowired
    public ProductoView(ProductoService productoService, IngredienteService ingredienteService, UsuarioProductoService usuarioProductoService) {
        this.productoService = productoService;
        this.productoForm = new ProductoForm(this, productoService, ingredienteService, usuarioProductoService);

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

        //grid.setColumns("nombre", "peso", "unidad");

        grid.addColumn(Producto -> Producto.getNombre() == null ? "Sin nombre" : Producto.getNombre()).setSortable(true).setHeader("Nombre");
        grid.addColumn(Producto::getPeso).setSortable(true).setHeader("Precio");
        grid.addColumn(Producto -> Producto.getUnidad() == null ? "Sin unidad" : Producto.getUnidad()).setSortable(true).setHeader("Unidad");
        grid.addColumn(Producto -> Producto.getIngrediente() == null ? "Sin precio" : Producto.getPrecio() + "€").setSortable(true).setHeader("Precio");
        grid.addColumn(Producto -> Producto.getIngrediente() == null ? "Sin ingrediente" : Producto.getIngrediente().getNombre()).setHeader("Ingrediente").setSortable(true);

        grid.setPageSize(15);

        // Sets how many pages should be visible on the pagination before and/or after the current selected page
        grid.setPaginatorSize(3);

        VerticalLayout gridLayout = new VerticalLayout(grid);
        HorizontalLayout mainContent = new HorizontalLayout(gridLayout, productoForm); //metemos en un objeto el grid y formulario
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
