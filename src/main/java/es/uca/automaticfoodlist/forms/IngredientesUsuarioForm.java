package es.uca.automaticfoodlist.forms;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import es.uca.automaticfoodlist.entities.Gusto;
import es.uca.automaticfoodlist.entities.Ingrediente;
import es.uca.automaticfoodlist.entities.PreferenciaIngrediente;
import es.uca.automaticfoodlist.entities.Usuario;
import es.uca.automaticfoodlist.services.PreferenciaIngredienteService;

public class IngredientesUsuarioForm extends FormLayout {
    private Select<Gusto> gusto = new Select<>(Gusto.Mucho, Gusto.Poco, Gusto.Nada);
    private Button save = new Button("Añadir");
    private Button delete = new Button("Borrar");
    private BeanValidationBinder<PreferenciaIngrediente> binder = new BeanValidationBinder<>(PreferenciaIngrediente.class);
    private PreferenciaIngredienteService preferenciaIngredienteService;
    private Ingrediente ingrediente;

    public IngredientesUsuarioForm(PreferenciaIngredienteService preferenciaIngredienteService) {
        this.preferenciaIngredienteService = preferenciaIngredienteService;

        gusto.setRequiredIndicatorVisible(true);
        gusto.setLabel("Nivel de agrado");

        save.addClickShortcut(Key.ENTER);

        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(gusto, buttons);

        binder.bindInstanceFields(this);

        save.addClickListener(event -> {
            if(gusto.getValue() != null)
                save();
        });
        delete.addClickListener(event -> {
            if(preferenciaIngredienteService.findByUsuarioAndIngrediente(UI.getCurrent().getSession().getAttribute(Usuario.class), this.ingrediente) != null)
                delete();});
    }

    public void setPreferenciaIngrediente(PreferenciaIngrediente preferenciaIngrediente, Ingrediente ingrediente) {
        binder.setBean(preferenciaIngrediente);

        if(preferenciaIngrediente == null && ingrediente == null) {
            setVisible(false);
        }
        else {
            if(ingrediente != null){
                setVisible(true);
                this.ingrediente = ingrediente;
            }
        }
    }

    public void save() {
        PreferenciaIngrediente preferenciaIngrediente = new PreferenciaIngrediente();
        if(preferenciaIngredienteService.findByUsuarioAndIngrediente(UI.getCurrent().getSession().getAttribute(Usuario.class), this.ingrediente) != null)
            preferenciaIngrediente = preferenciaIngredienteService.findByUsuarioAndIngrediente(UI.getCurrent().getSession().getAttribute(Usuario.class), this.ingrediente);
        preferenciaIngrediente.setIngrediente(ingrediente);
        preferenciaIngrediente.setUsuario(UI.getCurrent().getSession().getAttribute(Usuario.class));
        preferenciaIngrediente.setGusto(gusto.getValue());
        if(binder.validate().isOk()) {
            preferenciaIngredienteService.create(preferenciaIngrediente);
            //this.ingredientesUsuarioView.updateList();
            setPreferenciaIngrediente(null, null);
        }
        else {
            Notification.show("Rellene los campos", 5000, Notification.Position.MIDDLE);
        }
    }

    public void delete() {
        PreferenciaIngrediente preferenciaIngrediente = new PreferenciaIngrediente();
        if(binder.validate().isOk() && preferenciaIngredienteService.findByUsuarioAndIngrediente(UI.getCurrent().getSession().getAttribute(Usuario.class), this.ingrediente) != null) {
            preferenciaIngrediente = preferenciaIngredienteService.findByUsuarioAndIngrediente(UI.getCurrent().getSession().getAttribute(Usuario.class), this.ingrediente);
            preferenciaIngredienteService.delete(preferenciaIngrediente);
            //this.ingredientesUsuarioView.updateList();
            setPreferenciaIngrediente(null, null);
        } else
            Notification.show("Rellene los campos", 5000, Notification.Position.MIDDLE);
    }
}
