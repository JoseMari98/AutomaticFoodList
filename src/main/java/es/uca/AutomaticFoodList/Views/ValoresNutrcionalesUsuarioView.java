package es.uca.AutomaticFoodList.Views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import es.uca.AutomaticFoodList.Forms.ValoresNutricionalesUsuarioForm;
import es.uca.AutomaticFoodList.Services.UsuarioService;
import es.uca.AutomaticFoodList.Services.ValoresNutricionalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

@Route(value = "ValoresNutricionalesUsuarioView", layout = MainView.class)
@Secured({"User", "Admin", "Gerente"})
public class ValoresNutrcionalesUsuarioView extends AbstractView{
    private ValoresNutricionalesUsuarioForm valoresNutricionalesUsuarioForm;
    private ValoresNutricionalesService valoresNutricionalesService;
    private UsuarioService usuarioService;

    @Autowired
    ValoresNutrcionalesUsuarioView(UsuarioService usuarioService, ValoresNutricionalesService valoresNutricionalesService){
        this.valoresNutricionalesService = valoresNutricionalesService;
        this.usuarioService = usuarioService;
        this.valoresNutricionalesUsuarioForm = new ValoresNutricionalesUsuarioForm( this, valoresNutricionalesService, usuarioService);
        H1 titulo = new H1("Umbrales de valores nutricionales por cada 100g del plato");
        VerticalLayout contenido = new VerticalLayout(titulo, valoresNutricionalesUsuarioForm);
        contenido.setSizeFull();
        add(contenido);
        setSizeFull();
    }
}
