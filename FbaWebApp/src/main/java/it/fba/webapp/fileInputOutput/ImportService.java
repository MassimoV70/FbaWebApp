package it.fba.webapp.fileInputOutput;

import java.util.ArrayList;

import it.fba.webapp.beans.FileBean;
import it.fba.webapp.beans.PianoDIformazioneBean;

public interface ImportService {
	
	public ArrayList<PianoDIformazioneBean> importFile(FileBean file);

}
