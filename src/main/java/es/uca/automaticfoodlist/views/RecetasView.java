package es.uca.automaticfoodlist.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import es.uca.automaticfoodlist.entities.Receta;
import es.uca.automaticfoodlist.entities.Usuario;
import es.uca.automaticfoodlist.forms.RecetasForm;
import es.uca.automaticfoodlist.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.klaudeta.PaginatedGrid;

@Route(value = "GestionReceta", layout = MainView.class)
@Secured({"Admin", "Gerente", "User"})
public class RecetasView extends AbstractView {
    private PaginatedGrid<Receta> grid = new PaginatedGrid<>();
    private TextField filterText = new TextField();
    private RecetaService recetaService;
    private RecetasForm recetasForm;

    @Autowired
    public RecetasView(RecetaService recetaService, RecetaIngredienteService recetaIngredienteService, ValoresNutricionalesService valoresNutricionalesService,
                       UsuarioRecetaService usuarioRecetaService, IntoleranciaRecetaService intoleranciaRecetaService) {
        if (UI.getCurrent().getSession().getAttribute(Usuario.class) != null) {
            this.recetaService = recetaService;
            this.recetasForm = new RecetasForm(this, recetaService, valoresNutricionalesService, recetaIngredienteService, usuarioRecetaService, intoleranciaRecetaService);

            Paragraph paragraph = new Paragraph("Aquí podrás ver tus recetas creadas, si clicas en una de ellas verás su información");
            filterText.setPlaceholder("Filtrar por nombre"); //poner el campo
            filterText.setClearButtonVisible(true); //poner la cruz para borrar
            filterText.setValueChangeMode(ValueChangeMode.EAGER); //que se hagan los cambios cuando se escriba
            filterText.addValueChangeListener(event -> {
                if (recetaService.findByNombreList(filterText.getValue()) != null)
                    updateList();
                else {
                    filterText.clear();
                    Notification.show("No hay ninguna receta con ese nombre", 2000, Notification.Position.MIDDLE);
                }

            });

            HorizontalLayout toolbar = new HorizontalLayout(filterText, paragraph);

            grid.addColumn(Receta::getNombre).setHeader("Nombre").setSortable(true);
            if(UI.getCurrent().getSession().getAttribute(Usuario.class).getRole().equals("Admin"))
                grid.addColumn(Receta -> Receta.getIdApi() == null ? "Sin Id Api" : Receta.getIdApi()).setHeader("Id Api").setSortable(true);
            grid.addColumn(Receta::getPrecioAproximado).setHeader("Precio").setSortable(true);
            if(UI.getCurrent().getSession().getAttribute(Usuario.class).getRole().equals("Admin"))
                grid.addColumn(Receta -> Receta.getUsuario() == null ? "Sin usuario" : Receta.getUsuario().getUsername()).setHeader("Usuario").setSortable(true);

            grid.setPageSize(15);

            grid.setPaginatorSize(3);

            VerticalLayout mainContent = new VerticalLayout(grid, recetasForm); //metemos en un objeto el grid y formulario
            mainContent.setSizeFull();
            grid.setSizeFull();

            add(toolbar, mainContent);

            setSizeFull();

            updateList();

            recetasForm.setReceta(null);

            grid.asSingleSelect().addValueChangeListener(event -> {
                recetasForm.setReceta(grid.asSingleSelect().getValue());
                recetasForm.datosReceta(grid.asSingleSelect().getValue());
            });
        }
    }

    public void updateList() {
        if(UI.getCurrent().getSession().getAttribute(Usuario.class).getRole().equals("Admin")){
            if(filterText.isEmpty())
                grid.setItems(recetaService.findAll());
            else
                grid.setItems(recetaService.findByNombreList(filterText.getValue()));
        } else{
            if(filterText.isEmpty())
                grid.setItems(recetaService.findByUsuario(UI.getCurrent().getSession().getAttribute(Usuario.class)));
            else
                grid.setItems(recetaService.findByNombreAndUsuario(filterText.getValue(), UI.getCurrent().getSession().getAttribute(Usuario.class)));
        }
    }
}
