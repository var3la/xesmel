package com.jorge.xesmel.model;

import java.util.Date;

public class Venta {
	
	private Long id;
	private Double cantidad;
	private Double precioKg;
	private Date fechaVenta;
	private Long cosechaId;
	
	
	public Venta() {
		
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Double getCantidad() {
		return cantidad;
	}


	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}


	public Double getPrecioKg() {
		return precioKg;
	}


	public void setPrecioKg(Double precioKg) {
		this.precioKg = precioKg;
	}


	public Date getFechaVenta() {
		return fechaVenta;
	}


	public void setFechaVenta(Date fechaVenta) {
		this.fechaVenta = fechaVenta;
	}


	public Long getCosechaId() {
		return cosechaId;
	}


	public void setCosechaId(Long cosechaId) {
		this.cosechaId = cosechaId;
	}
}
	