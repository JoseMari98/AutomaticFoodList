package es.uca.automaticfoodlist.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import es.uca.automaticfoodlist.entities.ValoresNutricionales;
import es.uca.automaticfoodlist.forms.ValoresNutricionalesForm;
import es.uca.automaticfoodlist.services.RecetaService;
import es.uca.automaticfoodlist.services.UsuarioService;
import es.uca.automaticfoodlist.services.ValoresNutricionalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

@Route(value = "GestionValoresNutricionales", layout = MainView.class)
@Secured({"Admin", "Gerente"})
public class ValoresNutrcionalesView extends AbstractView {
    private Grid<ValoresNutricionales> grid = new Grid<>(ValoresNutricionales.class);
    private TextField filterText = new TextField();
    private ValoresNutricionalesService valoresNutricionalesService;
    private ValoresNutricionalesForm valoresNutricionalesForm;

    @Autowired
    public ValoresNutrcionalesView(ValoresNutricionalesService valoresNutricionalesService, RecetaService recetaService, UsuarioService usuarioService) {
        this.valoresNutricionalesService = valoresNutricionalesService;
        this.valoresNutricionalesForm = new ValoresNutricionalesForm(this, valoresNutricionalesService, recetaService, usuarioService);

        filterText.setPlaceholder("Filtrar por receta"); //poner el campo
        filterText.setClearButtonVisible(true); //poner la cruz para borrar
        filterText.setValueChangeMode(ValueChangeMode.EAGER); //que se hagan los cambios cuando se escriba
        filterText.addValueChangeListener(event -> {
            if (valoresNutricionalesService.findByReceta(filterText.getValue()) != null)
                updateList();
            else {
                filterText.clear();
                Notification.show("No hay ninguna receta con ese nombre", 2000, Notification.Position.MIDDLE);
            }

        });

        Button addValoresBtn = new Button("Añade unos valores");
        addValoresBtn.addClickListener(e -> {
            grid.asSingleSelect().clear(); //clear para que borre si habia algo antes
            valoresNutricionalesForm.setValoresNutricionales(new ValoresNutricionales()); //instancia un nuevo customer
        });

        HorizontalLayout toolbar = new HorizontalLayout(filterText,
                addValoresBtn);

        grid.setColumns("caloriasPlato", "hidratosPlato", "grasaPlato", "proteinaPlato");
        grid.addColumn(ValoresNutricionales -> ValoresNutricionales.getReceta() == null ? "Sin receta" : ValoresNutricionales.getReceta().getNombre()).setHeader("Receta").setSortable(true);
        grid.addColumn(ValoresNutricionales -> ValoresNutricionales.getUsuario() == null ? "Sin usuario" : ValoresNutricionales.getUsuario().getUsername()).setHeader("Usuario").setSortable(true);

        HorizontalLayout mainContent = new HorizontalLayout(grid, valoresNutricionalesForm); //metemos en un objeto el grid y formulario
        mainContent.setSizeFull();
        grid.setSizeFull();

        add(toolbar, mainContent);

        setSizeFull();

        updateList();

        valoresNutricionalesForm.setValoresNutricionales(null);

        grid.asSingleSelect().addValueChangeListener(event -> valoresNutricionalesForm.setValoresNutricionales( grid.asSingleSelect().getValue()));
    }

    public void updateList() {
        if (filterText.isEmpty())
            grid.setItems(valoresNutricionalesService.findAll());
        else
            grid.setItems(valoresNutricionalesService.findByReceta(filterText.getValue()));
    }
}
