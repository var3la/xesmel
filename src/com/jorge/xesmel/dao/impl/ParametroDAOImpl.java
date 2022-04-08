package com.jorge.xesmel.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jorge.xesmel.dao.ParametroDAO;
import com.jorge.xesmel.dao.util.JDBCUtils;
import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.model.Parametro;

public class ParametroDAOImpl implements ParametroDAO{
	
	public ParametroDAOImpl() {
		
	}
	
	
public Parametro findById(Connection c,Long id) throws DataException{
		
		
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Parametro parametro= null;
		
		try {
			
			String sql = "SELECT id, resultado, analisis_id,tipo_parametro_id"
									+" FROM parametro"
									+" WHERE id = ? ";
			System.out.println("ParametroDAO.findById:SQL= "+sql);
			
			preparedStatement = c.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			JDBCUtils.setParameter(preparedStatement, 1, id);
			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				parametro = loadNext(rs);
			}

		} catch (SQLException u) {
			u.printStackTrace();
			throw new DataException(id.toString());
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		
		}
		return parametro;


}
private Parametro loadNext(ResultSet rs) throws SQLException {
	Parametro parametro = new Parametro();

	int i = 1;
	parametro.setId(rs.getLong(i++));
	parametro.setResultado(rs.getString(i++));
	parametro.setIdAnalisis(rs.getLong(i++));
	parametro.setIdTipoParametro(rs.getLong(i++));
	
	return parametro;
}

}
