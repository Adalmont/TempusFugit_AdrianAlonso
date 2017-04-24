/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.adrian.beans;

import es.adrian.dao.IGenericoDAO;
import es.adrian.daofactory.DAOFactory;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.HibernateException;

/**
 *
 * @author Adrian
 */
@Entity
@Table(name = "Usuarios")
@ManagedBean(name = "usuario", eager = true)
@SessionScoped
public class Usuario implements Serializable {

    @Id
    @Column(name = "idUsuario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuario;
    private String nombre;
    private String apellidos;
    private String clave;
    private String email;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name= "idCiudad")
    private Ciudad ciudad;
    //private Integer ciudad;
    private int saldo;
    private String tipo;
    private String avatar;
    @Transient
    private String mensaje;

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /*public int getIdCiudad() {
        return ciudad;
    }*/

    public Ciudad getCiudad() {
    return ciudad;
    }
    public void setCiudad(Ciudad ciudad) {
    this.ciudad = ciudad;
    }
    /*public void setIdCiudad(int ciudad) {
        this.ciudad = ciudad;
    }*/

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void limpiarDatos() {
        this.apellidos = null;
        this.avatar = null;
        this.ciudad = null;
        this.clave = null;
        this.email = null;
        this.idUsuario = 0;
        this.saldo = 0;
        this.tipo = null;
        this.mensaje=null;
    }
    
    public String logOut(){
        limpiarDatos();
        return "true";
    }
    
    public String addUsuario() {
        String exito = null;
        try {
        DAOFactory daof = DAOFactory.getDAOFactory();
        IGenericoDAO gdao = daof.getGenericoDAO();
        if (this.ciudad==null){
            this.ciudad = new Ciudad();
            this.ciudad.setIdCiudad(1);
            this.ciudad = (Ciudad)gdao.getOne(this.ciudad.getIdCiudad(), this.ciudad.getClass());
        }
        gdao.add(this);
        logUsuario();
        exito = "true";
        } catch (HibernateException | NullPointerException e) {
            exito="false";
            limpiarDatos();
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, e);
        }
        return exito;
    }

    public String logUsuario() {
        String resultado = "false";
        DAOFactory daof = DAOFactory.getDAOFactory();
        IGenericoDAO gdao = daof.getGenericoDAO();
        List<Usuario> usuarios = gdao.get("Usuario");
        for (Usuario user : usuarios) {
            if (user.email.equals(this.email)) {
                if (user.clave.equals(this.clave)) {
                    this.apellidos = user.apellidos;
                    this.nombre = user.nombre;
                    this.avatar = user.avatar;
                    this.ciudad = user.ciudad;
                    this.idUsuario = user.idUsuario;
                    this.saldo = user.saldo;
                    this.tipo = user.tipo;
                    this.mensaje=null;
                    resultado = "true";
                }else{
                    resultado="claveIncorrecta";
                    limpiarDatos(); 
                    this.mensaje="Contraseña incorrecta";
                }
            }
        }
        if(resultado.equals("false")){
            limpiarDatos();
            this.mensaje="El email introducido es incorrecto";
        }
        return resultado;
    }
}
