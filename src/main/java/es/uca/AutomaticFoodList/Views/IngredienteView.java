package es.uca.AutomaticFoodList.Views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import es.uca.AutomaticFoodList.Entities.Ingrediente;
import es.uca.AutomaticFoodList.Entities.PreferenciaIngrediente;
import es.uca.AutomaticFoodList.Forms.IngredienteForm;
import es.uca.AutomaticFoodList.Services.IngredienteService;
import es.uca.AutomaticFoodList.Services.PreferenciaIngredienteService;
import es.uca.AutomaticFoodList.Services.RecetaIngredienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

@Route(value = "GestionIngrediente", layout = MainView.class)
@Secured({"Admin", "Gerente"})
public class IngredienteView extends AbstractView{
    private Grid<Ingrediente> grid = new Grid<>(Ingrediente.class);
    private TextField filterText = new TextField();
    private IngredienteService ingredienteService;
    private IngredienteForm ingredienteForm;

    @Autowired
    public IngredienteView(IngredienteService ingredienteService, RecetaIngredienteService recetaIngredienteService, PreferenciaIngredienteService preferenciaIngredienteService) {
        this.ingredienteService = ingredienteService;
        this.ingredienteForm = new IngredienteForm(this, ingredienteService, preferenciaIngredienteService, recetaIngredienteService);

        filterText.setPlaceholder("Filtrar por nombre"); //poner el campo
        filterText.setClearButtonVisible(true); //poner la cruz para borrar
        filterText.setValueChangeMode(ValueChangeMode.EAGER); //que se hagan los cambios cuando se escriba
        filterText.addValueChangeListener(event -> {
            if(ingredienteService.findByIngredient(filterText.getValue()) != null)
                updateList();
            else {
                filterText.clear();
                Notification.show("No hay ningun producto con ese nombre", 2000, Notification.Position.MIDDLE);
            }

        });

        Button addIngredienteBtn = new Button ("Añade un ingrediente");
        addIngredienteBtn.addClickListener(e -> {
            grid.asSingleSelect().clear(); //clear para que borre si habia algo antes
            ingredienteForm.setIngrediente(new Ingrediente()); //instancia un nuevo customer
        });

        HorizontalLayout toolbar = new HorizontalLayout(filterText,
                addIngredienteBtn);

        grid.setColumns("nombre", "categoria","idApi");

        HorizontalLayout mainContent = new HorizontalLayout(grid, ingredienteForm); //metemos en un objeto el grid y formulario
        mainContent.setSizeFull();
        grid.setSizeFull();

        add(toolbar, mainContent);

        setSizeFull();

        updateList();

        ingredienteForm.setIngrediente(null);

        grid.asSingleSelect().addValueChangeListener(event -> ingredienteForm.setIngrediente(grid.asSingleSelect().getValue()));
    }

    public void updateList() {
        if(filterText.isEmpty())
            grid.setItems(ingredienteService.findAll());
        else
            grid.setItems(ingredienteService.findByIngredient(filterText.getValue()));
    }
}
