package com.jorge.xesmel.service;

import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.exception.ServiceException;
import com.jorge.xesmel.model.Venta;

public interface VentaService {
	
	public Venta findById(Long id) throws DataException;
	
	public Long create(Venta v) throws DataException, ServiceException;
	
	public void update(Venta v) throws DataException;

}
