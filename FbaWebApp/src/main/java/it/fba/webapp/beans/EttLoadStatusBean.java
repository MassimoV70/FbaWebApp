package it.fba.webapp.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;


@Entity

@Table(name="ett_load_status")
public class EttLoadStatusBean {
	
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@NotNull
	private int id;
	
	@Column(name="ett_id_piano")
	private int ettIdPiano;
	
	@Column(name="fba_id_piano")
	private int fbaIdPiano;
	
	@Column(name="fba_id_progetto")
	private int fbaIdProgetto;
	
	@Column(name="status")
	private int status;
	
	@Column(name="message")
	private String message;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_inserimento",length = 240)
	private Date dtInserimento;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEttIdPiano() {
		return ettIdPiano;
	}

	public void setEttIdPiano(int ettIdPiano) {
		this.ettIdPiano = ettIdPiano;
	}

	public int getFbaIdPiano() {
		return fbaIdPiano;
	}

	public void setFbaIdPiano(int fbaIdPiano) {
		this.fbaIdPiano = fbaIdPiano;
	}

	public int getFbaIdProgetto() {
		return fbaIdProgetto;
	}

	public void setFbaIdProgetto(int fbaIdProgetto) {
		this.fbaIdProgetto = fbaIdProgetto;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDtInserimento() {
		return dtInserimento;
	}

	public void setDtInserimento(Date dtInserimento) {
		this.dtInserimento = dtInserimento;
	}
	
	
	

}
