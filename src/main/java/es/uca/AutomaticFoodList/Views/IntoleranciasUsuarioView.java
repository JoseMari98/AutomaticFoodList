package es.uca.AutomaticFoodList.Views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import es.uca.AutomaticFoodList.Forms.IntoleranciasUsuarioForm;
import es.uca.AutomaticFoodList.Services.IntoleranciaService;
import es.uca.AutomaticFoodList.Services.IntoleranciaUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

@Route(value = "IntoleranciasUsuarioView", layout = MainView.class)
@Secured({"User", "Admin", "Gerente"})
public class IntoleranciasUsuarioView extends AbstractView {
    private IntoleranciasUsuarioForm intoleranciasUsuarioForm;
    private IntoleranciaUsuarioService intoleranciasService;
    private IntoleranciaService intoleranciaService;

    @Autowired
    IntoleranciasUsuarioView(IntoleranciaUsuarioService intoleranciaUsuarioService, IntoleranciaService intoleranciaService){
        this.intoleranciasService = intoleranciaUsuarioService;
        this.intoleranciaService = intoleranciaService;
        this.intoleranciasUsuarioForm = new IntoleranciasUsuarioForm( this, intoleranciaUsuarioService, intoleranciaService);
        H1 titulo = new H1("Intolerancias o alergias");
        VerticalLayout contenido = new VerticalLayout(titulo, intoleranciasUsuarioForm);
        contenido.setSizeFull();
        add(contenido);
        setSizeFull();
    }
}
