package org.effortless.tests.model;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.apache.commons.lang3.ObjectUtils;
import org.effortless.model.AbstractIdEntity;
import org.effortless.model.Filter;

@Entity
//@org.hibernate.annotations.Tuplizer(impl = org.effortless.model.CustomEntityTuplizer.class)
public class AllBasicProperties extends AbstractIdEntity<AllBasicProperties> {

	public static Filter<AllBasicProperties> listBy () {
		return AbstractIdEntity.listBy(AllBasicProperties.class);
	}
	
	protected String text;

	protected void initiateText() {
		this.text = null;
	}

	@javax.persistence.Column(name = "TEXT")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public String getText() {
		return this.text;
	}

	public void setText(String newValue) {
		boolean _loaded = checkLoaded("text", true);
		String oldValue = (_loaded ? this.getText() : null);
		if (!_loaded || !_equals(oldValue, newValue)) {
			this.text = newValue;
			if (_loaded) {
				doChangeText(oldValue, newValue);
				firePropertyChange("text", oldValue, newValue);
			}
		}
	}
	
	protected void doChangeText (String oldValue, String newValue) {
		
	}
	
	protected Boolean flag;

	protected void initiateFlag() {
		this.flag = null;
	}

	@javax.persistence.Column(name = "FLAG")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public Boolean getFlag() {
		return this.flag;
	}

	public void setFlag(Boolean newValue) {
		Boolean oldValue = this.getFlag();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.flag = newValue;
			firePropertyChange("flag", oldValue, newValue);
		}
	}
	
	protected Double number;

	protected void initiateNumber() {
		this.number = null;
	}

	@javax.persistence.Column(name = "NUMBER")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public Double getNumber() {
		return this.number;
	}

	public void setNumber(Double newValue) {
		Double oldValue = this.getNumber();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.number = newValue;
			firePropertyChange("number", oldValue, newValue);
		}
	}
	
	protected Integer count;

	protected void initiateCount() {
		this.count = null;
	}

	@javax.persistence.Column(name = "COUNT")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer newValue) {
		Integer oldValue = this.getCount();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.count = newValue;
			firePropertyChange("count", oldValue, newValue);
		}
	}
	
	protected StatusTest mode;

	protected void initiateMode() {
		this.mode = null;
	}

	@javax.persistence.Column(name = "MODE")
	@javax.persistence.Enumerated(javax.persistence.EnumType.STRING)
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public StatusTest getMode() {
		return this.mode;
	}

	public void setMode(StatusTest newValue) {
		
		StatusTest oldValue = this.getMode();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.mode = newValue;
			firePropertyChange("mode", oldValue, newValue);
		}
	}
	
	protected Date fecha;

	protected void initiateFecha() {
		this.fecha = null;
	}

	@javax.persistence.Column(name = "FECHA")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date newValue) {
		Date oldValue = this.getFecha();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.fecha = newValue;
			firePropertyChange("fecha", oldValue, newValue);
		}
	}
	
	protected Time hora;

	protected void initiateHora() {
		this.hora = null;
	}

	@javax.persistence.Column(name = "HORA")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public Time getHora() {
		return this.hora;
	}

	public void setHora(Time newValue) {
		Time oldValue = this.getHora();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.hora = newValue;
			firePropertyChange("hora", oldValue, newValue);
		}
	}
	
	protected Timestamp fechaHora;

	protected void initiateFechaHora() {
		this.fechaHora = null;
	}

	@javax.persistence.Column(name = "FECHA_HORA")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public Timestamp getFechaHora() {
		return this.fechaHora;
	}

	public void setFechaHora(Timestamp newValue) {
		Timestamp oldValue = this.getFechaHora();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.fechaHora = newValue;
			firePropertyChange("fechaHora", oldValue, newValue);
		}
	}
	
	protected String comentario;

	protected void initiateComentario() {
		this.comentario = null;
	}

	@javax.persistence.Column(name = "COMMENT", length=3072)
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public String getComentario() {
		return this.comentario;
	}

	public void setComentario(String newValue) {
		String oldValue = this.getComentario();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.comentario = newValue;
			firePropertyChange("comentario", oldValue, newValue);
		}
	}
	
	
	

	public String toString () {
		return getText();
	}
	
}
