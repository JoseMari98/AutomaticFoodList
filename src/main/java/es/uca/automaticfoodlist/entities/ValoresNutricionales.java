package es.uca.automaticfoodlist.entities;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

import javax.persistence.*;

@Entity
public class ValoresNutricionales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int caloriasPlato, grasaPlato, hidratosPlato, proteinaPlato;
    @OneToOne(mappedBy = "valoresNutricionales", cascade = CascadeType.ALL)
    private Usuario usuario;
    @OneToOne(mappedBy = "valoresNutricionales", cascade = CascadeType.ALL)
    private Receta receta;

    public Long getId() {
        return id;
    }

    public Receta getReceta() {
        return receta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public int getCaloriasPlato() {
        return caloriasPlato;
    }

    public int getGrasaPlato() {
        return grasaPlato;
    }

    public int getHidratosPlato() {
        return hidratosPlato;
    }

    public int getProteinaPlato() {
        return proteinaPlato;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setCaloriasPlato(int caloriasPlato) {
        this.caloriasPlato = caloriasPlato;
    }

    public void setGrasaPlato(int grasaPlato) {
        this.grasaPlato = grasaPlato;
    }

    public void setHidratosPlato(int hidratosPlato) {
        this.hidratosPlato = hidratosPlato;
    }

    public void setProteinaPlato(int proteinaPlato) {
        this.proteinaPlato = proteinaPlato;
    }

    public static class MyConverter
            implements Converter<Double, Integer> {

        @Override
        public Result<Integer> convertToModel(Double aDouble, ValueContext valueContext) {
            return Result.ok(Integer.valueOf(aDouble.intValue()));
        }

        @Override
        public Double convertToPresentation(
                Integer integer, ValueContext context) {
            // Converting to the field type should
            // always succeed, so there is no support for
            // returning an error Result.
            return Double.valueOf(integer);
        }
    }
}
