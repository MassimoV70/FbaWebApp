package it.fba.webapp.form.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Properties;

import it.fba.webapp.beans.CalendarioBean;
import it.fba.webapp.beans.FileBean;
import it.fba.webapp.beans.LavoratoriBean;
import it.fba.webapp.beans.PianoDIformazioneBean;
import it.fba.webapp.beans.RendicontazioneBean;
import it.fba.webapp.fileInputOutput.ImportServiceExcel;
import it.fba.webapp.utils.Utils;

public class ExcelValidator {
	
	
	
	public static ArrayList<PianoDIformazioneBean> listaPianiFormazioneValidator(ArrayList<HashMap<String, String>> listaExcel,  String username, Properties myProperties)throws Exception {
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
				
				if(!Utils.eliminaSpazi(map.get("1")).equalsIgnoreCase("")){
				      pianoFormazione.setNuemroProtocollo(Utils.eliminaSpaziTot(map.get("1")));
				}else{
					 pianoFormazione.setNuemroProtocollo(myProperties.getProperty("assente"));
				}
				
				if(!Utils.eliminaSpazi(map.get("2")).equalsIgnoreCase("")){
					pianoFormazione.setNomeProgetto(Utils.eliminaSpazi(map.get("2")));
				}else{
					pianoFormazione.setNomeProgetto(myProperties.getProperty("assente"));
				}
				
				if(!Utils.eliminaSpazi(map.get("3")).equalsIgnoreCase("")){
					pianoFormazione.setTipoCorsoPiano(Utils.eliminaSpazi(map.get("3")));
				}else{
					pianoFormazione.setTipoCorsoPiano(myProperties.getProperty("assente"));
				}
				
				if(!Utils.eliminaSpazi(map.get("4")).equalsIgnoreCase("")){
					pianoFormazione.setTematicaFormativa(Utils.eliminaSpazi(map.get("4")));
				}else{
					pianoFormazione.setTematicaFormativa(myProperties.getProperty("assente"));
					
				}
				
				if(!Utils.eliminaSpaziTot(map.get("5")).equalsIgnoreCase("")&&FormSecurityValidator.isNumber(Utils.eliminaSpaziTot(map.get("5")))){
					pianoFormazione.setNumeroPartecipanti(Utils.eliminaSpaziTot(map.get("5")));
				}else{
					pianoFormazione.setNumeroPartecipanti(myProperties.getProperty("assente"));
				}
				
				if(!Utils.eliminaSpazi(map.get("6")).equalsIgnoreCase("")){
					pianoFormazione.setModulo1(Utils.eliminaSpazi(map.get("6")));
				}else{
					pianoFormazione.setModulo1(myProperties.getProperty("assente"));
				}
				
				if(Utils.eliminaSpazi(map.get("7")).equalsIgnoreCase(myProperties.getProperty("fad.si"))){
					pianoFormazione.setFadMod1("fad");
				}else if(Utils.eliminaSpazi(map.get("7")).equalsIgnoreCase(myProperties.getProperty("fad.no"))){
					pianoFormazione.setFadMod1("aula");
				}else{
					pianoFormazione.setFadMod1(myProperties.getProperty("assente"));
				}
				
				if(FormSecurityValidator.isOreMin(Utils.eliminaSpaziTot(map.get("8")))){
					pianoFormazione.setDurataModulo1(Utils.eliminaSpaziTot(map.get("8")));
				}else{
					pianoFormazione.setDurataModulo1(myProperties.getProperty("assente"));
				}
				
				if(!Utils.eliminaSpazi(map.get("6")).equalsIgnoreCase("")){
					pianoFormazione.setModulo2(Utils.eliminaSpazi(map.get("9")));
				}else{
					pianoFormazione.setModulo2(myProperties.getProperty("assente"));
				}
				
				if(Utils.eliminaSpazi(map.get("10")).equalsIgnoreCase(myProperties.getProperty("fad.si"))){
					pianoFormazione.setFadMod2(myProperties.getProperty("fad.si"));
				}else if(Utils.eliminaSpazi(map.get("10")).equalsIgnoreCase(myProperties.getProperty("fad.no"))){
					pianoFormazione.setFadMod2(myProperties.getProperty("fad.no"));
				}else{
					pianoFormazione.setFadMod2(myProperties.getProperty("assente"));
				}
				
				if(FormSecurityValidator.isOreMin(Utils.eliminaSpaziTot(map.get("11")))){
					pianoFormazione.setDurataModulo2(Utils.eliminaSpaziTot(map.get("11")));
				}else{
					pianoFormazione.setDurataModulo2(myProperties.getProperty("assente"));
				}
				if (Utils.eliminaSpazi(map.get("12")).equalsIgnoreCase("")){
					
					pianoFormazione.setFormeAiuti(Utils.eliminaSpazi(myProperties.getProperty("assente")));
				}else{
					pianoFormazione.setFormeAiuti(Utils.eliminaSpazi(map.get("12")));
				}
			
				if(Utils.eliminaSpazi(map.get("13")).equalsIgnoreCase(myProperties.getProperty("abilitato.si"))){
					pianoFormazione.setCategSvantagg("1");
				}else if(Utils.eliminaSpazi(map.get("13")).equalsIgnoreCase(myProperties.getProperty("abilitato.no"))){
					pianoFormazione.setCategSvantagg("0");
				}else{
					pianoFormazione.setCategSvantagg(myProperties.getProperty("assente"));
					
				}
				
				if (map.get("14")!=null){
					if(!Utils.eliminaSpazi(map.get("14")).equalsIgnoreCase("")){
						pianoFormazione.setAttuatorePIVA(Utils.eliminaSpaziTot(map.get("14")));
					}else{
						pianoFormazione.setAttuatorePIVA(myProperties.getProperty("assente"));
					}
					
				}else{
					pianoFormazione.setAttuatorePIVA(myProperties.getProperty("assente"));
				}
				pianoFormazione.setEnabled("0");
				listaPiani.add(pianoFormazione);
			}
		}catch (Exception e) {
			// TODO: handle exception
			
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
			calendario.setDataStr(Utils.eliminaSpazi(Utils.eliminaSpaziTot(map.get("1"))));
			calendario.setData(Utils.dataDBFormatter(calendario.getDataStr()));
			calendario.setInizioMattina(Utils.eliminaSpaziTot(map.get("2")));
			calendario.setFineMattina(Utils.eliminaSpaziTot(map.get("3")));
			calendario.setInizioPomeriggio(Utils.eliminaSpaziTot(map.get("4")));
			if (map.get("5")!=null){
					calendario.setFinePomeriggio(Utils.eliminaSpaziTot(map.get("5")));
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
			lavoratore.setMatricola(Utils.eliminaSpaziTot(map.get("1")));
			if(FormSecurityValidator.isNumber(Utils.eliminaSpaziTot(map.get("2")))){
				lavoratore.setOrePresenza(Utils.eliminaSpaziTot(map.get("2")));
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
		
		throw e;
	}
		return listaLavoratori;
	}
	
	// metodo che produce la lista calendario in base al nomeModulo
	public static ArrayList<CalendarioBean> listaCalendariModuliValidator (ArrayList<HashMap<String, String>> listaExcel, int idPiano, String nomeModulo,  Properties myProperties )throws Exception {
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
				if(map.get("2")!=null&&!Utils.eliminaSpaziTot(map.get("2")).equals("")){
					calendario.setDataStr(Utils.eliminaSpaziTot(map.get("2")));
					calendario.setData(Utils.dataDBFormatter(calendario.getDataStr()));
				}else{
					calendario.setDataStr(myProperties.getProperty("data.errore"));
					calendario.setData(Utils.dataDBFormatter(calendario.getDataStr()));
					calendario.setStato("0");
					
				}
				
				if(map.get("3")!=null&&!Utils.eliminaSpaziTot(map.get("3")).equals("")){
					if (Utils.eliminaSpaziTot(map.get("3")).equalsIgnoreCase(myProperties.getProperty("assente"))){
						calendario.setInizioMattina(myProperties.getProperty("assente"));
					}
					else if (FormSecurityValidator.isTime(Utils.eliminaSpaziTot(map.get("3")))){
						calendario.setInizioMattina(Utils.eliminaSpaziTot(map.get("3")));
					}
					else{
						calendario.setInizioMattina(myProperties.getProperty("assente"));
					}
				}else{
					calendario.setInizioMattina(myProperties.getProperty("assente"));
					calendario.setStato("0");
				}
				if(map.get("4")!=null&&!Utils.eliminaSpaziTot(map.get("4")).equals("")){
					if (Utils.eliminaSpaziTot(map.get("4")).equalsIgnoreCase(myProperties.getProperty("assente"))){
						calendario.setFineMattina(myProperties.getProperty("assente"));
					}
					else if (FormSecurityValidator.isTime(Utils.eliminaSpaziTot(map.get("4")))){
						calendario.setFineMattina(Utils.eliminaSpaziTot(map.get("4")));
					}
					else{
						calendario.setFineMattina(myProperties.getProperty("assente"));
					}
				}else{
					calendario.setFineMattina(myProperties.getProperty("assente"));
					calendario.setStato("0");
				}
				if(map.get("5")!=null&&!Utils.eliminaSpaziTot(map.get("5")).equals("")){
					if (Utils.eliminaSpaziTot(map.get("5")).equalsIgnoreCase(myProperties.getProperty("assente"))){
						calendario.setInizioPomeriggio(myProperties.getProperty("assente"));
					}
					else if (FormSecurityValidator.isTime(Utils.eliminaSpaziTot(map.get("5")))){
						calendario.setInizioPomeriggio(Utils.eliminaSpaziTot(map.get("5")));
					}
					else{
						calendario.setInizioPomeriggio(myProperties.getProperty("assente"));
					}
				}else{
					calendario.setInizioPomeriggio(myProperties.getProperty("assente"));
					calendario.setStato("0");
				}
				if(map.get("6")!=null&&!Utils.eliminaSpaziTot(map.get("6")).equals("")){
					if (Utils.eliminaSpaziTot(map.get("6")).equalsIgnoreCase(myProperties.getProperty("assente"))){
						calendario.setFinePomeriggio(myProperties.getProperty("assente"));
					}
					else if (FormSecurityValidator.isTime(Utils.eliminaSpaziTot(map.get("6")))){
						calendario.setFinePomeriggio(Utils.eliminaSpaziTot(map.get("6")));
					}
					else{
						calendario.setFinePomeriggio(myProperties.getProperty("assente"));
					}
				}else{
					calendario.setFinePomeriggio(myProperties.getProperty("assente"));
					calendario.setStato("0");
				}
				
				if (map.get("7")!=null){
					calendario.setStato("0");
				}
					
				//validazione bean per vedere se i dati inseriti nel file sono corretti.
				if (calendario.getInizioMattina().trim().equalsIgnoreCase(myProperties.getProperty("assente"))&&
						!calendario.getFineMattina().trim().equalsIgnoreCase(myProperties.getProperty("assente"))||
						!calendario.getInizioMattina().trim().equalsIgnoreCase(myProperties.getProperty("assente"))&&
						 calendario.getFineMattina().trim().equalsIgnoreCase(myProperties.getProperty("assente"))){
						
						calendario.setStato("0");
							 
					}else{
						 if (!calendario.getInizioMattina().equalsIgnoreCase(myProperties.getProperty("assente"))){
							 if (!FormSecurityValidator.isTime(calendario.getInizioMattina())){
								 calendario.setStato("0");
							 }
							 
						 }
						 if (!calendario.getFineMattina().equalsIgnoreCase(myProperties.getProperty("assente"))){
							 if (!FormSecurityValidator.isTime(calendario.getFineMattina())){
								 calendario.setStato("0");
							 }
							 
						 }
					}
					
					if (calendario.getInizioPomeriggio().trim().equalsIgnoreCase(myProperties.getProperty("assente"))&&
							!calendario.getFinePomeriggio().trim().equalsIgnoreCase(myProperties.getProperty("assente"))||
							!calendario.getInizioPomeriggio().trim().equalsIgnoreCase(myProperties.getProperty("assente"))&&
							calendario.getFinePomeriggio().trim().equalsIgnoreCase(myProperties.getProperty("assente"))){
							
							calendario.setStato("0");
								 
					}else{
					
							 if (!calendario.getInizioPomeriggio().equalsIgnoreCase(myProperties.getProperty("assente"))){
								 if (!FormSecurityValidator.isTime(calendario.getInizioPomeriggio())){
									 calendario.setStato("0");
								 }
								 
							 }
							 if (!calendario.getFinePomeriggio().equalsIgnoreCase(myProperties.getProperty("assente"))){
								 if (!FormSecurityValidator.isTime(calendario.getFinePomeriggio())){
									 calendario.setStato("0");
								 }
								 
							 }
					}
					
			
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
	public static ArrayList<LavoratoriBean> listaLavoratoriModuliValidator (ArrayList<HashMap<String, String>> listaExcel, int idPiano, String nomeModulo, Properties myProperties)throws Exception {
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
			if(map.get("1")!=null&&Utils.eliminaSpazi(map.get("1")).equalsIgnoreCase(nomeModulo)){
				lavoratore.setNomeModulo(nomeModulo);
				if(map.get("2")!=null&&!Utils.eliminaSpaziTot(map.get("2")).isEmpty()){
					lavoratore.setMatricola(Utils.eliminaSpaziTot(map.get("2")));
				}else{
					lavoratore.setMatricola(myProperties.getProperty("assente"));
					lavoratore.setStato("0");
				}
				if(map.get("3")!=null&&!Utils.eliminaSpaziTot(map.get("3")).isEmpty()
					&&FormSecurityValidator.isOreMin(Utils.eliminaSpaziTot(map.get("3")))){
					lavoratore.setOrePresenza(Utils.eliminaSpaziTot(map.get("3")));
				}else{
					lavoratore.setOrePresenza(myProperties.getProperty("assente"));
					lavoratore.setStato("0");
				}
				if (map.get("4")!=null&&!Utils.eliminaSpazi(map.get("4")).isEmpty()){
					lavoratore.setEsitoTest(Utils.eliminaSpazi(map.get("4")));
				}else{
					lavoratore.setEsitoTest(myProperties.getProperty("assente"));
				}
				if (map.get("5")!=null&&!Utils.eliminaSpazi(map.get("5")).isEmpty()){
					lavoratore.setNomeAllegato(Utils.eliminaSpazi(map.get("5")));
				}else{
					lavoratore.setNomeAllegato(myProperties.getProperty("assente"));
					lavoratore.setStato("0");
				}
				if (map.get("6")!=null){
					lavoratore.setStato("0");
				}
				//validazione bean per vedere se i dati inseriti nel file sono corretti.
				
		
				listaLavoratori.add(lavoratore);
			}
		}
	}catch(Exception e){
		
		throw e;
	}
		return listaLavoratori;
	}
	
	public static ArrayList<RendicontazioneBean> listaRendicontazioneValidator (ArrayList<HashMap<String, String>> listaExcel,  int idPiano,  Properties myProperties)throws Exception {
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
            if (map.get("1")!=null&&!Utils.eliminaSpazi(map.get("1")).equalsIgnoreCase("")){
            	rendicontazione.setTipologiaGiustificativo(Utils.eliminaSpazi(map.get("1")));
            }else{
            	rendicontazione.setTipologiaGiustificativo(myProperties.getProperty("assente"));
            	rendicontazione.setStato("0");
            }
            
            if (map.get("2")!=null&&!Utils.eliminaSpaziTot(map.get("2")).equalsIgnoreCase("")){
            	rendicontazione.setCodice(Utils.eliminaSpaziTot(map.get("2")));
            }else{
            	rendicontazione.setCodice(myProperties.getProperty("assente"));
            	rendicontazione.setStato("0");
            }
            
            if (map.get("3")!=null&&!Utils.eliminaSpaziTot(map.get("3")).equalsIgnoreCase("")){
            	rendicontazione.setDataGiustificativoStr(Utils.eliminaSpaziTot(map.get("3")));
            	rendicontazione.setDataGiustificativo(Utils.dataDBFormatter(rendicontazione.getDataGiustificativoStr()));
            }else{
            	rendicontazione.setDataGiustificativoStr(myProperties.getProperty("data.errore"));
            	rendicontazione.setDataGiustificativo(Utils.dataDBFormatter(rendicontazione.getDataGiustificativoStr()));
            	rendicontazione.setStato("0");
            }
            
            if (map.get("4")!=null&&!Utils.eliminaSpazi(map.get("4")).equalsIgnoreCase("")){
            	 rendicontazione.setFornitoreNominativo(Utils.eliminaSpazi(map.get("4")));
            }else{
            	rendicontazione.setFornitoreNominativo(myProperties.getProperty("assente"));
            	rendicontazione.setStato("0");
            }
            
            if ((map.get("5")!=null&&!Utils.eliminaSpaziTot(map.get("5")).equalsIgnoreCase(""))
            		&&FormSecurityValidator.isCifra(map.get("5"))){
           	 	rendicontazione.setValoreComplessivo(Utils.eliminaSpaziTot(map.get("5")));
            }else{
            	rendicontazione.setValoreComplessivo(myProperties.getProperty("assente"));
            	rendicontazione.setStato("0");
            }
            
            if ((map.get("6")!=null&&!Utils.eliminaSpaziTot(map.get("6")).equalsIgnoreCase(""))
            		&&FormSecurityValidator.isCifra(map.get("6"))){
           	 	rendicontazione.setContributoFBA(Utils.eliminaSpaziTot(map.get("6")));
            }else{
            	rendicontazione.setContributoFBA(myProperties.getProperty("assente"));
            	rendicontazione.setStato("0");
            }
            
            if (map.get("7")!=null&&!Utils.eliminaSpaziTot(map.get("7")).equalsIgnoreCase("")
            		&&FormSecurityValidator.isCifra(map.get("7"))){
           	 	rendicontazione.setContributoPrivato(Utils.eliminaSpaziTot(map.get("7")));
            }else{
            	rendicontazione.setContributoPrivato(myProperties.getProperty("assente"));
            	rendicontazione.setStato("0");
            }
            
            if (map.get("8")!=null&&!Utils.eliminaSpazi(map.get("8")).equalsIgnoreCase("")){
           	 	rendicontazione.setNomeAllegato(Utils.eliminaSpazi(map.get("8")));
            }else{
            	rendicontazione.setNomeAllegato(myProperties.getProperty("assente"));
            	rendicontazione.setStato("0");
            }
            
          
			if (map.get("9")!=null){
				rendicontazione.setStato("0");
			}
				//validazione bean per vedere se i dati inseriti nel file sono corretti.
				
		
			listaRendicontazione.add(rendicontazione);
			
		}
	}catch(Exception e){
		
		throw e;
	}
		return listaRendicontazione;
	}

}
