package it.fba.webapp.beans;

import java.util.Date;
import java.util.Properties;

import javax.annotation.Resource;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

@Entity


@Table(name="users")
public class UsersBean {
	
	
	@Size(min=2, max=30) 
	@Column(name="nome")
	private String nome;
	
	
	@Size(min=2, max=30) 
	@Column(name="cognome")
	private String cognome;
	
	@Size(min=2, max=30) 
	@Id
	@Column(name="username")
	private String username;
	
	@Size(min=5, max=30) 
	@Column(name="password")
	private String password;
	
	@Size(min=1, max=1 ) 
	@Column(name="enabled")
	private String enabled;
	
	@Size(min=2, max=20) 
	@Column(name= "role")
	private  String role;
	
	@DateTimeFormat(pattern="dd/mm/yyyy")
	@Column(name= "dataInizio")
	private Date dataInizio;
	
	
	@DateTimeFormat(pattern="dd/mm/yyyy")
	@Column(name= "dataFine")
	private Date dataFine;
	
	@NotEmpty @Email
	@Column(name= "email")
	private String email;
	
	
	@Transient
	@Size(min=10, max=10)
	private String dataInizioStr;
	
	
	@Transient
	@Size(min=10, max=10)
	private String dataFineStr;
	
	@Transient
	private String azione;
	
    public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}
	public Date getDataFine() {
		return dataFine;
	}
	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}
	public String getDataInizioStr() {
		return dataInizioStr;
	}
	public void setDataInizioStr(String dataInizioStr) {
		this.dataInizioStr = dataInizioStr;
	}
	public String getDataFineStr() {
		return dataFineStr;
	}
	public void setDataFineStr(String dataFineStr) {
		this.dataFineStr = dataFineStr;
	}
	public String getAzione() {
		return azione;
	}
	public void setAzione(String azione) {
		this.azione = azione;
	}
	
	
	

}
