package es.uca.AutomaticFoodList.Forms;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import es.uca.AutomaticFoodList.Entities.Ingrediente;
import es.uca.AutomaticFoodList.Entities.RecetaIngrediente;
import es.uca.AutomaticFoodList.Entities.UnidadMedida;
import es.uca.AutomaticFoodList.Services.IngredienteService;
import es.uca.AutomaticFoodList.Services.RecetaIngredienteService;
import es.uca.AutomaticFoodList.Views.CrearRecetaView;

public class AnadirIngredienteForm extends FormLayout {
    private ComboBox<Ingrediente> ingrediente = new ComboBox<>("Ingrediente");
    private ComboBox<UnidadMedida> unidadMedida = new ComboBox<>("Unidad medida");
    private NumberField cantidad = new NumberField("Cantidad");
    private Button guardar = new Button("Guardar ingrediente");
    private Button cancelar = new Button("Cancelar");
    private CrearRecetaView crearRecetaView;
    private RecetaIngredienteService recetaIngredienteService;
    private IngredienteService ingredienteService;
    public AnadirIngredienteForm(CrearRecetaView crearRecetaView, IngredienteService ingredienteService, RecetaIngredienteService recetaIngredienteService) {
        this.crearRecetaView = crearRecetaView;
        this.recetaIngredienteService = recetaIngredienteService;
        this.ingredienteService = ingredienteService;
        H1 titulo = new H1("Introducir ingrediente");

        ingrediente.setRequired(true);
        unidadMedida.setRequired(true);
        unidadMedida.setRequiredIndicatorVisible(true);
        ingrediente.setRequiredIndicatorVisible(true);
        cantidad.setRequiredIndicatorVisible(true);
        ingrediente.setItems(ingredienteService.findAll());
        ingrediente.setItemLabelGenerator(Ingrediente::getNombre);
        unidadMedida.setItems(UnidadMedida.values());

        guardar.addClickShortcut(Key.ENTER);

        guardar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancelar.addThemeVariants(ButtonVariant.LUMO_ERROR);
        HorizontalLayout buttons = new HorizontalLayout(guardar, cancelar), forms = new HorizontalLayout(ingrediente, cantidad, unidadMedida);
        VerticalLayout mainContent = new VerticalLayout(titulo, forms, buttons);
        add(mainContent);

        cancelar.addClickListener(event -> {
            this.setVisible(false);
            crearRecetaView.crearRecetaForm.setVisible(true);
            crearRecetaView.fullGrid.setVisible(true);
        });
        guardar.addClickListener(event -> save());
    }

    public void save() {
        RecetaIngrediente recetaIngrediente = new RecetaIngrediente();
        if(ingrediente.getValue() != null && (cantidad.getValue() > 0 && cantidad.getValue() != null) && unidadMedida.getValue() != null){
            recetaIngrediente.setUnidadMedida(unidadMedida.getValue());
            recetaIngrediente.setReceta(crearRecetaView.receta);
            recetaIngrediente.setIngrediente(ingrediente.getValue());
            recetaIngrediente.setCantidad(cantidad.getValue());
            crearRecetaView.ingredienteList.add(recetaIngrediente);
            this.setVisible(false);
            crearRecetaView.crearRecetaForm.setVisible(true);
            crearRecetaView.fullGrid.setVisible(true);
            ingrediente.setValue(ingrediente.getEmptyValue());
            cantidad.setValue(cantidad.getEmptyValue());
            unidadMedida.setValue(unidadMedida.getEmptyValue());
            crearRecetaView.updateList();
        } else {
            Notification.show("Rellene los campos", 5000, Notification.Position.MIDDLE);
        }
    }
}
