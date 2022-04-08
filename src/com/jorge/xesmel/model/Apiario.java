package com.jorge.xesmel.model;

public class Apiario {
	
		private Long id;
		private String nombre;
		private String ubicacion;
		private Double latitud;
		private Double longitud;
		private Long usuarioId;
		
		public Apiario() {
			
		}


		public Long getId() {
			return id;
		}


		public void setId(Long id) {
			this.id = id;
		}


		public String getNombre() {
			return nombre;
		}


		public void setNombre(String nombre) {
			this.nombre = nombre;
		}


		public String getUbicacion() {
			return ubicacion;
		}


		public void setUbicacion(String ubicacion) {
			this.ubicacion = ubicacion;
		}


		public Double getLatitud() {
			return latitud;
		}


		public void setLatitud(Double latitud) {
			this.latitud = latitud;
		}


		public Double getLongitud() {
			return longitud;
		}


		public void setLongitud(Double longitud) {
			this.longitud = longitud;
		}


		public Long getUsuarioId() {
			return usuarioId;
		}


		public void setUsuarioId(Long usuarioid) {
			this.usuarioId = usuarioid;
		}
		
}
