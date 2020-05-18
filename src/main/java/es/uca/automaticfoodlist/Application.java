package es.uca.automaticfoodlist;

import es.uca.automaticfoodlist.entities.*;
import es.uca.automaticfoodlist.services.*;
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
    public CommandLineRunner loadData(UsuarioService usuarioService, IntoleranciaService intoleranciaService, IngredienteService ingredienteService,
                                      RecetaService recetaService, RecetaIngredienteService recetaIngredienteService, UsuarioRecetaService usuarioRecetaService,
                                      ProductoService productoService, UsuarioProductoService usuarioProductoService) {
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
            for (int i = 0; i < 4; i++)
                intoleranciaVector.add(new Intolerancia());
            intoleranciaVector.elementAt(0).setIntolerancia("Leche");
            intoleranciaVector.elementAt(1).setIntolerancia("Gluten");
            intoleranciaVector.elementAt(2).setIntolerancia("Vegetariano");
            intoleranciaVector.elementAt(3).setIntolerancia("Vegano");
            for (Intolerancia intolerancia : intoleranciaVector) {
                intoleranciaService.create(intolerancia);
            }
            Ingrediente ingrediente = new Ingrediente();
            ingrediente.setIdApi("1");
            ingrediente.setCategoria(Categoria.Verduras);
            ingrediente.setNombre("Lechuga");
            ingredienteService.create(ingrediente);

            Ingrediente ingrediente1 = new Ingrediente();
            ingrediente1.setIdApi("2");
            ingrediente1.setCategoria(Categoria.Carnes);
            ingrediente1.setNombre("Ternera");
            ingredienteService.create(ingrediente1);

            Ingrediente ingrediente2 = new Ingrediente();
            ingrediente2.setIdApi("3");
            ingrediente2.setCategoria(Categoria.Condimentos);
            ingrediente2.setNombre("Sal");
            ingredienteService.create(ingrediente2);

            Receta receta = new Receta();
            receta.setIdApi("1");
            receta.setNombre("Lechuguita");
            receta.setPrecioAproximado(2);
            recetaService.create(receta);

            RecetaIngrediente recetaIngrediente = new RecetaIngrediente();
            recetaIngrediente.setCantidad(200);
            recetaIngrediente.setIngrediente(ingrediente);
            recetaIngrediente.setUnidadMedida(UnidadMedida.Gramos);
            recetaIngrediente.setReceta(receta);
            recetaIngredienteService.create(recetaIngrediente);

            RecetaIngrediente recetaIngrediente2 = new RecetaIngrediente();
            recetaIngrediente2.setCantidad(5);
            recetaIngrediente2.setIngrediente(ingrediente2);
            recetaIngrediente2.setUnidadMedida(UnidadMedida.Gramos);
            recetaIngrediente2.setReceta(receta);
            recetaIngredienteService.create(recetaIngrediente2);

            Receta receta1 = new Receta();
            receta1.setIdApi("2");
            receta1.setNombre("Carnecita");
            receta1.setPrecioAproximado(1);
            recetaService.create(receta1);

            RecetaIngrediente recetaIngrediente1 = new RecetaIngrediente();
            recetaIngrediente1.setCantidad(500);
            recetaIngrediente1.setIngrediente(ingrediente1);
            recetaIngrediente1.setUnidadMedida(UnidadMedida.Gramos);
            recetaIngrediente1.setReceta(receta1);
            recetaIngredienteService.create(recetaIngrediente1);

            RecetaIngrediente recetaIngrediente3 = new RecetaIngrediente();
            recetaIngrediente3.setCantidad(10);
            recetaIngrediente3.setIngrediente(ingrediente2);
            recetaIngrediente3.setUnidadMedida(UnidadMedida.Gramos);
            recetaIngrediente3.setReceta(receta1);
            recetaIngredienteService.create(recetaIngrediente3);

            UsuarioReceta usuarioReceta = new UsuarioReceta();
            usuarioReceta.setComida(Comida.Almuerzo);
            usuarioReceta.setPlato(Plato.Postre);
            usuarioReceta.setReceta(receta);
            usuarioReceta.setUsuario(usuarioService.loadUserByUsername("user"));
            usuarioReceta.setFecha(FechaSemana.Lunes);
            usuarioRecetaService.create(usuarioReceta);

            UsuarioReceta usuarioReceta1 = new UsuarioReceta();
            usuarioReceta1.setComida(Comida.Cena);
            usuarioReceta1.setPlato(Plato.Primero);
            usuarioReceta1.setReceta(receta1);
            usuarioReceta1.setUsuario(usuarioService.loadUserByUsername("user"));
            usuarioReceta1.setFecha(FechaSemana.Lunes);
            usuarioRecetaService.create(usuarioReceta1);

            UsuarioReceta usuarioReceta2 = new UsuarioReceta();
            usuarioReceta2.setComida(Comida.Desayuno);
            usuarioReceta2.setPlato(Plato.Primero);
            usuarioReceta2.setReceta(receta1);
            usuarioReceta2.setUsuario(usuarioService.loadUserByUsername("user"));
            usuarioReceta2.setFecha(FechaSemana.Lunes);
            usuarioRecetaService.create(usuarioReceta2);

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

            Producto producto2 = new Producto();
            producto2.setNombre("Sal escamas");
            producto2.setCategoria(Categoria.Condimentos);
            producto2.setIngrediente(ingrediente2);
            producto2.setUnidad(UnidadMedida.Gramos);
            producto2.setPeso(500);
            producto2.setPrecio(1);
            productoService.create(producto2);

            /*ListaCompra listaCompra = new ListaCompra();
            listaCompra.setCantidad(2);
            listaCompra.setProducto(producto);
            listaCompra.setUsuario(usuarioService.loadUserByUsername("user"));
            listaCompraService.create(listaCompra);

            ListaCompra listaCompra1 = new ListaCompra();
            listaCompra1.setCantidad(1);
            listaCompra1.setProducto(producto1);
            listaCompra1.setUsuario(usuarioService.loadUserByUsername("user"));
            listaCompraService.create(listaCompra1);*/
        };
    }
}
