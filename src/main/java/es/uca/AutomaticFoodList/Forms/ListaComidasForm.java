package es.uca.AutomaticFoodList.Forms;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import es.uca.AutomaticFoodList.Entities.ListaComida;
import es.uca.AutomaticFoodList.Entities.Receta;
import es.uca.AutomaticFoodList.Entities.RecetaIngrediente;
import es.uca.AutomaticFoodList.Services.IngredienteService;
import es.uca.AutomaticFoodList.Services.ListaComidaService;
import es.uca.AutomaticFoodList.Services.RecetaIngredienteService;
import es.uca.AutomaticFoodList.Services.RecetaService;
import es.uca.AutomaticFoodList.Views.ListaComidasView;
import org.springframework.security.core.parameters.P;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class ListaComidasForm extends FormLayout {
    private Button generarAleatoria = new Button("Sustituir");
    private Button delete = new Button("Borrar");
    private ListaComidasView listaComidasView;
    private RecetaService recetaService;
    private RecetaIngredienteService recetaIngredienteService;
    private BeanValidationBinder<ListaComida> binder = new BeanValidationBinder<>(ListaComida.class);
    private ListaComidaService listaComidaService;
    private H1 titulo = new H1("Informacion de receta");
    private H2 titulo2 = new H2("Ingredientes");
    private Paragraph datos = new Paragraph();
    private Paragraph ingredientes = new Paragraph();
    private VerticalLayout informacion = new VerticalLayout();
    private HorizontalLayout buttons = new HorizontalLayout();
    private VerticalLayout contenido = new VerticalLayout();


    public ListaComidasForm(ListaComidasView listaComidasView, ListaComidaService listaComidaService, RecetaService recetaService, RecetaIngredienteService recetaIngredienteService) {
        this.listaComidaService = listaComidaService;
        this.recetaService = recetaService;
        this.recetaIngredienteService = recetaIngredienteService;
        this.listaComidasView = listaComidasView;

        generarAleatoria.addClickShortcut(Key.ENTER);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

        generarAleatoria.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttons.add(generarAleatoria, delete);

        generarAleatoria.addClickListener(event -> {
            if(binder.getBean() != null)
                generarAleatoria();});
        delete.addClickListener(event -> {
            if(binder.getBean() != null)
                delete();});
    }

    public void setListaComida(ListaComida listaComida) {
        binder.setBean(listaComida);

        if(listaComida == null) {
            setVisible(false);
        }
        else {
            setVisible(true);
        }
    }

    public void datosReceta(ListaComida listaComida){
        datos = new Paragraph();
        ingredientes = new Paragraph();
        informacion = new VerticalLayout();
        contenido = new VerticalLayout();
        this.removeAll();
        if(listaComida != null) {
            Optional<Receta> receta = recetaService.findById(listaComida.getReceta());
            datos = new Paragraph(receta.get().getNombre() + '\n' +
                    receta.get().getPrecioAproximado());
            List<RecetaIngrediente> recetaIngredienteList = recetaIngredienteService.findByReceta(receta.get());
            for (RecetaIngrediente recetaIngrediente : recetaIngredienteList) {
                ingredientes.add(recetaIngrediente.getIngrediente().getNombre() + '\n');
            }
            informacion.add(titulo, datos, titulo2, ingredientes);
            contenido.add(informacion, buttons);
            add(contenido);
        }
    }

    public void generarAleatoria() {
        ListaComida listaComida = binder.getBean();
        Random random = new Random();
        LocalDate fecha = listaComida.getFecha();
        if(binder.validate().isOk()) {
            List<Receta> recetaList = recetaService.findAll();
            int numero = random.nextInt(recetaList.size());
            while(recetaList.get(numero).getId() == listaComida.getReceta().getId())
                numero = random.nextInt(recetaList.size());
            listaComida.setReceta(recetaList.get(numero)); //genero una comida random sin tener en cuenta los requisitos del usuario
            listaComida.setFecha(fecha.plusDays(1));
            listaComidaService.update(listaComida);
            this.listaComidasView.updateList();
            setListaComida(null);
        }
    }

    public void delete() {
        ListaComida listaComida = binder.getBean();
        listaComidaService.delete(listaComida);
        this.listaComidasView.updateList();
        setListaComida(null);
    }
}
