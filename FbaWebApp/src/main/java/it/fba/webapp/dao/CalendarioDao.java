package it.fba.webapp.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import it.fba.webapp.beans.CalendarioBean;
import it.fba.webapp.beans.PianoDIformazioneBean;

public interface CalendarioDao {
	
   CalendarioBean findGiornoCalendario (CalendarioBean calendarioBean) throws SQLException;
	
	void caricaCalendari (ArrayList<CalendarioBean> listaCalendari) throws SQLException;
	
	void updateCalednario (CalendarioBean calendarioBean) throws SQLException;
	
	void deleteCalednario (CalendarioBean calendarioBean) throws SQLException;
	
	void deleteAllCalednari (CalendarioBean calendarioBean)throws SQLException;
	
	ArrayList<CalendarioBean> getAllCalednario(CalendarioBean calendarioBean) throws SQLException;
	
	void deleteCalednariPiano (CalendarioBean calendarioBean)throws SQLException;

}
