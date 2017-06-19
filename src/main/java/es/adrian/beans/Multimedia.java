/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.adrian.beans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/**
 * Clase para controlar todo lo relacionado con la multimedia de la aplicacion
 * @author Adrian
 * @version final
 * @since 1.8
 */
@Entity
@Table(name="multimedia")
@ManagedBean
public class Multimedia implements Serializable {
    @Id
    @GeneratedValue
    private int idMultimedia;
    private String nombre;
    private String tipo;
    @ManyToOne
    private Oferta oferta;

    public int getIdMultimedia() {
        return idMultimedia;
    }

    public void setIdMultimedia(int idMultimedia) {
        this.idMultimedia = idMultimedia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Oferta getOferta() {
        return oferta;
    }

    public void setOferta(Oferta oferta) {
        this.oferta = oferta;
    }

    
    
}
