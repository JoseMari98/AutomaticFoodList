package es.uca.automaticfoodlist.views;

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
import es.uca.automaticfoodlist.entities.Ingrediente;
import es.uca.automaticfoodlist.entities.Usuario;
import es.uca.automaticfoodlist.forms.IngredientesUsuarioForm;
import es.uca.automaticfoodlist.services.IngredienteService;
import es.uca.automaticfoodlist.services.PreferenciaIngredienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

@Route(value = "IngredientesUsuarioView", layout = MainView.class)
@Secured("User")
public class IngredientesUsuarioView extends AbstractView{
    private Grid<Ingrediente> grid = new Grid<>(Ingrediente.class);
    private TextField filterText = new TextField();
    private IngredienteService ingredienteService;
    private PreferenciaIngredienteService preferenciaIngredienteService;
    private IngredientesUsuarioForm ingredientesUsuarioForm;
    private Button continuar = new Button("Siguiente");

    @Autowired
    public IngredientesUsuarioView(IngredienteService ingredienteService, PreferenciaIngredienteService preferenciaIngredienteService) {
        //agregar el filtro por categoria y la paginacion
        this.ingredienteService = ingredienteService;
        this.preferenciaIngredienteService = preferenciaIngredienteService;
        this.ingredientesUsuarioForm = new IngredientesUsuarioForm(this, preferenciaIngredienteService);

        H1 titulo = new H1("Lista de ingredientes");
        Paragraph descripcion = new Paragraph("Aqui podras introducir tu nivel de agrado para los ingredientes, si no introduces nada se presupone que te gusta.");
        filterText.setPlaceholder("Filtrar por ingrediente"); //poner el campo
        filterText.setClearButtonVisible(true); //poner la cruz para borrar
        filterText.setValueChangeMode(ValueChangeMode.EAGER); //que se hagan los cambios cuando se escriba
        filterText.addValueChangeListener(event -> {
            if(ingredienteService.findByIngredient(filterText.getValue()) != null)
                updateList();
            else {
                filterText.clear();
                Notification.show("No hay ningun ingrediente con ese nombre", 2000, Notification.Position.MIDDLE);
            }
        });

        VerticalLayout texto = new VerticalLayout(titulo, descripcion);
        HorizontalLayout toolbar = new HorizontalLayout(filterText); //meter lo de la categoria

        grid.setColumns("nombre", "categoria");

        HorizontalLayout mainContent = new HorizontalLayout(grid, ingredientesUsuarioForm); //metemos en un objeto el grid y formulario
        mainContent.setSizeFull();
        grid.setSizeFull();
        continuar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        continuar.addClickListener(e -> UI.getCurrent().navigate("PresupuestoPlatoView"));
        add(texto, toolbar, mainContent, continuar);

        setSizeFull();

        updateList();

        ingredientesUsuarioForm.setPreferenciaIngrediente(null, null);

        grid.asSingleSelect().addValueChangeListener(event -> {
            if(preferenciaIngredienteService.findByUsuarioAndIngrediente(UI.getCurrent().getSession().getAttribute(Usuario.class), grid.asSingleSelect().getValue()) == null){
                ingredientesUsuarioForm.setPreferenciaIngrediente(null, grid.asSingleSelect().getValue());
            } else{
                ingredientesUsuarioForm.setPreferenciaIngrediente(preferenciaIngredienteService.findByUsuarioAndIngrediente(UI.getCurrent().getSession().getAttribute(Usuario.class), grid.asSingleSelect().getValue()), grid.asSingleSelect().getValue());
            }
        });
    }
    public void updateList() {
        if(filterText.isEmpty())
            grid.setItems(ingredienteService.findAll());
        else
            grid.setItems(ingredienteService.findByIngredient(filterText.getValue()));
    }
}
