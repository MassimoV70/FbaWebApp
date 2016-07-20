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
import it.fba.webapp.beans.LavoratoriFileBean;
import it.fba.webapp.beans.PianoDIformazioneBean;
import it.fba.webapp.beans.RendicontazioneFileBean;

@Repository("LavoratoriDaoImpl")
@Transactional()
public class LavoratoriDaoImpl implements LavoratoriDao{
	
	@PersistenceContext
	EntityManager entityManager;
	
	private final String queryAll = "Select l from LavoratoriBean l where l.idPiano= :idPianoStr and l.nomeModulo= :nomemoduloStr";
	private final String esistonoLavoratori = "Select l from LavoratoriBean l where l.idPiano= :idPianoStr";
	private final String queryUpdateLavoratore = "Update LavoratoriBean l "
						  + "set l.matricola= :matricolaStr, l.orePresenza= :orePresenzaStr, l.esitoTest= :esitoTestStr, l.stato= :statoStr "
						  + "where l.id= :idStr"; 
	private final String queryDeleteLavoratori = "Delete from LavoratoriBean l where l.nomeModulo= :nomeModuloStr and l.idPiano= :idPianoStr";
	private final String queryDeleteLavoratoriPiano = "Delete from LavoratoriBean l where  l.idPiano= :idPianoStr";
	private final String trovaFile ="Select f.id from LavoratoriFileBean f where f.nomeAllegato= :nomeAllegatoStr and f.username = :usernameStr";
	private final String elencoFileByUser ="Select f.id, f.nomeAllegato from LavoratoriFileBean f where f.username = :usernameStr";
	private final String eliminaFile ="Delete from LavoratoriFileBean f where f.id = :idStr";
	

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
	
	@Override
	public ArrayList<LavoratoriBean> esistonoLavoratori(PianoDIformazioneBean pianoDIformazioneBean) throws SQLException {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery(esistonoLavoratori);
		@SuppressWarnings("unchecked")
		
		ArrayList<LavoratoriBean> resultList = (ArrayList<LavoratoriBean>)query.setParameter("idPianoStr", pianoDIformazioneBean.getId()).getResultList();
		return resultList;
	}

	@Override
	public void caricaFileLavoratori(LavoratoriFileBean lavoratoriFileBean) {
		// TODO Auto-generated method stub
		entityManager.persist(lavoratoriFileBean);
		
	}

	@Override
	public boolean esisteFile(String nomeFile, String username) {
		// TODO Auto-generated method stub
		boolean trovato = false;
		Query query = entityManager.createQuery(trovaFile);
		
		ArrayList<Object> oggetti = (ArrayList<Object>) query.setParameter("nomeAllegatoStr", nomeFile).setParameter("usernameStr", username).getResultList();
		if (oggetti!=null&&oggetti.size()>0){
			
				trovato = true;
			
		}
		return trovato;
	}

	@Override
	public ArrayList<LavoratoriFileBean> getElencoFileLavoratori(String username) {
		// TODO Auto-generated method stub
		ArrayList<LavoratoriFileBean> listaFile = new ArrayList<>();
		Query query = entityManager.createQuery(elencoFileByUser);
		ArrayList<Object[]> listaOggetti = (ArrayList<Object[]>) query.setParameter("usernameStr", username).getResultList();
		if(listaOggetti!=null&&!listaOggetti.isEmpty()){
			for (Object[] oggetto : listaOggetti){
				LavoratoriFileBean lavoratoriFileBean = new LavoratoriFileBean();
				lavoratoriFileBean.setId((Integer)oggetto[0]);
				lavoratoriFileBean.setNomeAllegato( (String)oggetto[1]);
				listaFile.add(lavoratoriFileBean);
			}
		}
		return listaFile;
	}

	@Override
	public void eliminaFile(LavoratoriFileBean lavoratoriFileBean) {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery(eliminaFile);
		query.setParameter("idStr", lavoratoriFileBean.getId()).executeUpdate();
		
	}
	
	

}
