package es.uca.automaticfoodlist.forms;

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
import es.uca.automaticfoodlist.entities.Ingrediente;
import es.uca.automaticfoodlist.entities.RecetaIngrediente;
import es.uca.automaticfoodlist.entities.UnidadMedida;
import es.uca.automaticfoodlist.services.IngredienteService;
import es.uca.automaticfoodlist.services.RecetaIngredienteService;
import es.uca.automaticfoodlist.views.CrearRecetaView;

public class AnadirIngredienteForm extends FormLayout {
    ComboBox<Ingrediente> ingrediente = new ComboBox<>("Ingrediente");
    ComboBox<UnidadMedida> unidadMedida = new ComboBox<>("Unidad medida");
    NumberField cantidad = new NumberField("Cantidad");

    Button guardar = new Button("Guardar ingrediente");
    private Button cancelar = new Button("Cancelar");
    private CrearRecetaView crearRecetaView;

    public AnadirIngredienteForm(CrearRecetaView crearRecetaView, IngredienteService ingredienteService, RecetaIngredienteService recetaIngredienteService) {
        this.crearRecetaView = crearRecetaView;
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

    public void setRecetaIngrediente(RecetaIngrediente recetaIngrediente){
        ingrediente.setValue(recetaIngrediente.getIngrediente());
        unidadMedida.setValue(recetaIngrediente.getUnidadMedida());
        cantidad.setValue(recetaIngrediente.getCantidad());
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
