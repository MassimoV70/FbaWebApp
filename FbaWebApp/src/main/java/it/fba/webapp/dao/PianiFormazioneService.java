package it.fba.webapp.dao;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import it.fba.webapp.beans.PianoDIformazioneBean;

@Component
public class PianiFormazioneService {
	
	private PianiFormazioneDao pianiFormazioneDao;

	public PianiFormazioneDao getPianiFormazioneDao() {
		return pianiFormazioneDao;
	}

	public void setPianiFormazioneDao(PianiFormazioneDao pianiFormazioneDao) {
		this.pianiFormazioneDao = pianiFormazioneDao;
	}
	
    public void caricaPianiFormazione (ArrayList<PianoDIformazioneBean> listaPiani)throws Exception{
    	pianiFormazioneDao.caricaPianiFormazione(listaPiani);
    }
	
	public void updatePianoDiFormazione (PianoDIformazioneBean pianoDiFormazione) throws Exception{
		pianiFormazioneDao.updatePianoDiFormazione(pianoDiFormazione);
	}
	
	void deletePianoDiFormazione (PianoDIformazioneBean pianoDiFormazione) throws Exception{
		pianiFormazioneDao.deletePianoDiFormazione(pianoDiFormazione);
	}
	
	void deletePianiDIFormazionebyUsera (String username)throws Exception{
		pianiFormazioneDao.deleteAllPianiDIFormazione(username);
	}
	
	public ArrayList<PianoDIformazioneBean>getAllPiani(String username)throws Exception{
		ArrayList<PianoDIformazioneBean> listaPiani = pianiFormazioneDao.getAllPiani(username);
		
		return listaPiani;
	}
	
	public PianoDIformazioneBean getPiano(PianoDIformazioneBean pianoDIformazioneBean)throws Exception{
		PianoDIformazioneBean piano = pianiFormazioneDao.findPianiFormazione(pianoDIformazioneBean);
		
		return piano;
	}

}
