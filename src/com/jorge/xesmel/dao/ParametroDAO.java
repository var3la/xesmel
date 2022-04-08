package com.jorge.xesmel.dao;

import java.sql.Connection;

import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.model.Parametro;

public interface ParametroDAO {
	
	public Parametro findById(Connection c,Long id)throws DataException;

}
