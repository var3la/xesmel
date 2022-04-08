package com.jorge.xesmel.service;

import com.jorge.xesmel.exception.ServiceException;

public interface MedicamentoService {

		public Long findById(Long id) throws ServiceException;
}
