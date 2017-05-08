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
import it.fba.webapp.beans.RendicontazioneBean;
import it.fba.webapp.beans.RendicontazioneFileBean;

@Repository("LavoratoriDaoImpl")
@Transactional()
public class LavoratoriDaoImpl implements LavoratoriDao{
	
	@PersistenceContext
	EntityManager entityManager;
	
	private final String queryAll = "Select l from LavoratoriBean l where l.idPiano= :idPianoStr and l.nomeModulo= :nomemoduloStr";
	private final String esistonoLavoratori = "Select l from LavoratoriBean l where l.idPiano= :idPianoStr";
	private final String queryUpdateLavoratore = "Update LavoratoriBean l "
						  + "set l.matricola= :matricolaStr, l.orePresenza= :orePresenzaStr, l.esitoTest= :esitoTestStr,"
						  + "l.nomeAllegato= :nomeAllegatoStr, l.stato= :statoStr "
						  + "where l.id= :idStr"; 
	private final String queryDeleteLavoratori = "Delete from LavoratoriBean l where l.nomeModulo= :nomeModuloStr and l.idPiano= :idPianoStr";
	private final String queryDeleteLavoratoriPiano = "Delete from LavoratoriBean l where  l.idPiano= :idPianoStr";
	private final String trovaPianiLavoratori = "select l.idPiano  from LavoratoriBean l where l.nomeAllegato= :nomeAllegatoStr";
	private final String trovaFile ="Select f.id from LavoratoriFileBean f where f.nomeAllegato= :nomeAllegatoStr and f.username = :usernameStr";
	private final String elencoFileByUser ="Select f.id, f.nomeAllegato from LavoratoriFileBean f where f.username = :usernameStr";
	private final String eliminaFile ="Delete from LavoratoriFileBean f where f.id = :idStr";
	private final String queryNomi = " select l.nomeAllegato  from LavoratoriFileBean l  where l.username= :usernameStr";
	private final String queryNomeByID = "select l.nomeAllegato  from LavoratoriFileBean l where l.id= :idStr and l.username= :usernameStr";

	
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
			
		}
		
	}

	@Override
	public void updateLavoratore(LavoratoriBean lavoratoriBean) throws SQLException {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery(queryUpdateLavoratore);
		int i= query.setParameter("matricolaStr", lavoratoriBean.getMatricola()).setParameter("orePresenzaStr", lavoratoriBean.getOrePresenza())
				 .setParameter("esitoTestStr",lavoratoriBean.getEsitoTest()).setParameter("nomeAllegatoStr", lavoratoriBean.getNomeAllegato()).setParameter("statoStr",lavoratoriBean.getStato())
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

	@Override
	public ArrayList<String> leggiNomiAllegati(String username) throws Exception {
		// TODO Auto-generated method stub
		
				Query query = entityManager.createQuery(queryNomi);
		        ArrayList<String> lavoratorFoleiList = (ArrayList<String>) query.setParameter("usernameStr", username).getResultList();

				return lavoratorFoleiList;
	}
	
	@Override
	public ArrayList<Integer> findLavoratoriByFile(String nomeFile) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<RendicontazioneBean> listaFile = new ArrayList<>();
		Query query= entityManager.createQuery(trovaPianiLavoratori);
		ArrayList<Integer> listaRendicontazione = (ArrayList<Integer>) query.setParameter("nomeAllegatoStr", nomeFile).getResultList();

		return listaRendicontazione;
	}

	@Override
	public String getNomeFileByID(int id, String username) throws Exception {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery(queryNomeByID);
		String nomefile =(String) query.setParameter("idStr", id).setParameter("usernameStr", username).getSingleResult();
		return nomefile;
	}

	
	
	

}
