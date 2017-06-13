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
@ManagedBean(name = "servicio", eager = false)
@SessionScoped
public class Servicio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idServicio;
    @ManyToOne
    @JoinColumn(name = "idOferta")
    private Oferta oferta;
    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "idCreador")
    private Usuario creador;
    private String estado;
    private int puntuacion;
    @ManyToOne
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

    public void limpiarDatos() {
        this.comentario = null;
        this.estado = null;
        this.horario = null;
        this.idServicio = 0;
        this.oferta = null;
        this.puntuacion = 0;
        this.usuario = null;
    }

    public String addServicio(Horario horario, Usuario usuario) throws Exception {
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            this.usuario = usuario;
            this.oferta = (Oferta) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("ofertaElegida");
            this.horario = horario;
            this.horario.setEstado("o");
            this.estado = "p";
            this.comentario = "";
            this.puntuacion = 0;
            this.creador = this.oferta.getUsuario();
            System.out.println(this.usuario.getSaldo());
            System.out.println(horario.getHoraFin());
            System.out.println(horario.getHoraInicio());
            this.usuario.setSaldo((this.usuario.getSaldo() - (horario.getHoraFin() - horario.getHoraInicio())));
            System.out.println("DATOS SERVICIO: " + this.estado + " Coment " + this.comentario + " Oferta " + this.oferta.getIdOferta() + " Usuario " + this.usuario.getIdUsuario() + " Horario " + this.horario.getIdHorario());
            gdao.add(this);
            gdao.update(this.horario);
            this.usuario.updateUsuario();
            limpiarDatos();
            return "true";
        } catch (HibernateException he) {
            Logger.getLogger(Servicio.class.getName()).log(Level.ALL, null, he);
            System.out.println("FALLO");
            limpiarDatos();
            return "false";
        }
    }

    public ArrayList<Servicio> getServicios(int idUsuario, String condicion) {
        ArrayList<Servicio> listaServicios = new ArrayList();
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            listaServicios = (ArrayList<Servicio>) gdao.get("Servicio where idCreador=" + idUsuario + " " + condicion);
        } catch (HibernateException he) {
            Logger.getLogger(Oferta.class.getName()).log(Level.SEVERE, null, he);
        }
        return listaServicios;
    }

    public ArrayList<Servicio> getServiciosContratados(int idUsuario) {
        ArrayList<Servicio> listaServicios = new ArrayList();
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            listaServicios = (ArrayList<Servicio>) gdao.get("Servicio where idUsuario=" + idUsuario);
        } catch (HibernateException he) {
            Logger.getLogger(Oferta.class.getName()).log(Level.SEVERE, null, he);
        }
        return listaServicios;
    }

    public String actualizarServicio(String nuevoEstado) {
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            this.estado = nuevoEstado;
            gdao.update(this);
            if (nuevoEstado.equals("f")) {
                this.creador.setSaldo((this.creador.getSaldo() + (this.horario.getHoraFin() - this.horario.getHoraInicio())));
                gdao.update(this.creador);
            }
            return "true";
        } catch (HibernateException he) {
            Logger.getLogger(Oferta.class.getName()).log(Level.SEVERE, null, he);
            return "false";
        }
    }

    public String rechazarServicio(Servicio servicio) {
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            Usuario cliente = servicio.getUsuario();
            Horario horario = servicio.getHorario();
            cliente.setSaldo((cliente.getSaldo() + (horario.getHoraFin() - horario.getHoraInicio())));
            horario.setEstado("l");
            gdao.update(cliente);
            gdao.update(horario);
            gdao.delete(servicio);
            return "true";
        } catch (HibernateException he) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, he);
            return "false";
        }
    }
}
