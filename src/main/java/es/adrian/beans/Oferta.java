/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.adrian.beans;

import static es.adrian.beans.Usuario.subirImagen;
import es.adrian.dao.IGenericoDAO;
import es.adrian.daofactory.DAOFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
@Table(name = "ofertas")
@ManagedBean(name = "oferta", eager = false)
@SessionScoped
public class Oferta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idOferta;
    private String nombre;
    private String descripcion;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idSubcategoria")
    private Subcategoria subcategoria;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idUsuario")
    @ManagedProperty(value = "#{usuario}")
    private Usuario usuario;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaInicio;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaFin;
    private String estado;
    private String imgPrincipal;
    @Transient
    private String mensaje;
    @Transient
    private Subcategoria subcatBusqueda;
    @Transient
    private Part imgSubir;

    public int getIdOferta() {
        return idOferta;
    }

    public void setIdOferta(int idOferta) {
        this.idOferta = idOferta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Subcategoria getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(Subcategoria subcategoria) {
        this.subcategoria = subcategoria;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getImgPrincipal() {
        return imgPrincipal;
    }

    public void setImgPrincipal(String imgPrincipal) {
        this.imgPrincipal = imgPrincipal;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Subcategoria getSubcatBusqueda() {
        return subcatBusqueda;
    }

    public void setSubcatBusqueda(Subcategoria subcatBusqueda) {
        this.subcatBusqueda = subcatBusqueda;
    }

    public Part getImgSubir() {
        return imgSubir;
    }

    public void setImgSubir(Part imgSubir) {
        this.imgSubir = imgSubir;
    }
    

    public ArrayList<Oferta> getOfertas() {
        ArrayList<Oferta> listaOfertas = new ArrayList();
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            if (this.subcatBusqueda == null) {
                listaOfertas = (ArrayList<Oferta>) gdao.get("Oferta");
            } else {
                listaOfertas = (ArrayList<Oferta>) gdao.get("Oferta where idSubcategoria= " + this.subcatBusqueda.getIdSubcategoria());
            }
        } catch (HibernateException he) {
            Logger.getLogger(Oferta.class.getName()).log(Level.SEVERE, null, he);
        }
        return listaOfertas;
    }

    /*public ArrayList<Oferta> getOfertasByCat() {
        ArrayList<Oferta> listaOfertas = new ArrayList();
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            listaOfertas = (ArrayList<Oferta>) gdao.get("Oferta");
        } catch (HibernateException he) {
            Logger.getLogger(Oferta.class.getName()).log(Level.SEVERE, null, he);
        }
    }*/
    public String addOferta() throws IOException {
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            
            this.estado = "a";
            gdao.add(this);
            if (this.imgSubir!=null){
                subirImagenPrincipal();
            }
            limpiarDatos();
            this.mensaje = "Oferta creada";
            return "true";
        } catch (HibernateException e) {
            Logger.getLogger(Oferta.class.getName()).log(Level.SEVERE, null, e);
            limpiarDatos();
            this.mensaje = "Error al crear la Oferta";
            return "false";
        }
    }

    /*Este metodo recibe los datos de una oferta en la lista de ofertas y la pasa al bean para mostrarla en la pagina de
    la oferta individual*/
    public String elegirOferta(Oferta ofertaElegida) {
        if (ofertaElegida != null) {
            this.idOferta = ofertaElegida.idOferta;
            this.descripcion = ofertaElegida.descripcion;
            this.estado = ofertaElegida.estado;
            this.nombre = ofertaElegida.nombre;
            this.fechaFin = ofertaElegida.fechaFin;
            this.fechaInicio = ofertaElegida.fechaInicio;
            this.subcategoria = ofertaElegida.subcategoria;
            this.usuario = ofertaElegida.usuario;
            this.imgPrincipal = ofertaElegida.imgPrincipal;
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ofertaElegida", this);
            return "true";
        } else {
            return "false";
        }
    }

    public void limpiarDatos() {
        this.idOferta = 0;
        this.descripcion = null;
        this.estado = null;
        this.nombre = null;
        this.fechaFin = null;
        this.fechaInicio = null;
        this.subcategoria = null;
        this.mensaje = null;
        this.subcatBusqueda = null;
        this.imgSubir= null;
        this.imgPrincipal= null;
    }

    public String updateOferta() {
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            gdao.update(this);
            return "true";

        } catch (HibernateException he) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, he);
            return "false";
        }
    }

    public void subirImagenPrincipal() throws IOException {
        setImgPrincipal(subirImagen("ofertas", this.imgSubir, String.valueOf(this.idOferta)));
        updateOferta();
    }

    static String subirImagen(String carpeta, Part archivo, String nombre) throws IOException {
        String filename = FilenameUtils.getBaseName(nombre);
        String extension = FilenameUtils.getExtension(".jpg");
        Path fichero = Paths.get("C:\\NetBeansProjects\\TempusFugitAdrianAlonso\\src\\main\\webapp\\resources\\imagenes\\ofertas" + System.getProperty("file.separator") + filename + "." + extension);
        Path file = Files.createFile(fichero);
        try (InputStream input = archivo.getInputStream()) {
            Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
        }

        System.out.println("Imagen subida a: " + file);

        return file.getFileName().toString();

    }
}
