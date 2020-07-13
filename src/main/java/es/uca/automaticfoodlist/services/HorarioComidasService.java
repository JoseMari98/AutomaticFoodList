package es.uca.automaticfoodlist.services;

import es.uca.automaticfoodlist.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
public class HorarioComidasService {
    @Autowired
    private UsuarioRecetaService usuarioRecetaService;
    @Autowired
    private RecetaService recetaService;
    @Autowired
    private IntoleranciaUsuarioService intoleranciaUsuarioService;
    @Autowired
    private IntoleranciaRecetaService intoleranciaRecetaService;
    @Autowired
    private PreferenciaIngredienteService preferenciaIngredienteService;
    @Autowired
    private RecetaIngredienteService recetaIngredienteService;
    public Usuario usuario;

    public List<Receta> recetasAdecuadas(Usuario usuario) {
        List<Receta> recetaList = recetaService.findAll();
        List<Receta> recetas = recetaService.findAll();

        //filtro de intolerancias
        if (!intoleranciaUsuarioService.buscarPorUsuario(usuario).isEmpty()) {
            int contUsuario = intoleranciaUsuarioService.buscarPorUsuario(usuario).size();
            for (Receta receta : recetas) {
                int cont = 0;
                for (IntoleranciaUsuario intoleranciaUsuario : intoleranciaUsuarioService.buscarPorUsuario(usuario)) {
                    for (IntoleranciaReceta intoleranciaReceta : intoleranciaRecetaService.buscarPorReceta(receta)) {
                        if (intoleranciaUsuario.getIntolerancia().getId().equals(intoleranciaReceta.getIntolerancia().getId())) {
                            cont++;
                        }
                    }
                }
                if (contUsuario > cont)
                    recetaList.removeIf(receta1 -> receta1.getId().equals(receta.getId()));
            }
        }


        recetas = new LinkedList<>(recetaList);

        if (usuario.getSignosValoresNutrcionales() != null) {
            String[] signos = usuario.getSignosValoresNutrcionales().split(",");
            for (Receta receta : recetas) {
                if (signos[0].equals("Menor"))
                    if (usuario.getValoresNutricionales().getCaloriasPlato() < receta.getValoresNutricionales().getCaloriasPlato()) {
                        recetaList.removeIf(receta1 -> receta1.getId().equals(receta.getId()));
                        continue;
                    } else if (signos[0].equals("Mayor"))
                        if (usuario.getValoresNutricionales().getCaloriasPlato() >= receta.getValoresNutricionales().getCaloriasPlato()) {
                            recetaList.removeIf(receta1 -> receta1.getId().equals(receta.getId()));
                            continue;
                        }

                if (signos[1].equals("Menor"))
                    if (usuario.getValoresNutricionales().getGrasaPlato() < receta.getValoresNutricionales().getGrasaPlato()) {
                        recetaList.removeIf(receta1 -> receta1.getId().equals(receta.getId()));
                        continue;
                    } else if (signos[1].equals("Mayor"))
                        if (usuario.getValoresNutricionales().getGrasaPlato() >= receta.getValoresNutricionales().getGrasaPlato()) {
                            recetaList.removeIf(receta1 -> receta1.getId().equals(receta.getId()));
                            continue;
                        }

                if (signos[2].equals("Menor"))
                    if (usuario.getValoresNutricionales().getHidratosPlato() < receta.getValoresNutricionales().getHidratosPlato()) {
                        recetaList.removeIf(receta1 -> receta1.getId().equals(receta.getId()));
                        continue;
                    } else if (signos[2].equals("Mayor"))
                        if (usuario.getValoresNutricionales().getHidratosPlato() >= receta.getValoresNutricionales().getHidratosPlato()) {
                            recetaList.removeIf(receta1 -> receta1.getId().equals(receta.getId()));
                            continue;
                        }

                if (signos[3].equals("Menor"))
                    if (usuario.getValoresNutricionales().getProteinaPlato() < receta.getValoresNutricionales().getProteinaPlato()) {
                        recetaList.removeIf(receta1 -> receta1.getId().equals(receta.getId()));
                    } else if (signos[3].equals("Mayor"))
                        if (usuario.getValoresNutricionales().getProteinaPlato() >= receta.getValoresNutricionales().getProteinaPlato()) {
                            recetaList.removeIf(receta1 -> receta1.getId().equals(receta.getId()));
                        }
            }
        }

        recetas = new LinkedList<>(recetaList);

        if (usuario.getPresupuestoPlato() != 0) {
            for (Receta receta : recetas) {
                if (usuario.getPresupuestoPlato() < receta.getPrecioAproximado())
                    recetaList.removeIf(receta1 -> receta1.getId().equals(receta.getId()));
            }
        }

        recetas = new LinkedList<>(recetaList);

        if (!preferenciaIngredienteService.findByUsuario(usuario).isEmpty()) {
            for (Receta receta : recetas) {
                boolean valido = false;
                for (RecetaIngrediente recetaIngrediente : recetaIngredienteService.findByReceta(receta)) {
                    if (preferenciaIngredienteService.findByUsuarioAndIngrediente(usuario, recetaIngrediente.getIngrediente()) != null) {
                        valido = true;
                        break;
                    }
                }
                if (valido)
                    recetaList.removeIf(receta1 -> receta1.getId().equals(receta.getId()));
            }
        }

        return recetaList;
    }

    public HorarioComidas findByUsuario(Usuario usuario) {
        List<Receta> recetaList = recetasAdecuadas(usuario);

        return new HorarioComidas(recetaList, usuarioRecetaService.findByUsuario(usuario), Arrays.asList(FechaSemana.values()), Arrays.asList(Comida.values()));
    }

    public void save(HorarioComidas horarioComidas) {
        for (UsuarioReceta usuarioReceta : horarioComidas.getUsuarioRecetas()) {
            usuarioReceta.setUsuario(usuario);
            usuarioRecetaService.create(usuarioReceta);
        }
    }
}
