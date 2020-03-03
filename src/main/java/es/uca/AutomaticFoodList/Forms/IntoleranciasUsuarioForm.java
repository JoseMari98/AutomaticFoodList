package es.uca.AutomaticFoodList.Forms;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import es.uca.AutomaticFoodList.Entities.IntoleranciaUsuario;
import es.uca.AutomaticFoodList.Entities.Intolerancia;
import es.uca.AutomaticFoodList.Entities.Usuario;
import es.uca.AutomaticFoodList.Services.IntoleranciaService;
import es.uca.AutomaticFoodList.Services.IntoleranciaUsuarioService;
import es.uca.AutomaticFoodList.Views.IntoleranciasUsuarioView;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class IntoleranciasUsuarioForm extends FormLayout {
    private Vector<Checkbox> checkboxVector = new Vector<>(9);
    private IntoleranciasUsuarioView intoleranciasUsuarioView;
    private IntoleranciaUsuarioService intoleranciaUsuarioService;
    private IntoleranciaService intoleranciaService;
    private Button save = new Button("Siguiente");
    private List<IntoleranciaUsuario> intoleranciaUsuarioList;

    public IntoleranciasUsuarioForm(IntoleranciasUsuarioView intoleranciasUsuarioView, IntoleranciaUsuarioService intoleranciaUsuarioService, IntoleranciaService intoleranciaService) {
        this.intoleranciaService = intoleranciaService;
        this.intoleranciasUsuarioView = intoleranciasUsuarioView;
        this.intoleranciaUsuarioService = intoleranciaUsuarioService;
        VerticalLayout checkboxes = new VerticalLayout();

        for(Intolerancia intolerancia : intoleranciaService.findAllOrderById()){
            checkboxVector.add(new Checkbox(intolerancia.getIntolerancia()));
        }
        if(UI.getCurrent().getSession().getAttribute(Usuario.class) != null) {
            intoleranciaUsuarioList = intoleranciaUsuarioService.buscarPorUsuario(UI.getCurrent().getSession().getAttribute(Usuario.class)); //Tuplas entre las asociaciones
            for(IntoleranciaUsuario intoleranciaUsuario : intoleranciaUsuarioList){
                checkboxVector.elementAt(Long.valueOf(intoleranciaUsuario.getIntolerancia().getId() - 1).intValue()).setValue(true); //Los elementos que esten en la lista es que ya tienen la intolerancia
            }

        } else {
            intoleranciaUsuarioList = new LinkedList<>();
        }

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        for(Intolerancia intolerancia : intoleranciaService.findAllOrderById())
            checkboxes.add(checkboxVector.elementAt(Long.valueOf(intolerancia.getId() - 1).intValue()));
        checkboxes.add(save);
        add(checkboxes);
        save.addClickShortcut(Key.ENTER);
        save.addClickListener(e -> save());

    }

    public void save() {
        int cont = 0;
        for(Intolerancia intolerancia : intoleranciaService.findAllOrderById()){
            IntoleranciaUsuario intoleranciaUsuario = intoleranciaUsuarioService.buscarPorUsuarioEIntolerancia(UI.getCurrent().getSession().getAttribute(Usuario.class), intolerancia);
            if(checkboxVector.elementAt(Long.valueOf(intolerancia.getId() - 1).intValue()).getValue() && intoleranciaUsuario == null){ //Para el caso de que se haya marcado y no exista
                IntoleranciaUsuario intoleranciaUsuario1 = new IntoleranciaUsuario();
                intoleranciaUsuario1.setIntolerancia(intolerancia); //Introducimos la intolerancia
                intoleranciaUsuario1.setUsuario(UI.getCurrent().getSession().getAttribute(Usuario.class)); //Introducimos el usuario
                intoleranciaUsuarioService.create(intoleranciaUsuario1);
            } else{
                if(!checkboxVector.elementAt(Long.valueOf(intolerancia.getId() - 1).intValue()).getValue() && intoleranciaUsuario != null){
                    intoleranciaUsuarioService.delete(intoleranciaUsuario);
                }
            }
        }
        UI.getCurrent().navigate("ValoresNutricionalesUsuarioView");
    }
}
