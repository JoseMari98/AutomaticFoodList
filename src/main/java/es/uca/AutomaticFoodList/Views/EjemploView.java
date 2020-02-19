package es.uca.AutomaticFoodList.Views;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.security.access.annotation.Secured;
import com.vaadin.flow.component.notification.Notification;

import javax.swing.*;
import java.io.IOException;


@Route(value = "Info", layout = MainView.class)
public class EjemploView extends VerticalLayout {

    private Long id;
    //private VehiculoService service;
    private Button volver = new Button("Regresar");

    //@Autowired
    public EjemploView(/*VehiculoService service*/) {
        //this.service = service;
        //this.id = UI.getCurrent().getSession().getAttribute(Long.class);
        try {
            //Vehiculo v = service.buscarIdVehiculo(id).get();
            Html nombre = new Html("<h1>" + "hola" + "</h1>");
            Html etiqueta2 = new Html("<h2>Información:</h2>");
            /*Label cuerpo = new Label("Precio: " + v.getPrecio() + " €/dia");
            Label marca = new Label("El vehiculo es: " + v.getMarca().getMarca() + " " + v.getTipo().getTipo());
            Label cuerpo4 = new Label("Tipo de motor: " + v.getMotor().toString());
            Label cuerpo5 = new Label("Aire acondicionado: " + v.getAc().toString());
            Label cuerpo6 = new Label("Ciudad: " + v.getCiudad().toString());
            Label cuerpo7 = new Label("Nº de puertas: " + v.getPuertas().intValue());
            Label cuerpo8 = new Label("Nº de plazas: " + v.getPlazas().intValue());*/
            VerticalLayout layout = new VerticalLayout(nombre, etiqueta2, /*cuerpo, marca,cuerpo4, cuerpo6, cuerpo7, cuerpo8, cuerpo5*/volver);
            add(layout);
        } catch (InvalidDataAccessApiUsageException e) {
            Notification.show("¡No hay parametros para mostrar ningun vehículo!");
            UI.getCurrent().navigate("");
        }
        volver.addClickListener(e -> UI.getCurrent().navigate("search"));
        volver.addClickShortcut(Key.ENTER);

    }

}