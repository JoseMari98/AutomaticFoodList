package es.uca.automaticfoodlist.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import es.uca.automaticfoodlist.entities.Intolerancia;
import es.uca.automaticfoodlist.forms.IntoleranciasForm;
import es.uca.automaticfoodlist.services.IntoleranciaRecetaService;
import es.uca.automaticfoodlist.services.IntoleranciaService;
import es.uca.automaticfoodlist.services.IntoleranciaUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

@Route(value = "IntoleranciasView", layout = MainView.class)
@Secured("Admin")
public class IntoleranciasView extends AbstractView {
    private Grid<Intolerancia> grid = new Grid<>(Intolerancia.class);
    private IntoleranciasForm intoleranciasForm;
    private IntoleranciaService intoleranciaService;

    @Autowired
    public IntoleranciasView(IntoleranciaService intoleranciaService, IntoleranciaUsuarioService intoleranciaUsuarioService, IntoleranciaRecetaService intoleranciaRecetaService) {
        this.intoleranciaService = intoleranciaService;
        this.intoleranciasForm = new IntoleranciasForm(this, intoleranciaService, intoleranciaUsuarioService, intoleranciaRecetaService);

        Button addIntoleranciaBtn = new Button("Añadir Intolerancia");
        addIntoleranciaBtn.addClickListener(e -> {
            grid.asSingleSelect().clear(); //clear para que borre si habia algo antes
            intoleranciasForm.setIntolerancia(new Intolerancia()); //instancia un nuevo customer
        });

        HorizontalLayout toolbar = new HorizontalLayout(addIntoleranciaBtn);

        grid.setColumns("intolerancia");

        HorizontalLayout mainContent = new HorizontalLayout(grid, intoleranciasForm); //metemos en un objeto el grid y formulario
        mainContent.setSizeFull();
        grid.setSizeFull();

        add(toolbar, mainContent);

        setSizeFull();

        updateList();

        intoleranciasForm.setIntolerancia(null);

        grid.asSingleSelect().addValueChangeListener(event -> intoleranciasForm.setIntolerancia(grid.asSingleSelect().getValue()));
    }

    public void updateList() {
        grid.setItems(intoleranciaService.findAll());
    }
}
