package es.uca.AutomaticFoodList.Forms;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import es.uca.AutomaticFoodList.Entities.*;
import es.uca.AutomaticFoodList.Services.RecetaIngredienteService;
import es.uca.AutomaticFoodList.Services.RecetaService;
import es.uca.AutomaticFoodList.Services.ValoresNutricionalesService;
import es.uca.AutomaticFoodList.Views.CrearRecetaView;

import java.util.List;

public class CrearRecetaForm extends FormLayout {
    private TextField nombre = new TextField("Nombre receta");
    private NumberField precioAproximado = new NumberField("Precio aprox.");
    private NumberField caloriasPlato = new NumberField("Calorias");
    private NumberField hidratosPlato = new NumberField("Hidratos");
    private NumberField grasaPlato = new NumberField("Grasa");
    private NumberField proteinaPlato = new NumberField("Proteina");
    private Button save = new Button("Guardar receta");
    private CrearRecetaView crearRecetaView;
    private RecetaService recetaService;
    private ValoresNutricionalesService valoresNutricionalesService;
    private RecetaIngredienteService recetaIngredienteService;

    public CrearRecetaForm(CrearRecetaView crearRecetaView, RecetaService recetaService, RecetaIngredienteService recetaIngredienteService, ValoresNutricionalesService valoresNutricionalesService) {
        this.recetaService = recetaService;
        this.recetaIngredienteService = recetaIngredienteService;
        this.crearRecetaView = crearRecetaView;
        this.valoresNutricionalesService = valoresNutricionalesService;
        H1 titulo = new H1("Crear receta");

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
        VerticalLayout mainContent = new VerticalLayout(titulo, nombrePrecio, caloriasGrasa, hidratosProteina, save);
        add(mainContent, save);

        save.addClickListener(event -> {
            if(!recetaService.findByNombre(nombre.getValue()).isPresent()){
                if(!crearRecetaView.ingredienteList.isEmpty())
                    save();
                else
                    Notification.show("No hay ingredientes", 5000, Notification.Position.MIDDLE);
            }
            else
                Notification.show("Nombre de receta repetido", 5000, Notification.Position.MIDDLE);
        });
    }

    public void save() {
        Receta receta = crearRecetaView.receta;
        List<RecetaIngrediente> recetaIngredienteList = crearRecetaView.ingredienteList;
        ValoresNutricionales valoresNutricionales = new ValoresNutricionales();
        if(nombre.getValue() != null && (precioAproximado.getValue() > 0 && precioAproximado.getValue() != null) && (caloriasPlato.getValue() > 0 && caloriasPlato.getValue() != null) &&
                (grasaPlato.getValue() > 0 && grasaPlato.getValue() != null) && (hidratosPlato.getValue() > 0 && hidratosPlato.getValue() != null) &&
                (proteinaPlato.getValue() > 0 && proteinaPlato.getValue() != null)){
            valoresNutricionales.setUsuario(null);
            valoresNutricionales.setProteinaPlato(proteinaPlato.getValue());
            valoresNutricionales.setHidratosPlato(hidratosPlato.getValue());
            valoresNutricionales.setGrasaPlato(grasaPlato.getValue());
            valoresNutricionales.setCaloriasPlato(caloriasPlato.getValue());
            valoresNutricionales.setReceta(null);
            valoresNutricionalesService.create(valoresNutricionales);

            receta.setPrecioAproximado(precioAproximado.getValue());
            receta.setNombre(nombre.getValue());
            receta.setIdApi(null);
            receta.setUsuario(UI.getCurrent().getSession().getAttribute(Usuario.class));
            receta.setValoresNutricionales(valoresNutricionales);
            receta = recetaService.create(receta);

            for(RecetaIngrediente recetaIngrediente : recetaIngredienteList){
                recetaIngrediente.setReceta(receta);
                recetaIngredienteService.create(recetaIngrediente);
            }
            Notification.show("Receta introducida con exito", 3000, Notification.Position.MIDDLE);
            UI.getCurrent().navigate("");
        } else {
            Notification.show("Rellene los campos", 5000, Notification.Position.MIDDLE);
        }
    }
}
