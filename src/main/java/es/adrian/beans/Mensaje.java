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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.*;
import org.hibernate.HibernateException;

/**
 *
 * @author Adrian
 */
@Entity
@Table(name = "mensajes")
@ManagedBean(name = "mensaje", eager = false)
@SessionScoped
public class Mensaje implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMensaje;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idOferta")
    private Oferta oferta;
    private String tipo;
    private String asunto;
    private String contenido;
    private String leido;

    public int getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(int idMensaje) {
        this.idMensaje = idMensaje;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Oferta getOferta() {
        return oferta;
    }

    public void setOferta(Oferta oferta) {
        this.oferta = oferta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getLeido() {
        return leido;
    }

    public void setLeido(String leido) {
        this.leido = leido;
    }
    
    public String addMensaje(Usuario usuario) {
        String exito = null;
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            this.oferta = (Oferta)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("ofertaElegida");
            this.usuario = usuario;
            this.leido = "n";
            gdao.add(this);
            limpiarDatos();
            exito = "true";

        } catch (HibernateException | NullPointerException e) {
            System.out.println("NULO2 ");
            Logger.getLogger(Mensaje.class.getName()).log(Level.SEVERE, null, e);
            limpiarDatos();
            exito = "false";
        }
        return exito;
    }

    public ArrayList<Mensaje> getMensajes(int idOferta) {
        ArrayList<Mensaje> listaMensajes = new ArrayList();
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            listaMensajes = (ArrayList<Mensaje>) gdao.get("Mensaje");
            if (listaMensajes.isEmpty()) {
                listaMensajes = null;
            }
        } catch (HibernateException | NullPointerException e) {
            Logger.getLogger(Mensaje.class.getName()).log(Level.SEVERE, null, e);
            listaMensajes = null;
        }
        return listaMensajes;
    }

    public void limpiarDatos() {
        this.asunto = null;
        this.contenido = null;
        this.usuario = null;
        this.idMensaje = 0;
        this.leido = null;
        this.oferta = null;
        this.tipo = null;
    }

}
