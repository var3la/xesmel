package com.jorge.xesmel.model;

public class Usuario {
	
	
	private Long id;
	private String nombre;
	private String apellido;
	private String segundoApellido;
	private String nombreComercial;
	private String dni;
	private String telefono;
	private String email;
	private String password;
	private String passwordEncript;
	private String rega;
	
	public Usuario() {
		
	}
	
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
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
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getSegundoApellido() {
		return segundoApellido;
	}
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}
	public String getNombreComercial() {
		return nombreComercial;
	}
	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordEncript() {
		return passwordEncript;
	}

	public void setPasswordEncript(String passwordEncript) {
		this.passwordEncript = passwordEncript;
	}

	public String getRega() {
		return rega;
	}
	public void setRega(String rega) {
		this.rega = rega;
	}
	@Override
	public String toString() {
		return "id={id="+getId()+",nombre={nombre="+getNombre()+",apellido={apellido="+getApellido()+
				",segundoApellido={segundoApellido="+getSegundoApellido()+",nombreComercial={nombreComercial="+getNombreComercial()+
				",dni={dni="+getDni()+",telefono={telefono="+getTelefono()+",email={email="+getEmail()+",password={password="+getPassword()+",rega={rega="+getRega();
	}
}
