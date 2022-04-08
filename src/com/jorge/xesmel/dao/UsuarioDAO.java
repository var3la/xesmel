package com.jorge.xesmel.dao;

import java.sql.Connection;
import java.util.List;

import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.model.Usuario;
import com.jorge.xesmel.model.UsuarioCriteria;

public interface UsuarioDAO {
		
		public Usuario findById(Connection c, Long id) throws DataException;
		
		public Usuario findByEmail(Connection c, String email) throws DataException;
		
		public List<Usuario> findBy(Connection c, UsuarioCriteria uc) throws DataException;
		
		public Long create(Connection c, Usuario u) throws DataException;
		
		public int update(Connection c, Usuario u) throws DataException;
		
		public void deleteById(Connection c, Long id) throws DataException;
		
}
