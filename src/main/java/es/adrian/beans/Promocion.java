/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.adrian.beans;

import es.adrian.dao.IGenericoDAO;
import es.adrian.daofactory.DAOFactory;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.HibernateException;

/**
 *
 * @author Adrian
 */
@Entity
@Table(name = "promocion")
@ManagedBean
public class Promocion implements Serializable {

    @Id
    @Column(name = "idPromocion")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPromocion;
    private String codigo;
    private int saldo;
    @Transient
    private String mensaje;

    public int getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(int idPromocion) {
        this.idPromocion = idPromocion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    public void limpiarDatos(){
        this.codigo = null;
        this.idPromocion = 0;
        this.saldo = 0;
    }

    public String addPromocion() {
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            gdao.add(this);
            limpiarDatos();
            this.mensaje="Codigo añadido";
            return "true";
        } catch (HibernateException he) {
            Logger.getLogger(Promocion.class.getName()).log(Level.SEVERE, null, he);
            limpiarDatos();
            this.mensaje="Error al añadir el codigo";
            return "false";
        }
    }
}
