package it.fba.webapp.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
@Entity

@Table(name="erroriprogetto")
public class ErroreProgettoBean {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@NotNull
	@Column(name="id")
	int id;
	@NotNull
	@Column(name="idPiano")
	int idPiano;
	
	private String errore;
	
	private String oggettoErrore;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdPiano() {
		return idPiano;
	}

	public void setIdPiano(int idPiano) {
		this.idPiano = idPiano;
	}

	public String getErrore() {
		return errore;
	}

	public void setErrore(String errore) {
		this.errore = errore;
	}

	public String getOggettoErrore() {
		return oggettoErrore;
	}

	public void setOggettoErrore(String oggettoErrore) {
		this.oggettoErrore = oggettoErrore;
	}
	
	
	

}
