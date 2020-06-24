package es.uca.automaticfoodlist.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import es.uca.automaticfoodlist.entities.Usuario;
import es.uca.automaticfoodlist.entities.UsuarioReceta;
import es.uca.automaticfoodlist.forms.ListaComidasForm;
import es.uca.automaticfoodlist.forms.SeleccionPlatoForm;
import es.uca.automaticfoodlist.services.RecetaIngredienteService;
import es.uca.automaticfoodlist.services.RecetaService;
import es.uca.automaticfoodlist.services.UsuarioRecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

@Route(value = "ListaComidasView", layout = MainView.class)
@Secured({"User", "Admin"})
public class ListaComidasView extends AbstractView{
    private Grid<UsuarioReceta> grid = new Grid<>(UsuarioReceta.class);
    private UsuarioRecetaService usuarioRecetaService;
    private ListaComidasForm listaComidasForm;
    private SeleccionPlatoForm seleccionPlatoForm;

    @Autowired
    public ListaComidasView(UsuarioRecetaService usuarioRecetaService, RecetaService recetaService, RecetaIngredienteService recetaIngredienteService) {
        this.usuarioRecetaService = usuarioRecetaService;
        this.listaComidasForm = new ListaComidasForm(this, usuarioRecetaService, recetaService, recetaIngredienteService);
        this.seleccionPlatoForm = new SeleccionPlatoForm(this, usuarioRecetaService, recetaService);

        if(usuarioRecetaService.findByUsuario(UI.getCurrent().getSession().getAttribute(Usuario.class)).isEmpty()){
            Dialog dialog = new Dialog();

            Label label = new Label("Oh, parece que no tienes ninguna lista de comidas, quieres generar una?, esto llevará unos 30 segundos");
            Button confirmButton = new Button("Confirmar", event -> {
                try {
                    usuarioRecetaService.generarListaComida(UI.getCurrent().getSession().getAttribute(Usuario.class));
                    grid.setItems(usuarioRecetaService.findByUsuario(UI.getCurrent().getSession().getAttribute(Usuario.class)));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                UI.getCurrent().navigate("ListaComidasView");
                dialog.close();
            });
            confirmButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
            Button cancelButton = new Button("Cancelar", event -> {
                dialog.close();
                UI.getCurrent().navigate("Inicio");
            });
            dialog.add(label, confirmButton, cancelButton);
            dialog.open();
        }

        Button addPlatoButton = new Button("Añade un plato");
        addPlatoButton.addClickListener(e -> {
            if (usuarioRecetaService.findByUsuario(UI.getCurrent().getSession().getAttribute(Usuario.class)).size() < 21) {
                grid.asSingleSelect().clear(); //clear para que borre si habia algo antes
                listaComidasForm.setVisible(false);
                seleccionPlatoForm.setVisible(true);
            } else
                Notification.show("No se pueden añadir más recetas", 3000, Notification.Position.MIDDLE);
        });

        Button generarLista = new Button("Generar lista");

        generarLista.addClickListener(e -> {
            Dialog dialog = new Dialog();

            Label label = new Label("Vas a borrar la lista actual y generar una nueva, ¿Quieres continuar?");
            Button confirmButton = new Button("Confirmar", event -> {
                try {
                    usuarioRecetaService.generarListaComida(UI.getCurrent().getSession().getAttribute(Usuario.class));
                    grid.setItems(usuarioRecetaService.findByUsuario(UI.getCurrent().getSession().getAttribute(Usuario.class)));
                } catch (InterruptedException ee) {
                    ee.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                UI.getCurrent().navigate("ListaComidasView");
                dialog.close();
            });
            confirmButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
            Button cancelButton = new Button("Cancelar", event -> {
                dialog.close();
            });
            dialog.add(label, confirmButton, cancelButton);
            dialog.open();
        });

        HorizontalLayout toolbar = new HorizontalLayout(addPlatoButton, generarLista);
        grid.setColumns();
        grid.addColumn(UsuarioReceta -> UsuarioReceta.getFecha() == null ? "Sin Fecha" : UsuarioReceta.getFecha()).setHeader("Fecha").setSortable(true);
        grid.addColumn(UsuarioReceta -> UsuarioReceta.getComida() == null ? "Sin Comida" : UsuarioReceta.getComida()).setHeader("Comida").setSortable(true);
        grid.addColumn(UsuarioReceta -> UsuarioReceta.getReceta() == null ? "Sin Receta" : UsuarioReceta.getReceta().getNombre()).setHeader("Receta").setSortable(true);

        VerticalLayout mainContent = new VerticalLayout(grid, listaComidasForm, seleccionPlatoForm); //metemos en un objeto el grid y formulario
        mainContent.setSizeFull();
        grid.setSizeFull();

        add(toolbar, mainContent);

        setSizeFull();

        updateList();

        listaComidasForm.setListaComida(null);
        seleccionPlatoForm.setVisible(false);

        grid.asSingleSelect().addValueChangeListener(event -> {
            seleccionPlatoForm.setVisible(false);
            listaComidasForm.datosReceta(grid.asSingleSelect().getValue());
            listaComidasForm.setListaComida(grid.asSingleSelect().getValue());
        });
    }

    public void updateList() {
        grid.setItems(usuarioRecetaService.findByUsuario(UI.getCurrent().getSession().getAttribute(Usuario.class)));
        for (UsuarioReceta usuarioReceta : usuarioRecetaService.findByUsuario(UI.getCurrent().getSession().getAttribute(Usuario.class))) {
            if (usuarioReceta.getReceta() == null || usuarioReceta.getComida() == null || usuarioReceta.getFecha() == null) {
                Notification.show("Hay recetas vacias, vuelve a generar la lista de comidas", 5000, Notification.Position.MIDDLE);
                break;
            }
        }
    }
}
