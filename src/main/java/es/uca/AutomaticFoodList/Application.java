package es.uca.AutomaticFoodList;

import es.uca.AutomaticFoodList.Entities.Categoria;
import es.uca.AutomaticFoodList.Entities.Ingrediente;
import es.uca.AutomaticFoodList.Entities.Intolerancia;
import es.uca.AutomaticFoodList.Entities.Usuario;
import es.uca.AutomaticFoodList.Services.IngredienteService;
import es.uca.AutomaticFoodList.Services.IntoleranciaService;
import es.uca.AutomaticFoodList.Services.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Vector;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @EnableJpaRepositories
    public class Config {
    }

    @Bean
    public CommandLineRunner loadData(UsuarioService usuarioService, IntoleranciaService intoleranciaService, IngredienteService ingredienteService) {
        return (args) -> {
            try {
                boolean valido = usuarioService.loadUserByUsername("admin").getRole().equals("Admin");
            } catch (UsernameNotFoundException e) {
                Usuario u = new Usuario();
                u.setNombre("admin");
                u.setPassword("admin");
                u.setApellido1("admin");
                u.setEmail("admin@admin.es");
                u.setUsername("admin");
                u.setRole("Admin");
                u.setPresupuestoPlato(-1);
                usuarioService.create(u);
            }
            try {
                boolean valido = usuarioService.loadUserByUsername("gerente").getRole().equals("Gerente");
            } catch (UsernameNotFoundException e) {
                Usuario u = new Usuario();
                u.setNombre("gerente");
                u.setPassword("gerente");
                u.setApellido1("gerente");
                u.setEmail("gerente@gerente.es");
                u.setUsername("gerente");
                u.setRole("Gerente");
                u.setPresupuestoPlato(-1);
                usuarioService.create(u);
            }
            try {
                boolean valido = usuarioService.loadUserByUsername("usuario").getRole().equals("User");
            } catch (UsernameNotFoundException e) {
                Usuario u = new Usuario();
                u.setNombre("usuario");
                u.setPassword("usuario");
                u.setApellido1("usuario");
                u.setEmail("usuario@usuario.es");
                u.setUsername("usuario");
                u.setRole("User");
                u.setPresupuestoPlato(-1);
                usuarioService.create(u);
            }
            Vector<Intolerancia> intoleranciaVector = new Vector<>(9);
            for(int i = 0 ; i < 9 ; i++)
                intoleranciaVector.add(new Intolerancia());
            intoleranciaVector.elementAt(0).setIntolerancia("Leche");
            intoleranciaVector.elementAt(1).setIntolerancia("Huevo");
            intoleranciaVector.elementAt(2).setIntolerancia("Gluten");
            intoleranciaVector.elementAt(3).setIntolerancia("Grano");
            intoleranciaVector.elementAt(4).setIntolerancia("Cacahuete");
            intoleranciaVector.elementAt(5).setIntolerancia("Mariscos");
            intoleranciaVector.elementAt(6).setIntolerancia("Sesamo");
            intoleranciaVector.elementAt(7).setIntolerancia("Soja");
            intoleranciaVector.elementAt(8).setIntolerancia("Trigo");
            for(Intolerancia intolerancia : intoleranciaVector){
                intoleranciaService.create(intolerancia);
            }
            Ingrediente ingrediente = new Ingrediente();
            ingrediente.setId_api("1");
            ingrediente.setCategoria(Categoria.Verduras);
            ingrediente.setNombre("Lechuga");
            ingredienteService.create(ingrediente);

            Ingrediente ingrediente1 = new Ingrediente();
            ingrediente1.setId_api("2");
            ingrediente1.setCategoria(Categoria.Carnes);
            ingrediente1.setNombre("Ternera");
            ingredienteService.create(ingrediente1);
        };
    }
}
