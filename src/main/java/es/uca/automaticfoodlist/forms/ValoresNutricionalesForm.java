package es.uca.automaticfoodlist.forms;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import es.uca.automaticfoodlist.entities.Receta;
import es.uca.automaticfoodlist.entities.Usuario;
import es.uca.automaticfoodlist.entities.ValoresNutricionales;
import es.uca.automaticfoodlist.services.RecetaService;
import es.uca.automaticfoodlist.services.UsuarioService;
import es.uca.automaticfoodlist.services.ValoresNutricionalesService;
import es.uca.automaticfoodlist.views.ValoresNutrcionalesView;

public class ValoresNutricionalesForm extends FormLayout {
    private NumberField caloriasPlato = new NumberField("Calorías");
    private NumberField hidratosPlato = new NumberField("Hidratos");
    private NumberField grasaPlato = new NumberField("Grasa");
    private NumberField proteinaPlato = new NumberField("Proteína");
    private ComboBox<Usuario> usuario = new ComboBox<>("Usuario");
    private ComboBox<Receta> receta = new ComboBox<>("Receta");
    private Button save = new Button("Añadir");
    private Button delete = new Button("Borrar");
    private ValoresNutrcionalesView valoresNutrcionalesView;
    private BeanValidationBinder<ValoresNutricionales> binder = new BeanValidationBinder<>(ValoresNutricionales.class);
    private ValoresNutricionalesService valoresNutricionalesService;
    private UsuarioService usuarioService;
    private RecetaService recetaService;
    private ValoresNutricionales valoresNutricionales;

    public ValoresNutricionalesForm(ValoresNutrcionalesView valoresNutrcionalesView, ValoresNutricionalesService valoresNutricionalesService, RecetaService recetaService, UsuarioService usuarioService) {
        this.valoresNutrcionalesView = valoresNutrcionalesView;
        this.valoresNutricionalesService = valoresNutricionalesService;
        this.recetaService = recetaService;
        this.usuarioService = usuarioService;
        usuario.setRequired(true);
        usuario.setRequiredIndicatorVisible(true);
        receta.setRequired(true);
        receta.setRequiredIndicatorVisible(true);
        caloriasPlato.setRequiredIndicatorVisible(true);
        hidratosPlato.setRequiredIndicatorVisible(true);
        grasaPlato.setRequiredIndicatorVisible(true);
        proteinaPlato.setRequiredIndicatorVisible(true);
        usuario.setItems(usuarioService.findAll());
        usuario.setItemLabelGenerator(Usuario::getNombre);
        receta.setItems(recetaService.findAll());
        receta.setItemLabelGenerator(Receta::getNombre);

        save.addClickShortcut(Key.ENTER);

        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(caloriasPlato, hidratosPlato, grasaPlato, proteinaPlato, usuario, receta, buttons);

        binder.forField(caloriasPlato).withConverter(new ValoresNutricionales.MyConverter()).bind(ValoresNutricionales::getCaloriasPlato, ValoresNutricionales::setCaloriasPlato);
        binder.forField(grasaPlato).withConverter(new ValoresNutricionales.MyConverter()).bind(ValoresNutricionales::getGrasaPlato, ValoresNutricionales::setGrasaPlato);
        binder.forField(hidratosPlato).withConverter(new ValoresNutricionales.MyConverter()).bind(ValoresNutricionales::getHidratosPlato, ValoresNutricionales::setHidratosPlato);
        binder.forField(proteinaPlato).withConverter(new ValoresNutricionales.MyConverter()).bind(ValoresNutricionales::getProteinaPlato, ValoresNutricionales::setProteinaPlato);


        save.addClickListener(event -> {
            if (binder.getBean() != null)
                save();
        });
        delete.addClickListener(event -> {
            if (binder.getBean() != null)
                delete();
        });
    }

    public void setValoresNutricionales(ValoresNutricionales valoresNutricionales) {
        binder.setBean(valoresNutricionales);

        if (valoresNutricionales != null) {
            this.valoresNutricionales = valoresNutricionales;
            proteinaPlato.setValue((double) valoresNutricionales.getProteinaPlato());
            caloriasPlato.setValue((double) valoresNutricionales.getCaloriasPlato());
            grasaPlato.setValue((double) valoresNutricionales.getGrasaPlato());
            hidratosPlato.setValue((double) valoresNutricionales.getHidratosPlato());
            usuario.setValue(valoresNutricionales.getUsuario());
            receta.setValue(valoresNutricionales.getReceta());
            setVisible(true);
        } else {
            this.valoresNutricionales = null;
            setVisible(false);
        }

        //setVisible(valoresNutricionales != null);
    }

    public void save() {
        if(binder.validate().isOk()) {
            if (valoresNutricionales.getUsuario() != null) {
                Usuario usuario = valoresNutricionales.getUsuario();
                usuario.setValoresNutricionales(null);
                usuarioService.update(usuario);
            }

            if (valoresNutricionales.getReceta() != null) {
                Receta receta = valoresNutricionales.getReceta();
                receta.setValoresNutricionales(null);
                recetaService.update(receta);
            }

            valoresNutricionales.setReceta(null);
            valoresNutricionales.setUsuario(null);
            valoresNutricionalesService.create(valoresNutricionales);
            if (!usuario.isEmpty()) {
                //hacer que sea nulo o lo contrario
                Usuario usuarioObject = usuario.getValue();
                usuarioObject.setValoresNutricionales(valoresNutricionales);
                usuarioService.update(usuarioObject);
            }
            if (!receta.isEmpty()) {
                Receta recetaObject = receta.getValue();
                recetaObject.setValoresNutricionales(valoresNutricionales);
                recetaService.update(recetaObject);
            }
            //setValoresNutricionales(null);
            setVisible(false);
            //this.valoresNutrcionalesView.updateList();
            UI.getCurrent().getPage().reload();
        }
        else {
            Notification.show("Rellene los campos", 5000, Notification.Position.MIDDLE);
        }
    }

    public void delete() {
        ValoresNutricionales valoresNutricionales = binder.getBean();
        if(binder.validate().isOk()) {
            //hacer que sea nulo en usuario y receta
            if(!usuarioService.findByValores(valoresNutricionales).isEmpty()){
                for(Usuario usuario : usuarioService.findByValores(valoresNutricionales)) {
                    usuario.setValoresNutricionales(null);
                    usuarioService.update(usuario);
                }
            }
            if(!recetaService.findByValores(valoresNutricionales).isEmpty()){
                for (Receta receta : recetaService.findByValores(valoresNutricionales)) {
                    receta.setValoresNutricionales(null);
                    recetaService.update(receta);
                }
            }
            valoresNutricionales.setReceta(null);
            valoresNutricionales.setUsuario(null);
            valoresNutricionalesService.delete(valoresNutricionales);
            //this.valoresNutrcionalesView.updateList();
            setValoresNutricionales(null);
            UI.getCurrent().getPage().reload();

        } else
            Notification.show("Rellene los campos", 5000, Notification.Position.MIDDLE);
    }
}
