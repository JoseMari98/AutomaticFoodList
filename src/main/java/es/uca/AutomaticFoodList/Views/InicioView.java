package es.uca.AutomaticFoodList.Views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;

@Route(value = "Inicio", layout = MainView.class)
public class InicioView extends VerticalLayout {
    private Button configuracion = new Button("Configuracion dietetica");
    private Button listaComida = new Button("Lista de comidas");
    public InicioView(){
        configuracion.addClickListener(e -> UI.getCurrent().navigate("IntoleranciasUsuarioView"));
        listaComida.addClickListener(e -> UI.getCurrent().navigate("ListaComidaView"));
        VerticalLayout main = new VerticalLayout(configuracion, listaComida);
        add(main);
    }
}
