package es.uca.automaticfoodlist.services;

import es.uca.automaticfoodlist.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class HorarioComidasService {
    @Autowired
    private UsuarioRecetaService usuarioRecetaService;
    @Autowired
    private RecetaService recetaService;
    public Usuario usuario;

    public HorarioComidas findByUsuario(Usuario usuario) {
        if (usuarioRecetaService.findByUsuario(usuario).isEmpty()) {
            throw new IllegalStateException("No hay una lista de comida para este usuario.");
        }
        // Occurs in a single transaction, so each initialized lesson references the same timeslot/room instance
        // that is contained by the timeTable's timeslotList/roomList.
        return new HorarioComidas(recetaService.findAll(), usuarioRecetaService.findByUsuario(usuario), Arrays.asList(FechaSemana.values()), Arrays.asList(Comida.values()));
    }

    public void save(HorarioComidas horarioComidas) {
        for (UsuarioReceta usuarioReceta : horarioComidas.getUsuarioRecetas()) {
            usuarioReceta.setUsuario(usuario);
            usuarioRecetaService.create(usuarioReceta);
        }
    }
}
