package it.fba.webapp.form.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import it.fba.webapp.beans.FileBean;
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
				pianoFormazione.setNumPartecipanti(map.get("6"));
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
				pianoFormazione.setFadMod1(map.get("17"));
				pianoFormazione.setModulo2(map.get("18"));
				pianoFormazione.setFadMod2(map.get("19"));
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

}
