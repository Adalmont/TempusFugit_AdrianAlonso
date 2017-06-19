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
import javax.faces.context.FacesContext;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.HibernateException;

/**
 * Clase para gestionar todo los relacionado con las
 * ciudades de la aplicacion
 * 
 * @author Adrian
 * @version final
 * @since 1.8
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
    /**
     * Metodo que devuelve un array list de todas las ciudades
     * aprobadas para seleccionarlas en el registro
     * 
     * @return lista de ciudades aprobadas
     * @since 1.8
     */
    public ArrayList<Ciudad> getCiudades() {
        ArrayList<Ciudad> listaCiudades = new ArrayList();
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            listaCiudades = (ArrayList<Ciudad>) gdao.get("Ciudad where estado='a'");
        } catch (HibernateException he) {
            Logger.getLogger(Oferta.class.getName()).log(Level.SEVERE, null, he);
        }
        return listaCiudades;
    }

    /**
     * Metodo que devuelve un array list de todas las ciudades
     * pendientes de aprobacion para visualizarlas en el panel de
     * administrador
     * 
     * @return lista de ciudades sin aprobar
     * @since 1.8
     */
    public ArrayList<Ciudad> getCiudadesPendientes() {
        ArrayList<Ciudad> listaCiudades = new ArrayList();
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            listaCiudades = (ArrayList<Ciudad>) gdao.get("Ciudad where estado='p'");
        } catch (HibernateException he) {
            Logger.getLogger(Oferta.class.getName()).log(Level.SEVERE, null, he);
        }
        return listaCiudades;
    }

    /**
     * Metodo para crear una nueva ciudad en la
     * base de datos
     * 
     * @return true si se ha creado correctamente,
     *         false si se produce algun error de hibernate
     * @since 1.8
     */
    public String addCiudad() {
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            this.estado = "a";
            gdao.add(this);
            limpiarDatos();
            this.mensaje = "Ciudad Añadida";
            return "true";
        } catch (HibernateException he) {
            Logger.getLogger(Oferta.class.getName()).log(Level.SEVERE, null, he);
            return "false";
        }
    }

    /**
     * Metodo para borrar una ciudad de la base de datos
     * 
     * @return true si se borra correctamente,
     *         false si se produce un error de hibernate
     * @since 1.8
     */
    public String deleteCiudad() {
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            gdao.delete(this);
            limpiarDatos();
            this.mensaje = "Ciudad Eliminada";
            return "true";
        } catch (HibernateException he) {
            Logger.getLogger(Oferta.class.getName()).log(Level.SEVERE, null, he);
            return "false";
        }
    }

    /**
     * Metodo para actualizar una ciudad en la base de datos
     * 
     * @return true si se actualiza correctamente,
     *         false si se produce un error de hibernate
     */
    public String updateCiudad() {
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            this.estado = "a";
            Ciudad ciudad = (Ciudad) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("ciudadElegida");
            this.idCiudad= ciudad.idCiudad;
            gdao.update(this);
            ArrayList<Usuario> usuariosCiudad = (ArrayList<Usuario>) gdao.get("Usuario where idCiudad=" + this.idCiudad);
            if (!usuariosCiudad.isEmpty()) {
                for (Usuario usuario : usuariosCiudad) {
                    usuario.setEstado("r");
                    gdao.update(usuario);
                }
            }
            limpiarDatos();
            this.mensaje = "Ciudad Actualizada";
            return "true";
        } catch (HibernateException he) {
            Logger.getLogger(Oferta.class.getName()).log(Level.SEVERE, null, he);
            return "false";
        }
    }

    /**
     * Metodo para elegir una ciudad de una lista
     * y guardarla en un atributo para poder persistirla
     * entre paginas
     * 
     * @param ciudadElegida objeto Ciudad elegido
     * @return true si el parametro no es nulo,
     *         false en caso contrario
     */
    public String elegirCiudad(Ciudad ciudadElegida) {
        if (ciudadElegida != null) {
            this.idCiudad = ciudadElegida.idCiudad;
            this.latitud = ciudadElegida.latitud;
            this.longitud = ciudadElegida.longitud;
            this.nombre = ciudadElegida.nombre;
            this.estado = ciudadElegida.estado;
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ciudadElegida", this);
            return "true";
        } else {
            return "false";
        }
    }

    /**
     * Metodo para llenar el bean con los parametros del
     * atributo de sesion creado en elegirCiudad()
     */
    public void getCiudad() {
        Ciudad ciudadElegida = (Ciudad) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("ciudadElegida");
        this.idCiudad = ciudadElegida.idCiudad;
        this.latitud = ciudadElegida.latitud;
        this.longitud = ciudadElegida.longitud;
        this.nombre = ciudadElegida.nombre;
        this.estado = ciudadElegida.estado;
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
