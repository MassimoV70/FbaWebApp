package it.fba.webapp.fileInputOutput;

import java.util.ArrayList;
import java.util.HashMap;

import it.fba.webapp.beans.FileBean;


public interface ImportService {
	
	public ArrayList<HashMap<String, String>> importFile(FileBean file) throws Exception;

}
