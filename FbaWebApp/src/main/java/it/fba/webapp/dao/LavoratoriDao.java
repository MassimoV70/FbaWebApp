package it.fba.webapp.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import it.fba.webapp.beans.AttuatoreBean;
import it.fba.webapp.beans.CalendarioBean;
import it.fba.webapp.beans.ImplementaPianoFormBean;
import it.fba.webapp.beans.LavoratoriBean;
import it.fba.webapp.beans.LavoratoriFileBean;
import it.fba.webapp.beans.PianoDIformazioneBean;
import it.fba.webapp.beans.RendicontazioneFileBean;

public interface LavoratoriDao {
	
    LavoratoriBean findLavoratore (LavoratoriBean lavoratoriBean) throws SQLException;
	
	void caricaLavoratori (ArrayList<LavoratoriBean> listaLavoratori) throws SQLException;
	
	void updateLavoratore (LavoratoriBean lavoratoriBean) throws SQLException;
	
	void deleteLavoratore (LavoratoriBean lavoratoriBean) throws SQLException;
	
	void deleteAllLavoratori (LavoratoriBean lavoratoriBean)throws SQLException;
	
	ArrayList<LavoratoriBean> getAllLavoratori(LavoratoriBean lavoratoriBean) throws SQLException;
	
	void deleteLavoratoriPiano (LavoratoriBean lavoratoriBean)throws SQLException;
	
	public ArrayList<LavoratoriBean> esistonoLavoratori(PianoDIformazioneBean pianoDIformazioneBean) throws SQLException;
	
    void caricaFileLavoratori (LavoratoriFileBean lavoratoriFileBean);
	
	boolean esisteFile (String nomeFile,String username);
	
	ArrayList<LavoratoriFileBean> getElencoFileLavoratori (String username);
	
	void eliminaFile(LavoratoriFileBean lavoratoriFileBean);
	
	public ArrayList<String> leggiNomiAllegati (String username) throws Exception;

}
