package com.jorge.xesmel.dao;

import java.sql.Connection;

import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.model.TipoEvento;

public interface TipoEventoDAO {
	
	public TipoEvento findById(Connection c, Long id)throws DataException;

	
}
