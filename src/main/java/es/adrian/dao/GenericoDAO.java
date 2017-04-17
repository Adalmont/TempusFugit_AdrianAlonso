package es.adrian.dao;


import es.adrian.beans.Usuario;
import es.adrian.persistencia.HibernateUtil;
import java.io.Serializable;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;

import org.hibernate.Session;
import org.hibernate.TransactionException;


public class GenericoDAO<T> implements IGenericoDAO<T> {
    
    private Session sesion;
    
    private void iniciaSesion(){
        sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.getTransaction().begin();
    }
    
    private void cierraSesion(){
        try{
        sesion.getTransaction().commit();
        sesion.close();
        }catch(TransactionException e){
            Logger.getLogger(GenericoDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    private void manejaExcepcion(HibernateException he) throws HibernateException {
        sesion.getTransaction().rollback();
        throw he;
    } 

    @Override
    public void add(T objeto) {
        try {
            iniciaSesion();
            sesion.saveOrUpdate(objeto);
            sesion.flush();
            
        } catch (HibernateException he){
            manejaExcepcion(he);
        } finally {
            cierraSesion();
        }
    }

    @Override
    public <T> List<T> get(String entidad) {
        List<T> listadoResultados = null;
        try {
            iniciaSesion();
            listadoResultados = sesion.createQuery(" from " + entidad).list();
        } catch(HibernateException he){
            this.manejaExcepcion(he);
        } finally {
            this.cierraSesion();
        }
        return listadoResultados;
    }

    @Override
    public <T> T getOne(Serializable pk, Class<T> claseEntidad) {
        T objetoRecuperado = null;
        
        try {
            iniciaSesion();
            objetoRecuperado = sesion.get(claseEntidad, pk);
        } catch(HibernateException he){
            this.manejaExcepcion(he);
        } finally {
            this.cierraSesion();
        }
        
        return objetoRecuperado;
    }
    
    @Override
    public void update(T objeto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(T objeto) {
        try {
            iniciaSesion();
            sesion.delete(objeto);
        } catch(HibernateException he){
            this.manejaExcepcion(he);
        } finally {
            this.cierraSesion();
        }
    }
}
