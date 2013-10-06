package org.effortless.tests.examples.mvvm;

public class Entidad extends Object {
    
	public Entidad(String nombre) {
		super();
		this.nombre = nombre;
    }

	protected String nombre;
	
	public String getNombre () {
		System.out.println("READING MODEL NOMBRE " + this.nombre);
		return this.nombre;
	}
	
	public void setNombre (String newValue) {
		System.out.println("WRITING MODEL NOMBRE " + newValue);
		this.nombre = newValue;
	}

	protected String etiqueta;
	
	public String getEtiqueta () {
		System.out.println("READING MODEL ETIQUETA " + this.etiqueta);
		return this.etiqueta;
	}
	
	public void setEtiqueta (String newValue) {
		System.out.println("WRITING MODEL ETIQUETA " + newValue);
		this.etiqueta = newValue;
	}

	protected Integer contador;
	
	public Integer getContador () {
		System.out.println("READING MODEL CONTADOR " + this.contador);
		return this.contador;
	}
	
	public void setContador (Integer newValue) {
		System.out.println("WRITING MODEL CONTADOR " + newValue);
		this.contador = newValue;
	}
	
	protected Double precio;
	
	public Double getPrecio () {
		System.out.println("READING MODEL PRECIO " + this.precio);
		return this.precio;
	}
	
	public void setPrecio (Double newValue) {
		System.out.println("WRITING MODEL PRECIO " + newValue);
		this.precio = newValue;
	}
	
	protected Boolean activo;
	
	public Boolean getActivo () {
		System.out.println("READING MODEL ACTIVO " + this.activo);
		return this.activo;
	}
	
	public void setActivo (Boolean newValue) {
		System.out.println("WRITING MODEL ACTIVO " + newValue);
		this.activo = newValue;
	}
	
	protected java.util.Date fecha;
	
	public java.util.Date getFecha () {
		System.out.println("READING MODEL FECHA " + this.fecha);
		return this.fecha;
	}
	
	public void setFecha (java.util.Date newValue) {
		System.out.println("WRITING MODEL FECHA " + newValue);
		this.fecha = newValue;
	}

	protected java.sql.Time hora;
	
	public java.sql.Time getHora () {
		System.out.println("READING MODEL HORA " + this.hora);
		return this.hora;
	}
	
	public void setHora (java.sql.Time newValue) {
		System.out.println("WRITING MODEL HORA " + newValue);
		this.hora = newValue;
	}
	
	protected java.sql.Timestamp fechaHora;
	
	public java.sql.Timestamp getFechaHora () {
		System.out.println("READING MODEL FECHAHORA " + this.fechaHora);
		return this.fechaHora;
	}
	
	public void setFechaHora (java.sql.Timestamp newValue) {
		System.out.println("WRITING MODEL FECHAHORA " + newValue);
		this.fechaHora = newValue;
	}
	
	protected java.io.File fichero;
	
	public java.io.File getFichero () {
		System.out.println("READING MODEL FICHERO " + this.fichero);
		return this.fichero;
	}
	
	public void setFichero (java.io.File newValue) {
		System.out.println("WRITING MODEL FICHERO " + newValue);
		this.fichero = newValue;
	}

	protected Entidad referencia;
	
	public Entidad getReferencia () {
		System.out.println("READING MODEL REFERENCIA " + this.referencia);
		return this.referencia;
	}
	
	public void setReferencia (Entidad newValue) {
		System.out.println("WRITING MODEL REFERENCIA " + newValue);
		this.referencia = newValue;
	}

	protected java.util.List<Entidad> listado;
	
	public java.util.List<Entidad> getListado () {
		System.out.println("READING MODEL LISTADO " + this.listado);
		return this.listado;
	}
	
	public void setListado (java.util.List<Entidad> newValue) {
		System.out.println("WRITING MODEL LISTADO " + newValue);
		this.listado = newValue;
	}
	
	public String toString () {
		return this.nombre;
	}
	
}
