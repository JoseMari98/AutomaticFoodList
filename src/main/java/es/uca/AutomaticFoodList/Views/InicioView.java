package es.uca.AutomaticFoodList.Views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.model.Responsive;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;

@Route(value = "Inicio", layout = MainView.class)
public class InicioView extends VerticalLayout {
    private Button configuracion = new Button("Configuracion dietetica");
    private Button listaComida = new Button("Lista de comidas");
    private Image imagen = new Image("https://www.tododisca.com/wp-content/uploads/2019/04/dia-mundial-de-la-salud-1000x600.jpg", "fondo");
    public InicioView(){
        H1 titulo = new H1("Bienvenido a Automatic Food List");
        configuracion.addClickListener(e -> UI.getCurrent().navigate("IntoleranciasUsuarioView"));
        listaComida.addClickListener(e -> UI.getCurrent().navigate("ListaComidaView"));
        VerticalLayout main = new VerticalLayout(titulo, imagen);
        add(main);
    }
}
