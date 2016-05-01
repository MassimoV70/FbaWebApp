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
				pianoFormazione.setPianoDiFormazione(map.get("1"));
				pianoFormazione.setTipoCorsoPiano(map.get("2"));
				pianoFormazione.setTematicaFormativa(map.get("3"));
				pianoFormazione.setDataInizioAttStr(map.get("4"));
				pianoFormazione.setDataInizioAtt(Utils.dataDBFormatter(pianoFormazione.getDataInizioAttStr()));
				pianoFormazione.setDataFineAttStr(map.get("5"));
				pianoFormazione.setDataFineAtt(Utils.dataDBFormatter(pianoFormazione.getDataFineAttStr()));
				if(FormSecurityValidator.isNumber(map.get("6"))){
					pianoFormazione.setNumPartecipanti(map.get("6"));
				}else{
					pianoFormazione.setNumPartecipanti("0");
				}
				pianoFormazione.setCompImprInn(validaOpzioni(map.get("7")));
				pianoFormazione.setCompSett(validaOpzioni(map.get("8")));
				pianoFormazione.setDelocInter(validaOpzioni(map.get("9")));
				pianoFormazione.setFormObblExLeg(validaOpzioni(map.get("10")));
				pianoFormazione.setFormInIngresso(validaOpzioni(map.get("11")));
				pianoFormazione.setMantenimOccup(validaOpzioni(map.get("12")));
				pianoFormazione.setManutAggComp(validaOpzioni(map.get("13")));
				pianoFormazione.setMobEstOutRic(validaOpzioni(map.get("14")));
				pianoFormazione.setSviluppoLoc(validaOpzioni(map.get("15")));
				pianoFormazione.setModulo1(map.get("16"));
				if(map.get("17").equalsIgnoreCase("fad")){
					pianoFormazione.setFadMod1("fad");
				}else{
					pianoFormazione.setFadMod1("aula");
				}
				pianoFormazione.setModulo2(map.get("18"));
				if(map.get("19").equalsIgnoreCase("fad")){
					pianoFormazione.setFadMod2("fad");
				}else{
					pianoFormazione.setFadMod2("aula");
				}
				if (map.get("20")!=null){
					pianoFormazione.setAttuatorePIVA(map.get("20"));
					
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
			calendario.setDataStr(map.get("1"));
			calendario.setData(Utils.dataDBFormatter(calendario.getDataStr()));
			calendario.setInizioMattina(map.get("2"));
			calendario.setFineMattina(map.get("3"));
			calendario.setInizioPomeriggio(map.get("4"));
			if (map.get("5")!=null){
					calendario.setFinePomeriggio(map.get("5"));
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
			lavoratore.setMatricola(map.get("1"));
			if(FormSecurityValidator.isNumber(map.get("2"))){
				lavoratore.setOrePresenza(map.get("2"));
			}else{
				lavoratore.setOrePresenza("0");
				lavoratore.setStato("0");
			}
			if (map.get("3")!=null){
				lavoratore.setEsitoTest(map.get("3"));
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
