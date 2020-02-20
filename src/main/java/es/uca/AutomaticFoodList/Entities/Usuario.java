package es.uca.AutomaticFoodList.Entities;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@Entity
public class Usuario implements UserDetails {
    @Id //esto sirve para decir cual es el id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //esto para que se genere aleatorio
    private Long id;
    @NotEmpty(message = "Este campo es obligatorio")
    private String nombre = "", apellido1 = "", password = "";
    @NotEmpty(message = "Este campo es obligatorio")
    @Column(unique = true)
    private String username = "";
    @NotEmpty(message = "Este campo es obligatorio")
    @Column(unique = true)
    private String email = "";
    private String role = "";
    private double presupuestoPlato;
    @OneToMany(mappedBy = "usuario") //esto para decir la cardinalidad y a que variable se asocia
    private Set<PreferenciasIngrediente> preferenciasIngredienteSet = new HashSet<>();
    @ManyToMany
    private Set<Intolerancia> intolerancias = new HashSet<>();
    @OneToOne
    private ValoresNutricionales valoresNutricionales;
    @OneToMany(mappedBy = "usuario")
    private Set<ListaCompra> listaCompras = new HashSet<>();
    //Getters
    public Long getId() {
        return id;
    }

    public Set<Intolerancia> getIntolerancias() {
        return intolerancias;
    }

    public Set<PreferenciasIngrediente> getPreferenciasIngredienteSet() {
        return preferenciasIngredienteSet;
    }

    public String getApellido1() {
        return apellido1;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setIntolerancias(Set<Intolerancia> intolerancias) {
        this.intolerancias = intolerancias;
    }

    public void setPreferenciasIngredienteSet(Set<PreferenciasIngrediente> preferenciasIngredienteSet) {
        this.preferenciasIngredienteSet = preferenciasIngredienteSet;
    }

    public String getRole() {
        return role;
    }

    public double getPresupuestoPlato() {
        return presupuestoPlato;
    }

    //Setters
    public void setId(Long Id) {
        this.id = Id;
    }

    public void setApellido1(String Apellido1) {
        this.apellido1 = Apellido1;
    }

    public void setNombre(String Nombre) {
        this.nombre = Nombre;
    }

    public void setEmail(String Email) {
        this.email = Email;
    }

    public void setPassword(String Contrasena) {
        this.password = Contrasena;
    }

    public void setUsername(String nombreUsuario) {
        this.username = nombreUsuario;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPresupuestoPlato(double presupuestoPlato) {
        this.presupuestoPlato = presupuestoPlato;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        list.add(new SimpleGrantedAuthority(role));
        return list;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}