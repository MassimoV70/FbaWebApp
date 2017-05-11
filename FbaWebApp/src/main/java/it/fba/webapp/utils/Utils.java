package it.fba.webapp.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.validation.Errors;

import it.fba.webapp.beans.CalendarioBean;
import it.fba.webapp.beans.LavoratoriBean;
import it.fba.webapp.beans.PianoDIformazioneBean;
import it.fba.webapp.beans.RendicontazioneBean;
import it.fba.webapp.beans.UsersBean;
import it.fba.webapp.form.validator.FormSecurityValidator;


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
		listaSelezioneFad.put(myProperty.getProperty("assente"), "assente");
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
		   stringa=stringa.replace("<", " ");
		   stringa=stringa.replace(">", " ");
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
   
   public static String eliminaSpaziTot(String stringa){
	   if (stringa!=null&&!stringa.isEmpty()){
		   stringa=stringa.trim();
		   stringa=stringa.replace(" ", "");
		   stringa=stringa.replace("<", "");
		   stringa=stringa.replace(">", "");
		   stringa=stringa.replace("\n", "");
	   }
	   
	   return stringa;
	   
	   
   }
   
   public static PianoDIformazioneBean formattaPiano (PianoDIformazioneBean piano){
	   piano.setAttuatorePIVA(eliminaSpaziTot(piano.getAttuatorePIVA()));
	   piano.setNuemroProtocollo(eliminaSpaziTot(piano.getNuemroProtocollo()));
	   piano.setNumeroPartecipanti(eliminaSpaziTot(piano.getNumeroPartecipanti()));
	   piano.setDurataModulo1(eliminaSpaziTot(piano.getDurataModulo1()));
	   piano.setDurataModulo2(eliminaSpaziTot(piano.getDurataModulo2()));
	   return piano;
   }
   
   public static LavoratoriBean formattaLavoratore (LavoratoriBean lavoratore){
	   lavoratore.setMatricola(eliminaSpaziTot(lavoratore.getMatricola()));
	   lavoratore.setOrePresenza(eliminaSpaziTot(lavoratore.getOrePresenza()));
	   return lavoratore;
   }
   
   
   
   public static RendicontazioneBean formattaRendicontazione (RendicontazioneBean rendicontazione){
	   
	   rendicontazione.setCodice(eliminaSpaziTot(rendicontazione.getCodice()));
	   rendicontazione.setContributoFBA(eliminaSpaziTot(rendicontazione.getContributoFBA()));
	   rendicontazione.setContributoPrivato(eliminaSpaziTot(rendicontazione.getContributoPrivato()));
	   rendicontazione.setValoreComplessivo(eliminaSpaziTot(rendicontazione.getValoreComplessivo()));
	   return rendicontazione;
	   
   }
   
   public static int calcolaIntervalloTempo (String oraInizio,String oraFine)throws Exception{
	   int differenza = -1; 
	   if (oraInizio!=null&&oraFine!=null){
		   if (FormSecurityValidator.isTime(oraInizio)&&FormSecurityValidator.isTime(oraFine)){
			    int indiceOraInizio = oraInizio.indexOf(":");
			    int indiceOraFine = oraFine.indexOf(":");
				int ora1 = Integer.parseInt(oraInizio.substring(0, indiceOraInizio));
				int minuti1 = Integer.parseInt(oraInizio.substring(indiceOraInizio+1));
				int ora2 = Integer.parseInt(oraFine.substring(0, indiceOraFine));
				int minuti2 = Integer.parseInt(oraFine.substring(indiceOraFine+1));
				if (ora2>=ora1){
					differenza = (ora2*60 + minuti2)-(ora1*60 + minuti1);
				}
				if (differenza<=0){
					 differenza = -1;
				}
		   }
				
	   }
		
		return differenza;
	}
   
   public static int stringToMinutes (String oreMin)throws Exception{
	   int minutiTotali=-1;
	   if (oreMin!=null){
		   if ( FormSecurityValidator.isTime(oreMin)){
			   int indiceOraInizio = oreMin.indexOf(":");
			   int ora = Integer.parseInt(oreMin.substring(0, indiceOraInizio));
			   int minuti = Integer.parseInt(oreMin.substring(indiceOraInizio+1));
			   minutiTotali =  ora*60+minuti;
		   }
	   }
	   return minutiTotali;
   }

}
