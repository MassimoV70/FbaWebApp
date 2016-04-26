package it.fba.webapp.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import it.fba.webapp.beans.CalendarioBean;
import it.fba.webapp.beans.LavoratoriBean;

@Repository("LavoratoriDaoImpl")
@Transactional()
public class LavoratoriDaoImpl implements LavoratoriDao{
	
	@PersistenceContext
	EntityManager entityManager;
	
	private final String queryAll = "Select l from LavoratoriBean l where l.idPiano= :idPianoStr and l.nomeModulo= :nomemoduloStr";
	private final String queryUpdateLavoratore = "Update LavoratoriBean l "
						  + "set l.matricola= :matricolaStr, l.orePresenza= :orePresenzaStr, l.esitoTest= :esitoTestStr, l.stato= :statoStr "
						  + "where l.id= :idStr"; 
	private final String queryDeleteLavoratori = "Delete from LavoratoriBean l where l.nomeModulo= :nomeModuloStr and l.idPiano= :idPianoStr";
	private final String queryDeleteLavoratoriPiano = "Delete from LavoratoriBean l where  l.idPiano= :idPianoStr";

	@Override
	public LavoratoriBean findLavoratore(LavoratoriBean lavoratoriBean) throws SQLException {
		// TODO Auto-generated method stub
		LavoratoriBean lavoratore = entityManager.find(LavoratoriBean.class, lavoratoriBean.getId());
		return lavoratore;
	
	}

	@Override
	public void caricaLavoratori(ArrayList<LavoratoriBean> listaLavoratori) throws SQLException {
		// TODO Auto-generated method stub
		for(LavoratoriBean lavoratore:listaLavoratori){
			entityManager.persist(lavoratore);
			entityManager.flush();
			entityManager.clear();
		}
		
	}

	@Override
	public void updateLavoratore(LavoratoriBean lavoratoriBean) throws SQLException {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery(queryUpdateLavoratore);
		int i= query.setParameter("matricolaStr", lavoratoriBean.getMatricola()).setParameter("orePresenzaStr", lavoratoriBean.getOrePresenza())
				 .setParameter("esitoTestStr",lavoratoriBean.getEsitoTest()).setParameter("statoStr",lavoratoriBean.getStato())
		         .setParameter("idStr",lavoratoriBean.getId()).executeUpdate();
		
	}

	@Override
	public void deleteLavoratore(LavoratoriBean lavoratoriBean) throws SQLException {
		// TODO Auto-generated method stub
		LavoratoriBean lavoratori = entityManager.find(LavoratoriBean.class, lavoratoriBean.getId());
		entityManager.remove(lavoratori);
		
	}

	@Override
	public void deleteAllLavoratori(LavoratoriBean lavoratoriBean) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<LavoratoriBean> getAllLavoratori(LavoratoriBean lavoratoriBean) throws SQLException {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery(queryAll);
		@SuppressWarnings("unchecked")
		
		ArrayList<LavoratoriBean> resultList = (ArrayList<LavoratoriBean>)query.setParameter("idPianoStr", lavoratoriBean.getIdPiano()).setParameter("nomemoduloStr", lavoratoriBean.getNomeModulo()).getResultList();
		return resultList;
	}

	@Override
	public void deleteLavoratoriPiano(LavoratoriBean lavoratoriBean) throws SQLException {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery(queryDeleteLavoratoriPiano);
		int i= query.setParameter("idPianoStr", lavoratoriBean.getIdPiano()).executeUpdate();
		
		
	}
	
	

}
