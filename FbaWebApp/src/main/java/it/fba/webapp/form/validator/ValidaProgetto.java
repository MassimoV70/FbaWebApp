package it.fba.webapp.form.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.hibernate.jpa.boot.internal.PersistenceXmlParser.ErrorHandlerImpl;

import it.fba.webapp.beans.AttuatoreBean;
import it.fba.webapp.beans.CalendarioBean;
import it.fba.webapp.beans.ErroreProgettoBean;
import it.fba.webapp.beans.LavoratoriBean;
import it.fba.webapp.beans.PianoDIformazioneBean;
import it.fba.webapp.beans.RendicontazioneBean;
import it.fba.webapp.utils.Utils;

public class ValidaProgetto {
	
	
	public static ArrayList<ErroreProgettoBean> validaProgettoTop (PianoDIformazioneBean pianoFormazione,Properties myProperties)throws Exception{
		ArrayList<ErroreProgettoBean> listaErroriProgetto = new ArrayList<ErroreProgettoBean>();
		
		
		// Numero protocollo
		if(pianoFormazione.getNuemroProtocollo().equalsIgnoreCase(myProperties.getProperty("assente"))
			||(Utils.eliminaSpazi(pianoFormazione.getNuemroProtocollo()).isEmpty())){
			
			ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Numero protocollo", myProperties.getProperty("assente"));
			listaErroriProgetto.add(erroreProgetto);
		   
		}
		
		// Nome progetto
		if((Utils.eliminaSpazi(pianoFormazione.getNomeProgetto()).isEmpty())||
				pianoFormazione.getNomeProgetto().equalsIgnoreCase(myProperties.getProperty("assente"))){
			
			ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Nome progetto", myProperties.getProperty("assente"));
			listaErroriProgetto.add(erroreProgetto);
			
		}
		
		// tipo corso
		if((Utils.eliminaSpazi(pianoFormazione.getTipoCorsoPiano()).isEmpty())||
				pianoFormazione.getTipoCorsoPiano().equalsIgnoreCase(myProperties.getProperty("assente"))){
			
			ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Tipo corso piano", myProperties.getProperty("assente"));
			listaErroriProgetto.add(erroreProgetto);
		}
		
		
		// Tematica formativa
		if((Utils.eliminaSpazi(pianoFormazione.getTematicaFormativa()).isEmpty())||
				pianoFormazione.getTematicaFormativa().equalsIgnoreCase(myProperties.getProperty("assente"))){
			
			ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Tematica formativa", myProperties.getProperty("assente"));
			listaErroriProgetto.add(erroreProgetto);
		}
			
		
		// Numero partecipanti
		if((Utils.eliminaSpazi(pianoFormazione.getNumeroPartecipanti()).isEmpty())||
				pianoFormazione.getNumeroPartecipanti().equalsIgnoreCase(myProperties.getProperty("assente"))){
			 
			ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Numero partecipanti", myProperties.getProperty("assente"));
			listaErroriProgetto.add(erroreProgetto);
		}
		
		
		if(((Utils.eliminaSpazi(pianoFormazione.getModulo1()).isEmpty())||pianoFormazione.getModulo1().equalsIgnoreCase(myProperties.getProperty("assente")))
				&&((Utils.eliminaSpazi(pianoFormazione.getModulo2()).isEmpty())||pianoFormazione.getModulo2().equalsIgnoreCase(myProperties.getProperty("assente")))){
			
			ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Moduli", myProperties.getProperty("moduli.assenti"));
			listaErroriProgetto.add(erroreProgetto);    
			    
			
		}else{
		
			// Modulo 1
			if(((Utils.eliminaSpazi(pianoFormazione.getModulo1()).isEmpty())||pianoFormazione.getModulo1().equalsIgnoreCase(myProperties.getProperty("assente")))
				&&(!((Utils.eliminaSpazi(pianoFormazione.getFadMod1()).isEmpty())||pianoFormazione.getFadMod1().equalsIgnoreCase(myProperties.getProperty("assente")))
				||!((Utils.eliminaSpazi(pianoFormazione.getDurataModulo1()).isEmpty())||pianoFormazione.getDurataModulo1().equalsIgnoreCase(myProperties.getProperty("assente"))))
				){
	
				ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Nome modulo 1", myProperties.getProperty("assente"));
				listaErroriProgetto.add(erroreProgetto);    
			}
			
			if(((Utils.eliminaSpazi(pianoFormazione.getFadMod1()).isEmpty())||pianoFormazione.getFadMod1().equalsIgnoreCase(myProperties.getProperty("assente")))
				&&(!((Utils.eliminaSpazi(pianoFormazione.getDurataModulo1()).isEmpty())||pianoFormazione.getDurataModulo1().equalsIgnoreCase(myProperties.getProperty("assente")))
				||!((Utils.eliminaSpazi(pianoFormazione.getModulo1()).isEmpty())||pianoFormazione.getModulo1().equalsIgnoreCase(myProperties.getProperty("assente"))))
				){
				
				ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Modulo 1 modalità formativa", myProperties.getProperty("assente"));
				listaErroriProgetto.add(erroreProgetto);
			}
			
			if((!((Utils.eliminaSpazi(pianoFormazione.getModulo1()).isEmpty())||pianoFormazione.getModulo1().equalsIgnoreCase(myProperties.getProperty("assente")))
				||!((Utils.eliminaSpazi(pianoFormazione.getFadMod1()).isEmpty())||pianoFormazione.getFadMod1().equalsIgnoreCase(myProperties.getProperty("assente"))))
				&&((Utils.eliminaSpazi(pianoFormazione.getDurataModulo1()).isEmpty())||pianoFormazione.getDurataModulo1().equalsIgnoreCase(myProperties.getProperty("assente")))
				){
				
				ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Modulo 1 durata modulo", myProperties.getProperty("assente"));
				listaErroriProgetto.add(erroreProgetto);
			}
			
			
			// Modulo 2
			if(((Utils.eliminaSpazi(pianoFormazione.getModulo2()).isEmpty())||pianoFormazione.getModulo2().equalsIgnoreCase(myProperties.getProperty("assente")))
					&&(!((Utils.eliminaSpazi(pianoFormazione.getFadMod2()).isEmpty())||pianoFormazione.getFadMod2().equalsIgnoreCase(myProperties.getProperty("assente")))
					||!((Utils.eliminaSpazi(pianoFormazione.getDurataModulo2()).isEmpty())||pianoFormazione.getDurataModulo2().equalsIgnoreCase(myProperties.getProperty("assente"))))
					){
	
				ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Nome modulo 2", myProperties.getProperty("assente"));
				listaErroriProgetto.add(erroreProgetto);    
			}
			
			if(((Utils.eliminaSpazi(pianoFormazione.getFadMod2()).isEmpty())||pianoFormazione.getFadMod2().equalsIgnoreCase(myProperties.getProperty("assente")))
					&&(!((Utils.eliminaSpazi(pianoFormazione.getDurataModulo2()).isEmpty())||pianoFormazione.getDurataModulo2().equalsIgnoreCase(myProperties.getProperty("assente")))
					||!((Utils.eliminaSpazi(pianoFormazione.getModulo2()).isEmpty())||pianoFormazione.getModulo2().equalsIgnoreCase(myProperties.getProperty("assente"))))
					){
				
				ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Modulo 2 modalità formativa", myProperties.getProperty("assente"));
				listaErroriProgetto.add(erroreProgetto);
			}
			
			if((!((Utils.eliminaSpazi(pianoFormazione.getModulo2()).isEmpty())||pianoFormazione.getModulo2().equalsIgnoreCase(myProperties.getProperty("assente")))
					||!((Utils.eliminaSpazi(pianoFormazione.getFadMod2()).isEmpty())||pianoFormazione.getFadMod2().equalsIgnoreCase(myProperties.getProperty("assente"))))
					&&((Utils.eliminaSpazi(pianoFormazione.getDurataModulo2()).isEmpty())||pianoFormazione.getDurataModulo2().equalsIgnoreCase(myProperties.getProperty("assente")))
					){
				
				ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Modulo 2 durata modulo", myProperties.getProperty("assente"));
				listaErroriProgetto.add(erroreProgetto);
			}
		}
		
		// Forme aiuti
		if((Utils.eliminaSpazi(pianoFormazione.getFormeAiuti()).isEmpty())||
				pianoFormazione.getFormeAiuti().equalsIgnoreCase(myProperties.getProperty("assente"))){
			
			ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Forme aiuti", myProperties.getProperty("assente"));
			listaErroriProgetto.add(erroreProgetto);
		}
	
		
		// Categorie svantaggiate
		if((Utils.eliminaSpazi(pianoFormazione.getCategSvantagg()).isEmpty())||
				pianoFormazione.getCategSvantagg().equalsIgnoreCase(myProperties.getProperty("assente"))){
			
			ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Categorie svantaggiate", myProperties.getProperty("assente"));
			listaErroriProgetto.add(erroreProgetto);
		}
		
		if ((Utils.eliminaSpazi(pianoFormazione.getAttuatorePIVA()).isEmpty())||
				pianoFormazione.getAttuatorePIVA().equalsIgnoreCase(myProperties.getProperty("assente"))){
			
			ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"PIVA attuatore", myProperties.getProperty("assente"));
			listaErroriProgetto.add(erroreProgetto);
		}
		
