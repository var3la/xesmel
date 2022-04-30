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

import com.jorge.xesmel.dao.EventoDAO;
import com.jorge.xesmel.dao.util.JDBCUtils;
import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.exception.UserNotFoundException;
import com.jorge.xesmel.model.EventoCriteria;
import com.jorge.xesmel.model.EventoDTO;

public class EventoDAOImpl implements EventoDAO {
	
	private static Logger logger = LogManager.getLogger(EventoDAOImpl.class);
	
	public EventoDAOImpl() {		
	}
	
	@Override
	public EventoDTO findById(Connection c, Long id) throws DataException{
		
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		EventoDTO evento = null;
		try {

			
			String sql = " SELECT id, comentario, fecha_inicio, fecha_fin, colmena_id, medicamento_id, tipo_evento_id "
					+ " FROM evento "
				    + " WHERE id = ? ";
			if(logger.isDebugEnabled()) {
			logger.debug("EventoDAO.findBy:SQL= "+sql);
			}
			preparedStatement = c.prepareStatement(sql, 
						ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			JDBCUtils.setParameter(preparedStatement, 1, id);			
			rs = preparedStatement.executeQuery();
			
			if (rs.next()) {
				evento = loadNext(rs);
			}
			
		} catch (SQLException e) {			
			logger.error(evento, e);
			throw new DataException(id.toString());
			 
 		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
			
		}
		return evento;
	}

	@Override
	public List<EventoDTO> findBy(Connection c, EventoCriteria ec)
				throws DataException {
		
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<EventoDTO> eventos = null;
		try {

			
			
			StringBuilder sqlSB = new StringBuilder(" SELECT id, comentario, fecha_inicio, fecha_fin, colmena_id, medicamento_id, tipo_evento_id ") 
					
					.append( " FROM evento ");
			
			boolean first = true;

			if(ec.getId()!=null) {
				addClause(sqlSB, first, " WHERE id = ? ");
				first = false;
			}

			if (ec.getComentario()!=null) {
				addClause(sqlSB, first, " and comentario = ? ");
				first = false;
			}
			if (ec.getFechaInicio()!=null) {
				addClause(sqlSB, first, " and fecha_inicio = ? ");
				first = false;
			}
			if (ec.getFechaFin()!=null) {
				addClause(sqlSB, first, " and fecha_fin = ? ");
				first = false;
			}
			if (ec.getColmenaId()!=null) {
				addClause(sqlSB, first, " and colmena_id = ? ");
				first = false;
			}
			if (ec.getMedicamentoId()!=null) {
				addClause(sqlSB, first, " and medicamento_id = ? ");
				first = false;
			}
			if (ec.getTipoEventoId()!=null) {
				addClause(sqlSB, first, " and tipo_evento_id = ? ");
				first = false;
			}
			

			addClause(sqlSB, first, " ORDER BY fecha_inicio DESC ");

			if(logger.isDebugEnabled()) {
			logger.debug("EventoDAO.findBy:SQL= "+sqlSB);
			}

			preparedStatement = c.prepareStatement(sqlSB.toString(), 
						ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i;

			i = 1;

			JDBCUtils.setParameter(preparedStatement, i++, ec.getId());
			JDBCUtils.setParameter(preparedStatement, i++, ec.getComentario());
			JDBCUtils.setParameter(preparedStatement, i++, ec.getFechaInicio());
			JDBCUtils.setParameter(preparedStatement, i++, ec.getFechaFin());
			JDBCUtils.setParameter(preparedStatement, i++, ec.getColmenaId());
			JDBCUtils.setParameter(preparedStatement, i++, ec.getMedicamentoId());
			JDBCUtils.setParameter(preparedStatement, i++, ec.getTipoEventoId());

			rs = preparedStatement.executeQuery();

			eventos = new ArrayList<EventoDTO>();

			EventoDTO evento = null;
			while (rs.next()) {
				evento = loadNext(rs);
				eventos.add(evento);
			}

		} catch (SQLException e) {			
			logger.error(ec, e);
			throw new DataException(e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
			
		}
		return eventos;	
	}


	@Override
	public Long create(Connection c, EventoDTO evento) throws DataException{
	
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		try {

			
			

			String sql = " INSERT INTO EVENTO(COMENTARIO, FECHA_INICIO, FECHA_FIN, COLMENA_ID, MEDICAMENTO_ID, TIPO_EVENTO_ID) "
					+ " VALUES (?,?,?,?,?,?) ";

			
			preparedStatement = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			int i  = 1;
			JDBCUtils.setParameter(preparedStatement, i++, evento.getComentario());
			JDBCUtils.setParameter(preparedStatement, i++, evento.getFechaInicio());
			JDBCUtils.setParameter(preparedStatement, i++, evento.getFechaFin());
			JDBCUtils.setParameter(preparedStatement, i++, evento.getColmenaId()); 
			JDBCUtils.setParameter(preparedStatement, i++, evento.getMedicamentoId(), true);				
			JDBCUtils.setParameter(preparedStatement, i++, evento.getTipoEventoId());


			int insertedRows = preparedStatement.executeUpdate();
			if (insertedRows==1) {
				rs = preparedStatement.getGeneratedKeys();
				if (rs.next()) {
					evento.setId(rs.getLong(i));
				}
			
			}
		
		} catch (SQLException e) {			
			logger.error(evento.getComentario(), e);
			throw new DataException(evento.getComentario());
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
			
		}
		return evento.getId();
	}

	@Override
	public int update(Connection c, EventoDTO evento) throws DataException{

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		int updatedRows = 0;
		try {

			
			

			String sql =" UPDATE EVENTO "
					+ " SET  COMENTARIO = ?,"
					+ "      FECHA_INICIO = ?,"
					+ "      FECHA_FIN = ?,"
					+ "      COLMENA_ID = ?,"
					+ "      MEDICAMENTO_ID = ?,"
					+ "      TIPO_EVENTO_ID = ? "
					+ "  WHERE ID = ? ";

			
			preparedStatement = c.prepareStatement(sql);

			int i  = 1;
			JDBCUtils.setParameter(preparedStatement, i++, evento.getComentario());
			JDBCUtils.setParameter(preparedStatement, i++, evento.getFechaInicio());
			JDBCUtils.setParameter(preparedStatement, i++, evento.getFechaFin());
			JDBCUtils.setParameter(preparedStatement, i++, evento.getColmenaId()); 
			JDBCUtils.setParameter(preparedStatement, i++, evento.getMedicamentoId(), true);				
			JDBCUtils.setParameter(preparedStatement, i++, evento.getTipoEventoId());
			JDBCUtils.setParameter(preparedStatement, i++, evento.getId());
			

			updatedRows = preparedStatement.executeUpdate();
			if (updatedRows!=1) {
				throw new UserNotFoundException(evento.getNombreTipoEvento()+" no encontrado");
			}
		
		} catch (SQLException e) {			
			logger.error(evento.getNombreTipoEvento(),e);
			throw new DataException(evento.getNombreTipoEvento());
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
			
		}
		return updatedRows;
	}
	
	@Override
	public void deleteById(Connection c, Long id) throws DataException{
		
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		try {
			String sql = " delete "
					+" from evento "
					+" where id = ? ";
			preparedStatement = c.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			JDBCUtils.setParameter(preparedStatement, 1, id); 
			int deletedRows = preparedStatement.executeUpdate();
			if (deletedRows!=1) {
				throw new UserNotFoundException(""+id);
			}
		} catch (SQLException sqle) {
			logger.error(id,sqle);
			throw new DataException(""+id, sqle);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		
		
		
	}

	
	private EventoDTO loadNext(ResultSet rs) 
		throws SQLException { 
		EventoDTO evento = new EventoDTO();
		int i = 1;
		evento.setId(rs.getLong(i++));
		evento.setComentario(rs.getString(i++));
		evento.setFechaInicio(rs.getDate(i++));
		evento.setFechaFin(rs.getDate(i++));
		evento.setColmenaId(rs.getLong(i++));
		evento.setMedicamentoId(rs.getLong(i++));
		evento.setTipoEventoId(rs.getLong(i++));
		return evento;
	}
	
	private void addClause(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first?" WHERE ": " AND ").append(clause);
	}
	
}
