package it.fba.webapp.dao;

import java.util.ArrayList;

import it.fba.webapp.beans.EttLoadStatusBean;

public interface EttLoadStatusDao {
	
	
	public ArrayList<EttLoadStatusBean> getErroriTrasmPiano(int idPiano);
	
	public void deletePianoTrasmesso(int idPiano);
	
	

}
