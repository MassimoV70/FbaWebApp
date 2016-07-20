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

@Table(name="RendicontazioneFile")
public class RendicontazioneFileBean {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@NotNull
	@Column(name="id")
	int id;
	
	@Column(name="nomeallegato")
	String nomeAllegato;
	  
	  
	@Lob
	@Column(name="allegatofile" , length = 1000000)
	byte[] allegatoFile;

	@Column(name="username")
	String username;
	
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNomeAllegato() {
		return nomeAllegato;
	}


	public void setNomeAllegato(String nomeAllegato) {
		this.nomeAllegato = nomeAllegato;
	}


	public byte[] getAllegatoFile() {
		return allegatoFile;
	}


	public void setAllegatoFile(byte[] allegatoFile) {
		this.allegatoFile = allegatoFile;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}

	

	
	
	
	

}
