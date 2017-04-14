package it.fba.webapp.dao;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import it.fba.webapp.beans.EttLoadStatusBean;


@Repository("EttLoadStatusDaoImpl")
@Transactional

public class EttLoadStatusDaoImpl implements EttLoadStatusDao{
	@PersistenceContext
	EntityManager entityManager; 
	
	private final String queryErroriPiano = "Select e from EttLoadStatusBean e where e.ettIdPiano= :ettIdPianoStr";
	private final String eliminaErroriPiano = "Delete from EttLoadStatusBean e where e.ettIdPiano= :ettIdPianoStr";

	@Override
	public ArrayList<EttLoadStatusBean> getErroriTrasmPiano(int idPiano) {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery(queryErroriPiano);
		ArrayList<EttLoadStatusBean> listaErroriTrasm = (ArrayList<EttLoadStatusBean>)query.setParameter("ettIdPianoStr", idPiano).getResultList();
	    return listaErroriTrasm;
	}

	@Override
	public void deletePianoTrasmesso(int idPiano) {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery(eliminaErroriPiano);
		int i =query.setParameter("ettIdPianoStr", idPiano).executeUpdate();
		query.executeUpdate();
	}


	
	
	

}
