package com.jorge.xesmel.dao;

import java.sql.Connection;

import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.model.Medicamento;

public interface MedicamentoDAO {
	
	public Medicamento findById(Connection c,Long id)throws DataException;
	
	public Long create(Connection c, Medicamento m)throws DataException;

}
