package it.fba.webapp.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import it.fba.webapp.beans.CalendarioBean;
import it.fba.webapp.beans.PianoDIformazioneBean;

public interface CalendarioDao {
	
	public CalendarioBean findGiornoCalendario (CalendarioBean calendarioBean) throws SQLException;
	
    public void caricaCalendari (ArrayList<CalendarioBean> listaCalendari) throws SQLException;
	
	public void updateCalednario (CalendarioBean calendarioBean) throws SQLException;
	
	public void deleteCalednario (CalendarioBean calendarioBean) throws SQLException;
	
	public void deleteAllCalednari (CalendarioBean calendarioBean)throws SQLException;
	
	public ArrayList<CalendarioBean> getAllCalednario(CalendarioBean calendarioBean) throws SQLException;
	
	public void deleteCalednariPiano (CalendarioBean calendarioBean)throws SQLException;
	
	public ArrayList<CalendarioBean> esisteCalendario (PianoDIformazioneBean pianoDIformazioneBean) throws SQLException;

	public ArrayList<CalendarioBean> getCalendari(PianoDIformazioneBean pianoDIformazioneBean) throws SQLException;
		
}
