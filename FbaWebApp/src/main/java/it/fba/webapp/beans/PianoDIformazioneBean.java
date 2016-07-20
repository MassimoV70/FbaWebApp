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
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Entity


@Table(name="piani_formazione")
public class PianoDIformazioneBean {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	int id;
	
	// numero protocollo
	@Size(min=2, max=50)
	@Column(name="nuemroprotocollo")
	String nuemroProtocollo;

	
	// nome progetto
	@Size(min=2, max=50)
	@Column(name="nomeprogetto")
	String nomeProgetto;
	
	// tipologia corso
	@Size(min=2, max=100)
	@Column(name="tipocorsopiano")
	String tipoCorsoPiano;
	
	// tematica formativa
	@Size(min=2, max=100)
	@Column(name="tematicaformativa")
	String tematicaFormativa;
	
	//numero partecipanti
	@Size(min=1, max=10)
	@Column(name="numeropartecipanti")
	String numeroPartecipanti;
	
	// titolo modulo 2
	@Size(min=2, max=50)
	@Column(name="modulo1")
	String modulo1;
	
	//modalita formativa modulo 1
	@Size(min=1, max=50)
	@Column(name="fadmod1")
	String fadMod1;
	
	//durata modulo1
	@NumberFormat
	@Size(min=1, max=10)
	@Column(name="duratamodulo1")
	String durataModulo1;

	// titolo modulo2
	@Size(min=2, max=50)
	@Column(name="modulo2")
	String modulo2;
	
	//modalita formativa modulo 2
	@Size(min=1, max=50)
	@Column(name="fadmod2")
	String fadMod2;
	
	//durata modulo2
	@NumberFormat
	@Size(min=1, max=10)
	@Column(name="duratamodulo2")
	String durataModulo2;
	
	// partita iva attuatore
	@NotNull
	@Size(min=2, max=50)
	@Column(name="pivaAttuatore")
	String attuatorePIVA;
	
	// stato piano
	@NotNull
	@Column(name="enabled")
	String enabled;
	
	// allegato 1 attuatore
	@Column(name="nomeAllegato1")
	String nomeAllegato1;
	
	// allegato 2 attuatore
	@Column(name="nomeAllegato2")
	String nomeAllegato2;
	
	// allegato 3 attuatore
	@Column(name="nomeAllegato3")
	String nomeAllegato3;
	
	// allegato 4 attuatore
	@Column(name="nomeAllegato4")
	String nomeAllegato4;
	
	// username
	@Column(name="username")
	String username;
	
	// forme di aiuti
	@Size(min=2, max=50)
	@Column(name="formeaiuti")
	String formeAiuti;
	
	// categorie svantaggiate
	@Size(min=1, max=1)
	@Column(name="categsvantagg")
	String categSvantagg;


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNuemroProtocollo() {
		return nuemroProtocollo;
	}


	public void setNuemroProtocollo(String nuemroProtocollo) {
		this.nuemroProtocollo = nuemroProtocollo;
	}


	public String getNomeProgetto() {
		return nomeProgetto;
	}


	public void setNomeProgetto(String nomeProgetto) {
		this.nomeProgetto = nomeProgetto;
	}


	public String getTipoCorsoPiano() {
		return tipoCorsoPiano;
	}


	public void setTipoCorsoPiano(String tipoCorsoPiano) {
		this.tipoCorsoPiano = tipoCorsoPiano;
	}


	public String getTematicaFormativa() {
		return tematicaFormativa;
	}


	public void setTematicaFormativa(String tematicaFormativa) {
		this.tematicaFormativa = tematicaFormativa;
	}


	public String getModulo1() {
		return modulo1;
	}


	public void setModulo1(String modulo1) {
		this.modulo1 = modulo1;
	}


	public String getFadMod1() {
		return fadMod1;
	}


	public void setFadMod1(String fadMod1) {
		this.fadMod1 = fadMod1;
	}


	public String getDurataModulo1() {
		return durataModulo1;
	}


	public void setDurataModulo1(String durataModulo1) {
		this.durataModulo1 = durataModulo1;
	}


	public String getModulo2() {
		return modulo2;
	}


	public void setModulo2(String modulo2) {
		this.modulo2 = modulo2;
	}


	public String getFadMod2() {
		return fadMod2;
	}


	public void setFadMod2(String fadMod2) {
		this.fadMod2 = fadMod2;
	}


	public String getDurataModulo2() {
		return durataModulo2;
	}


	public void setDurataModulo2(String durataModulo2) {
		this.durataModulo2 = durataModulo2;
	}


	public String getAttuatorePIVA() {
		return attuatorePIVA;
	}


	public void setAttuatorePIVA(String attuatorePIVA) {
		this.attuatorePIVA = attuatorePIVA;
	}


	public String getEnabled() {
		return enabled;
	}


	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}


	public String getNomeAllegato1() {
		return nomeAllegato1;
	}


	public void setNomeAllegato1(String nomeAllegato1) {
		this.nomeAllegato1 = nomeAllegato1;
	}


	public String getNomeAllegato2() {
		return nomeAllegato2;
	}


	public void setNomeAllegato2(String nomeAllegato2) {
		this.nomeAllegato2 = nomeAllegato2;
	}


	public String getNomeAllegato3() {
		return nomeAllegato3;
	}


	public void setNomeAllegato3(String nomeAllegato3) {
		this.nomeAllegato3 = nomeAllegato3;
	}


	public String getNomeAllegato4() {
		return nomeAllegato4;
	}


	public void setNomeAllegato4(String nomeAllegato4) {
		this.nomeAllegato4 = nomeAllegato4;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getNumeroPartecipanti() {
		return numeroPartecipanti;
	}


	public void setNumeroPartecipanti(String numeroPartecipanti) {
		this.numeroPartecipanti = numeroPartecipanti;
	}


	public String getFormeAiuti() {
		return formeAiuti;
	}


	public void setFormeAiuti(String formeAiuti) {
		this.formeAiuti = formeAiuti;
	}


	public String getCategSvantagg() {
		return categSvantagg;
	}


	public void setCategSvantagg(String categSvantagg) {
		this.categSvantagg = categSvantagg;
	}
    
	

	

	
	
	
	

}
