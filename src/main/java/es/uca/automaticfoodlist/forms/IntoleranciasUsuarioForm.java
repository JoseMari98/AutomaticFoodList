package es.uca.automaticfoodlist.forms;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import es.uca.automaticfoodlist.entities.Intolerancia;
import es.uca.automaticfoodlist.entities.IntoleranciaUsuario;
import es.uca.automaticfoodlist.entities.Usuario;
import es.uca.automaticfoodlist.services.IntoleranciaService;
import es.uca.automaticfoodlist.services.IntoleranciaUsuarioService;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class IntoleranciasUsuarioForm extends FormLayout {
    private Vector<Checkbox> checkboxVector = new Vector<>(9);
    private IntoleranciaUsuarioService intoleranciaUsuarioService;
    private IntoleranciaService intoleranciaService;
    private Button save = new Button("Siguiente");
    private List<IntoleranciaUsuario> intoleranciaUsuarioList;

    public IntoleranciasUsuarioForm(IntoleranciaUsuarioService intoleranciaUsuarioService, IntoleranciaService intoleranciaService) {
        this.intoleranciaService = intoleranciaService;
        this.intoleranciaUsuarioService = intoleranciaUsuarioService;
        VerticalLayout checkboxes = new VerticalLayout();

        for (Intolerancia intolerancia : intoleranciaService.findAllOrderById()) {
            checkboxVector.add(new Checkbox(intolerancia.getIntolerancia()));
        }
        if (UI.getCurrent().getSession().getAttribute(Usuario.class) != null) {
            intoleranciaUsuarioList = intoleranciaUsuarioService.buscarPorUsuario(UI.getCurrent().getSession().getAttribute(Usuario.class)); //Tuplas entre las asociaciones
            for (IntoleranciaUsuario intoleranciaUsuario : intoleranciaUsuarioList) {
                long id = intoleranciaUsuario.getIntolerancia().getId() - 1;
                int indice = (int) id;
                checkboxVector.elementAt(indice).setValue(true); //Los elementos que esten en la lista es que ya tienen la intolerancia
            }

        } else {
            intoleranciaUsuarioList = new LinkedList<>();
        }

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        for (Intolerancia intolerancia : intoleranciaService.findAllOrderById()) {
            long id = intolerancia.getId() - 1;
            int indice = (int) id;
            checkboxes.add(checkboxVector.elementAt(indice));
        }
        checkboxes.add(save);
        add(checkboxes);
        save.addClickShortcut(Key.ENTER);
        save.addClickListener(e -> save());

    }

    public void save() {
        for(Intolerancia intolerancia : intoleranciaService.findAllOrderById()) {
            IntoleranciaUsuario intoleranciaUsuario = intoleranciaUsuarioService.buscarPorUsuarioEIntolerancia(UI.getCurrent().getSession().getAttribute(Usuario.class), intolerancia);
            long id = intolerancia.getId() - 1;
            int indice = (int) id;
            if (checkboxVector.elementAt(indice).getValue() && intoleranciaUsuario == null) { //Para el caso de que se haya marcado y no exista
                IntoleranciaUsuario intoleranciaUsuario1 = new IntoleranciaUsuario();
                intoleranciaUsuario1.setIntolerancia(intolerancia); //Introducimos la intolerancia
                intoleranciaUsuario1.setUsuario(UI.getCurrent().getSession().getAttribute(Usuario.class)); //Introducimos el usuario
                intoleranciaUsuarioService.create(intoleranciaUsuario1);
            } else {
                if (!checkboxVector.elementAt(indice).getValue() && intoleranciaUsuario != null) {
                    intoleranciaUsuarioService.delete(intoleranciaUsuario);
                }
            }
        }
        UI.getCurrent().navigate("ValoresNutricionalesUsuarioView");
    }
}
