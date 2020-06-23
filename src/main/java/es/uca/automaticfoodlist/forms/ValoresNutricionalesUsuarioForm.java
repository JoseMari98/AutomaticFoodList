package es.uca.automaticfoodlist.forms;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import es.uca.automaticfoodlist.entities.Signo;
import es.uca.automaticfoodlist.entities.Usuario;
import es.uca.automaticfoodlist.entities.ValoresNutricionales;
import es.uca.automaticfoodlist.services.UsuarioService;
import es.uca.automaticfoodlist.services.ValoresNutricionalesService;

import java.util.Vector;

public class ValoresNutricionalesUsuarioForm extends FormLayout {
    private UsuarioService usuarioService;
    private ValoresNutricionalesService valoresNutricionalesService;
    private Button save = new Button("Siguiente");
    private NumberField caloriasPlato = new NumberField("Kcal");
    private NumberField grasaPlato = new NumberField("Gramos de grasa");
    private NumberField hidratosPlato = new NumberField("Gramos de carbohidratos");
    private NumberField proteinaPlato = new NumberField("Gramos de proteina");
    private Vector<Select<Signo>> selectVector = new Vector<>(4);
    private BeanValidationBinder<ValoresNutricionales> binder = new BeanValidationBinder<>(ValoresNutricionales.class);
    private ValoresNutricionales valoresNutricionales = new ValoresNutricionales();

    public ValoresNutricionalesUsuarioForm(ValoresNutricionalesService valoresNutricionalesService, UsuarioService usuarioService) {
        this.valoresNutricionalesService = valoresNutricionalesService;
        this.usuarioService = usuarioService;

        if (UI.getCurrent().getSession().getAttribute(Usuario.class) != null)
            if (usuarioService.loadUserByUsername(UI.getCurrent().getSession().getAttribute(Usuario.class).getUsername()).getValoresNutricionales() != null)
                binder.setBean(usuarioService.loadUserByUsername(UI.getCurrent().getSession().getAttribute(Usuario.class).getUsername()).getValoresNutricionales());

        for (int i = 0; i < 4; i++) {
            selectVector.add(new Select<>(Signo.Mayor, Signo.Menor));
            selectVector.elementAt(i).setLabel("Mayor o menor que: ");
            selectVector.elementAt(i).setEnabled(false);
            selectVector.elementAt(i).setValue(Signo.Menor);
        }

        caloriasPlato.addValueChangeListener(e -> {
            if(!caloriasPlato.isEmpty()) {
                selectVector.elementAt(0).setEnabled(true);
                selectVector.elementAt(0).setRequiredIndicatorVisible(true);
            }
            else {
                selectVector.elementAt(0).setRequiredIndicatorVisible(false);
                selectVector.elementAt(0).setEnabled(false);
            }
        });

        grasaPlato.addValueChangeListener(e -> {
            if(!grasaPlato.isEmpty()) {
                selectVector.elementAt(1).setEnabled(true);
                selectVector.elementAt(1).setRequiredIndicatorVisible(true);
            }
            else {
                selectVector.elementAt(1).setRequiredIndicatorVisible(false);
                selectVector.elementAt(1).setEnabled(false);
            }
        });

        hidratosPlato.addValueChangeListener(e -> {
            if(!hidratosPlato.isEmpty()) {
                selectVector.elementAt(2).setEnabled(true);
                selectVector.elementAt(2).setRequiredIndicatorVisible(true);
            }
            else {
                selectVector.elementAt(2).setRequiredIndicatorVisible(false);
                selectVector.elementAt(2).setEnabled(false);
            }
        });

        proteinaPlato.addValueChangeListener(e -> {
            if(!proteinaPlato.isEmpty()) {
                selectVector.elementAt(3).setEnabled(true);
                selectVector.elementAt(3).setRequiredIndicatorVisible(true);
            }
            else {
                selectVector.elementAt(3).setEnabled(false);
                selectVector.elementAt(3).setRequiredIndicatorVisible(false);
            }
        });

        grasaPlato.setMin(0);
        grasaPlato.setMax(50);

        proteinaPlato.setMin(0);
        proteinaPlato.setMax(50);

        hidratosPlato.setMin(0);
        hidratosPlato.setMax(50);

        caloriasPlato.setMin(0);
        caloriasPlato.setMax(1000);

        caloriasPlato.addValueChangeListener(e -> {
            if (caloriasPlato.getValue() > 1000 || caloriasPlato.getValue() < 0) {
                caloriasPlato.setValue(0.0);
                Notification.show("El valor debe estar entre 0 y 1000", 3000, Notification.Position.MIDDLE);
            }
        });

        proteinaPlato.addValueChangeListener(e -> {
            if (proteinaPlato.getValue() > 50 || proteinaPlato.getValue() < 0) {
                proteinaPlato.setValue(0.0);
                Notification.show("El valor debe estar entre 0 y 50", 3000, Notification.Position.MIDDLE);
            }
        });

        hidratosPlato.addValueChangeListener(e -> {
            if (hidratosPlato.getValue() > 50 || hidratosPlato.getValue() < 0) {
                hidratosPlato.setValue(0.0);
                Notification.show("El valor debe estar entre 0 y 50", 3000, Notification.Position.MIDDLE);
            }
        });

        grasaPlato.addValueChangeListener(e -> {
            if (grasaPlato.getValue() > 50 || grasaPlato.getValue() < 0) {
                grasaPlato.setValue(0.0);
                Notification.show("El valor debe estar entre 0 y 50", 3000, Notification.Position.MIDDLE);
            }
        });

        HorizontalLayout calorias = new HorizontalLayout(caloriasPlato, selectVector.elementAt(0));
        HorizontalLayout grasa = new HorizontalLayout(grasaPlato, selectVector.elementAt(1));
        HorizontalLayout hidratos = new HorizontalLayout(hidratosPlato, selectVector.elementAt(2));
        HorizontalLayout proteina = new HorizontalLayout(proteinaPlato, selectVector.elementAt(3));


        add(calorias, grasa, hidratos, proteina);

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(save);
        save.addClickShortcut(Key.ENTER);
        binder.forField(caloriasPlato).withConverter(new ValoresNutricionales.MyConverter()).bind(ValoresNutricionales::getCaloriasPlato, ValoresNutricionales::setCaloriasPlato);
        binder.forField(grasaPlato).withConverter(new ValoresNutricionales.MyConverter()).bind(ValoresNutricionales::getGrasaPlato, ValoresNutricionales::setGrasaPlato);
        binder.forField(hidratosPlato).withConverter(new ValoresNutricionales.MyConverter()).bind(ValoresNutricionales::getHidratosPlato, ValoresNutricionales::setHidratosPlato);
        binder.forField(proteinaPlato).withConverter(new ValoresNutricionales.MyConverter()).bind(ValoresNutricionales::getProteinaPlato, ValoresNutricionales::setProteinaPlato);
        save.addClickListener(event -> save());
    }

