package es.uca.AutomaticFoodList.Forms;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.validator.EmailValidator;
import es.uca.AutomaticFoodList.Entities.Usuario;
import es.uca.AutomaticFoodList.Services.MailNotificationService;
import es.uca.AutomaticFoodList.Services.UsuarioService;
import es.uca.AutomaticFoodList.Views.PreferenciasUsuarioView;

public class PreferenciasUsuarioForm extends FormLayout {
    /*private TextField username = new TextField("Nombre usuario");
    private TextField nombre = new TextField("Nombre");
    private TextField apellido1 = new TextField("Primer Apellido");
    private TextField apellido2 = new TextField("Segundo Apellido");
    private TextField dni = new TextField("Dni");
    private TextField telefono = new TextField("Teléfono");
    private EmailField email = new EmailField("Email");
    private PasswordField password = new PasswordField("Contraseña");
    private BeanValidationBinder<Usuario> binder = new BeanValidationBinder<>(Usuario.class);
    private PreferenciasUsuarioView preferenciasUsuarioView;
    private UsuarioService service;
    private Button save = new Button("Continuar");
    private Usuario usuario;

    public PreferenciasUsuarioForm(PreferenciasUsuarioView preferenciasUsuarioView, UsuarioService service) {
        if(UI.getCurrent().getSession().getAttribute(Usuario.class) != null) {
            usuario = service.listarPorUsername(UI.getCurrent().getSession().getAttribute(Usuario.class).getUsername());
            usuario.setPassword("");
            binder.setBean(usuario);
        } else
            usuario = new Usuario();
        this.preferenciasUsuarioView = preferenciasUsuarioView;
        this.service = service;

        nombre.setRequired(true);
        apellido1.setRequired(true);
        apellido2.setRequired(true);
        dni.setRequired(true);
        email.setRequiredIndicatorVisible(true);
        password.setRequired(true);
        username.setRequired(true);
        username.addValueChangeListener(e -> {
            if(service.listarPorUsername(username.getValue()) != null && usuario == null) {
                username.clear();
                Notification.show("Usuario repetido", 3000, Notification.Position.MIDDLE);
            } else {
                if(service.listarPorUsername(username.getValue()) != null && usuario != null && !service.listarPorUsername(username.getValue()).getId().equals(usuario.getId())) {
                    username.clear();
                    Notification.show("Usuario repetido", 3000, Notification.Position.MIDDLE);
                }
            }
        });
        email.addValueChangeListener(e -> {
            if(service.listarPorEmail(email.getValue()) != null && usuario == null) {
                email.clear();
                Notification.show("Email repetido", 3000, Notification.Position.MIDDLE);
            } else {
                if(service.listarPorEmail(email.getValue()) != null && usuario != null && !service.listarPorEmail(email.getValue()).getId().equals(usuario.getId())) {
                    email.clear();
                    Notification.show("Email repetido", 3000, Notification.Position.MIDDLE);
                }
            }
        });
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(username, nombre, apellido1, apellido2, dni, telefono, email, password, save);
        save.addClickShortcut(Key.ENTER);

        binder.bindInstanceFields(this);

        Dialog dialog = new Dialog();

        Button confirmButton = new Button("Confirmar", event -> {
            save();
            dialog.close();
        });
        confirmButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        Button cancelButton = new Button("Cancelar", event -> {
            dialog.close();
        });
        dialog.add(confirmButton, cancelButton);
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        save.addClickListener(event -> dialog.open());
        confirmButton.addClickShortcut(Key.ENTER);
    }

    public void save() {
        binder.forField(email)
                // Explicit validator instance
                .withValidator(new EmailValidator(
                        "No es un formato válido. Ejemplo de formato válido: usuario@gmail.com"))
                .bind(Usuario::getEmail, Usuario::setEmail);
        if(binder.validate().isOk()) {
            usuario.setRole("User");
            usuario.setUsername(username.getValue());
            usuario.setNombre(nombre.getValue());
            usuario.setApellido1(apellido1.getValue());
            usuario.setApellido2(apellido2.getValue());
            usuario.setDni(dni.getValue());
            usuario.setTelefono(telefono.getValue());
            usuario.setEmail(email.getValue());
            usuario.setPassword(password.getValue());
            service.create(usuario);
            if(UI.getCurrent().getSession().getAttribute(Usuario.class) != null) {
                Notification.show("Modificado con exito", 3000, Notification.Position.MIDDLE);
                MailNotificationService.enviaEmail(usuario.getEmail(), "Modificacion realizado con exito",
                        "Has modificado tus credenciales " + usuario.getUsername());
                UI.getCurrent().getSession().setAttribute(Usuario.class, usuario);
                UI.getCurrent().navigate("");
            } else {
                Notification.show("Registrado con exito", 3000, Notification.Position.MIDDLE);
                MailNotificationService.enviaEmail(usuario.getEmail(), "Registro realizado con exito",
                        "Te has registrado " + usuario.getUsername());
                UI.getCurrent().navigate("Login");
            }
        }
        else {
            Notification.show("Rellene los campos", 5000, Notification.Position.MIDDLE);
        }
    }*/
}
