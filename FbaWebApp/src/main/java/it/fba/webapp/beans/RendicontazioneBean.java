package it.fba.webapp.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity

@Table(name="rendicontazione")
public class RendicontazioneBean {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@NotNull
	@Column(name="id")
	int id;
	
	@NotNull
	@Column(name="idPiano")
	int idPiano;
	
	
	@Size(min=2, max=100)
	@Column(name="tipologiagiustificativo")
	String tipologiaGiustificativo;
	
	@Size(min=2, max=100)
	@Column(name="codice")
	String codice;
	

	@DateTimeFormat(pattern="dd/mm/yyyy")
	@Column(name="datagiustificativo")
	Date dataGiustificativo;
	
	@Size(min=10, max=10)
	@Transient
	String dataGiustificativoStr;
	
	@Size(min=2, max=100)
	@Column(name="fornitorenominativo")
	String fornitoreNominativo;
	
	@Size(min=4)
	@Column(name="valorecomplessivo")
	String valoreComplessivo;
	
	@Size(min=4)
	@Column(name="contributofba")
	String contributoFBA;
	
	@Size(min=4)
	@Column(name="contributoprivato")
	String contributoPrivato;
	
	@Size(min=2, max=100)
	@Column(name="nomeallegato")
	String nomeAllegato;
	
	@NotNull
	@Column(name="stato")
	String stato;

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

	public String getTipologiaGiustificativo() {
		return tipologiaGiustificativo;
	}

	public void setTipologiaGiustificativo(String tipologiaGiustificativo) {
		this.tipologiaGiustificativo = tipologiaGiustificativo;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public Date getDataGiustificativo() {
		return dataGiustificativo;
	}

	public void setDataGiustificativo(Date dataGiustificativo) {
		this.dataGiustificativo = dataGiustificativo;
	}

	public String getDataGiustificativoStr() {
		return dataGiustificativoStr;
	}

	public void setDataGiustificativoStr(String dataGiustificativoStr) {
		this.dataGiustificativoStr = dataGiustificativoStr;
	}

	public String getFornitoreNominativo() {
		return fornitoreNominativo;
	}

	public void setFornitoreNominativo(String fornitoreNominativo) {
		this.fornitoreNominativo = fornitoreNominativo;
	}

	public String getValoreComplessivo() {
		return valoreComplessivo;
	}

	public void setValoreComplessivo(String valoreComplessivo) {
		this.valoreComplessivo = valoreComplessivo;
	}

	public String getContributoFBA() {
		return contributoFBA;
	}

	public void setContributoFBA(String contributoFBA) {
		this.contributoFBA = contributoFBA;
	}

	public String getContributoPrivato() {
		return contributoPrivato;
	}

	public void setContributoPrivato(String contributoPrivato) {
		this.contributoPrivato = contributoPrivato;
	}

	public String getNomeAllegato() {
		return nomeAllegato;
	}

	public void setNomeAllegato(String nomeAllegato) {
		this.nomeAllegato = nomeAllegato;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}
	

}
