package es.uca.automaticfoodlist.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import es.uca.automaticfoodlist.forms.IntoleranciasUsuarioForm;
import es.uca.automaticfoodlist.services.IntoleranciaService;
import es.uca.automaticfoodlist.services.IntoleranciaUsuarioService;
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
        H1 titulo = new H1("Intolerancias, alergias o dieta");
        VerticalLayout contenido = new VerticalLayout(titulo, intoleranciasUsuarioForm);
        contenido.setSizeFull();
        add(contenido);
        setSizeFull();
    }
}
