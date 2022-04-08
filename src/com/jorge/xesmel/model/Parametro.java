package com.jorge.xesmel.model;

public class Parametro {
	
	private Long id;
	private String resultado;
	private Long idAnalisis;
	private Long idTipoParametro;
	
	public Parametro() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public Long getIdAnalisis() {
		return idAnalisis;
	}

	public void setIdAnalisis(Long idAnalisis) {
		this.idAnalisis = idAnalisis;
	}

	public Long getIdTipoParametro() {
		return idTipoParametro;
	}

	public void setIdTipoParametro(Long idTipoParametro) {
		this.idTipoParametro = idTipoParametro;
	}
	
}