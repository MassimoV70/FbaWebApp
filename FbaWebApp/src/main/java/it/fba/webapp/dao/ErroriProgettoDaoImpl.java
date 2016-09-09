package it.fba.webapp.dao;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import it.fba.webapp.beans.ErroreProgettoBean;

@Repository("ErroriProgettoDaoImpl")
@Transactional

public class ErroriProgettoDaoImpl implements ErroriProgettoDao{
	
	@PersistenceContext
	EntityManager entityManager;
	
	private final String leggiErrProgetto = "select e from ErroreProgettoBean e where e.idPiano= :idPianoInt";
	private final String deleteErroriPrigetto = "delete from ErroreProgettoBean e where e.idPiano= :idPianoInt";

	@Override
	public ArrayList<ErroreProgettoBean> getErroriProgetto(int idPiano) throws Exception {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery(leggiErrProgetto);
		ArrayList<ErroreProgettoBean> listaErroriProgetto =(ArrayList<ErroreProgettoBean>) query.setParameter("idPianoInt", idPiano).getResultList();
		return listaErroriProgetto;
	}

	@Override
	public void salvaErroriProgetto(ArrayList<ErroreProgettoBean> listaErroriProgetto) throws Exception {
		// TODO Auto-generated method stub
		for (ErroreProgettoBean errore :listaErroriProgetto){
			entityManager.persist(errore);
		}
		
	}

	@Override
	public void deleteErroriProgett(int idPiano) throws Exception {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery(deleteErroriPrigetto);
		int i = query.setParameter("idPianoInt", idPiano).executeUpdate();
		
	}

	

	
	

}
