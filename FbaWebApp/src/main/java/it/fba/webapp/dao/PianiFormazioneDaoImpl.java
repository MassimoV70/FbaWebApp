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
	
	private final String queryAll = "Select p from PianoDIformazioneBean p where username= :usernameStr";
	private final String queryFindPiano = "Select p from PianoDIformazioneBean p where id= :idStr and username= :usernameStr";
	private final String queryDeleteByUser = "Delete from PianoDIformazioneBean p  where username= :usernameStr";
	private final String queryUpdatePiano = "Update PianoDIformazioneBean p "
										  + "set p.pianoDiFormazione= :nomePianoStr, p.tipoCorsoPiano= :tipoCorsoPianoStr, p.tematicaFormativa= :tematicaFormativaStr,"
										  + "p.dataInizioAtt= :dataInizioAttStr, p.dataFineAtt= :dataFineAttStr, p.numPartecipanti= :numPartecipantiStr,"
										  + "p.compImprInn= :compImprInnStr, p.compSett= :compSettStr, p.delocInter= :delocInterStr,"
										  + "p.formObblExLeg= :formObblExLegStr, p.formInIngresso= :formInIngressoStr, p.mantenimOccup= :mantenimOccupStr,"
										  + "p.manutAggComp= :manutAggCompStr, p.mobEstOutRic= :mobEstOutRicStr, p.sviluppoLoc= :sviluppoLocStr,"
										  + "p.modulo1= :modulo1, p.fadMod1= :fadMod1Str, p.modulo2= :modulo2, p.fadMod2= :fadMod2Str, p.attuatorePIVA= :pivaAtt "
										  + "where id= :idStr and username= :usernameStr";
	private final String queryDeletePiano = "Delete from PianoDIformazioneBean p  where id= :idStr";

	@Override
	public void caricaPianiFormazione(ArrayList<PianoDIformazioneBean> listaPiani) throws SQLException {
		// TODO Auto-generated method stub
		for(PianoDIformazioneBean piano:listaPiani){
			entityManager.persist(piano);
			entityManager.flush();
			entityManager.clear();
		}
		
	}

	@Override
	public void updatePianoDiFormazione(PianoDIformazioneBean pianoDiFormazioneBean) throws SQLException {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery(queryUpdatePiano);
		
		
	   int i= query.setParameter("nomePianoStr", pianoDiFormazioneBean.getPianoDiFormazione())
			 .setParameter("tipoCorsoPianoStr", pianoDiFormazioneBean.getTipoCorsoPiano()).setParameter("tematicaFormativaStr", pianoDiFormazioneBean.getTematicaFormativa())
			 .setParameter("dataInizioAttStr", pianoDiFormazioneBean.getDataInizioAtt()).setParameter("dataFineAttStr", pianoDiFormazioneBean.getDataFineAtt())
			 .setParameter("numPartecipantiStr", pianoDiFormazioneBean.getNumPartecipanti())
			 .setParameter("compImprInnStr", pianoDiFormazioneBean.getCompImprInn()).setParameter("compSettStr", pianoDiFormazioneBean.getCompSett()).setParameter("delocInterStr", pianoDiFormazioneBean.getDelocInter())
			 .setParameter("formObblExLegStr", pianoDiFormazioneBean.getFormObblExLeg()).setParameter("formInIngressoStr", pianoDiFormazioneBean.getFormInIngresso()).setParameter("mantenimOccupStr", pianoDiFormazioneBean.getMantenimOccup())
			 .setParameter("manutAggCompStr", pianoDiFormazioneBean.getManutAggComp()).setParameter("mobEstOutRicStr", pianoDiFormazioneBean.getMobEstOutRic()).setParameter("sviluppoLocStr", pianoDiFormazioneBean.getSviluppoLoc())
			 .setParameter("modulo1", pianoDiFormazioneBean.getModulo1()).setParameter("fadMod1Str", pianoDiFormazioneBean.getFadMod1())
	         .setParameter("modulo2", pianoDiFormazioneBean.getModulo2()).setParameter("fadMod2Str", pianoDiFormazioneBean.getFadMod2())
	         .setParameter("pivaAtt", pianoDiFormazioneBean.getAttuatorePIVA())
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
	

} 
