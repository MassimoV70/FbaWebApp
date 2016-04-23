package it.fba.webapp.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Entity


@Table(name="calendario_modulo")

public class CalendarioBean {
	
	@Id
	@NotNull
	@Column(name="id")
	int id;
	@NotNull
	@Column(name="idPiano")
	int idPiano;
	
	@Size(min=2, max=100)
	@Column(name="nomemodulo")
	String nomeModulo;
	
	
	@Column(name="data")
	Date data;
	
	@Transient
	@NotNull
	@DateTimeFormat(pattern="dd/mm/yyyy")
	String dataStr;
	
	@Column(name="iniziomattina")
	String inizioMattina;
	
	@Column(name="finemattina")
	String fineMattina;
	
	@Column(name="iniziopomeriggio")
	String inizioPomeriggio;
	
	@Column(name="finepomeriggio")
	String finePomeriggio;
	
	@NotNull
	@Column(name="stato")
	String stato;
	
	@Transient
	public CommonsMultipartFile fileData;

	public CommonsMultipartFile getFileData() {
		return fileData;
	}

	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}

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
    
	public String getNomeModulo() {
		return nomeModulo;
	}

	public void setNomeModulo(String nomeModulo) {
		this.nomeModulo = nomeModulo;
	}


	public String getInizioMattina() {
		return inizioMattina;
	}

	public void setInizioMattina(String inizioMattina) {
		this.inizioMattina = inizioMattina;
	}

	public String getFineMattina() {
		return fineMattina;
	}

	public void setFineMattina(String fineMattina) {
		this.fineMattina = fineMattina;
	}

	public String getInizioPomeriggio() {
		return inizioPomeriggio;
	}

	public void setInizioPomeriggio(String inizioPomeriggio) {
		this.inizioPomeriggio = inizioPomeriggio;
	}

	public String getFinePomeriggio() {
		return finePomeriggio;
	}

	public void setFinePomeriggio(String finePomeriggio) {
		this.finePomeriggio = finePomeriggio;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDataStr() {
		return dataStr;
	}

	public void setDataStr(String dataStr) {
		this.dataStr = dataStr;
	}
	
	
	
	
	

}
