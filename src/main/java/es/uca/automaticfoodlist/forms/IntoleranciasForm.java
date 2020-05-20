package es.uca.automaticfoodlist.forms;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import es.uca.automaticfoodlist.entities.Intolerancia;
import es.uca.automaticfoodlist.entities.IntoleranciaReceta;
import es.uca.automaticfoodlist.entities.IntoleranciaUsuario;
import es.uca.automaticfoodlist.services.IntoleranciaRecetaService;
import es.uca.automaticfoodlist.services.IntoleranciaService;
import es.uca.automaticfoodlist.services.IntoleranciaUsuarioService;
import es.uca.automaticfoodlist.views.IntoleranciasView;

public class IntoleranciasForm extends FormLayout {
    private TextField intolerancia = new TextField("Intolerancia");
    private Button save = new Button("Añadir");
    private Button delete = new Button("Borrar");
    private IntoleranciasView intoleranciasView;
    private IntoleranciaService intoleranciaService;
    private IntoleranciaRecetaService intoleranciaRecetaService;
    private IntoleranciaUsuarioService intoleranciaUsuarioService;
    private Binder<Intolerancia> binder = new Binder<>(Intolerancia.class);

    public IntoleranciasForm(IntoleranciasView intoleranciasView, IntoleranciaService intoleranciaService,
                             IntoleranciaUsuarioService intoleranciaUsuarioService, IntoleranciaRecetaService intoleranciaRecetaService) {
        this.intoleranciasView = intoleranciasView;
        this.intoleranciaService = intoleranciaService;
        this.intoleranciaRecetaService = intoleranciaRecetaService;
        this.intoleranciaUsuarioService = intoleranciaUsuarioService;

        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(intolerancia, buttons);
        intolerancia.setRequired(true);
        save.addClickShortcut(Key.ENTER);

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

    public void setIntolerancia(Intolerancia intolerancia) {
        binder.setBean(intolerancia);

        setVisible(intolerancia != null);
    }

    public void save() {
        Intolerancia intolerancia = binder.getBean();
        if (binder.validate().isOk() && !binder.getBean().getIntolerancia().equals("")) {
            intoleranciaService.create(intolerancia);
            this.intoleranciasView.updateList();
            setIntolerancia(null);
        } else {
            Notification.show("Rellene los campos", 5000, Notification.Position.MIDDLE);
        }
    }

    public void delete() {
        Intolerancia intolerancia = binder.getBean();
        if (binder.validate().isOk() && !binder.getBean().getIntolerancia().equals("")) {
            borrarPadres(intolerancia);
            intoleranciaService.delete(intolerancia);
            this.intoleranciasView.updateList();
            setIntolerancia(null);
        } else
            Notification.show("Rellene los campos", 5000, Notification.Position.MIDDLE);

    }

    public void borrarPadres(Intolerancia intolerancia) {
        if (!intoleranciaRecetaService.buscarIntolerancia(intolerancia).isEmpty())
            for (IntoleranciaReceta intoleranciaReceta : intoleranciaRecetaService.buscarIntolerancia(intolerancia))
                intoleranciaRecetaService.delete(intoleranciaReceta);
        if (!intoleranciaUsuarioService.buscarIntolerancia(intolerancia).isEmpty())
            for (IntoleranciaUsuario intoleranciaUsuario : intoleranciaUsuarioService.buscarIntolerancia(intolerancia))
                intoleranciaUsuarioService.delete(intoleranciaUsuario);
    }
}
