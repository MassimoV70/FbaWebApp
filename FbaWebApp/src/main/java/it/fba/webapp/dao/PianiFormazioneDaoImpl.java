package it.fba.webapp.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import it.fba.webapp.beans.PianoDIformazioneBean;
import it.fba.webapp.beans.UsersBean;

@Repository("PianiFormazioneDaoImpl")
@Transactional()

public class PianiFormazioneDaoImpl implements PianiFormazioneDao{
	
	@PersistenceContext
	EntityManager entityManager;
	
	private final String queryAll = "Select p from PianoDIformazioneBean p where username= :usernameStr order by p.enabled";
	private final String queryFindPiano = "Select p from PianoDIformazioneBean p where id= :idStr and username= :usernameStr";
	private final String queryDeleteByUser = "Delete from PianoDIformazioneBean p  where username= :usernameStr";
	private final String queryUpdatePiano = "Update PianoDIformazioneBean p "
										  + "set p.nuemroProtocollo= :nuemroProtocolloStr, p.nomeProgetto= :nomeProgettoStr, p.tipoCorsoPiano= :tipoCorsoPianoStr, "
										  + " p.tematicaFormativa= :tematicaFormativaStr, p.numeroPartecipanti= :numeroPartecipantiStr,"
										  + " p.modulo1= :modulo1, p.fadMod1= :fadMod1Str, p.durataModulo1= :durataModulo1Str,"
										  + " p.modulo2= :modulo2, p.fadMod2= :fadMod2Str, p.durataModulo2= :durataModulo2Str,"
										  + " p.formeAiuti= :formeAiutiStr, p.categSvantagg= :categSvantaggStr ,p.attuatorePIVA= :pivaAtt,"
										  + " p.nomeAllegato1= :nomeAllegatoStr1 ,p.nomeAllegato2= :nomeAllegatoStr2 ,"
										  + " p.nomeAllegato3= :nomeAllegatoStr3 ,p.nomeAllegato4= :nomeAllegatoStr4 ,p.enabled= :enabledStr "
										  + " where id= :idStr and username= :usernameStr";
	private final String queryDeletePiano = "Delete from PianoDIformazioneBean p  where id= :idStr";
	
	private final String queryAggAttuatori = "Update PianoDIformazioneBean p set p.nomeAllegato1= :nomeAllegato1Str, p.nomeAllegato2= :nomeAllegato2Str, "
			                               + "p.nomeAllegato3= :nomeAllegato3Str, p.nomeAllegato4= :nomeAllegato4Str where p.attuatorePIVA= :attuatorePIVAStr";								
		

	private final String queryUpdateStatoPiano = "Update PianoDIformazioneBean p set p.enabled= :enabledStr"
												+ " where p.id= :idStr and p.username= :usernameStr";
	private final String queryUpdateStatoPianiByElimFile = "Update PianoDIformazioneBean p set p.enabled= :enabledStr"
			+ " where p.id= :idStr and username= :usernameStr";
	
	
	
	
	

	@Override
	public void caricaPianiFormazione(ArrayList<PianoDIformazioneBean> listaPiani) throws SQLException {
		// TODO Auto-generated method stub
		for(PianoDIformazioneBean piano:listaPiani){
			entityManager.persist(piano);
			entityManager.clear();
		}
		
	}

