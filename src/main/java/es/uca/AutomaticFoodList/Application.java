package es.uca.AutomaticFoodList;

import es.uca.AutomaticFoodList.Entities.Usuario;
import es.uca.AutomaticFoodList.Services.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @EnableJpaRepositories
    public class Config {
    }

    @Bean
    public CommandLineRunner loadData(UsuarioService usuarioService) {
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
                u.setPresupuestoPlato(0);
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
                usuarioService.create(u);
            }
        };
    }
}
