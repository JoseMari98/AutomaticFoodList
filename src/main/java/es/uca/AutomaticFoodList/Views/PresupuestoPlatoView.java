package es.uca.AutomaticFoodList.Views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import es.uca.AutomaticFoodList.Forms.PresupuestoPlatoForm;
import es.uca.AutomaticFoodList.Forms.ValoresNutricionalesUsuarioForm;
import es.uca.AutomaticFoodList.Services.UsuarioService;
import es.uca.AutomaticFoodList.Services.ValoresNutricionalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

@Route(value = "PresupuestoPlatoView", layout = MainView.class)
@Secured({"User", "Admin", "Gerente"})
public class PresupuestoPlatoView extends AbstractView{
    private PresupuestoPlatoForm presupuestoPlatoForm;
    private UsuarioService usuarioService;

    @Autowired
    PresupuestoPlatoView(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
        this.presupuestoPlatoForm = new PresupuestoPlatoForm( this, usuarioService);
        H1 titulo = new H1("Presupuesto plato");
        VerticalLayout contenido = new VerticalLayout(titulo, presupuestoPlatoForm);
        contenido.setSizeFull();
        add(contenido);
        setSizeFull();
    }
}