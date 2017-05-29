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
 *
 * @author Adrian
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
    @ManyToOne(cascade = CascadeType.ALL)
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

    public void addHorario() {
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            this.estado = "l";
            gdao.add(this);
        } catch (HibernateException e) {
            Logger.getLogger(Horario.class.getName()).log(Level.SEVERE, null, e);
        }
    }

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
