package it.fba.webapp.beans;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class ImplementaPianoFormBean {
	
	int id;
	
	String modulo1;
	
	String modulo2;
	
	String attuatorePIVA;
	
	public CommonsMultipartFile fileData1;
	
	public CommonsMultipartFile fileData2;
	
	public CommonsMultipartFile fileData3;
	
	public CommonsMultipartFile fileData4;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public CommonsMultipartFile getFileData1() {
		return fileData1;
	}

	public void setFileData1(CommonsMultipartFile fileData1) {
		this.fileData1 = fileData1;
	}

	public CommonsMultipartFile getFileData2() {
		return fileData2;
	}

	public void setFileData2(CommonsMultipartFile fileData2) {
		this.fileData2 = fileData2;
	}

	public CommonsMultipartFile getFileData3() {
		return fileData3;
	}

	public void setFileData3(CommonsMultipartFile fileData3) {
		this.fileData3 = fileData3;
	}

	public CommonsMultipartFile getFileData4() {
		return fileData4;
	}

	public void setFileData4(CommonsMultipartFile fileData4) {
		this.fileData4 = fileData4;
	}
	
	

}
