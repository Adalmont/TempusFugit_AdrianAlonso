package es.adrian.daofactory;

import es.adrian.dao.GenericoDAO;
import es.adrian.dao.IGenericoDAO;


public class MySQLDAOFactory extends DAOFactory{

    
    @Override
    public IGenericoDAO getGenericoDAO() {
        return new GenericoDAO();
    } 
}
