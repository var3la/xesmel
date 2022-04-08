package com.jorge.xesmel.service;

import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.exception.ServiceException;
import com.jorge.xesmel.model.Analisis;

public interface AnalisisService {
	
		public Long create(Analisis a)throws ServiceException;
		
		public Analisis findByCosechaId( Long cosechaId) throws DataException;
		
}
