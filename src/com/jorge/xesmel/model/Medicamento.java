package com.jorge.xesmel.model;

public class Medicamento {

	
		private long id;
		private String dosis;
		
		public Medicamento() {
			
		}
		
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public String getDosis() {
			return dosis;
		}
		public void setDosis(String dosis) {
			this.dosis = dosis;
		}
}
