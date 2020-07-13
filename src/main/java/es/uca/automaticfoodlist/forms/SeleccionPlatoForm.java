package es.uca.automaticfoodlist.forms;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import es.uca.automaticfoodlist.entities.*;
import es.uca.automaticfoodlist.services.HorarioComidasService;
import es.uca.automaticfoodlist.services.RecetaService;
import es.uca.automaticfoodlist.services.UsuarioRecetaService;
import es.uca.automaticfoodlist.views.ListaComidasView;

import java.util.List;
import java.util.Random;

public class SeleccionPlatoForm extends FormLayout {
    ComboBox<FechaSemana> fecha = new ComboBox<>("Fecha");
    private Select<Comida> comidaSelect = new Select<>(Comida.Desayuno, Comida.Almuerzo, Comida.Cena);
    private Button generar = new Button("Generar");
    private Button cancelar = new Button("Cancelar");
    private UsuarioReceta usuarioReceta;
    private RecetaService recetaService;
    private HorarioComidasService horarioComidasService;
    private UsuarioRecetaService usuarioRecetaService;
    private ListaComidasView listaComidasView;
    private Random random = new Random();

    public SeleccionPlatoForm(ListaComidasView listaComidasView, UsuarioRecetaService usuarioRecetaService, RecetaService recetaService, HorarioComidasService horarioComidasService) {
        this.recetaService = recetaService;
        this.horarioComidasService = horarioComidasService;
        this.usuarioRecetaService = usuarioRecetaService;
        this.listaComidasView = listaComidasView;
        usuarioReceta = new UsuarioReceta();
        H1 titulo = new H1("Introduce lo siguiente:");
        comidaSelect.setValue(Comida.Desayuno);
        fecha.setItems(FechaSemana.values());
        fecha.setRequired(true);
        fecha.setRequiredIndicatorVisible(true);
        comidaSelect.setRequiredIndicatorVisible(true);
        HorizontalLayout botones = new HorizontalLayout(cancelar, generar);
        VerticalLayout contenido = new VerticalLayout(titulo, fecha, comidaSelect, botones);
        generar.addClickListener(e -> generarAleatoria());
        cancelar.addClickListener(e -> this.setVisible(false));
        cancelar.addThemeVariants(ButtonVariant.LUMO_ERROR);
        add(contenido);
    }

    public void generarAleatoria() {
        if (fecha.getValue() != null && comidaSelect.getValue() != null) {
            usuarioReceta.setFecha(fecha.getValue());
            usuarioReceta.setUsuario(UI.getCurrent().getSession().getAttribute(Usuario.class));
            usuarioReceta.setComida(comidaSelect.getValue());
            if (!usuarioRecetaService.findByUsuarioAndComidaAndFecha(usuarioReceta.getUsuario(), usuarioReceta.getComida(), usuarioReceta.getFecha()).isPresent()) { //buscar si existe esta tupla
                List<Receta> recetaList = horarioComidasService.recetasAdecuadas(UI.getCurrent().getSession().getAttribute(Usuario.class));
                int numero = random.nextInt(recetaList.size());
                usuarioReceta.setReceta(recetaList.get(numero)); //genero una comida random sin tener en cuenta los requisitos del usuario
                usuarioRecetaService.update(usuarioReceta);
                this.listaComidasView.updateList();
                this.setVisible(false);
            } else
                Notification.show("Ya hay una comida para ese momento", 5000, Notification.Position.MIDDLE);
        } else
            Notification.show("Rellene los campos", 5000, Notification.Position.MIDDLE);
    }
}
