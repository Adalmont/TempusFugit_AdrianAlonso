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
    @JoinColumn(name = "idCiudad")
    private Ciudad ciudad;
    private int saldo;
    private String tipo;
    private String avatar;
    @Transient
    private String mensaje;
    @Transient
    private String confirmarClave;
    /*@Transient
    private String nuevaClave;*/

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

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

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

    public String getConfirmarClave() {
        return confirmarClave;
    }

    public void setConfirmarClave(String confirmarClave) {
        this.confirmarClave = confirmarClave;
    }

    /*public String getNuevaClave() {
        return nuevaClave;
    }

    public void setNuevaClave(String nuevaClave) {
        this.nuevaClave = nuevaClave;
    }*/

    public void limpiarDatos() {
        this.nombre = null;
        this.apellidos = null;
        this.avatar = null;
        this.ciudad = null;
        this.clave = null;
        this.email = null;
        this.idUsuario = 0;
        this.saldo = 0;
        this.tipo = null;
        this.mensaje = null;
    }

    public String logOut() {
        limpiarDatos();
        return "true";
    }

    public String addUsuario() {
        String exito = null;
        if (this.clave.equals(this.confirmarClave)) {
            try {
                DAOFactory daof = DAOFactory.getDAOFactory();
                IGenericoDAO gdao = daof.getGenericoDAO();
                if (this.ciudad == null) {
                    this.ciudad = new Ciudad();
                    this.ciudad.setIdCiudad(1);
                    this.ciudad = (Ciudad) gdao.getOne(this.ciudad.getIdCiudad(), this.ciudad.getClass());
                }
                this.tipo = "n";
                gdao.add(this);
                logUsuario();
                exito = "true";
            } catch (HibernateException | NullPointerException e) {
                exito = "false";
                limpiarDatos();
                this.mensaje = "Ha habido un error en el registro";
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, e);
            }
        } else {
            exito = "false";
            limpiarDatos();
            this.mensaje = "Las contraseñas no coinciden";
        }
        return exito;
    }

    public String logUsuario() {
        String resultado = "false";
        DAOFactory daof = DAOFactory.getDAOFactory();
        IGenericoDAO gdao = daof.getGenericoDAO();
        Usuario user = (Usuario) gdao.getUsuario(this.email, this.clave);
        if (user != null) {
            this.apellidos = user.apellidos;
            this.nombre = user.nombre;
            this.avatar = user.avatar;
            this.ciudad = user.ciudad;
            this.idUsuario = user.idUsuario;
            this.saldo = user.saldo;
            this.tipo = user.tipo;
            this.confirmarClave = null;
            this.mensaje = null;
            resultado = "true";
        }
        if (resultado.equals("false")) {
            limpiarDatos();
            this.mensaje = "Email o contraseña incorrectos";
        }
        return resultado;
    }
    /*public String updateUsuario(){
        try{
            if (!this.confirmarClave.equals(this.clave)){
                this.confirmarClave=null;
                this.nuevaClave=null;
                return "Contraseña incorrecta";
            }else{
                this.clave=this.nuevaClave;
                DAOFactory daof = DAOFactory.getDAOFactory();
                IGenericoDAO gdao = daof.getGenericoDAO();
                gdao.update(gdao);
            }
        }catch(HibernateException he){
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, he);
        }*/
}
