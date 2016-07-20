package it.fba.webapp.fileInputOutput;

import java.util.ArrayList;
import java.util.HashMap;

import it.fba.webapp.beans.AttuatoreBean;
import it.fba.webapp.beans.FileBean;
import it.fba.webapp.beans.ImplementaPianoFormBean;


public interface ImportService {
	
	public ArrayList<HashMap<String, String>> importFile(FileBean file) throws Exception;
	
	public AttuatoreBean importaCertificati (ImplementaPianoFormBean implementaPianoFormBean) throws Exception;

}