    public void save() {
        if(!caloriasPlato.isEmpty())
            valoresNutricionales.setCaloriasPlato(caloriasPlato.getValue().intValue());
        if(!grasaPlato.isEmpty())
            valoresNutricionales.setGrasaPlato(grasaPlato.getValue().intValue());
        if(!hidratosPlato.isEmpty())
            valoresNutricionales.setHidratosPlato(hidratosPlato.getValue().intValue());
        if(!proteinaPlato.isEmpty())
            valoresNutricionales.setProteinaPlato(proteinaPlato.getValue().intValue());
        binder.setBean(valoresNutricionales);
        Usuario usuario = UI.getCurrent().getSession().getAttribute(Usuario.class);
        String signos = "";
        if(binder.validate().isOk()) {
            valoresNutricionalesService.create(valoresNutricionales);
            if (selectVector.elementAt(0).isEmpty() || caloriasPlato.getValue() <= 0)
                signos += "NULL,";
            else
                signos += selectVector.elementAt(0).getValue().toString() + ",";
            if (selectVector.elementAt(1).isEmpty() || grasaPlato.getValue() <= 0)
                signos += "NULL,";
            else
                signos += selectVector.elementAt(1).getValue().toString() + ",";
            if (selectVector.elementAt(2).isEmpty() || hidratosPlato.getValue() <= 0)
                signos += "NULL,";
            else
                signos += selectVector.elementAt(2).getValue().toString() + ",";
            if (selectVector.elementAt(3).isEmpty() || proteinaPlato.getValue() <= 0)
                signos += "NULL";
            else
                signos += selectVector.elementAt(3).getValue().toString();
            usuario.setSignosValoresNutrcionales(signos);
            usuario.setValoresNutricionales(valoresNutricionales);
            usuarioService.update(usuario);
            UI.getCurrent().navigate("IngredientesUsuarioView");
        }
        else {
            Notification.show("Rellene los campos", 5000, Notification.Position.MIDDLE);
        }
    }
}
