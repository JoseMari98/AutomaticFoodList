package es.uca.automaticfoodlist.forms;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import es.uca.automaticfoodlist.entities.FechaSemana;
import es.uca.automaticfoodlist.entities.Receta;
import es.uca.automaticfoodlist.entities.RecetaIngrediente;
import es.uca.automaticfoodlist.entities.UsuarioReceta;
import es.uca.automaticfoodlist.services.RecetaIngredienteService;
import es.uca.automaticfoodlist.services.RecetaService;
import es.uca.automaticfoodlist.services.UsuarioRecetaService;
import es.uca.automaticfoodlist.views.ListaComidasView;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class ListaComidasForm extends FormLayout {
    private Button generarAleatoria = new Button("Sustituir");
    private Button delete = new Button("Borrar");
    private ListaComidasView listaComidasView;
    private RecetaService recetaService;
    private RecetaIngredienteService recetaIngredienteService;
    private BeanValidationBinder<UsuarioReceta> binder = new BeanValidationBinder<>(UsuarioReceta.class);
    private UsuarioRecetaService usuarioRecetaService;
    private H1 titulo = new H1("Información de receta");
    private H2 titulo2 = new H2("Ingredientes");
    private Label datos = new Label();
    private Paragraph ingredientes = new Paragraph();
    private VerticalLayout informacion = new VerticalLayout();
    private HorizontalLayout buttons = new HorizontalLayout();
    private VerticalLayout contenido = new VerticalLayout();
    private Random random = new Random();

    public ListaComidasForm(ListaComidasView listaComidasView, UsuarioRecetaService usuarioRecetaService, RecetaService recetaService, RecetaIngredienteService recetaIngredienteService) {
        this.usuarioRecetaService = usuarioRecetaService;
        this.recetaService = recetaService;
        this.recetaIngredienteService = recetaIngredienteService;
        this.listaComidasView = listaComidasView;

        generarAleatoria.addClickShortcut(Key.ENTER);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

        generarAleatoria.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttons.add(generarAleatoria, delete);

        generarAleatoria.addClickListener(event -> {
            if (binder.getBean() != null)
                generarAleatoria();
        });
        delete.addClickListener(event -> {
            if (binder.getBean() != null)
                delete();
        });
    }

    public void setListaComida(UsuarioReceta usuarioReceta) {
        binder.setBean(usuarioReceta);

        setVisible(usuarioReceta != null);
    }

    public void datosReceta(UsuarioReceta usuarioReceta) {
        datos.removeAll();
        informacion.removeAll();
        ingredientes.removeAll();
        contenido.removeAll();
        this.removeAll();
        if (usuarioReceta != null) {
            Optional<Receta> receta = recetaService.findById(usuarioReceta.getReceta());
            if (receta.isPresent()) {
                String s = receta.get().getNombre() + " con precio de: " + receta.get().getPrecioAproximado() + "€";
                datos = new Label(s);
                List<RecetaIngrediente> recetaIngredienteList = recetaIngredienteService.findByReceta(receta.get());
                for (RecetaIngrediente recetaIngrediente : recetaIngredienteList) {
                    ingredientes.add(recetaIngrediente.getIngrediente().getNombre() + ", ");
                }
                informacion.add(titulo, datos, titulo2, ingredientes);
                contenido.add(informacion, buttons);
                add(contenido);
            }
        }
    }

    public void generarAleatoria() {
        UsuarioReceta usuarioReceta = binder.getBean();
        FechaSemana fecha = usuarioReceta.getFecha();
        if (binder.validate().isOk()) {
            List<Receta> recetaList = recetaService.findAll();
            int numero = random.nextInt(recetaList.size());
            while (recetaList.get(numero).getId().equals(usuarioReceta.getReceta().getId()))
                numero = random.nextInt(recetaList.size());
            usuarioReceta.setReceta(recetaList.get(numero)); //genero una comida random sin tener en cuenta los requisitos del usuario
            usuarioReceta.setFecha(fecha);
            usuarioRecetaService.update(usuarioReceta);
            this.listaComidasView.updateList();
            setListaComida(null);
        }
    }

    public void delete() {
        UsuarioReceta usuarioReceta = binder.getBean();
        usuarioRecetaService.delete(usuarioReceta);
        this.listaComidasView.updateList();
        setListaComida(null);
    }
}
