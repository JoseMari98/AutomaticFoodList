package es.uca.automaticfoodlist.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
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

    @Autowired
    IntoleranciasUsuarioView(IntoleranciaUsuarioService intoleranciaUsuarioService, IntoleranciaService intoleranciaService) {
        this.intoleranciasUsuarioForm = new IntoleranciasUsuarioForm(intoleranciaUsuarioService, intoleranciaService);
        H1 titulo = new H1("Intolerancias o dieta");
        Paragraph descripcion = new Paragraph("Marca las casillas si eres alérgico o sigues alguna de las siguientes dietas");
        VerticalLayout contenido = new VerticalLayout(titulo, descripcion, intoleranciasUsuarioForm);
        contenido.setSizeFull();
        add(contenido);
        setSizeFull();
    }
}