	@Override
	public void updatePianoDiFormazione(PianoDIformazioneBean pianoDiFormazioneBean) throws SQLException {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery(queryUpdatePiano);
		
		
	   int i= query.setParameter("nuemroProtocolloStr", pianoDiFormazioneBean.getNuemroProtocollo())
			 .setParameter("nomeProgettoStr", pianoDiFormazioneBean.getNomeProgetto()).setParameter("tipoCorsoPianoStr", pianoDiFormazioneBean.getTipoCorsoPiano())
			 .setParameter("tematicaFormativaStr", pianoDiFormazioneBean.getTematicaFormativa()).setParameter("numeroPartecipantiStr", pianoDiFormazioneBean.getNumeroPartecipanti())
			 .setParameter("modulo1", pianoDiFormazioneBean.getModulo1()).setParameter("fadMod1Str", pianoDiFormazioneBean.getFadMod1())
			 .setParameter("durataModulo1Str", pianoDiFormazioneBean.getDurataModulo1())
	         .setParameter("modulo2", pianoDiFormazioneBean.getModulo2()).setParameter("fadMod2Str", pianoDiFormazioneBean.getFadMod2())
	         .setParameter("durataModulo2Str", pianoDiFormazioneBean.getDurataModulo2())
	         .setParameter("formeAiutiStr", pianoDiFormazioneBean.getFormeAiuti())
	         .setParameter("categSvantaggStr", pianoDiFormazioneBean.getCategSvantagg())
	         .setParameter("pivaAtt", pianoDiFormazioneBean.getAttuatorePIVA())
	         .setParameter("nomeAllegatoStr1", pianoDiFormazioneBean.getNomeAllegato1()).setParameter("nomeAllegatoStr2", pianoDiFormazioneBean.getNomeAllegato2())
	         .setParameter("nomeAllegatoStr3", pianoDiFormazioneBean.getNomeAllegato3()).setParameter("nomeAllegatoStr4", pianoDiFormazioneBean.getNomeAllegato4())
	         .setParameter("enabledStr", pianoDiFormazioneBean.getEnabled())
	         .setParameter("usernameStr", pianoDiFormazioneBean.getUsername()).setParameter("idStr",pianoDiFormazioneBean.getId()).executeUpdate();
		
	}

	@Override
	public void deletePianoDiFormazione(PianoDIformazioneBean pianoDiFormazione) throws SQLException {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery(queryDeletePiano);
		int i= query.setParameter("idStr", pianoDiFormazione.getId()).executeUpdate();
//		entityManager.find(PianoDIformazioneBean.class, pianoDiFormazione.getId());
//		entityManager.remove(pianoDiFormazione);
	}

	
	@Override
	public void deleteAllPianiDIFormazione(String username) throws SQLException {
		// TODO Auto-generated method stub
		Query queryDeleteByUsera = entityManager.createQuery("queryDeleteByUsera");
		queryDeleteByUsera.setParameter("usernameStr", username).executeUpdate();
			
			
		
		
	}
	
	public ArrayList<PianoDIformazioneBean> getAllPiani(String username){
		Query query = entityManager.createQuery(queryAll);
		@SuppressWarnings("unchecked")
		
		ArrayList<PianoDIformazioneBean> resultList = (ArrayList<PianoDIformazioneBean>)query.setParameter("usernameStr", username).getResultList();
		return resultList;
		
	}

	@Override
	public PianoDIformazioneBean findPianiFormazione(PianoDIformazioneBean pianoDIformazioneBean) throws SQLException {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery(queryFindPiano);
		@SuppressWarnings("unchecked")
		
		PianoDIformazioneBean piano = 
		(PianoDIformazioneBean) query.setParameter("usernameStr", pianoDIformazioneBean.getUsername()).setParameter("idStr", pianoDIformazioneBean.getId()).getSingleResult();
		return piano;
	}

	@Override
	public void updatePianoDiFormazioneAllegati(PianoDIformazioneBean pianoDiFormazione) throws SQLException {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery(queryAggAttuatori);
	int i = query.setParameter("nomeAllegato1Str", pianoDiFormazione.getNomeAllegato1()).setParameter("nomeAllegato2Str", pianoDiFormazione.getNomeAllegato2()).setParameter("nomeAllegato3Str", pianoDiFormazione.getNomeAllegato3())
		     .setParameter("nomeAllegato4Str", pianoDiFormazione.getNomeAllegato4()).setParameter("attuatorePIVAStr", pianoDiFormazione.getAttuatorePIVA()).executeUpdate();
		
	}

	@Override
	public void updateStatoPianoDiFormazione(PianoDIformazioneBean pianoDiFormazioneBean) throws SQLException {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery(queryUpdateStatoPiano);
		
		
		   int i= query.setParameter("enabledStr", pianoDiFormazioneBean.getEnabled())
		         .setParameter("usernameStr", pianoDiFormazioneBean.getUsername()).setParameter("idStr",pianoDiFormazioneBean.getId()).executeUpdate();
		
	}

	@Override
	public void aggiornaStatoPianoByEliminazioneFile(ArrayList<Integer> listaIdPiani, String stato, String username) {
		// TODO Auto-generated method stub
        Query query = entityManager.createQuery(queryUpdateStatoPianiByElimFile);
		for (Integer id : listaIdPiani){
		
			query.setParameter("enabledStr", stato).setParameter("idStr", id).setParameter("usernameStr", username).executeUpdate();
		}
		
	}


	

} 
