package it.fba.webapp.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity


@Table(name="attuatore")

public class AttuatoreBean {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@NotNull
	@Column(name="id")
	int id;
	
    @Column(name="attuatorepiva")
	String attuatorePIVA;
    
    @Column(name="username")
	String username;
	
    @Column(name="nomeallegato1")
	String nomeAllegato1;
	
    @Column(name="nomeallegato2")
	String nomeAllegato2;
	
    @Column(name="nomeallegato3")
	String nomeAllegato3;
	
    @Column(name="nomeallegato4")
	String nomeAllegato4;
	
    @Lob
    @Column(name="allegatofile1" , length = 1000000)
    byte[] allegatoFile1;
	
    @Lob
    @Column(name="allegatofile2", length = 1000000)
	byte[] allegatoFile2;
	
    @Lob
    @Column(name="allegatofile3", length = 1000000)
	byte[] allegatoFile3;
	
    @Lob
    @Column(name="allegatofile4", length = 1000000)
	byte[] allegatoFile4;

	public String getAttuatorePIVA() {
		return attuatorePIVA;
	}

	public void setAttuatorePIVA(String attuatorePIVA) {
		this.attuatorePIVA = attuatorePIVA;
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

	public byte[] getAllegatoFile1() {
		return allegatoFile1;
	}

	public void setAllegatoFile1(byte[] allegatoFile1) {
		this.allegatoFile1 = allegatoFile1;
	}

	public byte[] getAllegatoFile2() {
		return allegatoFile2;
	}

	public void setAllegatoFile2(byte[] allegatoFile2) {
		this.allegatoFile2 = allegatoFile2;
	}

	public byte[] getAllegatoFile3() {
		return allegatoFile3;
	}

	public void setAllegatoFile3(byte[] allegatoFile3) {
		this.allegatoFile3 = allegatoFile3;
	}

	public byte[] getAllegatoFile4() {
		return allegatoFile4;
	}

	public void setAllegatoFile4(byte[] allegatoFile4) {
		this.allegatoFile4 = allegatoFile4;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	

	
	
	
	

}
