package es.uca.AutomaticFoodList.Entities;

import javax.persistence.*;

@Entity
public class ValoresNutricionales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double caloriasPlato, grasaPlato, hidratosPlato, proteinaPlato;
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

    public double getCaloriasPlato() {
        return caloriasPlato;
    }

    public double getGrasaPlato() {
        return grasaPlato;
    }

    public double getHidratosPlato() {
        return hidratosPlato;
    }

    public double getProteinaPlato() {
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

    public void setCaloriasPlato(double caloriasPlato) {
        this.caloriasPlato = caloriasPlato;
    }

    public void setGrasaPlato(double grasaPlato) {
        this.grasaPlato = grasaPlato;
    }

    public void setHidratosPlato(double hidratosPlato) {
        this.hidratosPlato = hidratosPlato;
    }

    public void setProteinaPlato(double proteinaPlato) {
        this.proteinaPlato = proteinaPlato;
    }
}
