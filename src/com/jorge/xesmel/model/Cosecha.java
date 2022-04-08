package com.jorge.xesmel.model;

import java.util.Date;

public class Cosecha {
	
	private long id;
	private Double cantidad;
	private Date fechaRecogida;
	private long colmenaId;
	private long tipoCosechaId;
	
	
	public Cosecha() {
		
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Double getCantidad() {
		return cantidad;
	}
	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}
	public Date getFechaRecogida() {
		return fechaRecogida;
	}
	public void setFechaRecogida(Date fechaRecogida) {
		this.fechaRecogida = fechaRecogida;
	}
	public long getColmenaId() {
		return colmenaId;
	}
	public void setColmenaId(long colmenaId) {
		this.colmenaId = colmenaId;
	}
	public long getTipoCosechaId() {
		return tipoCosechaId;
	}
	public void setTipoCosechaId(long tipoCosechaId) {
		this.tipoCosechaId = tipoCosechaId;
	}
	
	

}
