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
 * Clase para controlar todo lo relacionado con las ofertas
 * de la aplicacion
 * @author Adrian
 * @version final
 * @since 1.8
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
    @ManyToOne
    @JoinColumn(name = "idSubcategoria")
    private Subcategoria subcategoria;
    @ManyToOne
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
    @Transient
    private int horaInicio;
    @Transient
    private int horaFin;
    @Transient
    private String opcion;

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

    public String getOpcion() {
        return opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }

    /**
     * metodo que devuelve una lista con todas las ofertas, o todas
     * las ofertas pertenecientes a una subcategoria
     * @return lista de ofertas
     */
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

    /**
     * Metodo para crear una nueva oferta en la base de datos
     * @return true si se crea correctamente, false si se produce un erro de hibernate
     * @throws IOException 
     */
    public String addOferta() throws IOException {
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();

            this.estado = "p";
            gdao.add(this);
            if (this.imgSubir != null) {
                subirImagenPrincipal();
            }
            this.mensaje = "Oferta creada";
            return "true";
        } catch (HibernateException e) {
            Logger.getLogger(Oferta.class.getName()).log(Level.SEVERE, null, e);
            limpiarDatos();
            this.mensaje = "Error al crear la Oferta";
            return "false";
        }
    }

    /**
     * Metodo para crear una oferta, que llama a addOferta() para añadirla a la base de datos 
     * y despues crea todos los horarios necesarios para la oferta
     * @return true si la oferta se crea sin problemas, false si se superan las 10
     * horas de trabajo o se produce un error de hibernate
     * @throws IOException 
     */
    public String crearOferta() throws IOException {
        String resultado = null;
        addOferta();
        if (this.horaFin - this.horaInicio > 480) {
            resultado = "false";
            this.mensaje = "No se pueden superar las 8 horas de trabajo";
            deleteOferta();
        } else {
            ArrayList<LocalDate> fechas = new ArrayList();
            LocalDate inicio = this.fechaInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            while (!inicio.isAfter(this.fechaFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
                fechas.add(inicio);
                inicio = inicio.plusDays(1);
            }
            for (LocalDate fechaActual : fechas) {
                Horario horario = new Horario();
                horario.setFecha(java.sql.Date.valueOf(fechaActual));
                horario.setHoraInicio(this.horaInicio);
                horario.setHoraFin(this.horaFin);
                horario.setOferta(this);
                horario.addHorario();
            }
            limpiarDatos();
            resultado = "creada";
        }

        return resultado;
    }

    /**
     * Metodo para guardar una oferta elegida en una lista en un atributo
     * de jsf
     * @param ofertaElegida objeto Oferta que se desea guardar
     * @return true si la oferta elegida no es nula, false en caso contrario
     */
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

    /**
     * metodo para reiniciar los parametros del bean
     */
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
        this.imgSubir = null;
        this.imgPrincipal = null;
    }

    /**
     * metodo para borrar una oferta de la base de datos
     */
    public void deleteOferta() {
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            gdao.delete(this);
        } catch (HibernateException e) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * metodo para actualizar una oferta de la base de datos
     * @return true si se actualiza correctamente, false en caso contrario
     */
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

    /**
     * Metodo para que el administrador apruebe o rechace una oferta
     * @param aprobar cadena que indica la accion a realizar
     * @return borrada si la oferta se ha borrado, aprobada si se ha aprobado
     */
    public String aprobarOferta(String aprobar) {
        if (!aprobar.equals("aprobada")) {
            deleteOferta();
            return "borrada";
        } else {
            this.estado = "a";
            updateOferta();
            return "aprobada";
        }
    }

    /**
     * Metodo que sube una imagen y actualiza la oferta
     * @throws IOException 
     */
    public void subirImagenPrincipal() throws IOException {
        setImgPrincipal(subirImagen("ofertas", this.imgSubir, String.valueOf(this.idOferta)));
        updateOferta();
    }

    /**
     * Metodo que sube una imagen al proyecto
     * @param carpeta carpeta en la que se desea guardar la imagen
     * @param archivo archivo que se desea guardar
     * @param nombre nombre del archivo que se guardara
     * @return  nombre del archivo creado
     * @throws IOException 
     */
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
