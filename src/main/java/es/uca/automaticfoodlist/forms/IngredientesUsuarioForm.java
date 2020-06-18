package es.uca.automaticfoodlist.forms;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import es.uca.automaticfoodlist.entities.Gusto;
import es.uca.automaticfoodlist.entities.Ingrediente;
import es.uca.automaticfoodlist.entities.PreferenciaIngrediente;
import es.uca.automaticfoodlist.entities.Usuario;
import es.uca.automaticfoodlist.services.PreferenciaIngredienteService;

public class IngredientesUsuarioForm extends FormLayout {
    private Button save = new Button("Añadir");
    private Button delete = new Button("Borrar");
    private PreferenciaIngredienteService preferenciaIngredienteService;
    private Ingrediente ingrediente;

    public IngredientesUsuarioForm(PreferenciaIngredienteService preferenciaIngredienteService) {
        this.preferenciaIngredienteService = preferenciaIngredienteService;

        save.addClickShortcut(Key.ENTER);

        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(buttons);


        save.addClickListener(event -> {
            save();
        });
        delete.addClickListener(event -> {
            if(preferenciaIngredienteService.findByUsuarioAndIngrediente(UI.getCurrent().getSession().getAttribute(Usuario.class), this.ingrediente) != null)
                delete();});
    }

    public void setPreferenciaIngrediente(PreferenciaIngrediente preferenciaIngrediente, Ingrediente ingrediente) {
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
        if (preferenciaIngredienteService.findByUsuarioAndIngrediente(UI.getCurrent().getSession().getAttribute(Usuario.class), this.ingrediente) == null) {
            preferenciaIngrediente.setIngrediente(ingrediente);
            preferenciaIngrediente.setUsuario(UI.getCurrent().getSession().getAttribute(Usuario.class));
            preferenciaIngrediente.setGusto(Gusto.Nada);
            preferenciaIngredienteService.create(preferenciaIngrediente);
            setPreferenciaIngrediente(null, null);
        } else
            Notification.show("Ya ha introducido este ingrediente", 5000, Notification.Position.MIDDLE);

    }

    public void delete() {
        PreferenciaIngrediente preferenciaIngrediente;
        if (preferenciaIngredienteService.findByUsuarioAndIngrediente(UI.getCurrent().getSession().getAttribute(Usuario.class), this.ingrediente) != null) {
            preferenciaIngrediente = preferenciaIngredienteService.findByUsuarioAndIngrediente(UI.getCurrent().getSession().getAttribute(Usuario.class), this.ingrediente);
            preferenciaIngredienteService.delete(preferenciaIngrediente);
            setPreferenciaIngrediente(null, null);
        } else
            Notification.show("No has introducido agrado a este ingrediente", 5000, Notification.Position.MIDDLE);
    }
}
