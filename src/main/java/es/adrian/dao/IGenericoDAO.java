package es.adrian.dao;

import java.io.Serializable;
import java.util.List;


public interface IGenericoDAO<T> {
    
    public void add(T objeto);
    public <T> List<T> get(String entidad);
    public <T> T getOne(Serializable pk, Class<T> claseEntidad);
    public void update(T objeto);
    public void delete(T objeto);
    public <T> T getUsuario(String email, String clave);
    public <T> List<T> getMensajes(int idOferta);
}