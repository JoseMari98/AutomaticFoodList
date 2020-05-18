package es.uca.AutomaticFoodList.Forms;

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
import com.vaadin.flow.data.binder.BeanValidationBinder;
import es.uca.AutomaticFoodList.Entities.*;
import es.uca.AutomaticFoodList.Services.UsuarioRecetaService;
import es.uca.AutomaticFoodList.Services.RecetaService;
import es.uca.AutomaticFoodList.Views.ListaComidasView;

import java.util.List;
import java.util.Random;

public class SeleccionPlatoForm extends FormLayout {
    ComboBox<FechaSemana> fecha = new ComboBox<>("Fecha");
    private Select<Comida> comidaSelect = new Select<>(Comida.Desayuno, Comida.Almuerzo, Comida.Cena);
    private Select<Plato> platoSelect = new Select<>(Plato.Primero, Plato.Segundo, Plato.Postre);
    private Button generar = new Button("Generar");
    private Button cancelar = new Button("Cancelar");
    private UsuarioReceta usuarioReceta;
    BeanValidationBinder<UsuarioReceta> binder = new BeanValidationBinder<>(UsuarioReceta.class);
    private RecetaService recetaService;
    private UsuarioRecetaService usuarioRecetaService;
    private ListaComidasView listaComidasView;

    public SeleccionPlatoForm(ListaComidasView listaComidasView, UsuarioRecetaService usuarioRecetaService, RecetaService recetaService) {
        this.recetaService = recetaService;
        this.usuarioRecetaService = usuarioRecetaService;
        this.listaComidasView = listaComidasView;
        usuarioReceta = new UsuarioReceta();
        H1 titulo = new H1("Introduce lo siguiente:");
        comidaSelect.setValue(Comida.Desayuno);
        platoSelect.setValue(Plato.Primero);
        fecha.setItems(FechaSemana.values());
        fecha.setRequired(true);
        fecha.setRequiredIndicatorVisible(true);
        platoSelect.setRequiredIndicatorVisible(true);
        comidaSelect.setRequiredIndicatorVisible(true);
        HorizontalLayout botones = new HorizontalLayout(cancelar, generar);
        VerticalLayout contenido = new VerticalLayout(titulo, fecha, comidaSelect, platoSelect, botones);
        generar.addClickListener(e -> generarAleatoria());
        cancelar.addClickListener(e -> this.setVisible(false));
        cancelar.addThemeVariants(ButtonVariant.LUMO_ERROR);
        add(contenido);
    }

    public void generarAleatoria() {
        if (fecha.getValue() != null && platoSelect.getValue() != null && comidaSelect.getValue() != null) {
            usuarioReceta.setFecha(fecha.getValue());
            usuarioReceta.setPlato(platoSelect.getValue());
            usuarioReceta.setUsuario(UI.getCurrent().getSession().getAttribute(Usuario.class));
            usuarioReceta.setComida(comidaSelect.getValue());
            if (!usuarioRecetaService.findByUsuarioAndComidaAndPlatoAndFecha(usuarioReceta.getUsuario(), usuarioReceta.getComida(), usuarioReceta.getPlato(), usuarioReceta.getFecha()).isPresent()) { //buscar si existe esta tupla
                Random random = new Random();
                List<Receta> recetaList = recetaService.findAll();
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
