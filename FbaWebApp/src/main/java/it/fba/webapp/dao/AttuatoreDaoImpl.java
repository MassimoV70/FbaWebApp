package it.fba.webapp.dao;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import it.fba.webapp.beans.AttuatoreBean;

@Repository("AttuatoreDaoImpl")
@Transactional()
public class AttuatoreDaoImpl implements AttuatoriDao{
	
	@PersistenceContext
    EntityManager entityManager;
	
	private final String queryNomi = "select a.attuatorePIVA, a.nomeAllegato1, a.nomeAllegato2, a.nomeAllegato3, a.nomeAllegato4 from AttuatoreBean a"
									+ " where a.attuatorePIVA = :attuatorePIVAStr";
	private final String queryUpdate = "update AttuatoreBean a set a.nomeAllegato1= :nomeAllegatoStr1, a.nomeAllegato2 = :nomeAllegatoStr2,"
									  + " a.nomeAllegato3= :nomeAllegatoStr3, a.nomeAllegato4= :nomeAllegatoStr4,"
									  + " a.allegatoFile1= :allegatoFileByte1, a.allegatoFile2= :allegatoFileByte2, "
									  + " a.allegatoFile3= :allegatoFileByte3, a.allegatoFile4= :allegatoFileByte4 "
			+ " where a.attuatorePIVA= :attuatorePIVAStr";
	
	
	
	
	@Override
	public void updateAttuatore(AttuatoreBean attuatoreBean) throws Exception {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery(queryUpdate);
		int i = query.setParameter("nomeAllegatoStr1", attuatoreBean.getNomeAllegato1()).setParameter("nomeAllegatoStr2", attuatoreBean.getNomeAllegato2())
				.setParameter("nomeAllegatoStr3", attuatoreBean.getNomeAllegato3()).setParameter("nomeAllegatoStr4", attuatoreBean.getNomeAllegato4())
				.setParameter("allegatoFileByte1", attuatoreBean.getAllegatoFile1()).setParameter("allegatoFileByte2", attuatoreBean.getAllegatoFile2())
				.setParameter("allegatoFileByte3", attuatoreBean.getAllegatoFile3()).setParameter("allegatoFileByte4", attuatoreBean.getAllegatoFile4())
				.setParameter("attuatorePIVAStr", attuatoreBean.getAttuatorePIVA()).executeUpdate();
		
	}

	@Override
	public AttuatoreBean leggiNomiAllegati(AttuatoreBean attuatoreBean) throws Exception {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery(queryNomi);
        ArrayList<Object[]> attuatoreList = (ArrayList<Object[]>) query.setParameter("attuatorePIVAStr", attuatoreBean.getAttuatorePIVA()).getResultList();
		if (attuatoreList!=null&&!attuatoreList.isEmpty()){
				Object[] oggetto = (Object[]) attuatoreList.get(0);
				attuatoreBean.setAttuatorePIVA((String) oggetto[0]);
				attuatoreBean.setNomeAllegato1((String) oggetto[1]);
				attuatoreBean.setNomeAllegato2((String) oggetto[2]);
				attuatoreBean.setNomeAllegato3((String) oggetto[3]);
				attuatoreBean.setNomeAllegato4((String) oggetto[4]);
			
		}
		return attuatoreBean;
	}

	@Override
	public AttuatoreBean findAttuatoreById(AttuatoreBean attuatoreBean) throws Exception {
		// TODO Auto-generated method stub
		AttuatoreBean attuat = new AttuatoreBean();
		attuat = entityManager.find(AttuatoreBean.class, attuatoreBean.getAttuatorePIVA());
		return attuat;
	}

	@Override
	public void creaAttuatore(AttuatoreBean attuatoreBean) throws Exception {
		// TODO Auto-generated method stub
		entityManager.persist(attuatoreBean);
	}

	@Override
	public boolean esisteAttuatore(AttuatoreBean attuatoreBean) throws Exception {
		// TODO Auto-generated method stub
		boolean esiste=false;
		Query query = entityManager.createQuery(queryNomi);
		ArrayList<Object[]> attuatoreList = (ArrayList<Object[]>) query.setParameter("attuatorePIVAStr", attuatoreBean.getAttuatorePIVA()).getResultList();
		if (attuatoreList!=null&&!attuatoreList.isEmpty()){
			Object[] oggetto = (Object[]) attuatoreList.get(0);
			if ( oggetto[0]!=null){
				esiste=true;
			}
		
		}
		
		return esiste;
	}

	
	
	

}
