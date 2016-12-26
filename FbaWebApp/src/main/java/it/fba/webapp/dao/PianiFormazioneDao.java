package it.fba.webapp.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import it.fba.webapp.beans.PianoDIformazioneBean;
import it.fba.webapp.beans.UsersBean;

public interface PianiFormazioneDao {
	
	PianoDIformazioneBean findPianiFormazione (PianoDIformazioneBean pianoDIformazioneBean) throws SQLException;
	
	void caricaPianiFormazione (ArrayList<PianoDIformazioneBean> listaPiani) throws SQLException;
	
	void updatePianoDiFormazione (PianoDIformazioneBean pianoDiFormazione) throws SQLException;
	
	void deletePianoDiFormazione (PianoDIformazioneBean pianoDiFormazione) throws SQLException;
	
	void deleteAllPianiDIFormazione (String username)throws SQLException;
	
	ArrayList<PianoDIformazioneBean> getAllPiani(String username) throws SQLException;
	
	void updatePianoDiFormazioneAllegati (PianoDIformazioneBean pianoDiFormazione) throws SQLException;
	
	void updateStatoPianoDiFormazione (PianoDIformazioneBean pianoDiFormazioneBean) throws SQLException;
	
	void aggiornaStatoPianoByEliminazioneFile (ArrayList<Integer> listaIdPiani,String stato, String username);

}
