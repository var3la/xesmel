package com.jorge.xesmel.model;

import java.util.Date;

public class Analisis {
	
	private Long id;
	private Date fecha;
	private Long cosechaId;
	
	public Analisis() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Long getCosechaId() {
		return cosechaId;
	}

	public void setCosechaId(Long cosechaId) {
		this.cosechaId = cosechaId;
	}
	
	

}
