package es.uca.AutomaticFoodList.Views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import es.uca.AutomaticFoodList.Entities.Ingrediente;
import es.uca.AutomaticFoodList.Entities.Receta;
import es.uca.AutomaticFoodList.Entities.RecetaIngrediente;
import es.uca.AutomaticFoodList.Entities.Usuario;
import es.uca.AutomaticFoodList.Forms.AnadirIngredienteForm;
import es.uca.AutomaticFoodList.Forms.CrearRecetaForm;
import es.uca.AutomaticFoodList.Forms.IngredientesUsuarioForm;
import es.uca.AutomaticFoodList.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.klaudeta.PaginatedGrid;

import java.util.LinkedList;
import java.util.List;

@Route(value = "CrearRecetaView", layout = MainView.class)
@Secured("User")
public class CrearRecetaView extends AbstractView {
    public PaginatedGrid<RecetaIngrediente> grid = new PaginatedGrid<>();
    //private TextField filterText = new TextField();
    private IngredienteService ingredienteService;
    private RecetaIngredienteService recetaIngredienteService;
    private RecetaService recetaService;
    public Receta receta = new Receta();
    public CrearRecetaForm crearRecetaForm;
    private Button anadir = new Button("Anadir ingrediente");
    public AnadirIngredienteForm anadirIngredienteForm;
    public List<RecetaIngrediente> ingredienteList = new LinkedList<>();
    public VerticalLayout fullGrid = new VerticalLayout();

    @Autowired
    public CrearRecetaView(IngredienteService ingredienteService, RecetaService recetaService, RecetaIngredienteService recetaIngredienteService, ValoresNutricionalesService valoresNutricionalesService) {
        //agregar el filtro por categoria y la paginacion
        this.ingredienteService = ingredienteService;
        this.recetaService = recetaService;
        this.crearRecetaForm = new CrearRecetaForm(this, recetaService, recetaIngredienteService, valoresNutricionalesService);
        this.anadirIngredienteForm = new AnadirIngredienteForm(this, ingredienteService, recetaIngredienteService);

        H1 titulo = new H1("Ingredientes");
        /*filterText.setPlaceholder("Filtrar por ingrediente"); //poner el campo
        filterText.setClearButtonVisible(true); //poner la cruz para borrar
        filterText.setValueChangeMode(ValueChangeMode.EAGER); //que se hagan los cambios cuando se escriba
        filterText.addValueChangeListener(event -> {
            if (recetaIngredienteService.findByIngredienteNombre(filterText.getValue()) != null)
                updateList();
            else {
                filterText.clear();
                Notification.show("No hay ningun ingrediente con ese nombre", 2000, Notification.Position.MIDDLE);
            }
        });*/

        HorizontalLayout toolbar = new HorizontalLayout(anadir); //meter lo de la categoria
        fullGrid.add(titulo, toolbar, grid);

        grid.addColumn(RecetaIngrediente -> RecetaIngrediente.getIngrediente().getNombre()).setHeader("Ingrediente").setSortable(true);
        grid.addColumn(RecetaIngrediente -> RecetaIngrediente.getCantidad() + " " + RecetaIngrediente.getUnidadMedida()).setHeader("Cantidad").setSortable(true);

        // Sets the max number of items to be rendered on the grid for each page
        grid.setPageSize(9);

        // Sets how many pages should be visible on the pagination before and/or after the current selected page
        grid.setPaginatorSize(3);

        HorizontalLayout forms = new HorizontalLayout(crearRecetaForm, anadirIngredienteForm);
        VerticalLayout mainContent = new VerticalLayout(forms, fullGrid); //metemos en un objeto el grid y formulario
        mainContent.setSizeFull();
        grid.setSizeFull();
        crearRecetaForm.setVisible(true);
        anadirIngredienteForm.setVisible(false);
        anadir.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        anadir.addClickListener(e -> {
            anadirIngredienteForm.setVisible(true);
            crearRecetaForm.setVisible(false);
            fullGrid.setVisible(false);
        });
        add(mainContent);

        setSizeFull();

        updateList();
    }

    public void updateList() {
        if(!ingredienteList.isEmpty())
            grid.setItems(ingredienteList);
    }
}
