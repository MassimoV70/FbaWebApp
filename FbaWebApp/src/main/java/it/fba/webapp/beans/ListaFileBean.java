package it.fba.webapp.beans;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class ListaFileBean {
	
	private List<CommonsMultipartFile> listaFile;

	public List<CommonsMultipartFile> getListaFile() {
		return listaFile;
	}

	public void setListaFile(List<CommonsMultipartFile> listaFile) {
		this.listaFile = listaFile;
	}

	
	
	

}
