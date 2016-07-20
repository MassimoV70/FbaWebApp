package it.fba.webapp.form.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import it.fba.webapp.beans.CalendarioBean;
import it.fba.webapp.beans.FileBean;
import it.fba.webapp.beans.LavoratoriBean;
import it.fba.webapp.beans.PianoDIformazioneBean;
import it.fba.webapp.beans.RendicontazioneBean;
import it.fba.webapp.fileInputOutput.ImportServiceExcel;
import it.fba.webapp.utils.Utils;

public class ExcelValidator {
	
	public static ArrayList<PianoDIformazioneBean> listaPianiFormazioneValidator(ArrayList<HashMap<String, String>> listaExcel,  String username)throws Exception {
		// TODO Auto-generated method stub
		ArrayList<PianoDIformazioneBean> listaPiani = new ArrayList<>();
		try{
		HashMap<String, String> map = new LinkedHashMap<>();
		Iterator<HashMap<String, String>> hashIterator = listaExcel.listIterator();
		int i=0;
		
		while( hashIterator.hasNext()){
				i++;
				map = hashIterator.next();
				PianoDIformazioneBean pianoFormazione = new PianoDIformazioneBean();
				pianoFormazione.setUsername(username);
				pianoFormazione.setNuemroProtocollo(Utils.eliminaSpazi(map.get("1")));
				pianoFormazione.setNomeProgetto(Utils.eliminaSpazi(map.get("2")));
				pianoFormazione.setTipoCorsoPiano(Utils.eliminaSpazi(map.get("3")));
				pianoFormazione.setTematicaFormativa(Utils.eliminaSpazi(map.get("4")));
				if(FormSecurityValidator.isNumber(Utils.eliminaSpazi(map.get("5")))){
					pianoFormazione.setNumeroPartecipanti(Utils.eliminaSpazi(map.get("5")));
				}else{
					pianoFormazione.setNumeroPartecipanti("0");

				}
				pianoFormazione.setModulo1(Utils.eliminaSpazi(map.get("6")));
				if(map.get("7").trim().equalsIgnoreCase("fad")){
					pianoFormazione.setFadMod1("fad");
				}else{
					pianoFormazione.setFadMod1("aula");
				}
				if(FormSecurityValidator.isOreMin(Utils.eliminaSpazi(map.get("8")))){
					pianoFormazione.setDurataModulo1(Utils.eliminaSpazi(map.get("8")));
				}else{
					pianoFormazione.setDurataModulo1("0");

				}
				pianoFormazione.setModulo2(Utils.eliminaSpazi(map.get("9")));
				if(map.get("10").trim().equalsIgnoreCase("fad")){
					pianoFormazione.setFadMod2("fad");
				}else{
					pianoFormazione.setFadMod2("aula");
				}
				if(FormSecurityValidator.isOreMin(Utils.eliminaSpazi(map.get("11")))){
					pianoFormazione.setDurataModulo2(Utils.eliminaSpazi(map.get("11")));
				}else{
					pianoFormazione.setDurataModulo2("0");

				}
				pianoFormazione.setFormeAiuti(Utils.eliminaSpazi(map.get("12")));
				if (map.get("13")!=null){
					if(Utils.eliminaSpazi(map.get("13")).equalsIgnoreCase("si")){
						pianoFormazione.setCategSvantagg("1");
					}else if(map.get("13").equalsIgnoreCase("no")){
						pianoFormazione.setCategSvantagg("0");
					}else{
						pianoFormazione.setCategSvantagg("No");
						pianoFormazione.setEnabled("0");
					}
				}
				if (map.get("14")!=null){
					pianoFormazione.setAttuatorePIVA(Utils.eliminaSpazi(map.get("14")));
					pianoFormazione.setAttuatorePIVA(pianoFormazione.getAttuatorePIVA().replace(" ", ""));
				}
				pianoFormazione.setEnabled("0");
				listaPiani.add(pianoFormazione);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
		}
		return listaPiani;
	
	}
	
	
	public static String validaOpzioni(String scelta){
		String valore = "0";
		if (scelta.equalsIgnoreCase("si")){
			valore = "1";
		}
		return valore;
	}
	
	public static String validaModalitaFormativa(String scelta){
		String valore = "AULA";
		if (scelta.equalsIgnoreCase("fad")){
			valore = "FAD";
		}
		return valore;
	}
	
	public static ArrayList<CalendarioBean> listaCalendarioValidator (ArrayList<HashMap<String, String>> listaExcel, CalendarioBean calendarioBean)throws Exception {
		ArrayList<CalendarioBean> listaCalendario = new ArrayList<>();
		try{
		
		HashMap<String, String> map = new LinkedHashMap<>();
		Iterator<HashMap<String, String>> hashIterator = listaExcel.listIterator();
		int i=0;
		while( hashIterator.hasNext()){
			i++;
			map = hashIterator.next();
			CalendarioBean calendario = new CalendarioBean();
			calendario.setIdPiano(calendarioBean.getIdPiano());
			calendario.setStato("1");
			calendario.setNomeModulo(calendarioBean.getNomeModulo());
			calendario.setDataStr(Utils.eliminaSpazi(Utils.eliminaSpazi(map.get("1"))));
			calendario.setData(Utils.dataDBFormatter(calendario.getDataStr()));
			calendario.setInizioMattina(Utils.eliminaSpazi(map.get("2")));
			calendario.setFineMattina(Utils.eliminaSpazi(map.get("3")));
			calendario.setInizioPomeriggio(Utils.eliminaSpazi(map.get("4")));
			if (map.get("5")!=null){
					calendario.setFinePomeriggio(Utils.eliminaSpazi(map.get("5")));
			}else{
				calendario.setStato("0");
			}
			if (map.get("6")!=null){
				calendario.setStato("0");
			}
				//validazione bean per vedere se i dati inseriti nel file sono corretti.
				
		
			listaCalendario.add(calendario);
			
		}
	}catch(Exception e){
		e.printStackTrace();
		throw e;
	}
		return listaCalendario;
	}
	
	public static ArrayList<LavoratoriBean> listaLavoratoriValidator (ArrayList<HashMap<String, String>> listaExcel, LavoratoriBean lavoratoriBean)throws Exception {
		ArrayList<LavoratoriBean> listaLavoratori = new ArrayList<>();
		try{
		
		HashMap<String, String> map = new LinkedHashMap<>();
		Iterator<HashMap<String, String>> hashIterator = listaExcel.listIterator();
		int i=0;
		while( hashIterator.hasNext()){
			i++;
			map = hashIterator.next();
			LavoratoriBean lavoratore = new LavoratoriBean();
			lavoratore.setIdPiano(lavoratoriBean.getIdPiano());
			lavoratore.setStato("1");
			lavoratore.setNomeModulo(lavoratoriBean.getNomeModulo());
			lavoratore.setMatricola(Utils.eliminaSpazi(map.get("1")));
			if(FormSecurityValidator.isNumber(Utils.eliminaSpazi(map.get("2")))){
				lavoratore.setOrePresenza(Utils.eliminaSpazi(map.get("2")));
			}else{
				lavoratore.setOrePresenza("0");
				lavoratore.setStato("0");
			}
			if (map.get("3")!=null){
				lavoratore.setEsitoTest(Utils.eliminaSpazi(map.get("3")));
			}else{
				lavoratore.setStato("0");
			}
			if (map.get("4")!=null){
				lavoratore.setStato("0");
			}
				//validazione bean per vedere se i dati inseriti nel file sono corretti.
				
		
			listaLavoratori.add(lavoratore);
			
		}
	}catch(Exception e){
		e.printStackTrace();
		throw e;
	}
		return listaLavoratori;
	}
	
	// metodo che produce la lista calendario in base al nomeModulo
	public static ArrayList<CalendarioBean> listaCalendariModuliValidator (ArrayList<HashMap<String, String>> listaExcel, int idPiano, String nomeModulo )throws Exception {
		ArrayList<CalendarioBean> listaCalendario = new ArrayList<>();
		try{
		
		HashMap<String, String> map = new LinkedHashMap<>();
		Iterator<HashMap<String, String>> hashIterator = listaExcel.listIterator();
		int i=0;
		while( hashIterator.hasNext()){
			i++;
			map = hashIterator.next();
			CalendarioBean calendario = new CalendarioBean();
			calendario.setIdPiano(idPiano);
			calendario.setStato("1");
			if(Utils.eliminaSpazi(map.get("1")).equalsIgnoreCase(nomeModulo)){
				calendario.setNomeModulo(nomeModulo);
				calendario.setDataStr(Utils.eliminaSpazi(map.get("2")));
				calendario.setData(Utils.dataDBFormatter(calendario.getDataStr().trim()));
				calendario.setInizioMattina(map.get("3").trim());
				calendario.setFineMattina(Utils.eliminaSpazi(map.get("4")));
				calendario.setInizioPomeriggio(Utils.eliminaSpazi(map.get("5")));
				if (map.get("6")!=null){
						calendario.setFinePomeriggio(Utils.eliminaSpazi(map.get("6")));
				}else{
					calendario.setFinePomeriggio("assente");
					calendario.setStato("0");
				}
				if (map.get("8")!=null){
					calendario.setStato("0");
				}
					//validazione bean per vedere se i dati inseriti nel file sono corretti.
					
			
				listaCalendario.add(calendario);
			}
			
		}
	}catch(Exception e){
		e.printStackTrace();
		throw e;
	}
		return listaCalendario;
	}
	
	
	// metodo che produce la lista di lavoratori in base al nomeModulo
	public static ArrayList<LavoratoriBean> listaLavoratoriModuliValidator (ArrayList<HashMap<String, String>> listaExcel, int idPiano, String nomeModulo)throws Exception {
		ArrayList<LavoratoriBean> listaLavoratori = new ArrayList<>();
		try{
		
		HashMap<String, String> map = new LinkedHashMap<>();
		Iterator<HashMap<String, String>> hashIterator = listaExcel.listIterator();
		int i=0;
		while( hashIterator.hasNext()){
			i++;
			map = hashIterator.next();
			LavoratoriBean lavoratore = new LavoratoriBean();
			lavoratore.setIdPiano(idPiano);
			lavoratore.setStato("1");
			lavoratore.setNomeModulo(nomeModulo);
			if(Utils.eliminaSpazi(map.get("1")).equalsIgnoreCase(nomeModulo)){
				lavoratore.setNomeModulo(nomeModulo);
				lavoratore.setMatricola(Utils.eliminaSpazi(map.get("2")));
				if(FormSecurityValidator.isOreMin(Utils.eliminaSpazi(map.get("3")))){
					lavoratore.setOrePresenza(Utils.eliminaSpazi(map.get("3")));
				}else{
					lavoratore.setOrePresenza("0");
				}
				if (map.get("4")!=null){
					lavoratore.setEsitoTest(Utils.eliminaSpazi(map.get("4")));
				}else{
					lavoratore.setEsitoTest("assente");
					lavoratore.setStato("0");
				}
				if (map.get("5")!=null){
					lavoratore.setStato("0");
				}
				//validazione bean per vedere se i dati inseriti nel file sono corretti.
				
		
				listaLavoratori.add(lavoratore);
			}
		}
	}catch(Exception e){
		e.printStackTrace();
		throw e;
	}
		return listaLavoratori;
	}
	
	public static ArrayList<RendicontazioneBean> listaRendicontazioneValidator (ArrayList<HashMap<String, String>> listaExcel,  int idPiano)throws Exception {
		ArrayList<RendicontazioneBean> listaRendicontazione = new ArrayList<>();
		try{
		
		HashMap<String, String> map = new LinkedHashMap<>();
		Iterator<HashMap<String, String>> hashIterator = listaExcel.listIterator();
		int i=0;
		while( hashIterator.hasNext()){
			i++;
			map = hashIterator.next();
            RendicontazioneBean rendicontazione = new RendicontazioneBean();
            rendicontazione.setIdPiano(idPiano);
            rendicontazione.setStato("1");
            rendicontazione.setTipologiaGiustificativo(Utils.eliminaSpazi(map.get("1")));
            rendicontazione.setCodice(Utils.eliminaSpazi(map.get("2")));
            rendicontazione.setDataGiustificativoStr(Utils.eliminaSpazi(Utils.eliminaSpazi(map.get("3"))));
            rendicontazione.setDataGiustificativo(Utils.dataDBFormatter(rendicontazione.getDataGiustificativoStr()));
            rendicontazione.setFornitoreNominativo(Utils.eliminaSpazi(map.get("4")));
            rendicontazione.setValoreComplessivo(Utils.eliminaSpazi(map.get("5")));
            rendicontazione.setContributoFBA(Utils.eliminaSpazi(map.get("6")));
            rendicontazione.setContributoPrivato(Utils.eliminaSpazi(map.get("7")));
			if (map.get("8")!=null){
				rendicontazione.setNomeAllegato(Utils.eliminaSpazi(map.get("8")));
			}else{
				rendicontazione.setStato("assente");
				rendicontazione.setStato("0");
			}
			if (map.get("9")!=null){
				rendicontazione.setStato("0");
			}
				//validazione bean per vedere se i dati inseriti nel file sono corretti.
				
		
			listaRendicontazione.add(rendicontazione);
			
		}
	}catch(Exception e){
		e.printStackTrace();
		throw e;
	}
		return listaRendicontazione;
	}

}
