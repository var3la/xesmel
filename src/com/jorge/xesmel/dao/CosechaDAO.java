package com.jorge.xesmel.dao;


import java.sql.Connection;
import java.util.Date;
import java.util.List;

import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.model.Cosecha;

public interface CosechaDAO {
	
	public Cosecha findById(Connection c, Long id) throws DataException;
	
	public List <Cosecha> findBy(Connection c, Long id,Double cantidad, Date FechaRecogida,Long colmenaId,Long tipoCosechaId) throws DataException;
	
	public Long create(Connection c, Cosecha co)throws DataException;
	
	
	
	public void deleteById(Connection c, Long id)throws DataException;

}