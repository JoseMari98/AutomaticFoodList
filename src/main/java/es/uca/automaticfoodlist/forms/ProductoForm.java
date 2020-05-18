package es.uca.automaticfoodlist.forms;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import es.uca.automaticfoodlist.entities.*;
import es.uca.automaticfoodlist.services.IngredienteService;
import es.uca.automaticfoodlist.services.ProductoService;
import es.uca.automaticfoodlist.services.UsuarioProductoService;
import es.uca.automaticfoodlist.views.ProductoView;

public class ProductoForm extends FormLayout {
    private TextField nombre = new TextField("Nombre");
    private NumberField precio = new NumberField("Precio");
    private NumberField peso = new NumberField("Peso");
    private ComboBox<UnidadMedida> unidad = new ComboBox<>("Unidad medida");
    private ComboBox<Ingrediente> ingrediente = new ComboBox<>("Ingrediente");
    private ComboBox<Categoria> categoria = new ComboBox<>("Categoria");
    private Button save = new Button("Añadir");
    private Button delete = new Button("Borrar");
    private ProductoView productoView;
    private BeanValidationBinder<Producto> binder = new BeanValidationBinder<>(Producto.class);
    private IngredienteService ingredienteService;
    private ProductoService productoService;
    private UsuarioProductoService usuarioProductoService;

    public ProductoForm(ProductoView productoView, ProductoService productoService, IngredienteService ingredienteService, UsuarioProductoService usuarioProductoService) {
        this.ingredienteService = ingredienteService;
        this.productoService = productoService;
        this.usuarioProductoService = usuarioProductoService;
        this.productoView = productoView;

        precio.setSuffixComponent(new Span("€"));
        precio.setMin(0);
        precio.setMax(1000);
        peso.setMin(0);
        peso.setMax(1000000);
        peso.setStep(0.01);
        precio.setStep(0.01);
        nombre.setRequired(true);
        categoria.setRequired(true);
        precio.setRequiredIndicatorVisible(true);
        peso.setRequiredIndicatorVisible(true);
        unidad.setRequired(true);
        unidad.setRequiredIndicatorVisible(true);
        nombre.setRequiredIndicatorVisible(true);
        categoria.setRequiredIndicatorVisible(true);
        categoria.setItems(Categoria.values());
        unidad.setItems(UnidadMedida.values());
        ingrediente.setItems(ingredienteService.findAll());
        ingrediente.setItemLabelGenerator(Ingrediente::getNombre);

        save.addClickShortcut(Key.ENTER);

        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(nombre, categoria, precio, peso, unidad, ingrediente, buttons);

        binder.bindInstanceFields(this);

        save.addClickListener(event -> {
            if(binder.getBean() != null)
                save();});
        delete.addClickListener(event -> {
            if(binder.getBean() != null)
                delete();});
    }

    public void setProducto(Producto producto) {
        binder.setBean(producto);

        if(producto == null) {
            setVisible(false);
        }
        else {
            setVisible(true);
            nombre.focus();
        }
    }

    public void save() {
        Producto producto = binder.getBean();
        if(binder.validate().isOk()) {
            productoService.create(producto);
            this.productoView.updateList();
            setProducto(null);
        }
        else {
            Notification.show("Rellene los campos", 5000, Notification.Position.MIDDLE);
        }
    }

    public void delete() {
        Producto producto = binder.getBean();
        if(binder.validate().isOk()) {
            //borrar las recetas que haya que borrar y productos y demas
            borrarPadres(producto);
            productoService.delete(producto);
            this.productoView.updateList();
            setProducto(null);
        } else
            Notification.show("Rellene los campos", 5000, Notification.Position.MIDDLE);
    }

    public void borrarPadres(Producto producto){
        if(!usuarioProductoService.findByProducto(producto.getNombre()).isEmpty())
            for(UsuarioProducto usuarioProducto : usuarioProductoService.findByProducto(producto.getNombre()))
                usuarioProductoService.delete(usuarioProducto);
    }
}
