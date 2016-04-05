package it.fba.webapp.beans;



import org.springframework.web.multipart.commons.CommonsMultipartFile;


public class FileBean {
	
	private String username;
	
	public CommonsMultipartFile fileData;
	
	

	public CommonsMultipartFile getFileData() {
		return fileData;
	}

	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
    
	
	
	
	

	

}
