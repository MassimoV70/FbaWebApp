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
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Entity


@Table(name="piani_formazione")
public class PianoDIformazioneBean {
	
	@Id
	@Column(name="id")
	int id;
	
	
	// nome piano
	@Size(min=2, max=50)
	@Column(name="nomepiano")
	String pianoDiFormazione;
	
	// tipologia corso
	@Size(min=2, max=100)
	@Column(name="tipocorsopiano")
	String tipoCorsoPiano;
	
	// tematica formativa
	@Size(min=2, max=100)
	@Column(name="tematicaformativa")
	String tematicaFormativa;
	
	// inizio attivita formativa
	@DateTimeFormat(pattern="dd/mm/yyyy")
	@Column(name="datainizioAtt")
	Date dataInizioAtt;
	
	// inizio attivita formativa front end
	@NotNull
	@Transient
	@DateTimeFormat(pattern="dd/mm/yyyy")
	String dataInizioAttStr;
	
	// fine attivita formativa
	
	@DateTimeFormat(pattern="dd/mm/yyyy")
	@Column(name="dataFineAtt")
	Date dataFineAtt;
	
	// fine attivita formativa front end
	@NotNull
	@Transient
	@DateTimeFormat(pattern="dd/mm/yyyy")
	String dataFineAttStr;
	
	// numero partecipanti

	@NumberFormat
	@Size(min=1, max=100)
	@Column(name="numpartecipanti")
	String numPartecipanti;
	
	// competitivita impresa innovazione
	@Column(name="compimprinn")
	String compImprInn;
	
	// competitivita settoriale
	@Column(name="compsett")
	String compSett;
	
	// delocalizzazione internazionalizzazione
	@Column(name="delocinter")
	String delocInter;
	
	// formazione obbligatoria ex leg
	@Column(name="formobblexleg")
	String formObblExLeg;
	
	// formazione in ingresso
	@Column(name="forminingr")
	String formInIngresso;
	
	// mantenimento occupazione
	@Column(name="mantenimoccup")
	String mantenimOccup;
	
	// manutenzione aggiornamento delle competenze
	@Column(name="manutaggcomp")
	String manutAggComp;
	
	// mobilita esterna outplacement ricollocazione
	@Column(name="mobestoutric")
	String mobEstOutRic;
	
	// sviluppo locale
	@Column(name="sviluppoloc")
	String sviluppoLoc;
	
	// titolo modulo 2
	@Size(min=2, max=50)
	@Column(name="modulo1")
	String modulo1;
	
	//modalita formativa modulo 1
	@Size(min=1, max=50)
	@Column(name="fadmod1")
	String fadMod1;

	// titolo modulo2
	@Size(min=2, max=50)
	@Column(name="modulo2")
	String modulo2;
	
	//modalita formativa modulo 2
	@Size(min=1, max=50)
	@Column(name="fadmod2")
	String fadMod2;
	
	public String getDataInizioAttStr() {
		return dataInizioAttStr;
	}
	public void setDataInizioAttStr(String dataInizioAttStr) {
		this.dataInizioAttStr = dataInizioAttStr;
	}
	public String getDataFineAttStr() {
		return dataFineAttStr;
	}
	public void setDataFineAttStr(String dataFineAttStr) {
		this.dataFineAttStr = dataFineAttStr;
	}
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
	
	
	@Column(name="allegato1")
	CommonsMultipartFile allegato1;
	
	@Column(name="allegato2")
	CommonsMultipartFile allegato2;
	
	@Column(name="allegato3")
	CommonsMultipartFile allegato3;
	
	
	@Column(name="allegato4")
	CommonsMultipartFile allegato4;

	
	public String getPianoDiFormazione() {
		return pianoDiFormazione;
	}
	public void setPianoDiFormazione(String pianoDiFormazione) {
		this.pianoDiFormazione = pianoDiFormazione;
	}
	public String getModulo1() {
		return modulo1;
	}
	public void setModulo1(String modulo1) {
		this.modulo1 = modulo1;
	}
	public String getModulo2() {
		return modulo2;
	}
	public void setModulo2(String modulo2) {
		this.modulo2 = modulo2;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public CommonsMultipartFile getAllegato1() {
		return allegato1;
	}
	public void setAllegato1(CommonsMultipartFile allegato1) {
		this.allegato1 = allegato1;
	}
	public CommonsMultipartFile getAllegato2() {
		return allegato2;
	}
	public void setAllegato2(CommonsMultipartFile allegato2) {
		this.allegato2 = allegato2;
	}
	public CommonsMultipartFile getAllegato3() {
		return allegato3;
	}
	public void setAllegato3(CommonsMultipartFile allegato3) {
		this.allegato3 = allegato3;
	}
	public CommonsMultipartFile getAllegato4() {
		return allegato4;
	}
	public void setAllegato4(CommonsMultipartFile allegato4) {
		this.allegato4 = allegato4;
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
	
	public String getNumPartecipanti() {
		return numPartecipanti;
	}
	public void setNumPartecipanti(String numPartecipanti) {
		this.numPartecipanti = numPartecipanti;
	}
	public String getCompImprInn() {
		return compImprInn;
	}
	public void setCompImprInn(String compImprInn) {
		this.compImprInn = compImprInn;
	}
	public String getCompSett() {
		return compSett;
	}
	public void setCompSett(String compSett) {
		this.compSett = compSett;
	}
	public String getDelocInter() {
		return delocInter;
	}
	public void setDelocInter(String delocInter) {
		this.delocInter = delocInter;
	}
	public String getFormObblExLeg() {
		return formObblExLeg;
	}
	public void setFormObblExLeg(String formObblExLeg) {
		this.formObblExLeg = formObblExLeg;
	}
	public String getFormInIngresso() {
		return formInIngresso;
	}
	public void setFormInIngresso(String formInIngresso) {
		this.formInIngresso = formInIngresso;
	}
	public String getMantenimOccup() {
		return mantenimOccup;
	}
	public void setMantenimOccup(String mantenimOccup) {
		this.mantenimOccup = mantenimOccup;
	}
	public String getManutAggComp() {
		return manutAggComp;
	}
	public void setManutAggComp(String manutAggComp) {
		this.manutAggComp = manutAggComp;
	}
	public String getMobEstOutRic() {
		return mobEstOutRic;
	}
	public void setMobEstOutRic(String mobEstOutRic) {
		this.mobEstOutRic = mobEstOutRic;
	}
	public String getSviluppoLoc() {
		return sviluppoLoc;
	}
	public void setSviluppoLoc(String sviluppoLoc) {
		this.sviluppoLoc = sviluppoLoc;
	}
	public String getFadMod1() {
		return fadMod1;
	}
	public void setFadMod1(String fadMod1) {
		this.fadMod1 = fadMod1;
	}
	public String getFadMod2() {
		return fadMod2;
	}
	public void setFadMod2(String fadMod2) {
		this.fadMod2 = fadMod2;
	}
	public Date getDataInizioAtt() {
		return dataInizioAtt;
	}
	public void setDataInizioAtt(Date dataInizioAtt) {
		this.dataInizioAtt = dataInizioAtt;
	}
	public Date getDataFineAtt() {
		return dataFineAtt;
	}
	public void setDataFineAtt(Date dataFineAtt) {
		this.dataFineAtt = dataFineAtt;
	}
	
	
	

}
