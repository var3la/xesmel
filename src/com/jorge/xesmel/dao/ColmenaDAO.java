package com.jorge.xesmel.dao;

import java.sql.Connection;
import java.util.List;

import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.model.Colmena;
import com.jorge.xesmel.model.ColmenaCriteria;
import com.jorge.xesmel.model.Results;

public interface ColmenaDAO {

	public Colmena findById(Connection c, Long Id) throws DataException;
	
	public Results<Colmena> findBy(Connection c,ColmenaCriteria cc, int startIndex, int pageSize) throws DataException;
	
	public Long create(Connection c, Colmena co) throws DataException;
	
	public int update(Connection c, Colmena co) throws DataException;
	
	public void delete(Connection c, Long id) throws DataException;
	
}
