package com.jorge.xesmel.dao;

import java.sql.Connection;

import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.model.TipoParametro;

public interface TipoParametroDAO {

	public TipoParametro findById(Connection c, Long id)throws DataException;
	
	}



