package es.uca.AutomaticFoodList.Services;

import es.uca.AutomaticFoodList.Entities.ValoresNutricionales;
import es.uca.AutomaticFoodList.Repositories.ValoresNutricionalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ValoresNutricionalesService {
    private ValoresNutricionalesRepository valoresNutricionalesRepository;

    @Autowired
    public ValoresNutricionalesService(ValoresNutricionalesRepository valoresNutricionalesRepository) {
        this.valoresNutricionalesRepository = valoresNutricionalesRepository;
    }

    public ValoresNutricionales create(ValoresNutricionales valoresNutricionales) {
        return valoresNutricionalesRepository.save(valoresNutricionales);
    }

    public ValoresNutricionales update(ValoresNutricionales valoresNutricionales) {
        return valoresNutricionalesRepository.save(valoresNutricionales);
    }

    public Optional<ValoresNutricionales> buscarId(Long id) {
        return valoresNutricionalesRepository.findById(id);
    }

    public List<ValoresNutricionales> findAll() {
        return valoresNutricionalesRepository.findAll();
    }

    public void delete(ValoresNutricionales valoresNutricionales) {
        valoresNutricionalesRepository.delete(valoresNutricionales);
    }
}
