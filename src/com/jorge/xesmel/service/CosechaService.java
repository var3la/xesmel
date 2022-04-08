package com.jorge.xesmel.service;

import java.util.Date;
import java.util.List;

import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.model.Cosecha;

public interface CosechaService {
	
	public Cosecha findById(Long id)throws DataException;
	
	public List<Cosecha>findBy(Long id,Double cantidad, Date FechaRecogida,Long colmenaId,Long tipoCosechaId) throws DataException;
	
	public Long create(Cosecha co)throws DataException;
	
	public void deleteById(Long id)throws DataException;

}
