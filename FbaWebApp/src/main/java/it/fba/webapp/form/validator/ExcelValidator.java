package it.fba.webapp.form.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import it.fba.webapp.beans.CalendarioBean;
import it.fba.webapp.beans.FileBean;
import it.fba.webapp.beans.LavoratoriBean;
import it.fba.webapp.beans.PianoDIformazioneBean;
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
				pianoFormazione.setNuemroProtocollo(map.get("1").trim());
				pianoFormazione.setNomeProgetto(map.get("2").trim());
				pianoFormazione.setTipoCorsoPiano(map.get("3").trim());
				pianoFormazione.setTematicaFormativa(map.get("4").trim());
				pianoFormazione.setModulo1(map.get("5").trim());
				if(map.get("6").trim().equalsIgnoreCase("fad")){
					pianoFormazione.setFadMod1("fad");
				}else{
					pianoFormazione.setFadMod1("aula");
				}
				if(FormSecurityValidator.isNumber(map.get("7").trim())){
					pianoFormazione.setDurataModulo1(map.get("7").trim());
				}else{
					pianoFormazione.setDurataModulo1("0");

				}
				pianoFormazione.setModulo2(map.get("8").trim());
				if(map.get("9").trim().equalsIgnoreCase("fad")){
					pianoFormazione.setFadMod2("fad");
				}else{
					pianoFormazione.setFadMod2("aula");
				}
				if(FormSecurityValidator.isNumber(map.get("10").trim())){
					pianoFormazione.setDurataModulo2(map.get("10").trim());
				}else{
					pianoFormazione.setDurataModulo2("0");

				}
				if (map.get("11")!=null){
					pianoFormazione.setAttuatorePIVA(map.get("11").trim());
					
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
			calendario.setNomeModulo(calendarioBean.getNomeModulo().trim());
			calendario.setDataStr(map.get("1").trim());
			calendario.setData(Utils.dataDBFormatter(calendario.getDataStr()));
			calendario.setInizioMattina(map.get("2").trim());
			calendario.setFineMattina(map.get("3").trim());
			calendario.setInizioPomeriggio(map.get("4").trim());
			if (map.get("5")!=null){
					calendario.setFinePomeriggio(map.get("5").trim());
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
			lavoratore.setMatricola(map.get("1").trim());
			if(FormSecurityValidator.isNumber(map.get("2").trim())){
				lavoratore.setOrePresenza(map.get("2").trim());
			}else{
				lavoratore.setOrePresenza("0");
				lavoratore.setStato("0");
			}
			if (map.get("3")!=null){
				lavoratore.setEsitoTest(map.get("3").trim());
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

}
