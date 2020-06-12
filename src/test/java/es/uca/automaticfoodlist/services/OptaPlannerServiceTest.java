/*package es.uca.automaticfoodlist.services;

import es.uca.automaticfoodlist.entities.HorarioComidas;
import es.uca.automaticfoodlist.entities.UsuarioReceta;
import org.junit.Test;
import org.junit.jupiter.api.Timeout;
import org.optaplanner.core.api.solver.SolverStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;

@SpringBootTest(properties = {
        "optaplanner.solver.termination.spent-limit=1h", // Effectively disable this termination in favor of the best-score-limit
        "optaplanner.solver.termination.best-score-limit=0hard/*soft"})
public class OptaPlannerServiceTest {
    @Autowired
    private OptaPlannerService optaPlannerService;
    @Autowired
    private UsuarioService usuarioService;

    @Test
    @Timeout(600_000)
    public void solveDemoDataUntilFeasible() throws InterruptedException {
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
        assertTrue(horarioComidas.getScore().isFeasible());
        assertFalse(horarioComidas.getUsuarioRecetas().size() > 21);

    }
}*/