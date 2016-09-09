package it.fba.webapp.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import it.fba.webapp.beans.AttuatoreBean;
import it.fba.webapp.beans.DatiFIleUploadBean;
import it.fba.webapp.beans.LavoratoriBean;
import it.fba.webapp.beans.PianoDIformazioneBean;
import it.fba.webapp.beans.RendicontazioneBean;
import it.fba.webapp.beans.RendicontazioneFileBean;

@Repository("RendicontazioneDaoImpl")

@Transactional
public class RendicontazioneDaoImpl implements RendicontazioneDao {
	
	@PersistenceContext
	EntityManager entityManager;
	
	
	private final String queryRendicontazione = "Select r from RendicontazioneBean r where r.idPiano = :idPianoStr";
	private final String queryDeleteRendicontazionePiano = "Delete from RendicontazioneBean r where  r.idPiano= :idPianoStr";
	private final String trovaFile ="Select f.id from RendicontazioneFileBean f where f.nomeAllegato= :nomeAllegatoStr and f.username = :usernameStr";
	private final String elencoFileByUser ="Select f.id, f.nomeAllegato from RendicontazioneFileBean f where f.username = :usernameStr";
	private final String eliminaFile ="Delete from RendicontazioneFileBean f where f.id = :idStr";
	private final String esisteRendicontazione = "Select r from RendicontazioneBean r where r.idPiano= :idPianoStr";
	private final String queryNomi = "select r.nomeAllegato  from RendicontazioneFileBean r  where r.username= :usernameStr";
	
	@Override
	public RendicontazioneBean findRendicontazioneById(RendicontazioneBean rendicontazioneBean) throws Exception {
		// TODO Auto-generated method stub
		RendicontazioneBean rendicontazione = entityManager.find(RendicontazioneBean.class, rendicontazioneBean.getId());
		return rendicontazione;
	}

	@Override
	public void updateRendicontazione(RendicontazioneBean rendicontazioneBean) throws Exception {
		// TODO Auto-generated method stub
		
		rendicontazioneBean = entityManager.merge(rendicontazioneBean);
		
	}

	@Override
	public void creaRendicontazione(RendicontazioneBean rendicontazioneBean) throws Exception {
		// TODO Auto-generated method stub
		entityManager.persist(rendicontazioneBean);
		
	}

	@Override
	public ArrayList<RendicontazioneBean> getAllrendicontazione(PianoDIformazioneBean pianoDIformazioneBean)
			throws Exception {
		// TODO Auto-generated method stub
		
		Query query = entityManager.createQuery(queryRendicontazione);
		ArrayList<RendicontazioneBean> resultList = (ArrayList<RendicontazioneBean>) query.setParameter("idPianoStr", pianoDIformazioneBean.getId()).getResultList();
		
		return resultList;
	}

	@Override
	public void caricaRendicontazione(ArrayList<RendicontazioneBean> listaRendicontazione) throws SQLException {
		// TODO Auto-generated method stub
		for (RendicontazioneBean rendicontazione :listaRendicontazione){
			entityManager.persist(rendicontazione);
			entityManager.clear();
		}
		
	}

	@Override
	public void eliminaSoggettoRendicontato(RendicontazioneBean rendicontazioneBean) {
		// TODO Auto-generated method stub
		RendicontazioneBean rendicontazione = entityManager.find(RendicontazioneBean.class, rendicontazioneBean.getId());
		entityManager.remove(rendicontazione);
		
	}

	@Override
	public void eliminaRendicontazionePiano(RendicontazioneBean rendicontazioneBean) {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery(queryDeleteRendicontazionePiano);
		
		int i= query.setParameter("idPianoStr", rendicontazioneBean.getIdPiano()).executeUpdate();
		
	}

	@Override
	public void caricaFileRendicontazione(RendicontazioneFileBean rendicontazioneFileBean) {
		// TODO Auto-generated method stub
		entityManager.persist(rendicontazioneFileBean);
		
		
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
	public ArrayList<RendicontazioneFileBean> getElencoFileRendicontazione(String username) {
		// TODO Auto-generated method stub
		ArrayList<RendicontazioneFileBean> listaFile = new ArrayList<>();
		Query query = entityManager.createQuery(elencoFileByUser);
		ArrayList<Object[]> listaOggetti = (ArrayList<Object[]>) query.setParameter("usernameStr", username).getResultList();
		if(listaOggetti!=null&&!listaOggetti.isEmpty()){
			for (Object[] oggetto : listaOggetti){
				RendicontazioneFileBean rendicontazioneFileBean = new RendicontazioneFileBean();
				rendicontazioneFileBean.setId((Integer)oggetto[0]);
				rendicontazioneFileBean.setNomeAllegato( (String)oggetto[1]);
				listaFile.add(rendicontazioneFileBean);
			}
		}
		return listaFile;
	}

	@Override
	public void eliminaFile(RendicontazioneFileBean rendicontazioneFileBean) {
		// TODO Auto-generated method stub
		
		Query query = entityManager.createQuery(eliminaFile);
		query.setParameter("idStr", rendicontazioneFileBean.getId()).executeUpdate();
	}

	@Override
	public ArrayList<RendicontazioneBean> esisteRendicontazione(PianoDIformazioneBean pianoDIformazioneBean)
			throws SQLException {
		// TODO Auto-generated method stub
				Query query = entityManager.createQuery(esisteRendicontazione);
				@SuppressWarnings("unchecked")
				
				ArrayList<RendicontazioneBean> resultList = (ArrayList<RendicontazioneBean>)query.setParameter("idPianoStr", pianoDIformazioneBean.getId()).getResultList();
				return resultList;
	}

	@Override
	public ArrayList<String> leggiNomiAllegati(String username) throws Exception {
		// TODO Auto-generated method stub

		Query query = entityManager.createQuery(queryNomi);
        ArrayList<String> rendicontazioneFileiList = (ArrayList<String>) query.setParameter("usernameStr", username).getResultList();
//		if (lavoratoriList!=null&&!lavoratoriList.isEmpty()){
//			
//				Object[] oggetto = (Object[]) attuatoreList.get(0);
//				
//				attuatoreBean.setNomeAllegato1((String) oggetto[1]);
//				attuatoreBean.setNomeAllegato2((String) oggetto[2]);
//				attuatoreBean.setNomeAllegato3((String) oggetto[3]);
//				attuatoreBean.setNomeAllegato4((String) oggetto[4]);
//			
//		}
		return rendicontazioneFileiList;
		
	}
	
	
	

	

}
