package es.uca.automaticfoodlist.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import es.uca.automaticfoodlist.entities.Usuario;
import es.uca.automaticfoodlist.springclasses.SecurityUtils;

/**
 * The main view contains a button and a click listener.
 */
@Theme(Lumo.class)
@Route(value = "")
@PWA(name = "AutomaticFoodList app",
        shortName = "AFL app",
        description = "Planificaci'on automatica de tu comida",
        enableInstallPrompt = true)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class MainView extends AppLayout {
    Tabs tabs = new Tabs();
    final DrawerToggle drawerToggle = new DrawerToggle();
    final VerticalLayout menuLayout = new VerticalLayout();
    Image img = new Image("https://i.imgur.com/GPpnszs.png", "Vaadin Logo");

    final boolean touchOptimized = true;
    Button logout = new Button(new Icon(VaadinIcon.SIGN_OUT));

    public MainView() {
        logout.addClickListener(e -> signOut());
        logout.addThemeVariants(ButtonVariant.LUMO_ERROR);
        img.setHeight("44px");
        img.addClickListener(e -> UI.getCurrent().navigate(""));
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.add(createTab(VaadinIcon.HOME, "Inicio", InicioView.class));

        if (!SecurityUtils.isUserLoggedIn()) {
            tabs.add(createTab(VaadinIcon.SIGN_IN, "Iniciar sesion", LoginView.class));
            tabs.add(createTab(VaadinIcon.USER_CARD, "Registrate", UsuarioDatosView.class));
        } else {
            tabs.add(createTab(VaadinIcon.PLUS, "Crear receta", CrearRecetaView.class));
            if (SecurityUtils.hasRole("User")) {
                tabs.add(createTab(VaadinIcon.RECORDS, "Mis recetas", RecetasView.class));
                tabs.add(createTab(VaadinIcon.COGS, "Configuracion dietetica", IntoleranciasUsuarioView.class));
                tabs.add(createTab(VaadinIcon.CALENDAR, "Lista de Comidas", ListaComidasView.class));
                tabs.add(createTab(VaadinIcon.LIST, "Lista de la compra", ListaCompraView.class));
                tabs.add(createTab(VaadinIcon.COG, "Configuracion de datos", UsuarioDatosView.class));
            }

            if (SecurityUtils.hasRole("Admin") || SecurityUtils.hasRole("Gerente")) {
                tabs.add(createTab(VaadinIcon.RECORDS, "Recetas", RecetasView.class));
                tabs.add(createTab(VaadinIcon.RECORDS, "Gestion ingrediente", IngredienteView.class));
                tabs.add(createTab(VaadinIcon.RECORDS, "Gestion producto", ProductoView.class));
                tabs.add(createTab(VaadinIcon.RECORDS, "Gestion valores nutricionales", ValoresNutrcionalesView.class));
            }
        }
        addToDrawer(menuLayout, tabs); //anadirlo al desplegable
        addToNavbar(touchOptimized, drawerToggle, img); //anadirlo a la barra vertical
        if (SecurityUtils.isUserLoggedIn())
            logout.getStyle().set("center", "auto");
        addToDrawer(logout);
    }

    public static Tab createTab(Component content) {
        final Tab tab = new Tab();
        tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        tab.add(content);
        return tab;
    }

    public static Tab createTab(VaadinIcon icon, String title, Class<? extends Component> viewClass) {
        return createTab(populateLink(new RouterLink(null, viewClass), icon, title));
    }

    public static <T extends HasComponents> T populateLink(T a, VaadinIcon icon, String title) {
        a.add(icon.create());
        a.add(title);
        return a;
    }

    private void signOut() {
        UI.getCurrent().getPage().executeJavaScript("location.assign('logout')");
        UI.getCurrent().getSession().close();
        UI.getCurrent().getSession().setAttribute(Usuario.class, null);
        UI.getCurrent().setPollInterval(3000);
    }
}