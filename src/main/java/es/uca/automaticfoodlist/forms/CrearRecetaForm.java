package es.uca.automaticfoodlist.forms;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import es.uca.automaticfoodlist.entities.*;
import es.uca.automaticfoodlist.services.*;
import es.uca.automaticfoodlist.views.CrearRecetaView;

import java.util.List;
import java.util.Vector;

public class CrearRecetaForm extends FormLayout {
    private TextField nombre = new TextField("Nombre receta");
    private Vector<Checkbox> checkboxVector = new Vector<>(4);
    private NumberField precioAproximado = new NumberField("Precio aprox.");
    private NumberField caloriasPlato = new NumberField("Calorias");
    private NumberField hidratosPlato = new NumberField("Hidratos");
    private NumberField grasaPlato = new NumberField("Grasa");
    private NumberField proteinaPlato = new NumberField("Proteína");
    private Button save = new Button("Guardar receta");
    private CrearRecetaView crearRecetaView;
    private RecetaService recetaService;
    private ValoresNutricionalesService valoresNutricionalesService;
    private RecetaIngredienteService recetaIngredienteService;
    private IntoleranciaService intoleranciaService;
    private IntoleranciaRecetaService intoleranciaRecetaService;

    public CrearRecetaForm(CrearRecetaView crearRecetaView, RecetaService recetaService, RecetaIngredienteService recetaIngredienteService,
                           ValoresNutricionalesService valoresNutricionalesService, IntoleranciaService intoleranciaService, IntoleranciaRecetaService intoleranciaRecetaService) {
        this.recetaService = recetaService;
        this.intoleranciaService = intoleranciaService;
        this.intoleranciaRecetaService = intoleranciaRecetaService;
        this.recetaIngredienteService = recetaIngredienteService;
        this.crearRecetaView = crearRecetaView;
        this.valoresNutricionalesService = valoresNutricionalesService;
        H1 titulo = new H1("Crear receta");
        Paragraph descripcion = new Paragraph("Aquí podrás registrar tus propias recetas. Introduce los datos requeridos.");

        for (Intolerancia intolerancia : intoleranciaService.findAllOrderById()) {
            checkboxVector.add(new Checkbox(intolerancia.getIntolerancia()));
        }

        nombre.setRequired(true);
        nombre.setRequiredIndicatorVisible(true);
        precioAproximado.setRequiredIndicatorVisible(true);
        caloriasPlato.setRequiredIndicatorVisible(true);
        hidratosPlato.setRequiredIndicatorVisible(true);
        grasaPlato.setRequiredIndicatorVisible(true);
        proteinaPlato.setRequiredIndicatorVisible(true);

        save.addClickShortcut(Key.ENTER);

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout nombrePrecio = new HorizontalLayout(nombre, precioAproximado);
        HorizontalLayout caloriasGrasa = new HorizontalLayout(caloriasPlato, grasaPlato);
        HorizontalLayout hidratosProteina = new HorizontalLayout(hidratosPlato, proteinaPlato);
        HorizontalLayout intolerancias = new HorizontalLayout(checkboxVector.elementAt(0), checkboxVector.elementAt(1));
        HorizontalLayout intolerancias1 = new HorizontalLayout(checkboxVector.elementAt(2), checkboxVector.elementAt(3));
        VerticalLayout mainContent = new VerticalLayout(titulo, descripcion, nombrePrecio, caloriasGrasa, hidratosProteina, intolerancias, intolerancias1, save);
        add(mainContent, save);

        save.addClickListener(event -> {
            if (!recetaService.findByNombre(nombre.getValue()).isPresent()) {
                if (!crearRecetaView.ingredienteList.isEmpty())
                    save();
                else
                    Notification.show("No hay ingredientes", 5000, Notification.Position.MIDDLE);
            } else
                Notification.show("Nombre de receta repetido", 5000, Notification.Position.MIDDLE);
        });
    }

    public void save() {
        Receta receta = crearRecetaView.receta;
        List<RecetaIngrediente> recetaIngredienteList = crearRecetaView.ingredienteList;
        ValoresNutricionales valoresNutricionales = new ValoresNutricionales();
        if (nombre.getValue() != null && (precioAproximado.getValue() != null && precioAproximado.getValue() > 0) && (caloriasPlato.getValue() != null && caloriasPlato.getValue() > 0) &&
                (grasaPlato.getValue() != null && grasaPlato.getValue() > 0) && (hidratosPlato.getValue() != null && hidratosPlato.getValue() > 0) &&
                (proteinaPlato.getValue() != null && proteinaPlato.getValue() > 0)) {
            valoresNutricionales.setUsuario(null);
            valoresNutricionales.setProteinaPlato(proteinaPlato.getValue().intValue());
            valoresNutricionales.setHidratosPlato(hidratosPlato.getValue().intValue());
            valoresNutricionales.setGrasaPlato(grasaPlato.getValue().intValue());
            valoresNutricionales.setCaloriasPlato(caloriasPlato.getValue().intValue());
            valoresNutricionales.setReceta(null);
            valoresNutricionalesService.create(valoresNutricionales);

            receta.setPrecioAproximado(precioAproximado.getValue());
            receta.setNombre(nombre.getValue());
            receta.setIdApi(null);
            receta.setUsuario(UI.getCurrent().getSession().getAttribute(Usuario.class));
            receta.setValoresNutricionales(valoresNutricionales);
            receta = recetaService.create(receta);

            int i = 0;
            for (Intolerancia intolerancia : intoleranciaService.findAllOrderById()) {
                if (checkboxVector.elementAt(i).getValue()) { //Para el caso de que se haya marcado y no exista
                    IntoleranciaReceta intoleranciaReceta = new IntoleranciaReceta();
                    intoleranciaReceta.setIntolerancia(intolerancia); //Introducimos la intolerancia
                    intoleranciaReceta.setReceta(receta); //Introducimos el usuario
                    intoleranciaRecetaService.create(intoleranciaReceta);
                }
                i++;
            }

            for (RecetaIngrediente recetaIngrediente : recetaIngredienteList) {
                recetaIngrediente.setReceta(receta);
                recetaIngredienteService.create(recetaIngrediente);
            }
            Notification.show("Receta introducida con éxito", 3000, Notification.Position.MIDDLE);
            UI.getCurrent().navigate("GestionReceta");
        } else {
            Notification.show("Rellene los campos", 5000, Notification.Position.MIDDLE);
        }
    }
}
