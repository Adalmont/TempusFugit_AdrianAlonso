/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.adrian.beans;

import es.adrian.dao.IGenericoDAO;
import es.adrian.daofactory.DAOFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.persistence.*;
import javax.servlet.http.Part;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.HibernateException;

/**
 *
 * @author Adrian
 */
@Entity
@Table(name = "servicios")
@ManagedBean
public class Servicio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idServicio;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idOferta")
    private Oferta oferta;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idCreador")
    private Usuario creador;
    private String estado;
    private int puntuacion;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idHorario")
    private Horario horario;
    private String comentario;

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public Oferta getOferta() {
        return oferta;
    }

    public void setOferta(Oferta oferta) {
        this.oferta = oferta;
    }

    public Usuario getCliente() {
        return usuario;
    }

    public void setCliente(Usuario cliente) {
        this.usuario = cliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getCreador() {
        return creador;
    }

    public void setCreador(Usuario creador) {
        this.creador = creador;
    }

    public String addServicio(Oferta oferta, Horario horario, Usuario usuario) {
        this.oferta = oferta;
        this.usuario= usuario;
        this.creador = this.oferta.getUsuario();
        this.horario = horario;
        this.horario.setEstado("o");
        this.estado = "p";
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            gdao.add(this);
            gdao.update(this.horario);
            return "true";
        } catch (HibernateException he) {
            Logger.getLogger(Servicio.class.getName()).log(Level.SEVERE, null, he);
            return "false";
        }
    }
}
