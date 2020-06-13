package es.uca.automaticfoodlist.services;

import es.uca.automaticfoodlist.entities.HorarioComidas;
import es.uca.automaticfoodlist.entities.Usuario;
import es.uca.automaticfoodlist.entities.UsuarioReceta;
import org.junit.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.runner.RunWith;
import org.optaplanner.core.api.solver.SolverStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        "optaplanner.solver.termination.spent-limit=1h", // Effectively disable this termination in favor of the best-score-limit
        "optaplanner.solver.termination.best-score-limit=0hard/*soft"})
public class OptaPlannerServiceTest {
    @Autowired
    private OptaPlannerService optaPlannerService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioRecetaService usuarioRecetaService;

    @Test
    @Timeout(600_000)
    public void solveAndNotNull() throws InterruptedException {
        Usuario usuario = usuarioService.loadUserByUsername("user");
        for (UsuarioReceta usuarioReceta : usuarioRecetaService.findByUsuario(usuario))
            usuarioRecetaService.delete(usuarioReceta);
        for (int i = 0; i < 21; i++) {
            UsuarioReceta usuarioReceta = new UsuarioReceta();
            usuarioReceta.setUsuario(usuario);
            usuarioRecetaService.create(usuarioReceta);
        }
        optaPlannerService.solve(usuarioService.loadUserByUsername("user"));
        HorarioComidas horarioComidas = optaPlannerService.getTimeTable(usuarioService.loadUserByUsername("user"));
        while (horarioComidas.getSolverStatus() != SolverStatus.NOT_SOLVING) {
            // Quick polling (not a Test Thread Sleep anti-pattern)
            // Test is still fast on fast machines and doesn't randomly fail on slow machines.
            Thread.sleep(20L);
            horarioComidas = optaPlannerService.getTimeTable(usuarioService.loadUserByUsername("user"));
        }
        assertFalse(horarioComidas.getUsuarioRecetas().isEmpty());
        for (UsuarioReceta usuarioReceta : horarioComidas.getUsuarioRecetas()) {
            assertNotNull(usuarioReceta.getReceta());
            assertNotNull(usuarioReceta.getFecha());
            assertNotNull(usuarioReceta.getUsuario());
            assertNotNull(usuarioReceta.getComida());
        }
        assertFalse(horarioComidas.getUsuarioRecetas().size() > 21);
    }
}