package es.uca.AutomaticFoodList.Forms;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import es.uca.AutomaticFoodList.Entities.Categoria;
import es.uca.AutomaticFoodList.Entities.Ingrediente;
import es.uca.AutomaticFoodList.Entities.PreferenciaIngrediente;
import es.uca.AutomaticFoodList.Entities.RecetaIngrediente;
import es.uca.AutomaticFoodList.Services.IngredienteService;
import es.uca.AutomaticFoodList.Services.PreferenciaIngredienteService;
import es.uca.AutomaticFoodList.Services.RecetaIngredienteService;
import es.uca.AutomaticFoodList.Views.IngredienteView;

public class IngredienteForm extends FormLayout {
    TextField nombre = new TextField("Nombre");
    TextField idApi = new TextField("Id api");
    ComboBox<Categoria> categoria = new ComboBox<>("Categoria");
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
        categoria.setRequired(true);
        nombre.setRequiredIndicatorVisible(true);
        categoria.setRequiredIndicatorVisible(true);
        categoria.setItems(Categoria.values());

        save.addClickShortcut(Key.ENTER);

        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(nombre, categoria, idApi, buttons);

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
