/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.adrian.beans;

import es.adrian.dao.IGenericoDAO;
import es.adrian.daofactory.DAOFactory;
import java.io.Serializable;
import java.util.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.persistence.Temporal;
import org.hibernate.HibernateException;

/**
 * Clase para controlar todo lo relacionado con los
 * horarios de las ofertas de la aplicacion
 * 
 * @author Adrian
 * @version final
 * @since 1.8
 */
@Entity
@Table(name = "horarios")
@ManagedBean
public class Horario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idHorario;
    private int horaInicio;
    private int horaFin;
    private String estado;
    @ManyToOne
    @JoinColumn(name = "idOferta")
    private Oferta oferta;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public int getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(int horaInicio) {
        this.horaInicio = horaInicio;
    }

    public int getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(int horaFin) {
        this.horaFin = horaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Oferta getOferta() {
        return oferta;
    }

    public void setOferta(Oferta oferta) {
        this.oferta = oferta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Metodo para devolver la hora de inicio
     * en formato 24h
     * 
     * @return hora de inicio en formato hh:mm o solo hh
     */
    public String getHoraInicioFormateada() {
        if ((this.horaInicio % 60) == 0) {
            return (int) (this.horaInicio / 60) + "";
        } else {
            return (int) (this.horaInicio / 60) + ":" + (this.horaInicio % 60);
        }
    }

    /**
     * Metodo para devolver la hora de fin
     * en formato 24h
     * 
     * @return hora de fin en formato hh:mm o solo hh
     */
    public String getHoraFinFormateada() {
        if ((this.horaFin % 60) == 0) {
            return (int) (this.horaFin / 60) + "";
        } else {
            return (int) (this.horaFin / 60) + ":" + (this.horaFin % 60);
        }

    }

    /**
     * metodo para reiniciar los parametros del bean
     */
    public void limpiarDatos() {
        this.estado = null;
        this.fecha = null;
        this.horaFin = 0;
        this.horaInicio = 0;
        this.idHorario = 0;
        this.oferta = null;
    }

    /**
     * metodo para crear un nuevo horario en la base de datos
     */
    public void addHorario() {
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            this.estado = "l";
            gdao.add(this);
            limpiarDatos();
        } catch (HibernateException e) {
            Logger.getLogger(Horario.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Metodo para obtener todos los horarios pertenecientes a una oferta
     * concreta
     * @param idOferta identificador de la oferta de la que se desean obtener los horarios
     * @return lista de horarios de la oferta
     */
    public ArrayList<Horario> getHorarios(int idOferta) {
        ArrayList<Horario> listaHorarios = new ArrayList();
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            listaHorarios = (ArrayList<Horario>) gdao.get("Horario where idOferta= " + idOferta);
        } catch (HibernateException he) {
            Logger.getLogger(Oferta.class.getName()).log(Level.SEVERE, null, he);
        }
        return listaHorarios;
    }
}
