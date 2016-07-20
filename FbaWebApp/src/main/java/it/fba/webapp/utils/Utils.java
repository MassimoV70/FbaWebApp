package it.fba.webapp.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.validation.Errors;

import it.fba.webapp.beans.CalendarioBean;
import it.fba.webapp.beans.PianoDIformazioneBean;
import it.fba.webapp.beans.RendicontazioneBean;
import it.fba.webapp.beans.UsersBean;


public class Utils {
	
	@Resource(name="myProperties")
	private static Properties myProperties;
	
	
    final static SimpleDateFormat formatter= new SimpleDateFormat("dd/MM/yyyy");
	final static SimpleDateFormat formatterDB= new SimpleDateFormat("dd/MM/yyyy");
	private static final String TIME24HOURS_PATTERN = 
            "([01]?[0-9]|2[0-3]):[0-5][0-9]";
	private static final String NUMBER_PATTERN = 
            "([0-9]*)";
	private static final String DATE_PATTERN = 
            "([0-3]?[0-9])/[01]?[0-2]/[ANNO]";
	
	public static String dataOggi() {
		Date dataOggi=null;
		String dataOggiStr="";
		try {
			dataOggi = new Date();
			dataOggiStr = formattaData(dataOggi);
		} catch (Exception e) {
			// TODO: handle exception
			return dataOggiStr;
		}
		return dataOggiStr;
	}
	
	public static String formattaData (Date data)throws Exception{
		String formattedDate="";
		if (data!=null){
			formattedDate = formatter.format(data);
		}
		return formattedDate;		
	}
	
	public static Date dataDBFormatter(String data){
		Date dataFormattata=null;
		try {
			if (data!=null&&!data.isEmpty()){
				dataFormattata = formatterDB.parse(data);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		return dataFormattata;
	}
	
	
	
	public static ArrayList<UsersBean> userFormSettings (ArrayList<UsersBean> listaUtenti){
		try{
			if (listaUtenti!=null&&!listaUtenti.isEmpty()){
				for(UsersBean user :listaUtenti ){
					user.setDataInizioStr(Utils.formattaData(user.getDataInizio()));
					user.setDataFineStr(Utils.formattaData(user.getDataFine()));
					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return listaUtenti;
	}
	
	public static Map<String, String> getListaStati(Properties myProperty) {
		
		Map<String, String> listaStati = new LinkedHashMap<String, String>();
		listaStati.put(myProperty.getProperty("enabled.si"), "abilitato");
		listaStati.put(myProperty.getProperty("enabled.no"), "disabilitato");
		return listaStati;
		
	}
	
	public static boolean dataFuture(Date dataInizio, Date  dataFine) {
		// TODO Auto-generated method stub
		
		if (dataInizio!=null){
			if(dataFine!=null){
				if (dataFine.before(dataInizio)){
					return false;
				}
				
			}
		}
		return true;
		
       
	}
	
//	public static ArrayList<PianoDIformazioneBean> pianoFormazioneFormSetting (ArrayList<PianoDIformazioneBean> listaPiani){
//		try{
//			if (listaPiani!=null&&!listaPiani.isEmpty()){
//				for(PianoDIformazioneBean piano :listaPiani ){
//					piano.setDataInizioAttStr(Utils.formattaData(piano.getDataInizioAtt()));
//					piano.setDataFineAttStr(Utils.formattaData(piano.getDataFineAtt()));
//					
//					
//				}
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//			return null;
//		}
//		return listaPiani;
//	}
	
	public static ArrayList<CalendarioBean> calendarioModuloFormSetting (ArrayList<CalendarioBean> listaCalendario){
		try{
			if (listaCalendario!=null&&!listaCalendario.isEmpty()){
				for(CalendarioBean giorno :listaCalendario ){
					giorno.setDataStr(Utils.formattaData(giorno.getData()));
					
					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return listaCalendario;
	}
	
   public static Map<String, String> getListaSiNo(Properties myProperty) {
		
		Map<String, String> listaSelezione = new LinkedHashMap<String, String>();
		listaSelezione.put(myProperty.getProperty("selezione.si"), "Si");
		listaSelezione.put(myProperty.getProperty("selezione.no"), "No");
		return listaSelezione;
		
	}
   
   public static Map<String, String> getListaFadSiNo(Properties myProperty) {
		
		Map<String, String> listaSelezioneFad = new LinkedHashMap<String, String>();
		listaSelezioneFad.put(myProperty.getProperty("fad.si"), "FAD");
		listaSelezioneFad.put(myProperty.getProperty("fad.no"), "AULA");
		return listaSelezioneFad;
		
	}
   
//   public static PianoDIformazioneBean singoloPianoFormazioneFormSetting (PianoDIformazioneBean piano){
//		try{
//			if (piano.getDataInizioAtt()!=null){
//				piano.setDataInizioAttStr(Utils.formattaData(piano.getDataInizioAtt()));
//			}
//			if (piano.getDataFineAtt()!=null){
//				piano.setDataFineAttStr(Utils.formattaData(piano.getDataFineAtt()));
//			}
//			
//		}catch(Exception e){
//			e.printStackTrace();
//			return null;
//		}
//		return piano;
//	}
   
   public static String eliminaSpazi(String stringa){
	   if (stringa!=null&&!stringa.isEmpty()){
		   stringa=stringa.trim();
		   stringa=stringa.replace("  ", " ");
		   stringa=stringa.replace("\n", " ");
	   }
	   
	   return stringa;
	   
	   
   }
   
   public static String convertDoubleToString(Double numero)throws Exception{
	   
	   String numeroStringa = "";
	  
	    numeroStringa = numero.toString();
	  
	   return numeroStringa;
   }
   
   public static ArrayList<RendicontazioneBean> rendicontazioneFormSetting (ArrayList<RendicontazioneBean> listaRendicontazione){
		try{
			if (listaRendicontazione!=null&&!listaRendicontazione.isEmpty()){
				for(RendicontazioneBean rendicontazione : listaRendicontazione ){
					rendicontazione.setDataGiustificativoStr(Utils.formattaData(rendicontazione.getDataGiustificativo()));
					
					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return listaRendicontazione;
	}
   

}
