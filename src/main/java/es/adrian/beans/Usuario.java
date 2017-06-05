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
import java.nio.file.*;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
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
import javax.servlet.http.Part;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.HibernateException;

/**
 *
 * @author Adrian
 */
@Entity
@Table(name = "Usuarios")
@ManagedBean(name = "usuario", eager = false)
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
    @ManyToOne
    @JoinColumn(name = "idCiudad")
    private Ciudad ciudad;
    private int saldo;
    private String tipo;
    private String avatar;
    @Transient
    private String mensaje;
    @Transient
    private String confirmarClave;
    @Transient
    private Part imgSubir;
    @Transient
    private String codigoProm;
    @Transient
    private int numeroSolicitudes;

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

    public Part getImgSubir() {
        return imgSubir;
    }

    public void setImgSubir(Part imgSubir) {
        this.imgSubir = imgSubir;
    }

    public String getCodigoProm() {
        return codigoProm;
    }

    public void setCodigoProm(String codigoProm) {
        this.codigoProm = codigoProm;
    }

    public int getNumeroSolicitudes() {
        return numeroSolicitudes;
    }

    public void setNumeroSolicitudes(int numeroSolicitudes) {
        this.numeroSolicitudes = numeroSolicitudes;
    }

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
        this.imgSubir = null;
    }

    public String logOut() {
        limpiarDatos();
        return "true";
    }

    /*Este metodo añade usuarios a la base de datos. si no se ha seleccionado ninguna ciudad, asigna una por defecto */
    public String addUsuario() throws Exception {
        String exito = null;
        if (this.clave.equals(this.confirmarClave)) {
            try {
                if (this.ciudad.getIdCiudad() == 1) {
                    exito = "false";
                    limpiarDatos();
                    this.mensaje = "Elija una ciudad";
                } else {
                    DAOFactory daof = DAOFactory.getDAOFactory();
                    IGenericoDAO gdao = daof.getGenericoDAO();
                    this.tipo = "n";
                    this.clave = encriptarMD5(this.clave);
                    gdao.add(this);
                    logUsuario();
                    if (this.imgSubir != null) {
                        subirAvatar();
                    }
                    exito = "true";
                }
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

    public String logUsuario() throws Exception {
        String resultado;
        DAOFactory daof = DAOFactory.getDAOFactory();
        IGenericoDAO gdao = daof.getGenericoDAO();
        ArrayList<Usuario> user;
        if (this.nombre == null && this.apellidos == null) {
            user = (ArrayList<Usuario>) gdao.get("Usuario as u where u.email= '" + this.email + "' and u.clave= '" + encriptarMD5(this.clave) + "'");
        } else {
            user = (ArrayList<Usuario>) gdao.get("Usuario as u where u.email= '" + this.email + "' and u.clave= '" + this.clave + "'");
        }
        if (!user.isEmpty()) {
            this.apellidos = user.get(0).apellidos;
            this.nombre = user.get(0).nombre;
            this.avatar = user.get(0).avatar;
            this.ciudad = user.get(0).ciudad;
            this.idUsuario = user.get(0).idUsuario;
            this.saldo = user.get(0).saldo;
            this.tipo = user.get(0).tipo;
            this.clave = user.get(0).clave;
            this.confirmarClave = null;
            this.mensaje = null;
            resultado = "true";
        } else {
            resultado = "false";
        }
        if (resultado.equals("false")) {
            limpiarDatos();
            this.mensaje = "Email o contraseña incorrectos";
        }
        return resultado;
    }

    public String updateUsuario() throws Exception {
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            gdao.update(this);
            logUsuario();
            return "true";

        } catch (HibernateException he) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, he);
            return "false";
        }
    }

    public String comprobarCodigo() throws Exception {
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            ArrayList<Promocion> codigo;
            codigo = (ArrayList<Promocion>) gdao.get("Promocion as p where p.codigo = '" + this.codigoProm + "'");
            if (codigo.isEmpty()) {
                this.codigoProm = null;
                this.mensaje = "El codigo introducido no es correcto";
                return "false";
            } else {
                this.codigoProm = null;
                this.saldo = this.saldo + codigo.get(0).getSaldo();
                updateUsuario();
                this.mensaje = codigo.get(0).getSaldo() + " minutos se han añadido a su cuenta";
                return "true";
            }
        } catch (HibernateException he) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, he);
            return "false";
        }
    }

    public void subirAvatar() throws IOException, Exception {
        setAvatar(subirImagen("avatares", this.imgSubir, String.valueOf(this.idUsuario)));
        updateUsuario();
    }

    static String subirImagen(String carpeta, Part archivo, String nombre) throws IOException {
        String filename = FilenameUtils.getBaseName(nombre);
        String extension = FilenameUtils.getExtension(".jpg");
        Path fichero = Paths.get("C:\\NetBeansProjects\\TempusFugitAdrianAlonso\\src\\main\\webapp\\resources\\imagenes\\avatares" + System.getProperty("file.separator") + filename + "." + extension);
        Path file = Files.createFile(fichero);
        try (InputStream input = archivo.getInputStream()) {
            Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
        }

        System.out.println("Imagen subida a: " + file);

        return file.getFileName().toString();

    }

    public static String encriptarMD5(String cadena) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(cadena.getBytes());
        byte[] digest = md.digest();
        byte[] encoded = Base64.encodeBase64(digest);
        return new String(encoded);
    }

    public boolean solicitudesPendientes() {
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            ArrayList<Servicio> listaServicios = (ArrayList<Servicio>)gdao.get("Servicio where idCreador=" + this.idUsuario + " and estado='p'");
            if (!listaServicios.isEmpty()){
                this.numeroSolicitudes= listaServicios.size();
                return true;
            }else{
                return false;
            }
        } catch (HibernateException he) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, he);
            return false;
        }
    }
    public boolean contratos() {
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            ArrayList<Servicio> listaContratos = (ArrayList<Servicio>)gdao.get("Servicio where idUsuario=" + this.idUsuario + " and estado='a'");
            if (!listaContratos.isEmpty()){
                this.numeroSolicitudes= listaContratos.size();
                return true;
            }else{
                return false;
            }
        } catch (HibernateException he) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, he);
            return false;
        }
    }
    public boolean ofertas() {
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            ArrayList<Servicio> listaOfertas = (ArrayList<Servicio>)gdao.get("Servicio where idCreador=" + this.idUsuario + " and estado='a'");
            if (!listaOfertas.isEmpty()){
                return true;
            }else{
                return false;
            }
        } catch (HibernateException he) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, he);
            return false;
        }
    }
}
