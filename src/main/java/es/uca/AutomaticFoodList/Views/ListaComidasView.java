package es.uca.AutomaticFoodList.Views;

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
import es.uca.AutomaticFoodList.Entities.ListaComida;
import es.uca.AutomaticFoodList.Entities.Usuario;
import es.uca.AutomaticFoodList.Forms.ListaComidasForm;
import es.uca.AutomaticFoodList.Forms.SeleccionPlatoForm;
import es.uca.AutomaticFoodList.GenerarListaComida;
import es.uca.AutomaticFoodList.Services.ListaComidaService;
import es.uca.AutomaticFoodList.Services.RecetaIngredienteService;
import es.uca.AutomaticFoodList.Services.RecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.klaudeta.PaginatedGrid;

@Route(value = "ListaComidasView", layout = MainView.class)
@Secured({"User", "Admin", "Gerente"})
public class ListaComidasView extends AbstractView{
    private PaginatedGrid<ListaComida> grid = new PaginatedGrid<>();
    private ListaComidaService listaComidaService;
    private ListaComidasForm listaComidasForm;
    private SeleccionPlatoForm seleccionPlatoForm;
    private RecetaService recetaService;

    @Autowired
    public ListaComidasView(ListaComidaService listaComidaService, RecetaService recetaService, RecetaIngredienteService recetaIngredienteService) {
        this.listaComidaService = listaComidaService;
        this.listaComidasForm = new ListaComidasForm(this, listaComidaService, recetaService, recetaIngredienteService);
        this.seleccionPlatoForm = new SeleccionPlatoForm(this, listaComidaService, recetaService);

        if(listaComidaService.findByUsuario(UI.getCurrent().getSession().getAttribute(Usuario.class)).isEmpty()){
            Dialog dialog = new Dialog();

            Label label = new Label("Oh, parece que no tienes ninguna lista de comidas, quieres generar una?");
            Button confirmButton = new Button("Confirmar", event -> {
                GenerarListaComida.generarListaComida();
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

        Button addPlatoButton = new Button ("Añade un plato");
        addPlatoButton.addClickListener(e -> {
            grid.asSingleSelect().clear(); //clear para que borre si habia algo antes
            listaComidasForm.setVisible(false);
            seleccionPlatoForm.setVisible(true);
        });

        HorizontalLayout toolbar = new HorizontalLayout(addPlatoButton);
        //grid.addColumn(ListaComida::getReceta).setHeader("Receta").setSortable(true);
        grid.addColumn(ListaComida -> ListaComida.getReceta().getNombre()).setHeader("Receta").setSortable(true);
        grid.addColumn(ListaComida::getComida).setHeader("Comida").setSortable(true);
        grid.addColumn(ListaComida::getPlato).setHeader("Plato").setSortable(true);
        grid.addColumn(ListaComida -> ListaComida.getFecha().getDayOfMonth() + "/" + ListaComida.getFecha().getMonthValue()).setHeader("Fecha").setSortable(true);
        //grid.setColumns("receta.nombre", "comida", "plato", "fecha");
        //grid.setItems(listaComidaService.findByUsuario(UI.getCurrent().getSession().getAttribute(Usuario.class)));

        // Sets the max number of items to be rendered on the grid for each page
        grid.setPageSize(9);

        // Sets how many pages should be visible on the pagination before and/or after the current selected page
        grid.setPaginatorSize(3);

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

    public void updateList(){
        grid.setItems(listaComidaService.findByUsuario(UI.getCurrent().getSession().getAttribute(Usuario.class)));
    }
}
