package com.jorge.xesmel.dao;

import java.sql.Connection;

import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.model.TipoOrigen;

public interface TipoOrigenDAO {
	
	public TipoOrigen findById(Connection c,Long id)throws DataException;
	
}
