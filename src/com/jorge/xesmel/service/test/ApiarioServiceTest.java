package com.jorge.xesmel.service.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.exception.ServiceException;
import com.jorge.xesmel.model.Apiario;
import com.jorge.xesmel.model.Usuario;
import com.jorge.xesmel.service.ApiarioService;
import com.jorge.xesmel.service.UsuarioService;
import com.jorge.xesmel.service.impl.ApiarioServiceImpl;
import com.jorge.xesmel.service.impl.UsuarioServiceImpl;

public class ApiarioServiceTest {

	private ApiarioService apiarioService = null;
	private UsuarioService usuarioService = null;
	private Apiario apiario = null;
	private Usuario usuario = null;
	private static Logger logger = LogManager.getLogger(ApiarioServiceTest.class);

	public ApiarioServiceTest() {
		apiarioService = new ApiarioServiceImpl();
		usuarioService = new UsuarioServiceImpl();

	}
	public void testFindById() throws DataException{
		logger.info("testing findById :");
		Long idApiario = 2L;
		Apiario apiario = new Apiario();
		try {
			apiarioService.findById(idApiario);
			logger.info("apiario "+apiario);
		}catch (DataException de) {
			logger.error("error buscando apiario "+apiario.getNombre()+de);
		}
		logger.info("sucessfull " +apiario);
	}
	
	public void testFindByUsuario() throws DataException{
		
		logger.info("testing findByUsuario...");
		apiario = new Apiario();
		usuario = new Usuario();
		
		usuario.setId(5L);
		
		try {
			apiarioService.findByUsuarioId(apiario.getId());
			logger.info("searching for apiario "+apiario.getNombre());
		}catch (DataException de) {
			logger.error("error buscando apiario "+apiario.getNombre()+de);
		}
		
	}

	public void testFindBy() throws DataException {

		logger.info("testing findByUsuario...");
		apiario = new Apiario();
		usuario = new Usuario();

		apiario.setUsuarioId(1L);;

		try {
			apiarioService.findByUsuarioId(apiario.getId());
			logger.info("searching for apiario " + apiario.getNombre());
		} catch (DataException de) {
			logger.error("error buscando apiario " + apiario.getNombre() + de);
		}

	}
	public void testCreate()throws ServiceException{
		logger.info("testing createApiario: ");
		Apiario a = new Apiario();
		try {
		a.setId(32L);
		a.setNombre("a coba");
		a.setUbicacion("a coba");
		a.setLatitud(72.4545);
		a.setLongitud(45.7876);
		a.setUsuarioId(1L);
		logger.info("create apiario "+a.getNombre());
			Long idApiario = apiarioService.create(a);
			
		}catch (DataException de) {
			logger.error("apiario no creado");
		}
		
	}


	public static void main(String args[]) throws ServiceException{
		ApiarioServiceTest test = new ApiarioServiceTest();
		test.testFindById();
		test.testFindBy();
		//test.testFindByUsuario();
		//test.testCreate();
	}

}
