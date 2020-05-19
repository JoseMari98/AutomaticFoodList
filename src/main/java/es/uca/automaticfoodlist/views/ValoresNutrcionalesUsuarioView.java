package es.uca.automaticfoodlist.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import es.uca.automaticfoodlist.forms.ValoresNutricionalesUsuarioForm;
import es.uca.automaticfoodlist.services.UsuarioService;
import es.uca.automaticfoodlist.services.ValoresNutricionalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

@Route(value = "ValoresNutricionalesUsuarioView", layout = MainView.class)
@Secured({"User", "Admin", "Gerente"})
public class ValoresNutrcionalesUsuarioView extends AbstractView{
    private ValoresNutricionalesUsuarioForm valoresNutricionalesUsuarioForm;

    @Autowired
    ValoresNutrcionalesUsuarioView(UsuarioService usuarioService, ValoresNutricionalesService valoresNutricionalesService){
        this.valoresNutricionalesUsuarioForm = new ValoresNutricionalesUsuarioForm( this, valoresNutricionalesService, usuarioService);
        H1 titulo = new H1("Valores nutricionales");
        VerticalLayout contenido = new VerticalLayout(titulo, valoresNutricionalesUsuarioForm);
        contenido.setSizeFull();
        add(contenido);
        setSizeFull();
    }
}
