package es.uca.AutomaticFoodList.Forms;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import es.uca.AutomaticFoodList.Entities.*;
import es.uca.AutomaticFoodList.Services.*;
import es.uca.AutomaticFoodList.Views.RecetasView;

import java.util.List;

public class RecetasForm extends FormLayout {
    private Button delete = new Button("Borrar");
    private RecetasView recetasView;
    private RecetaService recetaService;
    private ValoresNutricionalesService valoresNutricionalesService;
    private RecetaIngredienteService recetaIngredienteService;
    private Receta receta;
    private UsuarioRecetaService usuarioRecetaService;
    private H1 titulo = new H1("Informacion de receta");
    private H2 titulo2 = new H2("Ingredientes");
    private Label datos = new Label();
    private Paragraph ingredientes = new Paragraph();
    private VerticalLayout informacion = new VerticalLayout();
    private VerticalLayout contenido = new VerticalLayout();

    public RecetasForm(RecetasView recetasView, RecetaService recetaService, ValoresNutricionalesService valoresNutricionalesService,
                       RecetaIngredienteService recetaIngredienteService, UsuarioRecetaService usuarioRecetaService) {
        this.recetaService = recetaService;
        this.valoresNutricionalesService = valoresNutricionalesService;
        this.recetaIngredienteService = recetaIngredienteService;
        this.recetasView = recetasView;
        this.usuarioRecetaService = usuarioRecetaService;

        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

        delete.addClickListener(event -> {
            if(receta != null)
                delete();});
    }

    public void setReceta(Receta receta) {
        if(receta == null) {
            setVisible(false);
        }
        else {
            setVisible(true);
            this.receta = receta;
        }
    }

    public void datosReceta(Receta receta){
        datos.removeAll();
        informacion.removeAll();
        ingredientes.removeAll();
        contenido.removeAll();
        this.removeAll();
        if(receta != null) {
            String s = receta.getNombre() + "\n" + receta.getPrecioAproximado();
            datos = new Label(s);
            List<RecetaIngrediente> recetaIngredienteList = recetaIngredienteService.findByReceta(receta);
            for (RecetaIngrediente recetaIngrediente : recetaIngredienteList) {
                ingredientes.add(recetaIngrediente.getIngrediente().getNombre() + '\n');
            }
            informacion.add(titulo, datos, titulo2, ingredientes);
            contenido.add(informacion, delete);
            add(contenido);
        }
    }

    public void delete() {
        borrarPadres();
        recetaService.delete(receta);
        this.recetasView.updateList();
        setReceta(null);
    }

    public void borrarPadres(){
        if(valoresNutricionalesService.findByReceta(receta).isPresent())
            valoresNutricionalesService.delete(valoresNutricionalesService.findByReceta(receta).get());
        if(!recetaIngredienteService.findByReceta(receta).isEmpty())
            for(RecetaIngrediente recetaIngrediente : recetaIngredienteService.findByReceta(receta))
                recetaIngredienteService.delete(recetaIngrediente);
        if(!usuarioRecetaService.findByReceta(receta).isEmpty()){
            for(UsuarioReceta usuarioReceta : usuarioRecetaService.findByReceta(receta))
                usuarioRecetaService.delete(usuarioReceta);
        }
    }
}
