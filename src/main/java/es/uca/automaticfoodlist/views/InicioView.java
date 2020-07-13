package es.uca.automaticfoodlist.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import es.uca.automaticfoodlist.springclasses.SecurityUtils;

@Route(value = "", layout = MainView.class)
public class InicioView extends VerticalLayout {
    private Button configuracion = new Button("Ir");
    private Button listaComida = new Button("Ir");
    private Button listaCompra = new Button("Ir");
    private Button registrate = new Button("Ir");
    private Button login = new Button("Ir");
    private Image imagen = new Image("https://www.tododisca.com/wp-content/uploads/2019/04/dia-mundial-de-la-salud-1000x600.jpg", "fondo");

    public InicioView() {
        H1 titulo = new H1("Bienvenido/a a Automatic Food List");

        if (SecurityUtils.isUserLoggedIn()) {
            Label configuracionTexto = new Label("Configuración dietética");
            Label listaComidaTexto = new Label("Lista de comidas");
            Label listaCompraTexto = new Label("Lista de la compra");

            configuracion.addClickListener(e -> UI.getCurrent().navigate("IntoleranciasUsuarioView"));
            listaComida.addClickListener(e -> UI.getCurrent().navigate("ListaComidasView"));
            listaCompra.addClickListener(e -> UI.getCurrent().navigate("ListaCompraView"));
            listaComida.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            listaCompra.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            configuracion.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            VerticalLayout configuracionLay = new VerticalLayout(configuracionTexto, configuracion);
            VerticalLayout comidaLay = new VerticalLayout(listaComidaTexto, listaComida);
            VerticalLayout compraLay = new VerticalLayout(listaCompraTexto, listaCompra);
            configuracionLay.setAlignItems(Alignment.CENTER);
            comidaLay.setAlignItems(Alignment.CENTER);
            compraLay.setAlignItems(Alignment.CENTER);
            HorizontalLayout contenido = new HorizontalLayout(configuracionLay, comidaLay, compraLay);
            VerticalLayout main = new VerticalLayout(titulo, contenido, imagen);
            setAlignItems(Alignment.CENTER);
            add(main);
            main.setAlignItems(Alignment.CENTER);
        } else {
            Label loginTexto = new Label("Inicia sesión");
            Label registroTexto = new Label("Regístrate");

            login.addClickListener(e -> UI.getCurrent().navigate("Login"));
            registrate.addClickListener(e -> UI.getCurrent().navigate("UsuarioView"));
            VerticalLayout registrateLay = new VerticalLayout(registroTexto, registrate);
            VerticalLayout loginLay = new VerticalLayout(loginTexto, login);
            registrateLay.setAlignItems(Alignment.CENTER);
            HorizontalLayout contenido = new HorizontalLayout(registrateLay, loginLay);
            VerticalLayout main = new VerticalLayout(titulo, contenido, imagen);
            loginLay.setAlignItems(Alignment.CENTER);
            contenido.setAlignItems(Alignment.CENTER);
            main.setAlignItems(Alignment.CENTER);
            setAlignItems(Alignment.CENTER);
            login.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            registrate.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

            add(main);
        }

    }
}
