package es.uca.AutomaticFoodList.Services;

import es.uca.AutomaticFoodList.Entities.IntoleranciaUsuario;
import es.uca.AutomaticFoodList.Entities.Intolerancia;
import es.uca.AutomaticFoodList.Entities.Usuario;
import es.uca.AutomaticFoodList.Repositories.IntoleranciaUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IntoleranciaUsuarioService {
    private IntoleranciaUsuarioRepository intoleranciaUsuarioRepository;

    @Autowired
    public IntoleranciaUsuarioService(IntoleranciaUsuarioRepository intoleranciaUsuarioRepository) {
        this.intoleranciaUsuarioRepository = intoleranciaUsuarioRepository;
    }

    public IntoleranciaUsuario create(IntoleranciaUsuario intolerancia) {
        return intoleranciaUsuarioRepository.save(intolerancia);
    }

    public IntoleranciaUsuario update(IntoleranciaUsuario intolerancia) {
        return intoleranciaUsuarioRepository.save(intolerancia);
    }

    public Optional<IntoleranciaUsuario> buscarId(Long id) {
        return intoleranciaUsuarioRepository.findById(id);
    }

    public List<IntoleranciaUsuario> buscarPorUsuario(Usuario usuario){
        return intoleranciaUsuarioRepository.findByUsuario(usuario);
    }

    public List<IntoleranciaUsuario> findAllOrderById(){
        return intoleranciaUsuarioRepository.findByOrderByIdAsc();
    }

    public IntoleranciaUsuario buscarPorUsuarioEIntolerancia(Usuario usuario, Intolerancia intolerancia){
        return intoleranciaUsuarioRepository.findByUsuarioAndIntolerancia(usuario, intolerancia);
    }

    public List<IntoleranciaUsuario> findAll() {
        return intoleranciaUsuarioRepository.findAll();
    }

    public void delete(IntoleranciaUsuario intolerancia) {
        intoleranciaUsuarioRepository.delete(intolerancia);
    }
}
