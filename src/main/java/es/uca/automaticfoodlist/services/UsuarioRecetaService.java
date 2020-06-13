package es.uca.automaticfoodlist.services;

import es.uca.automaticfoodlist.entities.*;
import es.uca.automaticfoodlist.repositories.UsuarioRecetaRepository;
import org.optaplanner.core.api.solver.SolverStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioRecetaService {
    private UsuarioRecetaRepository usuarioRecetaRepository;
    @Autowired
    private OptaPlannerService optaPlannerService;
    @Autowired
    private UsuarioProductoService usuarioProductoService;
    @Autowired
    private IntoleranciaRecetaService intoleranciaRecetaService;
    @Autowired
    private IntoleranciaUsuarioService intoleranciaUsuarioService;

    @Autowired
    public UsuarioRecetaService(UsuarioRecetaRepository usuarioRecetaRepository) {
        this.usuarioRecetaRepository = usuarioRecetaRepository;
    }

    public UsuarioReceta create(UsuarioReceta usuarioReceta) {
        return usuarioRecetaRepository.save(usuarioReceta);
    }

    public UsuarioReceta update(UsuarioReceta usuarioReceta) {
        return usuarioRecetaRepository.save(usuarioReceta);
    }

    public Optional<UsuarioReceta> buscarId(Long id) {
        return usuarioRecetaRepository.findById(id);
    }

    public Optional<UsuarioReceta> findByUsuarioAndComidaAndFecha(Usuario usuario, Comida comida, FechaSemana fechaSemana) {
        return usuarioRecetaRepository.findByUsuarioAndComidaAndFechaSemana(usuario, comida, fechaSemana);
    }

    public List<UsuarioReceta> findByReceta(Receta receta) {
        return usuarioRecetaRepository.findByReceta(receta);
    }

    public List<UsuarioReceta> findByUsuarioAndComida(Usuario usuario, Comida comida) {
        return usuarioRecetaRepository.findByUsuarioAndComida(usuario, comida);
    }

    public List<UsuarioReceta> findByUsuario(Usuario usuario) {
        return usuarioRecetaRepository.findByUsuario(usuario);
    }

    public List<UsuarioReceta> findAll() {
        return usuarioRecetaRepository.findAll();
    }

    public void delete(UsuarioReceta usuarioReceta) {
        usuarioRecetaRepository.delete(usuarioReceta);
    }

    public void generarListaCompra(Usuario usuario) throws InterruptedException {
        for (UsuarioReceta usuarioReceta : findByUsuario(usuario))
            delete(usuarioReceta);
        for (UsuarioProducto usuarioProducto : usuarioProductoService.findByUsuario(usuario))
            usuarioProductoService.delete(usuarioProducto);
        for (int i = 0; i < 21; i++) {
            UsuarioReceta usuarioReceta = new UsuarioReceta();
            usuarioReceta.setUsuario(usuario);
            create(usuarioReceta);
        }
        optaPlannerService.solve(usuario);
        HorarioComidas horarioComidas = optaPlannerService.getTimeTable(usuario);
        while (horarioComidas.getSolverStatus() != SolverStatus.NOT_SOLVING) {
            // Quick polling (not a Test Thread Sleep anti-pattern)
            // Test is still fast on fast machines and doesn't randomly fail on slow machines.
            Thread.sleep(20L);
            horarioComidas = optaPlannerService.getTimeTable(usuario);
        }
    }
}
