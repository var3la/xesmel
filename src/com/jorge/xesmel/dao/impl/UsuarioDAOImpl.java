package com.jorge.xesmel.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jorge.xesmel.dao.UsuarioDAO;
import com.jorge.xesmel.dao.util.JDBCUtils;
import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.exception.UserNotFoundException;
import com.jorge.xesmel.model.Usuario;
import com.jorge.xesmel.model.UsuarioCriteria;

public class UsuarioDAOImpl implements UsuarioDAO {
	
	private static Logger logger = LogManager.getLogger(UsuarioDAOImpl.class);

	public UsuarioDAOImpl() {

	}

	@Override
	public Usuario findById(Connection c, Long id) throws DataException{

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Usuario usuario = null;
		try {

			
			
			String sql = " SELECT id, nombre, apellido1, apellido2, nombre_comercial, dni, telefono, email, password, rega "
					+ " FROM usuario " + " WHERE id = ?";
			
			if(logger.isDebugEnabled()) {
				logger.debug("UsuarioDAO.findById:SQL= " + sql);
			}
			

			
			preparedStatement = c.prepareStatement(sql);

			JDBCUtils.setParameter(preparedStatement, 1, id);
			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				usuario = loadNext(rs);
			}

		} catch (SQLException sqle) {
			logger.error("findById: "+id+": "+sqle.getMessage(), sqle);
			throw new DataException("findById: "+id+": "+sqle.getMessage(), sqle);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		
		}
		return usuario;
	}
	@Override
	public Usuario findByEmail(Connection c, String email) throws DataException{
	
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Usuario usuario = null;
		try {

			// SQL
			String sql = " SELECT id, nombre, apellido1, apellido2, nombre_comercial, dni, telefono, email, password, rega "
					+ " FROM usuario " + " WHERE email = ? ";
			
			if (logger.isDebugEnabled()) {
				logger.debug("UsuarioDAO.findByEmail:SQL= "+ sql);
			}
			//System.out.println("UsuarioDAO.findByEmail:SQL= " + sql);

			// create prepared statement
			preparedStatement = c.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			JDBCUtils.setParameter(preparedStatement, 1, email);
			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				usuario = loadNext(rs);
				
			}

		} catch (SQLException sqle) {
			logger.error("find byEmail: "+email+sqle.getMessage(), sqle);
			throw new DataException("find byEmail: "+email+sqle.getMessage(), sqle);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
			
		}
		return usuario;
	}
	
	
	@Override
	public List<Usuario> findBy(Connection c, UsuarioCriteria uc) throws DataException{

	
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<Usuario> usuarios = null;

		try {
			
			StringBuilder sqlSB = new StringBuilder(" SELECT id, nombre, apellido1, apellido2, nombre_comercial, dni, telefono, email, password, rega ")
					.append(" FROM USUARIO ");
			
			boolean first = false;
			
			if (uc.getId() != null) {
				addClause(sqlSB, first, " id = ? ");
				first = false;
			}
			if (uc.getNombre() != null) {
				addClause(sqlSB, first, " and nombre = ? ");
				first = false;
			}
			if (uc.getApellido() != null) {
				addClause(sqlSB, first, " and apellido1 = ? ");
				first = false;
			}
			if (uc.getSegundoApellido() != null) {
				addClause(sqlSB, first, " and apellido2 = ? ");
				first = false;
			}
			if (uc.getNombreComercial() != null) {
				addClause(sqlSB, first, " and nombre_comercial = ? ");
				first = false;
			}
			if (uc.getDni() != null) {
				addClause(sqlSB, first, " and dni = ? ");
				first = false;
			}
			if (uc.getTelefono() != null) {
				addClause(sqlSB, first, " and telefono = ? ");
				first = false;
			}
			if (uc.getEmail() != null) {
				addClause(sqlSB, first, " and email = ? ");
				first = false;
			}
			if (uc.getPassword() != null) {
				addClause(sqlSB, first, " and password = ? ");
				first = false;
			}
			if (uc.getRega() != null) {
				addClause(sqlSB, first, " and rega = ? ");
				first = false;
			}

			sqlSB.append(" ORDER BY id DESC ");
			
			if (logger.isDebugEnabled()) {
				logger.debug("UsuarioDAO.findBy:SQL= "+sqlSB);
			}
			
			//System.out.println("EventoDAO.findBy:SQL= " + sqlSB);
			preparedStatement = c.prepareStatement(sqlSB.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i=1;

			JDBCUtils.setParameter(preparedStatement, i++, uc.getId());
			JDBCUtils.setParameter(preparedStatement, i++, uc.getNombre());
			JDBCUtils.setParameter(preparedStatement, i++, uc.getApellido());
			JDBCUtils.setParameter(preparedStatement, i++, uc.getSegundoApellido());
			JDBCUtils.setParameter(preparedStatement, i++, uc.getNombreComercial());
			JDBCUtils.setParameter(preparedStatement, i++, uc.getDni());
			JDBCUtils.setParameter(preparedStatement, i++, uc.getTelefono());
			JDBCUtils.setParameter(preparedStatement, i++, uc.getEmail());
			JDBCUtils.setParameter(preparedStatement, i++, uc.getPassword());
			JDBCUtils.setParameter(preparedStatement, i++, uc.getRega());

			rs = preparedStatement.executeQuery();

			usuarios = new ArrayList<Usuario>();

			Usuario usuario = null;
			while (rs.next()) {
				usuario = loadNext(rs);
				usuarios.add(usuario);
			}
		} catch (SQLException e) {
			logger.error(uc.getNombre()+e);
			throw new DataException(uc.getNombre().toString());
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
			
		}
		return usuarios;
	}

	@Override
	public Long create(Connection c, Usuario usuario) throws DataException{
		
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {


			String sql = " INSERT INTO usuario( nombre, apellido1, apellido2, nombre_comercial, dni, telefono, email, password, rega) "
					+ " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
			
			if(logger.isDebugEnabled()) {
				logger.debug("UsuarioDAO.create SQL: " + sql);
			}

		
			preparedStatement = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			int i = 1;
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getNombre());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getApellido());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getSegundoApellido());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getNombreComercial());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getDni());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getTelefono());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getEmail());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getPassword());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getRega());

			int insertedRows = preparedStatement.executeUpdate();
			if (insertedRows == 1) {
				rs = preparedStatement.getGeneratedKeys();
				if (rs.next()) {
					usuario.setId(rs.getLong(1));
				}
			}else{
				throw new DataException(usuario.getNombre());			
			}
			

		} catch (SQLException e) {
			logger.error(usuario.getNombre()+ e);
			throw new DataException("usuario: "+usuario.getNombre()+e.getMessage(),e);
	
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
			
		}
		return usuario.getId();
	}

	@Override
	public int update(Connection c, Usuario usuario) throws DataException{

	
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		int updatedRows = 0;
		try {
			

			String sql = "UPDATE USUARIO " + "SET NOMBRE = ?," + "	   APELLIDO1= ?," + "	   APELLIDO2= ?,"
					+ "    NOMBRE_COMERCIAL= ?," + "    DNI= ?," + "    TELEFONO= ?,"+"    EMAIL= ?,"+"    PASSWORD= ?," + "    REGA= ? "
					+ " WHERE ID = ? ";
			
			if (logger.isDebugEnabled()) {
				logger.debug("UsuarioDAO.update SQL: ", sql);
			}

			preparedStatement = c.prepareStatement(sql);

			int i = 1;
			
			
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getNombre());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getApellido());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getSegundoApellido());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getNombreComercial());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getDni());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getTelefono());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getEmail());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getPassword());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getRega());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getId());

			updatedRows = preparedStatement.executeUpdate();
			if (updatedRows != 1) {
				throw new UserNotFoundException("User: "+usuario.getNombre());
			}
		} catch (SQLException e) {
			logger.error(usuario.getNombre() +e);
			throw new DataException("User: "+usuario.getId()+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
			
		}
		return updatedRows;
	}
	
	@Override
	public void deleteById(Connection c, Long id) 
		throws DataException {
		
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		try {			
			String sql = " delete "
					+ " from usuario "
					+ " where id = ? ";
			
			if (logger.isDebugEnabled()) {
				logger.debug("UsuarioDAO.delete SQL: "+ sql);
			}
			
			preparedStatement = c.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			JDBCUtils.setParameter(preparedStatement, 1, id); 
			int deletedRows = preparedStatement.executeUpdate();
			if (deletedRows!=1) {
				throw new UserNotFoundException(""+id);
			}
		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(""+id, sqle);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}

		
	}

	private Usuario loadNext(ResultSet rs) throws SQLException {
		Usuario usuario = new Usuario();

		int i = 1;
		usuario.setId(rs.getLong(i++));
		usuario.setNombre(rs.getString(i++));
		usuario.setApellido(rs.getString(i++));
		usuario.setSegundoApellido(rs.getString(i++));
		usuario.setNombreComercial(rs.getString(i++));
		usuario.setDni(rs.getString(i++));
		usuario.setTelefono(rs.getString(i++));
		usuario.setEmail(rs.getString(i++));
		usuario.setPassword(rs.getString(i++));
		usuario.setRega(rs.getString(i++));

		return usuario;
	}
	
	private void addClause(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first?" WHERE ": " AND ").append(clause);
	}
	
}