		return listaErroriProgetto;
		
	}
	
	public static ArrayList<ErroreProgettoBean> validaFileAttuatore (PianoDIformazioneBean pianoFormazione, AttuatoreBean attuatoreBean, 
																	 ArrayList<ErroreProgettoBean> listaErroriProgetto, Properties myProperties)throws Exception{
		
		if(attuatoreBean!=null){
			ArrayList<String> listaNomiFileCaricati = new ArrayList<>();
			ArrayList<String> listaNomiFileAttuatoreProgetto = new ArrayList<>();
			boolean trovato=false;
			if(attuatoreBean.getNomeAllegato1()!=null&&!attuatoreBean.getNomeAllegato1().isEmpty()){
				listaNomiFileCaricati.add(attuatoreBean.getNomeAllegato1());
			}
			if(attuatoreBean.getNomeAllegato2()!=null&&!attuatoreBean.getNomeAllegato2().isEmpty()){
				listaNomiFileCaricati.add(attuatoreBean.getNomeAllegato2());
			}
			if(attuatoreBean.getNomeAllegato3()!=null&&!attuatoreBean.getNomeAllegato3().isEmpty()){
				listaNomiFileCaricati.add(attuatoreBean.getNomeAllegato3());
			}
			if(attuatoreBean.getNomeAllegato4()!=null&&!attuatoreBean.getNomeAllegato4().isEmpty()){
				listaNomiFileCaricati.add(attuatoreBean.getNomeAllegato4());
			}
			
			if(listaNomiFileCaricati!=null&&!listaNomiFileCaricati.isEmpty()){
				
				if(pianoFormazione.getNomeAllegato1()!=null&&!pianoFormazione.getNomeAllegato1().isEmpty()){
					listaNomiFileAttuatoreProgetto.add(pianoFormazione.getNomeAllegato1());
				}
				if(pianoFormazione.getNomeAllegato2()!=null&&!pianoFormazione.getNomeAllegato2().isEmpty()){
					listaNomiFileAttuatoreProgetto.add(pianoFormazione.getNomeAllegato2());
				}
				if(pianoFormazione.getNomeAllegato3()!=null&&!pianoFormazione.getNomeAllegato3().isEmpty()){
					listaNomiFileAttuatoreProgetto.add(pianoFormazione.getNomeAllegato3());
				}
				if(pianoFormazione.getNomeAllegato4()!=null&&!pianoFormazione.getNomeAllegato4().isEmpty()){
					listaNomiFileAttuatoreProgetto.add(pianoFormazione.getNomeAllegato4());
				}
				
				for (String nomeFileAttProg : listaNomiFileAttuatoreProgetto){
					trovato=false;
					int i= 0;
					while (!trovato&&!(i>=listaNomiFileCaricati.size())){
						if (nomeFileAttProg.equals(listaNomiFileCaricati.get(i))){
							trovato=true;
						}
					}
					
					if (!trovato){
						ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"File attuatore "+nomeFileAttProg, myProperties.getProperty("assente"));
						listaErroriProgetto.add(erroreProgetto);
					}
					i++;
				}
			}else{
				ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"File attuatore ", myProperties.getProperty("file.assenti"));
				listaErroriProgetto.add(erroreProgetto);
			}
			
		}else{
			ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"File attuatore ", myProperties.getProperty("file.assenti"));
			listaErroriProgetto.add(erroreProgetto);
		}
		
		return listaErroriProgetto;
		
		
	}
	
	public static ArrayList<ErroreProgettoBean> validaCalendari (ArrayList<CalendarioBean> listacalendari,PianoDIformazioneBean pianoFormazione, ArrayList<ErroreProgettoBean> listaErroriProgetto,
			Properties myProperties)throws Exception{
		
		HashMap<String,Boolean> nomiModuliMap = new HashMap<>();
		if (pianoFormazione.getModulo1()!=null&&!pianoFormazione.getModulo1().isEmpty()){
			if(pianoFormazione.getFadMod1()!=null&&pianoFormazione.getFadMod1().equalsIgnoreCase(myProperties.getProperty("fad.no"))){
				nomiModuliMap.put(pianoFormazione.getModulo1(), false);
			}else{
				nomiModuliMap.put(pianoFormazione.getModulo1(), true);
			}
		}
		if (pianoFormazione.getModulo2()!=null&&!pianoFormazione.getModulo2().isEmpty()){
			if(pianoFormazione.getFadMod2()!=null&&pianoFormazione.getFadMod2().equalsIgnoreCase(myProperties.getProperty("fad.no"))){
				nomiModuliMap.put(pianoFormazione.getModulo2(), false);
			}else{
				nomiModuliMap.put(pianoFormazione.getModulo2(), true);
			}
		}
		
		if(listacalendari.size()<nomiModuliMap.size()){
			ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Calendari ", myProperties.getProperty("numero.calendari.err"));
			listaErroriProgetto.add(erroreProgetto);
		}
		if(nomiModuliMap.size()!=0){
			if (listacalendari!=null&&!listacalendari.isEmpty()){
				int i=1;
				for(CalendarioBean calendario : listacalendari){
					
					if(calendario.getNomeModulo()==null||(Utils.eliminaSpazi(calendario.getNomeModulo()).equals(""))){
						ErroreProgettoBean erroreProgetto = creaErrore(calendario.getIdPiano(),"Calendario "+i+ " nome modulo ", myProperties.getProperty("assente"));
					
						listaErroriProgetto.add(erroreProgetto);
					}else{
						if(nomiModuliMap.containsKey(calendario.getNomeModulo())){
							nomiModuliMap.put(calendario.getNomeModulo(), true);
						}
					}
					if(!(calendario.getData()!=null&&!Utils.formattaData(calendario.getData()).equals(myProperties.getProperty("data.errore")))){
						ErroreProgettoBean erroreProgetto = creaErrore(calendario.getIdPiano(),"Calendario "+i+ " data giorno ", myProperties.getProperty("assente"));
						
						listaErroriProgetto.add(erroreProgetto);
					}
					
					
					if (calendario.getInizioMattina().trim().equalsIgnoreCase(myProperties.getProperty("assente"))&&
							!calendario.getFineMattina().trim().equalsIgnoreCase(myProperties.getProperty("assente"))||
							!calendario.getInizioMattina().trim().equalsIgnoreCase(myProperties.getProperty("assente"))&&
							 calendario.getFineMattina().trim().equalsIgnoreCase(myProperties.getProperty("assente"))){
						
							ErroreProgettoBean erroreProgetto = creaErrore(calendario.getIdPiano(),"Calendario "+i+ " intervallo tempo mattina ", myProperties.getProperty("Not.interval"));
							
							listaErroriProgetto.add(erroreProgetto);
								 
						}else{
							 if (!calendario.getInizioMattina().equalsIgnoreCase(myProperties.getProperty("assente"))){
								 if (!FormSecurityValidator.isTime(calendario.getInizioMattina())){
									 ErroreProgettoBean erroreProgetto = creaErrore(calendario.getIdPiano(),"Calendario "+i+ " inizio mattina ", myProperties.getProperty("Not.time"));
										
										listaErroriProgetto.add(erroreProgetto);
								 }
								 
							 }
							 if (!calendario.getFineMattina().equalsIgnoreCase(myProperties.getProperty("assente"))){
								 if (!FormSecurityValidator.isTime(calendario.getFineMattina())){
									 ErroreProgettoBean erroreProgetto = creaErrore(calendario.getIdPiano(),"Calendario "+i+ " fine mattina ", myProperties.getProperty("Not.time"));
										
										listaErroriProgetto.add(erroreProgetto);
								 }
								 
							 }
						}
						
						if (calendario.getInizioPomeriggio().trim().equalsIgnoreCase(myProperties.getProperty("assente"))&&
								!calendario.getFinePomeriggio().trim().equalsIgnoreCase(myProperties.getProperty("assente"))||
								!calendario.getInizioPomeriggio().trim().equalsIgnoreCase(myProperties.getProperty("assente"))&&
								calendario.getFinePomeriggio().trim().equalsIgnoreCase(myProperties.getProperty("assente"))){
								
								ErroreProgettoBean erroreProgetto = creaErrore(calendario.getIdPiano(),"Calendario "+i+ " intervallo tempo pomeriggio ", myProperties.getProperty("Not.interval"));
								
								listaErroriProgetto.add(erroreProgetto);
									 
						}else{
						
								 if (!calendario.getInizioPomeriggio().equalsIgnoreCase(myProperties.getProperty("assente"))){
									 if (!FormSecurityValidator.isTime(calendario.getInizioPomeriggio())){
										 ErroreProgettoBean erroreProgetto = creaErrore(calendario.getIdPiano(),"Calendario "+i+ " inizio pomeriggio ", myProperties.getProperty("Not.time"));
										
										 listaErroriProgetto.add(erroreProgetto);
									 }
									 
								 }
								 if (!calendario.getFinePomeriggio().equalsIgnoreCase(myProperties.getProperty("assente"))){
									 if (!FormSecurityValidator.isTime(calendario.getFinePomeriggio())){
										 ErroreProgettoBean erroreProgetto = creaErrore(calendario.getIdPiano(),"Calendario "+i+ " fine pomeriggio ", myProperties.getProperty("Not.time"));
										
										 listaErroriProgetto.add(erroreProgetto);
									 }
									 
								 }
						}
						
						if(calendario.getStato()!=null&&calendario.getStato().equalsIgnoreCase(myProperties.getProperty("enabled.no"))){
							ErroreProgettoBean erroreProgetto = creaErrore(calendario.getIdPiano(),"Calendario "+i, myProperties.getProperty("errore.lettura.excel"));
							
							listaErroriProgetto.add(erroreProgetto);
						}
					
				}
				
				
				
			}else{
				ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Calendari ", myProperties.getProperty("assenti"));
				listaErroriProgetto.add(erroreProgetto);
			}
		}
		return listaErroriProgetto;
	}
	
	
	public static ArrayList<ErroreProgettoBean> validaLavoratori (ArrayList<LavoratoriBean> listaLavoratori,PianoDIformazioneBean pianoFormazione, ArrayList<ErroreProgettoBean> listaErroriProgetto,
			Properties myProperties)throws Exception{
		
		
		HashMap<String,Boolean> nomiModuliMap = new HashMap<>();
		if (pianoFormazione.getModulo1()!=null&&!pianoFormazione.getModulo1().isEmpty()){
			nomiModuliMap.put(pianoFormazione.getModulo1(), false);
		}
		if (pianoFormazione.getModulo2()!=null&&!pianoFormazione.getModulo2().isEmpty()){
			nomiModuliMap.put(pianoFormazione.getModulo2(), false);
		}
		
		if(nomiModuliMap.size()!=0){
			if (listaLavoratori!=null&&!listaLavoratori.isEmpty()){
				int i=1;
				for(LavoratoriBean lavoratore : listaLavoratori){
					
					
					if(!(lavoratore.getNomeModulo()!=null&&!lavoratore.getNomeModulo().isEmpty()&&!lavoratore.getNomeModulo().equals("assente"))){
						lavoratore.setNomeModulo(myProperties.getProperty("assente"));
						
						ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Lavoratori modulo "+lavoratore.getNomeModulo()+" nome modulo ", myProperties.getProperty("assente"));
						listaErroriProgetto.add(erroreProgetto);
					}else{
						if(nomiModuliMap.containsKey(lavoratore.getNomeModulo())){
							nomiModuliMap.put(lavoratore.getNomeModulo(), true);
						}
						
					}
					if(!(lavoratore.getMatricola()!=null&&!lavoratore.getMatricola().isEmpty()&&!lavoratore.getMatricola().equals("assente"))){
						lavoratore.setMatricola(myProperties.getProperty("assente"));
						
						ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Lavoratori modulo "+lavoratore.getNomeModulo()+" matricola ", myProperties.getProperty("assente"));
						listaErroriProgetto.add(erroreProgetto);
					}
					
					if(!(lavoratore.getOrePresenza()!=null&&!lavoratore.getOrePresenza().isEmpty()&&!lavoratore.getOrePresenza().equals("assente"))){
						lavoratore.setOrePresenza(myProperties.getProperty("assente"));
						
						ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Lavoratori modulo "+lavoratore.getNomeModulo()+" ore presenza ", myProperties.getProperty("assente"));
						listaErroriProgetto.add(erroreProgetto);
					}else{
						if(!FormSecurityValidator.isOreMin(lavoratore.getOrePresenza())){
							ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Lavoratori modulo "+lavoratore.getNomeModulo()+" ore presenza ", myProperties.getProperty("Not.time"));
							listaErroriProgetto.add(erroreProgetto);
						}
					}
					
					if(!(lavoratore.getEsitoTest()!=null&&!lavoratore.getEsitoTest().isEmpty()&&!lavoratore.getEsitoTest().equals("assente"))){
						lavoratore.setEsitoTest(myProperties.getProperty("assente"));
						
						ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Lavoratori modulo "+lavoratore.getNomeModulo()+" esito test", myProperties.getProperty("assente"));
						listaErroriProgetto.add(erroreProgetto);
					}
					
					if(!(lavoratore.getNomeAllegato()!=null&&!lavoratore.getNomeAllegato().isEmpty()&&!lavoratore.getNomeAllegato().equals("assente"))){
						lavoratore.setNomeAllegato(myProperties.getProperty("assente"));
						
						ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Lavoratori modulo "+lavoratore.getNomeModulo()+" nome allegato ", myProperties.getProperty("assente"));
						listaErroriProgetto.add(erroreProgetto);
					}
					
					if(lavoratore.getStato()!=null&&lavoratore.getStato().equals(myProperties.getProperty("enabled.no"))){
						ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Lavoratori modulo "+lavoratore.getNomeModulo(), myProperties.getProperty("errore.lettura.excel"));
						listaErroriProgetto.add(erroreProgetto);
					}
					
					i++;
				}
				
				
			}else{
				ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Lavoratori ", myProperties.getProperty("assenti"));
				listaErroriProgetto.add(erroreProgetto);
				
			}
			
			
			
			
		}
		
		return listaErroriProgetto;
		
	}
	
	public static ArrayList<ErroreProgettoBean> validaFileLavoratori (int idPiano, ArrayList<LavoratoriBean> listaLavoratori, ArrayList<String> listaNomiFileLavoratori, 
		 ArrayList<ErroreProgettoBean> listaErroriProgetto, Properties myProperties)throws Exception{
		
		 boolean trovato = false;
         if(listaLavoratori!=null&&!listaLavoratori.isEmpty()){
        	 	if(listaNomiFileLavoratori!=null&&!listaNomiFileLavoratori.isEmpty()){
				
					for (LavoratoriBean lavoratore : listaLavoratori){
						int i= 0;
						while (!trovato&&!(i>=listaNomiFileLavoratori.size())){
							if (!lavoratore.getNomeAllegato().isEmpty()&&!lavoratore.getNomeAllegato().equalsIgnoreCase( myProperties.getProperty("assente"))&&lavoratore.getNomeAllegato().equals(listaNomiFileLavoratori.get(i))){
								trovato=true;
							}
							i++;
						}
						
						if (!trovato){
							ErroreProgettoBean erroreProgetto = creaErrore(idPiano,"Il file "+lavoratore.getNomeAllegato()+" del lavoratore nome modulo "+lavoratore.getNomeModulo()+" matricola "+lavoratore.getMatricola(), myProperties.getProperty("assente"));
							listaErroriProgetto.add(erroreProgetto);
						}
						
						trovato=false;
					}
				}else{
					ErroreProgettoBean erroreProgetto = creaErrore(idPiano,"File lavoratori ", myProperties.getProperty("file.assenti"));
					listaErroriProgetto.add(erroreProgetto);
				}
			
			}
         
			
		 return listaErroriProgetto;


	}
	
	
	public static ArrayList<ErroreProgettoBean> validaRendicontazione (ArrayList<RendicontazioneBean> listaRendicontazione, PianoDIformazioneBean pianoFormazione, ArrayList<ErroreProgettoBean> listaErroriProgetto,
			Properties myProperties)throws Exception{
		
		
		if(listaRendicontazione!=null&&!listaRendicontazione.isEmpty()){
			for(RendicontazioneBean rendicontazione :listaRendicontazione){
				
				
				//codice
				if(!(rendicontazione.getCodice()!=null&&!rendicontazione.getCodice().isEmpty()&&!rendicontazione.getCodice().equalsIgnoreCase(myProperties.getProperty("assente"))	)){
					ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Rendicontazione  codice ", myProperties.getProperty("assente"));
					listaErroriProgetto.add(erroreProgetto);
					
				}
				
				//nominativo fornitore
				if(!(rendicontazione.getFornitoreNominativo()!=null&&!rendicontazione.getFornitoreNominativo().isEmpty()&&!rendicontazione.getFornitoreNominativo().equalsIgnoreCase(myProperties.getProperty("assente"))	)){
					ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Rendicontazione  nominativo fornitore  ", myProperties.getProperty("assente"));
					listaErroriProgetto.add(erroreProgetto);
					
				}
				
				//data giustificativo
				if(!(rendicontazione.getDataGiustificativo()!=null&&!Utils.formattaData(rendicontazione.getDataGiustificativo()).equals(myProperties.getProperty("data.errore")))){
					ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Rendicontazione data giustificativo ", myProperties.getProperty("assente"));
					listaErroriProgetto.add(erroreProgetto);
					
				}
				
				//valore complessivo
				if(!(rendicontazione.getValoreComplessivo()!=null&&!rendicontazione.getValoreComplessivo().isEmpty()&&!rendicontazione.getValoreComplessivo().equalsIgnoreCase(myProperties.getProperty("assente"))	)){
					ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Rendicontazione  codice ", myProperties.getProperty("assente"));
					listaErroriProgetto.add(erroreProgetto);
					
				}else if(!FormSecurityValidator.isCifra(rendicontazione.getValoreComplessivo())){
					
					ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Rendicontazione valore complessivo ", myProperties.getProperty("Not.budget"));
					listaErroriProgetto.add(erroreProgetto);
				}
				
				//contributo FBA
				if(!(rendicontazione.getContributoFBA()!=null&&!rendicontazione.getContributoFBA().isEmpty()&&!rendicontazione.getContributoFBA().equalsIgnoreCase( myProperties.getProperty("assente")))){
					ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Rendicontazione  contributo FBA ", myProperties.getProperty("assente"));
					listaErroriProgetto.add(erroreProgetto);
					
				}else if(!FormSecurityValidator.isCifra(rendicontazione.getContributoFBA())&&!rendicontazione.getContributoFBA().equalsIgnoreCase(myProperties.getProperty("assente"))){
					
					ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Rendicontazione contributo FBA ", myProperties.getProperty("Not.budget"));
					listaErroriProgetto.add(erroreProgetto);
				}
				
				//contributo privato
				if(!(rendicontazione.getContributoPrivato()!=null&&!rendicontazione.getContributoPrivato().isEmpty()&&!rendicontazione.getContributoPrivato().equalsIgnoreCase( myProperties.getProperty("assente")))){
					ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Rendicontazione  contributo privato ", myProperties.getProperty("assente"));
					listaErroriProgetto.add(erroreProgetto);
					
				}else if(!FormSecurityValidator.isCifra(rendicontazione.getContributoPrivato())&&!rendicontazione.getContributoPrivato().equalsIgnoreCase(myProperties.getProperty("assente"))){
					
					ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Rendicontazione contributo privato ",myProperties.getProperty("Not.budget"));
					listaErroriProgetto.add(erroreProgetto);
				}
				
				//tipologia giustificativo
				if(!(rendicontazione.getTipologiaGiustificativo()!=null&&!rendicontazione.getTipologiaGiustificativo().isEmpty()&&!rendicontazione.getTipologiaGiustificativo().equalsIgnoreCase(myProperties.getProperty("assente")))){
					ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Rendicontazione  tipologia giustificativo ", myProperties.getProperty("assente"));
					listaErroriProgetto.add(erroreProgetto);
					
				}
				
				//nome allegato
				if(!(rendicontazione.getNomeAllegato()!=null&&!rendicontazione.getNomeAllegato().isEmpty()&&!rendicontazione.getNomeAllegato().equalsIgnoreCase(myProperties.getProperty("assente")))){
					ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Rendicontazione  nome allegato ", myProperties.getProperty("assente"));
					listaErroriProgetto.add(erroreProgetto);
					
				}
				
				//stato
				if(rendicontazione.getStato()!=null&&rendicontazione.getStato().equals(myProperties.getProperty("enabled.no"))){
					ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Rendicontazione ", myProperties.getProperty("errore.lettura.excel"));
					listaErroriProgetto.add(erroreProgetto);
					
				}
				
				
			}
			
			
			
		}else{
			ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Rendicontazione ", myProperties.getProperty("assente"));
			listaErroriProgetto.add(erroreProgetto);
			
		}
		
		return listaErroriProgetto;
	}
	
	public static ArrayList<ErroreProgettoBean> validaFileRendicontazione (int idPiano, ArrayList<RendicontazioneBean> listaRendicontazione, ArrayList<String> listaNomiFileRendicontazione, 
			 ArrayList<ErroreProgettoBean> listaErroriProgetto, Properties myProperties)throws Exception{
			
			 boolean trovato = false;
	         if(listaRendicontazione!=null&&!listaRendicontazione.isEmpty()){
	        	 	if(listaNomiFileRendicontazione!=null&&!listaNomiFileRendicontazione.isEmpty()){
					
						for (RendicontazioneBean rendicontazione : listaRendicontazione){
							int i= 0;
							while (!trovato&&!(i>=listaNomiFileRendicontazione.size())){
								if (!rendicontazione.getNomeAllegato().isEmpty()&&!rendicontazione.getNomeAllegato().equalsIgnoreCase( myProperties.getProperty("assente"))&&rendicontazione.getNomeAllegato().equals(listaNomiFileRendicontazione.get(i))){
									trovato=true;
								}
								i++;
							}
							
							if (!trovato){
								ErroreProgettoBean erroreProgetto = creaErrore(idPiano,"File rendicontazione "+rendicontazione.getNomeAllegato(), myProperties.getProperty("assente"));
								listaErroriProgetto.add(erroreProgetto);
							}
							trovato=false;
						}
					}else{
						ErroreProgettoBean erroreProgetto = creaErrore(idPiano,"File rendicontazione ", myProperties.getProperty("file.assenti"));
						listaErroriProgetto.add(erroreProgetto);
					}
				
				}
	         
				
			 return listaErroriProgetto;


		}
	
	
	
	
	private static ErroreProgettoBean creaErrore (int idProgetto, String oggettoErrore, String errore) throws Exception{
		ErroreProgettoBean erroreBean = new ErroreProgettoBean();
		erroreBean.setIdPiano(idProgetto);
		erroreBean.setOggettoErrore(oggettoErrore);
		erroreBean.setErrore(errore);
		return erroreBean;
	}
	
	private static boolean isInTheList (String stringa, ArrayList<String> listaNomiFile)throws Exception{
		boolean trovato = false;
		int i = 0;
		while (trovato || i==4){
			if (stringa.equals(listaNomiFile.get(i))){
				trovato=true;
			}
			i++;
		}
		return trovato;
	}

}
