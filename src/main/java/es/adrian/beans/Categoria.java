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
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.faces.bean.ManagedBean;
import javax.persistence.GenerationType;
import org.hibernate.HibernateException;

/**
 *
 * @author Adrian
 */
@Entity
@Table(name = "categorias")
@ManagedBean(name = "categoria", eager = false)
public class Categoria implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCategoria;
    private String nombre;
    private String imagen;

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public ArrayList<Categoria> getCat() {
        ArrayList<Categoria> listaCat = new ArrayList();
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            listaCat = (ArrayList<Categoria>) gdao.get("Categoria");
        } catch (HibernateException he) {
            Logger.getLogger(Categoria.class.getName()).log(Level.SEVERE, null, he);
        }
        return listaCat;
    }

    @Override
    public boolean equals(Object other) {
        return (other != null && getClass() == other.getClass() && this.idCategoria != null)
                ? this.idCategoria.equals(((Categoria) other).idCategoria)
                : (other == this);
    }

    @Override
    public int hashCode() {
        return (this.idCategoria != null)
                ? (getClass().hashCode() + this.idCategoria.hashCode())
                : super.hashCode();
    }

}
