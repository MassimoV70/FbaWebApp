package it.fba.webapp.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import it.fba.webapp.beans.AttuatoreBean;
import it.fba.webapp.beans.DatiFIleUploadBean;
import it.fba.webapp.beans.LavoratoriBean;
import it.fba.webapp.beans.PianoDIformazioneBean;
import it.fba.webapp.beans.RendicontazioneBean;
import it.fba.webapp.beans.RendicontazioneFileBean;

public interface RendicontazioneDao {
	
	public RendicontazioneBean findRendicontazioneById (RendicontazioneBean rendicontazioneBean) throws Exception;
	
	public void updateRendicontazione (RendicontazioneBean rendicontazioneBean) throws Exception;
	
	public void creaRendicontazione (RendicontazioneBean rendicontazioneBean) throws Exception;
	
	public ArrayList<RendicontazioneBean> getAllrendicontazione (PianoDIformazioneBean pianoDIformazioneBean) throws Exception;
	
	void caricaRendicontazione (ArrayList<RendicontazioneBean> listaRendicontazione) throws SQLException;
	
	void eliminaSoggettoRendicontato (RendicontazioneBean rendicontazioneBean);
	
	void eliminaRendicontazionePiano (RendicontazioneBean rendicontazioneBean);
	
	void caricaFileRendicontazione (RendicontazioneFileBean rendicontazioneFileBean);
	
	boolean esisteFile (String nomeFile,String username);
	
	ArrayList<RendicontazioneFileBean> getElencoFileRendicontazione (String username);
	
	void eliminaFile(RendicontazioneFileBean rendicontazioneFileBean);

}
