package es.uca.automaticfoodlist;

import es.uca.automaticfoodlist.entities.*;
import es.uca.automaticfoodlist.services.*;
import net.ricecode.similarity.JaroWinklerStrategy;
import net.ricecode.similarity.SimilarityStrategy;
import net.ricecode.similarity.StringSimilarityService;
import net.ricecode.similarity.StringSimilarityServiceImpl;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
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
                                      ProductoService productoService, UsuarioProductoService usuarioProductoService, ValoresNutricionalesService valoresNutricionalesService,
                                      IntoleranciaRecetaService intoleranciaRecetaService) {
        return (args) -> {
            try {
                boolean valido = usuarioService.loadUserByUsername("admin").getRole().equals("Admin");
            } catch (UsernameNotFoundException e) {
                Usuario u = new Usuario();
                u.setNombre("admin");
                u.setPassword("admin");
                u.setApellido("admin");
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
                u.setApellido("usuario");
                u.setEmail("usuario@usuario.es");
                u.setUsername("user");
                u.setRole("User");
                u.setPresupuestoPlato(-1);
                usuarioService.create(u);
            }
            Vector<Intolerancia> intoleranciaVector = new Vector<>(9);
            for (int i = 0; i < 4; i++)
                intoleranciaVector.add(new Intolerancia());
            intoleranciaVector.elementAt(0).setIntolerancia("Lactosa");
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
            usuarioReceta.setReceta(receta);
            usuarioReceta.setUsuario(usuarioService.loadUserByUsername("user"));
            usuarioReceta.setFecha(FechaSemana.Lunes);
            usuarioRecetaService.create(usuarioReceta);

            UsuarioReceta usuarioReceta1 = new UsuarioReceta();
            usuarioReceta1.setComida(Comida.Cena);
            usuarioReceta1.setReceta(receta1);
            usuarioReceta1.setUsuario(usuarioService.loadUserByUsername("user"));
            usuarioReceta1.setFecha(FechaSemana.Lunes);
            usuarioRecetaService.create(usuarioReceta1);

            UsuarioReceta usuarioReceta2 = new UsuarioReceta();
            usuarioReceta2.setComida(Comida.Desayuno);
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

            /*JSONObject obj = new JSONObject("/home/jose/Descargas/untitled/ingredients_recetas.json");
            String pageName = obj.getJSONObject("pageInfo").getString("pageName");*/
            JSONParser jsonParser = new JSONParser();
            try {
                //Parsing the contents of the JSON file
                JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader("/home/jose/Documentos/TFG/automatic-food-list/ingredients_recetas.json"));
                //JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader("../../../../../../../ingredients_recetas.json"));
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    if (jsonObject.get("id") != null) {
                        Ingrediente ingrediente3 = new Ingrediente();
                        ingrediente3.setIdApi(jsonObject.get("id").toString());
                        String str = jsonObject.get("name").toString();
                        if (str != null && !str.isEmpty()) {
                            ingrediente3.setNombre(str.substring(0, 1).toUpperCase() + str.substring(1));
                        }
                        if (ingredienteService.findByNombre(jsonObject.get("name").toString()) == null)
                            ingredienteService.create(ingrediente3);
                    }
                }
                jsonArray = (JSONArray) jsonParser.parse(new FileReader("/home/jose/Documentos/TFG/automatic-food-list/recipies_recetas.json"));
                //jsonArray = (JSONArray) jsonParser.parse(new FileReader("/../../../../../../../recipies_recetas.json"));
                for (Object o : jsonArray) {
                    JSONObject jsonObject = (JSONObject) o;
                    if (jsonObject.get("id") != null) {
                        Random random = new Random();
                        Receta receta2 = new Receta();
                        receta2.setIdApi(jsonObject.get("id").toString());
                        receta2.setNombre(jsonObject.get("title").toString());
                        Object precio = jsonObject.get("pricePerServing");
                        if (precio instanceof Long) {
                            Long l = new Long((Long) precio);
                            double d = l.doubleValue();
                            receta2.setPrecioAproximado(Math.round(d * 0.01));
                        } else
                            receta2.setPrecioAproximado(Math.round((Double) precio * 0.01));

                        ValoresNutricionales valoresNutricionales = new ValoresNutricionales();
                        valoresNutricionales.setCaloriasPlato(random.nextInt(1000) + 1);
                        valoresNutricionales.setGrasaPlato(random.nextInt(50) + 1);
                        valoresNutricionales.setHidratosPlato(random.nextInt(50) + 1);
                        valoresNutricionales.setProteinaPlato(random.nextInt(50) + 1);
                        receta2.setValoresNutricionales(valoresNutricionalesService.create(valoresNutricionales));
                        receta2.setComidaAdecuada(Comida.values()[random.nextInt(2) + 1].toString());

                        if (jsonObject.get("title") != null) {
                            //meter los ingredientes
                            if (!recetaService.findByNombre(jsonObject.get("title").toString()).isPresent()) {
                                recetaService.create(receta2);

                                IntoleranciaReceta intoleranciaReceta = new IntoleranciaReceta();
                                if (jsonObject.get("dairyFree").toString().equals("true")) {
                                    intoleranciaReceta.setIntolerancia(intoleranciaVector.elementAt(0));
                                    intoleranciaReceta.setReceta(receta2);
                                    intoleranciaRecetaService.create(intoleranciaReceta);
                                }

                                intoleranciaReceta = new IntoleranciaReceta();
                                if (jsonObject.get("glutenFree").toString().equals("true")) {
                                    intoleranciaReceta.setIntolerancia(intoleranciaVector.elementAt(1));
                                    intoleranciaReceta.setReceta(receta2);
                                    intoleranciaRecetaService.create(intoleranciaReceta);
                                }

                                intoleranciaReceta = new IntoleranciaReceta();
                                if (jsonObject.get("vegetarian").toString().equals("true")) {
                                    intoleranciaReceta.setIntolerancia(intoleranciaVector.elementAt(2));
                                    intoleranciaReceta.setReceta(receta2);
                                    intoleranciaRecetaService.create(intoleranciaReceta);
                                }

                                intoleranciaReceta = new IntoleranciaReceta();
                                if (jsonObject.get("vegan").toString().equals("true")) {
                                    intoleranciaReceta.setIntolerancia(intoleranciaVector.elementAt(3));
                                    intoleranciaReceta.setReceta(receta2);
                                    intoleranciaRecetaService.create(intoleranciaReceta);
                                }

                                JSONArray jsonArray1 = (JSONArray) jsonObject.get("ingredients");
                                for (int i = 0; i < jsonArray1.size(); i++) {
                                    JSONObject ingredientes = (JSONObject) jsonArray1.get(i);
                                    if (ingredienteService.findByIdApi(ingredientes.get("id").toString()) != null) {
                                        RecetaIngrediente recetaIngrediente4 = new RecetaIngrediente();
                                        recetaIngrediente4.setReceta(receta2);
                                        recetaIngrediente4.setIngrediente(ingredienteService.findByIdApi(ingredientes.get("id").toString()));
                                        recetaIngredienteService.create(recetaIngrediente4);
                                    }
                                }
                            }
                        }
                    }
                }
                jsonArray = (JSONArray) jsonParser.parse(new FileReader("/home/jose/Documentos/TFG/automatic-food-list/productos.json"));
                //JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader("../../../../../../../ingredients_recetas.json"));
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    if (productoService.findByNombre(jsonObject.get("nombre").toString()) == null) {
                        Producto productoMercadona = new Producto();
                        productoMercadona.setNombre(jsonObject.get("nombre").toString());
                        Object cantidad = jsonObject.get("cantidad");
                        if (cantidad instanceof Long) {
                            Long l = new Long((Long) cantidad);
                            double d = l.doubleValue();
                            productoMercadona.setPeso(d);
                        } else
                            productoMercadona.setPeso((Double) cantidad);
                        Object precio = jsonObject.get("precio");
                        if (precio instanceof Long) {
                            Long l = new Long((Long) precio);
                            double d = l.doubleValue();
                            productoMercadona.setPrecio(d);
                        } else
                            productoMercadona.setPrecio((Double) precio);

                        switch (jsonObject.get("unidadmedida").toString()) {
                            case "Gramos":
                                productoMercadona.setUnidad(UnidadMedida.Gramos);
                                break;
                            case "Litros":
                                productoMercadona.setUnidad(UnidadMedida.Litros);
                                break;
                            case "Kilogramos":
                                productoMercadona.setUnidad(UnidadMedida.Kilogramos);
                                break;
                            case "Mililitros":
                                productoMercadona.setUnidad(UnidadMedida.Mililitros);
                                break;
                            case "Unidad":
                                productoMercadona.setUnidad(UnidadMedida.Unidad);
                                break;
                        }
                        productoService.create(productoMercadona);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //producto e ingrediente
            for (Ingrediente i : ingredienteService.findAll()) {
                double puntuacion = 0.0;
                Producto productoLink = new Producto();
                for (Producto p : productoService.findAll()) {
                    SimilarityStrategy strategy = new JaroWinklerStrategy();
                    String target = i.getNombre();
                    String source = p.getNombre();
                    StringSimilarityService service = new StringSimilarityServiceImpl(strategy);
                    double score = service.score(source, target);
                    if (puntuacion < score) {
                        puntuacion = score;
                        productoLink = p;
                    }
                }
                if (productoLink.getIngrediente() == null) {
                    productoLink.setIngrediente(i);
                    productoService.create(productoLink);
                }
            }
        };
    }
}
