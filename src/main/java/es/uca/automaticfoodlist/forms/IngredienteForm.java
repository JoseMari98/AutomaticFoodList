package es.uca.automaticfoodlist.forms;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import es.uca.automaticfoodlist.entities.Ingrediente;
import es.uca.automaticfoodlist.entities.PreferenciaIngrediente;
import es.uca.automaticfoodlist.entities.RecetaIngrediente;
import es.uca.automaticfoodlist.services.IngredienteService;
import es.uca.automaticfoodlist.services.PreferenciaIngredienteService;
import es.uca.automaticfoodlist.services.RecetaIngredienteService;
import es.uca.automaticfoodlist.views.IngredienteView;

public class IngredienteForm extends FormLayout {
    TextField nombre = new TextField("Nombre");
    TextField idApi = new TextField("Id api");
    Button save = new Button("Añadir");
    Button delete = new Button("Borrar");

    private IngredienteView ingredienteView;
    private BeanValidationBinder<Ingrediente> binder = new BeanValidationBinder<>(Ingrediente.class);
    private IngredienteService ingredienteService;
    private RecetaIngredienteService recetaIngredienteService;
    private PreferenciaIngredienteService preferenciaIngredienteService;

    public IngredienteForm(IngredienteView ingredienteView, IngredienteService ingredienteService,
                           PreferenciaIngredienteService preferenciaIngredienteService, RecetaIngredienteService recetaIngredienteService) {
        this.ingredienteService = ingredienteService;
        this.preferenciaIngredienteService = preferenciaIngredienteService;
        this.recetaIngredienteService = recetaIngredienteService;
        this.ingredienteView = ingredienteView;

        nombre.setRequired(true);
        nombre.setRequiredIndicatorVisible(true);

        save.addClickShortcut(Key.ENTER);

        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(nombre, idApi, buttons);

        binder.bindInstanceFields(this);

        save.addClickListener(event -> {
            if (binder.getBean() != null)
                save();
        });
        delete.addClickListener(event -> {
            if (binder.getBean() != null)
                delete();
        });
    }

    public void setIngrediente(Ingrediente ingrediente) {
        binder.setBean(ingrediente);

        if (ingrediente == null) {
            setVisible(false);
        } else {
            setVisible(true);
            nombre.focus();
        }
    }

    public void save() {
        Ingrediente ingrediente = binder.getBean();
        if (binder.validate().isOk()) {
            ingredienteService.create(ingrediente);
            this.ingredienteView.updateList();
            setIngrediente(null);
        } else {
            Notification.show("Rellene los campos", 5000, Notification.Position.MIDDLE);
        }
    }

    public void delete() {
        Ingrediente ingrediente = binder.getBean();
        if (binder.validate().isOk()) {
            //borrar las recetas que haya que borrar y productos y demas
            borrarPadres(ingrediente);
            ingredienteService.delete(ingrediente);
            this.ingredienteView.updateList();
            setIngrediente(null);
        } else
            Notification.show("Rellene los campos", 5000, Notification.Position.MIDDLE);
    }

    public void borrarPadres(Ingrediente ingrediente) {
        if (!preferenciaIngredienteService.findByIngrediente(ingrediente).isEmpty())
            for (PreferenciaIngrediente preferenciaIngrediente : preferenciaIngredienteService.findByIngrediente(ingrediente))
                preferenciaIngredienteService.delete(preferenciaIngrediente);
        if (!recetaIngredienteService.findByIngrediente(ingrediente).isEmpty())
            for (RecetaIngrediente recetaIngrediente : recetaIngredienteService.findByIngrediente(ingrediente))
                recetaIngredienteService.delete(recetaIngrediente);
    }
}
