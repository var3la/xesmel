package com.jorge.xesmel.service.test;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jorge.xesmel.exception.AlreadyExistsException;
import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.exception.ServiceException;
import com.jorge.xesmel.model.Colmena;
import com.jorge.xesmel.model.ColmenaCriteria;
import com.jorge.xesmel.model.Results;
import com.jorge.xesmel.service.ColmenaService;
import com.jorge.xesmel.service.impl.ColmenaServiceImpl;

public class ColmenaServiceTest {
	
	private ColmenaService colmenaService = null;
	private Colmena colmena = null;
	
	private static Logger logger = LogManager.getLogger(ColmenaServiceTest.class);
	
	public ColmenaServiceTest() {
		
		colmenaService = new ColmenaServiceImpl();
		
	}
	public void testFindById()throws DataException{
		logger.trace("testing findById...");
		colmena = new Colmena();
		
		colmena.setId(6L);
		logger.info("buscando colmena..."+colmena.getId());
		try {
			colmenaService.findById(colmena.getId());
		}catch (DataException de) {
			logger.error(de.getMessage());
		}
	}
	
	public void testCreate()throws AlreadyExistsException, ServiceException{
		logger.info("testing Create...");
		colmena = new Colmena();
		
		colmena.setApiarioId(3L);
		colmena.setCodEnApiario(34L);
		colmena.setFechaAlta(null);
		colmena.setFechaBaja(null);
		colmena.setTipoOrigenId(2L);
		System.out.println("creando colmena "+colmena.getId());
		try {
			colmenaService.create(colmena);
		}catch (AlreadyExistsException ae) {
			System.out.println("la colmena ya existe "+ae);
		}catch (ServiceException de){ 
			System.out.println("Problema al crear la colmena "+de);
			de.printStackTrace();
		}
		
	}
	
	public void testFindByCriteria() throws DataException {		
		logger.trace("testing findByCriteria...");		
		ColmenaCriteria cc = new ColmenaCriteria();		
		try {
			cc.setCodEnApiario(4L);;
			int startIndex = 1;
			int pageSize=5;
			Results<Colmena> results=null;
			do {
				results = colmenaService.findBy(cc, startIndex, pageSize);
				
				System.out.println("Encontrados "+results.getTotal()+" resultados");
				System.out.println("Mostrado del "+startIndex+" al "+(startIndex+results.getData().size()-1));
				startIndex = startIndex+results.getData().size();
				for (Colmena c:results.getData()) {
					System.out.println("\t"+c);
				}
			}while (startIndex<=results.getTotal());
		}catch (ServiceException de) {
			logger.error(de.getMessage());
			
		}		
	}	
		
		public static void main(String args[]) throws AlreadyExistsException, ServiceException{
			ColmenaServiceTest test = new ColmenaServiceTest();
			//test.testFindByCriteria();
			//test.testFindById();
			test.testCreate();
		}
	}


