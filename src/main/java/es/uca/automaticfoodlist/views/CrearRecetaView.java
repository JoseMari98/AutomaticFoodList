package es.uca.automaticfoodlist.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import es.uca.automaticfoodlist.entities.Receta;
import es.uca.automaticfoodlist.entities.RecetaIngrediente;
import es.uca.automaticfoodlist.forms.AnadirIngredienteForm;
import es.uca.automaticfoodlist.forms.CrearRecetaForm;
import es.uca.automaticfoodlist.services.IngredienteService;
import es.uca.automaticfoodlist.services.RecetaIngredienteService;
import es.uca.automaticfoodlist.services.RecetaService;
import es.uca.automaticfoodlist.services.ValoresNutricionalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.klaudeta.PaginatedGrid;

import java.util.LinkedList;
import java.util.List;

@Route(value = "CrearRecetaView", layout = MainView.class)
@Secured("User")
public class CrearRecetaView extends AbstractView {
    public PaginatedGrid<RecetaIngrediente> grid = new PaginatedGrid<>();
    public Receta receta = new Receta();
    public CrearRecetaForm crearRecetaForm;
    private Button anadir = new Button("Anadir ingrediente");
    public AnadirIngredienteForm anadirIngredienteForm;
    public List<RecetaIngrediente> ingredienteList = new LinkedList<>();
    public VerticalLayout fullGrid = new VerticalLayout();

    @Autowired
    public CrearRecetaView(IngredienteService ingredienteService, RecetaService recetaService, RecetaIngredienteService recetaIngredienteService, ValoresNutricionalesService valoresNutricionalesService) {
        this.crearRecetaForm = new CrearRecetaForm(this, recetaService, recetaIngredienteService, valoresNutricionalesService);
        this.anadirIngredienteForm = new AnadirIngredienteForm(this, ingredienteService, recetaIngredienteService);

        H1 titulo = new H1("Ingredientes");

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
