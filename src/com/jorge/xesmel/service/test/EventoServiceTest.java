package com.jorge.xesmel.service.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.exception.ServiceException;
import com.jorge.xesmel.model.EventoCriteria;
import com.jorge.xesmel.model.EventoDTO;
import com.jorge.xesmel.service.EventoService;
import com.jorge.xesmel.service.impl.EventoServiceImpl;

public class EventoServiceTest {
	
	private EventoService eventoService = null;
	
	
	private static Logger logger = LogManager.getLogger(EventoServiceTest.class);
	
	public EventoServiceTest() {
		
		eventoService = new EventoServiceImpl();
		
	}
	
	public void testFindById() {
		logger.info("testing by id...");
		
	try {
		logger.info(eventoService.findById(4L));
	}catch (ServiceException se){
		logger.error("error test");
	}
		
	}
	
	public void testFindByCriteria() throws ServiceException{
		logger.info("testing by criteria... ");
		EventoCriteria ec = new EventoCriteria();
		
		ec.setColmenaId(4L);
		logger.info("searching evento "+ec.getNombreTipoEvento());
		try {
			eventoService.findBy(ec);
		}catch (DataException de) {
			logger.info(de);
		}
	}
	
	public void testCreateEvento(){
		logger.info("creating evento...");
		try {
			EventoDTO e = new EventoDTO();
			e.setColmenaId(4L);
			e.setComentario("OSO");
			e.setFechaInicio(null);
			e.setTipoEventoId(3L);
			e.setNombreMedicamento("este");
			e.setMedicamentoId(2L);
			e.setNombreTipoEvento("ese");
			Long ed = eventoService.create(e);
			
		}catch (Exception ex) {
			logger.info("error..."+ex);
		}
		
	}
	
	public static void main (String Args[]) throws ServiceException {
		EventoServiceTest test = new EventoServiceTest();
		
		//test.testFindById();
		//test.testFindByCriteria();
		test.testCreateEvento();
		
	}

}
