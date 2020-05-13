package es.uca.AutomaticFoodList.Forms;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import es.uca.AutomaticFoodList.Entities.Usuario;
import es.uca.AutomaticFoodList.Services.UsuarioService;
import es.uca.AutomaticFoodList.Views.PresupuestoPlatoView;

public class PresupuestoPlatoForm extends FormLayout {
    private PresupuestoPlatoView presupuestoPlatoView;
    private UsuarioService usuarioService;
    private NumberField presupuestoPlato = new NumberField("Presupuesto");
    private Button save = new Button("Finalizar");
    private BeanValidationBinder<Usuario> binder = new BeanValidationBinder<>(Usuario.class);

    public PresupuestoPlatoForm(PresupuestoPlatoView presupuestoPlatoView, UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        this.presupuestoPlatoView = presupuestoPlatoView;

        if(UI.getCurrent().getSession().getAttribute(Usuario.class) != null)
            if(usuarioService.loadUserByUsername(UI.getCurrent().getSession().getAttribute(Usuario.class).getUsername()).getPresupuestoPlato() > 0)
                binder.setBean(usuarioService.loadUserByUsername(UI.getCurrent().getSession().getAttribute(Usuario.class).getUsername()));

        presupuestoPlato.setMin(0);
        presupuestoPlato.setMax(100);
        presupuestoPlato.setSuffixComponent(new Span("€"));

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        VerticalLayout main = new VerticalLayout(presupuestoPlato, save);
        add(main);
        save.addClickShortcut(Key.ENTER);
        binder.bindInstanceFields(this);

        save.addClickListener(event -> {
            save();
        });
    }

    public void save() {
        if(presupuestoPlato.isEmpty() || presupuestoPlato.getValue() < 1)
            UI.getCurrent().getSession().getAttribute(Usuario.class).setPresupuestoPlato(-1);
        else
            UI.getCurrent().getSession().getAttribute(Usuario.class).setPresupuestoPlato(presupuestoPlato.getValue());
        usuarioService.update(UI.getCurrent().getSession().getAttribute(Usuario.class));
        UI.getCurrent().navigate("ListaComidasView");
    }
}
