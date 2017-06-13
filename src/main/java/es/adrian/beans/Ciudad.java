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
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.HibernateException;

/**
 *
 * @author Adrian
 */
@Entity
@Table(name = "ciudades")
@ManagedBean
public class Ciudad implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCiudad;
    private String nombre;
    private String estado;
    private double latitud;
    private double longitud;
    @Transient
    private String mensaje;

    public Integer getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(Integer idCiudad) {
        this.idCiudad = idCiudad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void limpiarDatos() {
        this.nombre = null;
        this.latitud = 0;
        this.longitud = 0;
        this.estado = null;
    }

    public ArrayList<Ciudad> getCiudades() {
        ArrayList<Ciudad> listaCiudades = new ArrayList();
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            listaCiudades = (ArrayList<Ciudad>) gdao.get("Ciudad");
        } catch (HibernateException he) {
            Logger.getLogger(Oferta.class.getName()).log(Level.SEVERE, null, he);
        }
        return listaCiudades;
    }

    public String addCiudad() {
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            this.estado = "a";
            gdao.add(this);
            limpiarDatos();
            this.mensaje= "Ciudad Añadida";
            return "true";
        } catch (HibernateException he) {
            Logger.getLogger(Oferta.class.getName()).log(Level.SEVERE, null, he);
            return "false";
        }
    }

    @Override
    public boolean equals(Object other) {
        return (other != null && getClass() == other.getClass() && this.idCiudad != null)
                ? this.idCiudad.equals(((Ciudad) other).idCiudad)
                : (other == this);
    }

    @Override
    public int hashCode() {
        return (this.idCiudad != null)
                ? (getClass().hashCode() + this.idCiudad.hashCode())
                : super.hashCode();
    }
}
