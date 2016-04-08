package it.fba.webapp.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Entity


@Table(name="piani_formazione")
public class PianoDIformazioneBean {
	
	@Id
	@Column(name="id")
	int id;
	
	@Transient
	String idStr;
	
	@Size(min=2, max=50)
	@Column(name="nomePiano")
	String pianoDiFormazione;
	
	@Size(min=2, max=50)
	@Column(name="modulo1")
	String modulo1;
	
	@Size(min=0, max=50)
	@Column(name="modulo2")
	String modulo2;
	
	@Size(min=2, max=50)
	@Column(name="pivaAttuatore")
	String attuatorePIVA;
		
	@Column(name="enabled")
	String enabled;
	@Column(name="nomeAllegato1")
	String nomeAllegato1;
	@Column(name="nomeAllegato2")
	String nomeAllegato2;
	@Column(name="nomeAllegato3")
	String nomeAllegato3;
	@Column(name="nomeAllegato4")
	String nomeAllegato4;
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
	public String getIdStr() {
		return idStr;
	}
	public void setIdStr(String idStr) {
		this.idStr = idStr;
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
	
	
	

}