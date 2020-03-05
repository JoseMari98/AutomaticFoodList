package es.uca.AutomaticFoodList.Views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import es.uca.AutomaticFoodList.Entities.Usuario;
import es.uca.AutomaticFoodList.Forms.UsuarioDatosForm;
import es.uca.AutomaticFoodList.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "UsuarioView", layout = MainView.class)
public class UsuarioDatosView extends VerticalLayout{
    private UsuarioDatosForm usuarioDatosForm;
    private UsuarioService usuarioService;
    private H1 titulo = new H1();

    @Autowired
    UsuarioDatosView(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
        this.usuarioDatosForm = new UsuarioDatosForm( this , usuarioService);
        if(UI.getCurrent().getSession().getAttribute(Usuario.class) == null)
            titulo.add("Registrate");
        else
            titulo.add("Modifica tus datos.");
        VerticalLayout contenido = new VerticalLayout(titulo, usuarioDatosForm);
        contenido.setSizeFull();
        add(contenido);
        setSizeFull();
    }
}
