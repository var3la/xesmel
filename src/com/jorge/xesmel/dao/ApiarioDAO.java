package com.jorge.xesmel.dao;

import java.sql.Connection;
import java.util.List;

import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.model.Apiario;
import com.jorge.xesmel.model.ApiarioCriteria;

public interface ApiarioDAO {

	public Apiario findById(Connection c, Long id)throws DataException;
	
	public List<Apiario> findBy(Connection c, ApiarioCriteria ac)throws DataException;
	
	public List<Apiario> findByUsuario(Connection c, Long id)throws DataException;
	
	public Long create(Connection c, Apiario a)throws DataException;
	
	public int update(Connection c, Apiario a)throws DataException;
	
	public void deleteById(Connection c, Long id)throws DataException;
	
	

}
