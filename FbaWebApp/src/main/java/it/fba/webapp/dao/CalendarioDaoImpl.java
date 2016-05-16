package it.fba.webapp.dao;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import it.fba.webapp.beans.CalendarioBean;
import it.fba.webapp.beans.PianoDIformazioneBean;

@Repository("CalendarioDaoImpl")
@Transactional()

public class CalendarioDaoImpl implements CalendarioDao {
	
	@PersistenceContext
	EntityManager entityManager;
	
	private final String queryAll = "Select c from CalendarioBean c where c.idPiano= :idPianoStr and c.nomeModulo= :nomemoduloStr";
	private final String queryEsisteCalendario = "Select c from CalendarioBean c where c.idPiano= :idPianoStr";
	private final String queryUpdateGiorno = "Update CalendarioBean c "
						  + "set c.data= :dataStr, c.inizioMattina= :inizioMattinaStr, c.fineMattina= :fineMattinaStr, "
						  + "c.inizioPomeriggio= :inizioPomeriggioStr, c.finePomeriggio= :finePomeriggioStr, c.stato= :statoStr "
						  + "where c.id= :idStr"; 
	private final String queryDeleteCalendario = "Delete from CalendarioBean c where c.nomeModulo= :nomeModuloStr and c.idPiano= :idPianoStr";
	private final String queryDeleteCalendariPiano = "Delete from CalendarioBean c where  c.idPiano= :idPianoStr";
	
	@Override
	public CalendarioBean findGiornoCalendario(CalendarioBean calendarioBean) throws SQLException {
		// TODO Auto-generated method stub
		CalendarioBean giornoCalendario = entityManager.find(CalendarioBean.class, calendarioBean.getId());
		return giornoCalendario;
	}

	@Override
	public void caricaCalendari(ArrayList<CalendarioBean> listaCalendari) throws SQLException {
		// TODO Auto-generated method stub
		for(CalendarioBean calendario:listaCalendari){
			entityManager.persist(calendario);
			entityManager.flush();
			entityManager.clear();
		}
		
		
	}

	@Override
	public void updateCalednario(CalendarioBean calendarioBean) throws SQLException {
		Query query = entityManager.createQuery(queryUpdateGiorno);
		
		// TODO Auto-generated method stub
		int i= query.setParameter("dataStr", calendarioBean.getData()).setParameter("inizioMattinaStr", calendarioBean.getInizioMattina())
		         .setParameter("fineMattinaStr", calendarioBean.getFineMattina()).setParameter("inizioPomeriggioStr", calendarioBean.getInizioPomeriggio())
		         .setParameter("finePomeriggioStr", calendarioBean.getFinePomeriggio()).setParameter("statoStr",calendarioBean.getStato())
		         .setParameter("idStr",calendarioBean.getId()).executeUpdate();
		        
		
	}

	@Override
	public void deleteCalednario(CalendarioBean calendarioBean) throws SQLException {
		// TODO Auto-generated method stub
		CalendarioBean calen = entityManager.find(CalendarioBean.class, calendarioBean.getId());
		entityManager.remove(calen);
		
	}

	@Override
	public void deleteAllCalednari(CalendarioBean calendarioBean) throws SQLException {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery(queryDeleteCalendario);
		
		int i = query.setParameter("idPianoStr",calendarioBean.getIdPiano()).setParameter("nomeModuloStr",calendarioBean.getNomeModulo()).executeUpdate();
		
	}

	@Override
	public ArrayList<CalendarioBean> getAllCalednario(CalendarioBean calendarioBean) throws SQLException {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery(queryAll);
		@SuppressWarnings("unchecked")
		
		ArrayList<CalendarioBean> resultList = (ArrayList<CalendarioBean>)query.setParameter("idPianoStr", calendarioBean.getIdPiano()).setParameter("nomemoduloStr", calendarioBean.getNomeModulo()).getResultList();
		return resultList;
	}

	@Override
	public void deleteCalednariPiano(CalendarioBean calendarioBean) throws SQLException {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery(queryDeleteCalendariPiano);
		
		// TODO Auto-generated method stub
		int i= query.setParameter("idPianoStr", calendarioBean.getIdPiano()).executeUpdate();
		
	}
	
	@Override
	public ArrayList<CalendarioBean> esisteCalendario (PianoDIformazioneBean pianoDIformazioneBean) throws SQLException {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery(queryEsisteCalendario);
		@SuppressWarnings("unchecked")
		
		ArrayList<CalendarioBean> resultList = (ArrayList<CalendarioBean>)query.setParameter("idPianoStr", pianoDIformazioneBean.getId()).getResultList();
		return resultList;
	}
	
	

}
