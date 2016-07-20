package it.fba.webapp.dao;

import it.fba.webapp.beans.AttuatoreBean;

public interface AttuatoriDao {
	
	public AttuatoreBean findAttuatoreById (AttuatoreBean attuatoreBean) throws Exception;
	
	public void updateAttuatore (AttuatoreBean attuatoreBean) throws Exception;
	
	public AttuatoreBean leggiNomiAllegati (AttuatoreBean attuatoreBean) throws Exception;
	
	public void creaAttuatore (AttuatoreBean attuatoreBean) throws Exception;
	
	public boolean esisteAttuatore (AttuatoreBean attuatoreBean) throws Exception;
	
	

}
