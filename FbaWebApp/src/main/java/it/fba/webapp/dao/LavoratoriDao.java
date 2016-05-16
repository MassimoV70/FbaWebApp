package it.fba.webapp.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import it.fba.webapp.beans.CalendarioBean;
import it.fba.webapp.beans.ImplementaPianoFormBean;
import it.fba.webapp.beans.LavoratoriBean;
import it.fba.webapp.beans.PianoDIformazioneBean;

public interface LavoratoriDao {
	
    LavoratoriBean findLavoratore (LavoratoriBean lavoratoriBean) throws SQLException;
	
	void caricaLavoratori (ArrayList<LavoratoriBean> listaLavoratori) throws SQLException;
	
	void updateLavoratore (LavoratoriBean lavoratoriBean) throws SQLException;
	
	void deleteLavoratore (LavoratoriBean lavoratoriBean) throws SQLException;
	
	void deleteAllLavoratori (LavoratoriBean lavoratoriBean)throws SQLException;
	
	ArrayList<LavoratoriBean> getAllLavoratori(LavoratoriBean lavoratoriBean) throws SQLException;
	
	void deleteLavoratoriPiano (LavoratoriBean lavoratoriBean)throws SQLException;
	
	public ArrayList<LavoratoriBean> esistonoLavoratori(PianoDIformazioneBean pianoDIformazioneBean) throws SQLException;

}
