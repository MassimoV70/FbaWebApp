package it.fba.webapp.beans;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Entity


@Table(name="lavoratori_modulo")
public class LavoratoriBean {
	
	
	@Id
	@NotNull
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	@NotNull
	@Column(name="idpiano")
	private int idPiano;
	
	@Size(min=2, max=100)
	@Column(name="nomemodulo")
	private String nomeModulo;
	
	@Transient
	private String modalitaFormatvia;
	
	@Size(min=2, max=100)
	@Column(name="matricola")
	private String matricola;
	
	@Size(min=1, max=100)
	@Column(name="orepresenza")
	private String orePresenza;
	
	@Size(min=1, max=100)
	@Column(name="esitotest")
	private String esitoTest;
	
	@Size(min=4, max=100)
	@Column(name="nomeallegato")
	private String nomeAllegato;
	
	@NotNull
	@Column(name="stato")
	private String stato;
	
	@Column(name="allegato")
    CommonsMultipartFile fileData;
	

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

	public String getMatricola() {
		return matricola;
	}

	public void setMatricola(String matricola) {
		this.matricola = matricola;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public CommonsMultipartFile getFileData() {
		return fileData;
	}

	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}

	public String getOrePresenza() {
		return orePresenza;
	}

	public void setOrePresenza(String orePresenza) {
		this.orePresenza = orePresenza;
	}

	public String getNomeAllegato() {
		return nomeAllegato;
	}

	public void setNomeAllegato(String nomeAllegato) {
		this.nomeAllegato = nomeAllegato;
	}

	public String getEsitoTest() {
		return esitoTest;
	}

	public void setEsitoTest(String esitoTest) {
		this.esitoTest = esitoTest;
	}

	public String getModalitaFormatvia() {
		return modalitaFormatvia;
	}

	public void setModalitaFormatvia(String modalitaFormatvia) {
		this.modalitaFormatvia = modalitaFormatvia;
	}
	
	
	

}
