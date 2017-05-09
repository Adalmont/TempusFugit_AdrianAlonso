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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.HibernateException;
/**
 *
 * @author Adrian
 */
@Entity
@Table(name="subcategorias")
@ManagedBean
public class Subcategoria implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSubcategoria;
    private String nombre;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idCategoria")
    private Categoria categoria;
    private String imagen;

    public int getIdSubcategoria() {
        return idSubcategoria;
    }

    public void setIdSubcategoria(int idSubcategoria) {
        this.idSubcategoria = idSubcategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    /*Para que funcionen los convertidores de JSF se necesita que la clase que se quiere convertir disponga de
    metodos equals y hashcode definidos*/
    @Override
    public boolean equals(Object other) {
        return (other != null && getClass() == other.getClass() && this.idSubcategoria != null)
            ? this.idSubcategoria.equals(((Subcategoria) other).idSubcategoria)
            : (other == this);
    }

    @Override
    public int hashCode() {
        return (this.idSubcategoria != null) 
            ? (getClass().hashCode() + this.idSubcategoria.hashCode())
            : super.hashCode();
    }

}
