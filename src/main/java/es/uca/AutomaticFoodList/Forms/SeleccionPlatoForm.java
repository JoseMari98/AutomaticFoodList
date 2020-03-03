package es.uca.AutomaticFoodList.Forms;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import es.uca.AutomaticFoodList.Entities.*;
import es.uca.AutomaticFoodList.Services.ListaComidaService;
import es.uca.AutomaticFoodList.Services.RecetaService;
import es.uca.AutomaticFoodList.Views.ListaComidasView;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class SeleccionPlatoForm extends FormLayout {
    private DatePicker fecha = new DatePicker();
    private Select<Comida> comidaSelect = new Select<>(Comida.Desayuno, Comida.Almuerzo, Comida.Cena);
    private Select<Plato> platoSelect = new Select<>(Plato.Primero, Plato.Segundo, Plato.Postre);
    private Button generar = new Button("Generar");
    private Button cancelar = new Button("Cancelar");
    private ListaComida listaComida;
    BeanValidationBinder<ListaComida> binder = new BeanValidationBinder<>(ListaComida.class);
    private RecetaService recetaService;
    private ListaComidaService listaComidaService;
    private ListaComidasView listaComidasView;

    public SeleccionPlatoForm(ListaComidasView listaComidasView, ListaComidaService listaComidaService, RecetaService recetaService){
        this.recetaService = recetaService;
        this.listaComidaService = listaComidaService;
        this.listaComidasView = listaComidasView;
        listaComida = new ListaComida();
        H1 titulo = new H1("Introduce lo siguiente:");
        fecha.setValue(LocalDate.now());
        comidaSelect.setValue(Comida.Desayuno);
        platoSelect.setValue(Plato.Primero);
        fecha.setLabel("Fecha");
        fecha.setValue(LocalDate.now());
        fecha.setMin(LocalDate.now());
        fecha.setMax(LocalDate.now().plusDays(7));
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
        if(fecha.getValue() != null && platoSelect.getValue() != null && comidaSelect.getValue() != null) {
            listaComida.setFecha(fecha.getValue());
            listaComida.setPlato(platoSelect.getValue());
            listaComida.setUsuario(UI.getCurrent().getSession().getAttribute(Usuario.class));
            listaComida.setComida(comidaSelect.getValue());
            if(!listaComidaService.findByUsuarioAndComidaAndPlatoAndFecha(listaComida.getUsuario(), listaComida.getComida(), listaComida.getPlato(), listaComida.getFecha()).isPresent()){ //buscar si existe esta tupla
                Random random = new Random();
                LocalDate fecha = listaComida.getFecha();
                List<Receta> recetaList = recetaService.findAll();
                int numero = random.nextInt(recetaList.size());
                listaComida.setReceta(recetaList.get(numero)); //genero una comida random sin tener en cuenta los requisitos del usuario
                listaComida.setFecha(fecha.plusDays(1));
                listaComidaService.update(listaComida);
                this.listaComidasView.updateList();
                this.setVisible(false);
            } else
                Notification.show("Ya hay una comida para ese momento", 5000, Notification.Position.MIDDLE);
        } else
            Notification.show("Rellene los campos", 5000, Notification.Position.MIDDLE);
    }
}
