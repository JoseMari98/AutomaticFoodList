package es.uca.AutomaticFoodList.Entities;

import javax.persistence.*;

@Entity
public class ListaCompra {
    @Id //esto sirve para decir cual es el id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //esto para que se genere aleatorio
    private Long id;
    private double cantidad;
    @ManyToOne
    private Producto producto;
    @ManyToOne
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public Long getId() {
        return id;
    }

    public Producto getProducto() {
        return producto;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }
}
