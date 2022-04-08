package com.jorge.xesmel.model;

import java.util.Date;

import com.jorge.xesmel.dao.util.AbstractValueObject;

public class Colmena extends AbstractValueObject {

	
	private Long id = null;
	private Long codEnApiario = null;
	private Date fechaAlta = null;
	private Date fechaBaja = null;
	private Long apiarioId = null;
	private Long tipoOrigenId = null;
	
	
	public Colmena() {
		
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getCodEnApiario() {
		return codEnApiario;
	}


	public void setCodEnApiario(Long codEnApiario) {
		this.codEnApiario = codEnApiario;
	}


	public Date getFechaAlta() {
		return fechaAlta;
	}


	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}


	public Date getFechaBaja() {
		return fechaBaja;
	}


	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}


	public Long getApiarioId() {
		return apiarioId;
	}


	public void setApiarioId(Long apiarioId) {
		this.apiarioId = apiarioId;
	}


	public Long getTipoOrigenId() {
		return tipoOrigenId;
	}


	public void setTipoOrigenId(Long tipoOrigenId) {
		this.tipoOrigenId = tipoOrigenId;
	}


	
	}