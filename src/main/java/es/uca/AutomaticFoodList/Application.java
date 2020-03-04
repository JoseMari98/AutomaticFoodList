package es.uca.AutomaticFoodList;

import es.uca.AutomaticFoodList.Entities.*;
import es.uca.AutomaticFoodList.Services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDate;
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
    public CommandLineRunner loadData(UsuarioService usuarioService, IntoleranciaService intoleranciaService, IngredienteService ingredienteService,
                                      RecetaService recetaService, RecetaIngredienteService recetaIngredienteService, ListaComidaService listaComidaService,
                                      ProductoService productoService, ListaCompraService listaCompraService) {
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
                u.setPassword("user1234");
                u.setApellido1("usuario");
                u.setEmail("usuario@usuario.es");
                u.setUsername("user");
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

            Receta receta = new Receta();
            receta.setId_api("1");
            receta.setNombre("Lechuguita");
            receta.setPrecioAproximado(2);
            recetaService.create(receta);

            RecetaIngrediente recetaIngrediente = new RecetaIngrediente();
            recetaIngrediente.setCantidad(1);
            recetaIngrediente.setIngrediente(ingrediente);
            recetaIngrediente.setUnidadMedida(UnidadMedida.Gramos);
            recetaIngrediente.setReceta(receta);
            recetaIngredienteService.create(recetaIngrediente);

            Receta receta1 = new Receta();
            receta1.setId_api("2");
            receta1.setNombre("Carnecita");
            receta1.setPrecioAproximado(1);
            recetaService.create(receta1);

            RecetaIngrediente recetaIngrediente1 = new RecetaIngrediente();
            recetaIngrediente1.setCantidad(1);
            recetaIngrediente1.setIngrediente(ingrediente1);
            recetaIngrediente1.setUnidadMedida(UnidadMedida.Gramos);
            recetaIngrediente1.setReceta(receta1);
            recetaIngredienteService.create(recetaIngrediente1);

            ListaComida listaComida = new ListaComida();
            listaComida.setComida(Comida.Almuerzo);
            listaComida.setPlato(Plato.Postre);
            listaComida.setReceta(receta);
            listaComida.setUsuario(usuarioService.loadUserByUsername("user"));
            listaComida.setFecha(LocalDate.now().plusDays(1));
            listaComidaService.create(listaComida);

            ListaComida listaComida1 = new ListaComida();
            listaComida1.setComida(Comida.Cena);
            listaComida1.setPlato(Plato.Primero);
            listaComida1.setReceta(receta1);
            listaComida1.setUsuario(usuarioService.loadUserByUsername("user"));
            listaComida1.setFecha(LocalDate.now().plusDays(1));
            listaComidaService.create(listaComida1);

            ListaComida listaComida2 = new ListaComida();
            listaComida2.setComida(Comida.Desayuno);
            listaComida2.setPlato(Plato.Primero);
            listaComida2.setReceta(receta1);
            listaComida2.setUsuario(usuarioService.loadUserByUsername("user"));
            listaComida2.setFecha(LocalDate.now().plusDays(1));
            listaComidaService.create(listaComida2);

            Producto producto = new Producto();
            producto.setNombre("Lechuga iceberg");
            producto.setCategoria(Categoria.Verduras);
            producto.setIngrediente(ingrediente);
            producto.setUnidad(UnidadMedida.Gramos);
            producto.setPeso(100);
            producto.setPrecio(1);
            productoService.create(producto);

            Producto producto1 = new Producto();
            producto1.setNombre("Lomo ternera");
            producto1.setCategoria(Categoria.Carnes);
            producto1.setIngrediente(ingrediente1);
            producto1.setUnidad(UnidadMedida.Gramos);
            producto1.setPeso(500);
            producto1.setPrecio(5);
            productoService.create(producto1);

            ListaCompra listaCompra = new ListaCompra();
            listaCompra.setCantidad(200);
            listaCompra.setProducto(producto);
            listaCompra.setUsuario(usuarioService.loadUserByUsername("user"));
            listaCompraService.create(listaCompra);

            ListaCompra listaCompra1 = new ListaCompra();
            listaCompra1.setCantidad(500);
            listaCompra1.setProducto(producto1);
            listaCompra1.setUsuario(usuarioService.loadUserByUsername("user"));
            listaCompraService.create(listaCompra1);
        };
    }
}
