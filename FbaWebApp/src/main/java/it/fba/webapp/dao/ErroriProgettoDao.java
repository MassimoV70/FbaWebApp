package it.fba.webapp.dao;

import java.util.ArrayList;

import it.fba.webapp.beans.ErroreProgettoBean;

public interface ErroriProgettoDao {
	
	ArrayList<ErroreProgettoBean> getErroriProgetto(int idPiano) throws Exception;
	
	void salvaErroriProgetto(ArrayList<ErroreProgettoBean> listaErroriProgetto) throws Exception;
	
	void deleteErroriProgett(int idPiano) throws Exception;

}
