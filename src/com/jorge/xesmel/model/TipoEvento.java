package com.jorge.xesmel.model;

public class TipoEvento {

		private Long id;
		private String tratamientoGenerico;
		private String ataque;
		private String vacunacion;
		private String alza;
		private String revision;
		private String divisionColmena;
		private String otroActo;
		

		public TipoEvento() {
		
		
		}


		public Long getId() {
			return id;
		}


		public void setId(Long id) {
			this.id = id;
		}


		public String getTratamientoGenerico() {
			return tratamientoGenerico;
		}


		public void setTratamientoGenerico(String tratamientoGenerico) {
			this.tratamientoGenerico = tratamientoGenerico;
		}


		public String getAtaque() {
			return ataque;
		}


		public void setAtaque(String ataque) {
			this.ataque = ataque;
		}


		public String getVacunacion() {
			return vacunacion;
		}


		public void setVacunacion(String vacunacion) {
			this.vacunacion = vacunacion;
		}


		public String getAlza() {
			return alza;
		}


		public void setAlza(String alza) {
			this.alza = alza;
		}


		public String getRevision() {
			return revision;
		}


		public void setRevision(String revision) {
			this.revision = revision;
		}


		public String getDivisionColmena() {
			return divisionColmena;
		}


		public void setDivisionColmena(String divisionColmena) {
			this.divisionColmena = divisionColmena;
		}


		public String getOtroActo() {
			return otroActo;
		}


		public void setOtroActo(String otroActo) {
			this.otroActo = otroActo;
		}
		
}
