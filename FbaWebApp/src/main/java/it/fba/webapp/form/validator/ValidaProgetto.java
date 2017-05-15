package it.fba.webapp.form.validator;

import java.time.LocalTime;
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
	
	
	public static ArrayList<ErroreProgettoBean> validaProgettoTop (PianoDIformazioneBean pianoFormazione,ArrayList<LavoratoriBean> listaLavoratori,Properties myProperties)throws Exception{
		ArrayList<ErroreProgettoBean> listaErroriProgetto = new ArrayList<ErroreProgettoBean>();
		
		// prepara la hashmap dei lavoratori che partecipano al piano
		HashMap<String, String> lavoratori =new HashMap<>();
		if (listaLavoratori!=null&&!listaLavoratori.isEmpty()){
			for (LavoratoriBean lavoratore : listaLavoratori){
				if (!(lavoratori.isEmpty()&&lavoratori.containsKey(lavoratore.getMatricola()))){
					lavoratori.put(lavoratore.getMatricola(), lavoratore.getNomeModulo());
				}
			}
			
		}
		
		// prepara la hashmap dei lavoratori che partecipano ai moduli
			HashMap<String, String> lavoratoriModuli =new HashMap<>();
			if (listaLavoratori!=null&&!listaLavoratori.isEmpty()){
				for (LavoratoriBean lavoratore : listaLavoratori){
					if (!(lavoratoriModuli.isEmpty()&&lavoratoriModuli.containsKey(lavoratore.getNomeModulo()))){
						lavoratoriModuli.put(lavoratore.getNomeModulo(),lavoratore.getMatricola());
					}
				}
				
			}
		
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
		if((Utils.eliminaSpaziTot(pianoFormazione.getNumeroPartecipanti()).isEmpty())||
				pianoFormazione.getNumeroPartecipanti().equalsIgnoreCase(myProperties.getProperty("assente"))){
			 
			ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Numero partecipanti", myProperties.getProperty("assente"));
			listaErroriProgetto.add(erroreProgetto);
		}else if(FormSecurityValidator.isNumber(pianoFormazione.getNumeroPartecipanti())) {
			// verifica che il numero di partecipanti al piano siano uguali al numero di lavoratori iscritti
			
			if (!(Integer.parseInt(Utils.eliminaSpaziTot(pianoFormazione.getNumeroPartecipanti()))==lavoratori.size())){
				ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Numero partecipanti", myProperties.getProperty("numero.partecipanti"));
				listaErroriProgetto.add(erroreProgetto);
				
			}
			
		}else {
			ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Numero partecipanti", myProperties.getProperty("NumberFormat.pianoFormazioneForm.numPartecipanti"));
			listaErroriProgetto.add(erroreProgetto);
		}
		
		// verifica che almeno uno dei moduli sia presente
		if(((Utils.eliminaSpazi(pianoFormazione.getModulo1()).isEmpty())||pianoFormazione.getModulo1().equalsIgnoreCase(myProperties.getProperty("assente")))
				&&((Utils.eliminaSpazi(pianoFormazione.getModulo2()).isEmpty())||pianoFormazione.getModulo2().equalsIgnoreCase(myProperties.getProperty("assente")))){
			
			ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Moduli", myProperties.getProperty("moduli.assenti"));
			listaErroriProgetto.add(erroreProgetto);    
			    
		// verifica che i nomi moduli siano diversi	
		}else if(pianoFormazione.getModulo1().equalsIgnoreCase(pianoFormazione.getModulo2())){
			
			ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Moduli", myProperties.getProperty("nomi.moduli.uguali"));
			listaErroriProgetto.add(erroreProgetto);    
			
			
		}else{
		
			// Modulo 1
			if(((Utils.eliminaSpazi(pianoFormazione.getModulo1()).isEmpty())||pianoFormazione.getModulo1().equalsIgnoreCase(myProperties.getProperty("assente")))
				&&(!((Utils.eliminaSpazi(pianoFormazione.getFadMod1()).isEmpty())||pianoFormazione.getFadMod1().equalsIgnoreCase(myProperties.getProperty("assente")))
				||!((Utils.eliminaSpazi(pianoFormazione.getDurataModulo1()).isEmpty())||pianoFormazione.getDurataModulo1().equalsIgnoreCase(myProperties.getProperty("assente"))))
				){
	
				ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Nome modulo 1", myProperties.getProperty("assente"));
				listaErroriProgetto.add(erroreProgetto);    
			}else{
				if(!lavoratoriModuli.isEmpty()&&!lavoratoriModuli.containsKey(pianoFormazione.getModulo1())&&!pianoFormazione.getModulo1().equalsIgnoreCase(myProperties.getProperty("assente"))){
					ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Modulo "+pianoFormazione.getModulo1(), myProperties.getProperty("no.lavoratore.associato"));
					listaErroriProgetto.add(erroreProgetto);    
				}
				
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
			}else{
				if(!lavoratoriModuli.isEmpty()&&!lavoratoriModuli.containsKey(pianoFormazione.getModulo2())&&!pianoFormazione.getModulo2().equalsIgnoreCase(myProperties.getProperty("assente"))){
					ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Modulo "+pianoFormazione.getModulo2(), myProperties.getProperty("no.lavoratore.associato"));
					listaErroriProgetto.add(erroreProgetto);    
				}
				
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
						i++;
					}
					
					if (!trovato){
						ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"File attuatore "+nomeFileAttProg, myProperties.getProperty("assente"));
						listaErroriProgetto.add(erroreProgetto);
					}
				
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
		if((pianoFormazione.getModulo1()!=null&&!pianoFormazione.getModulo1().isEmpty()&&!pianoFormazione.getModulo1().equalsIgnoreCase(myProperties.getProperty("assente")))
		   ||(pianoFormazione.getModulo2()!=null&&!pianoFormazione.getModulo2().isEmpty()&&!pianoFormazione.getModulo2().equalsIgnoreCase(myProperties.getProperty("assente")))){
			HashMap<String,Boolean> nomiModuliMap = new HashMap<>();
			HashMap<String,Boolean> nomiModuliGiornateMap = new HashMap<>();
			HashMap<String,Integer> nomiModuliOreCalendarioMap = new HashMap<>();
			
			// controllo se il modulo 1 è un modulo fad o no ed inizializzo la hashmap dei nomi moduli e quella delle ore calendario totali se è aula.
			if (pianoFormazione.getModulo1()!=null&&!pianoFormazione.getModulo1().isEmpty()&&!pianoFormazione.getModulo1().equalsIgnoreCase(myProperties.getProperty("assente"))){
				if(pianoFormazione.getFadMod1()!=null&&pianoFormazione.getFadMod1().equalsIgnoreCase(myProperties.getProperty("fad.si"))){
					nomiModuliMap.put(pianoFormazione.getModulo1(), false);
				}else{
					nomiModuliMap.put(pianoFormazione.getModulo1(), true);
					nomiModuliOreCalendarioMap.put(pianoFormazione.getModulo1(),0);
				}
			}
			
			// controllo se il modulo 1 è un modulo fad o no ed inizializzo la hashmap dei nomi moduli e quella delle ore calendario totali se è aula.
			if (pianoFormazione.getModulo2()!=null&&!pianoFormazione.getModulo2().isEmpty()&&!pianoFormazione.getModulo2().equalsIgnoreCase(myProperties.getProperty("assente"))){
				if(pianoFormazione.getFadMod2()!=null&&pianoFormazione.getFadMod2().equalsIgnoreCase(myProperties.getProperty("fad.si"))){
					nomiModuliMap.put(pianoFormazione.getModulo2(), false);
				}else{
					nomiModuliMap.put(pianoFormazione.getModulo2(), true);
					nomiModuliOreCalendarioMap.put(pianoFormazione.getModulo2(),0);
				}
			}
			
			// controllo se sono presenti delle giornate salavate nel caso esiste uno o più calendari con modalità formativa aula
			if(listacalendari.size()==0&&nomiModuliMap.containsValue(true)){
				ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Calendari ", myProperties.getProperty("numero.calendari.err"));
				listaErroriProgetto.add(erroreProgetto);
			}
			
			// Izializzo la mappa in modo che se viene trovata una corrispondenza di una giornata viene a messa a true il nome modulo aula
				if (pianoFormazione.getModulo1()!=null&&!pianoFormazione.getModulo1().isEmpty()){
					nomiModuliGiornateMap.put(pianoFormazione.getModulo1(), false);
				}
				if (pianoFormazione.getModulo2()!=null&&!pianoFormazione.getModulo2().isEmpty()){
					nomiModuliGiornateMap.put(pianoFormazione.getModulo2(), false);
				}
				
			// verifica se è presente almeno un modulo con modalità formativa aula
		   if(nomiModuliMap.containsValue(true)){  
			        // verifica se esistono dei calendari associati al piano
			   		if (listacalendari!=null&&!listacalendari.isEmpty()){
					
					String nomeModulo="";
					for(CalendarioBean calendario : listacalendari){
						calendario.setStato(myProperties.getProperty("enabled.si"));
						//test per vedere se la giornata corrisponde ad un modulo
						if(calendario.getNomeModulo()==null||(Utils.eliminaSpazi(calendario.getNomeModulo()).equals(""))){
							ErroreProgettoBean erroreProgetto = creaErrore(calendario.getIdPiano(),"Calendario nome modulo ", myProperties.getProperty("assente"));
							calendario.setStato(myProperties.getProperty("enabled.no"));
							listaErroriProgetto.add(erroreProgetto);
						}else{
							if(nomiModuliMap.containsKey(calendario.getNomeModulo())){
								nomiModuliGiornateMap.put(calendario.getNomeModulo(), true);
								nomeModulo=calendario.getNomeModulo();
							}
						}
						
						//test sulla validità della data della giornata
						if(!(calendario.getData()!=null&&!Utils.formattaData(calendario.getData()).equals(myProperties.getProperty("data.errore")))){
							ErroreProgettoBean erroreProgetto = creaErrore(calendario.getIdPiano(),"Calendario "+nomeModulo+ " data giorno ", myProperties.getProperty("assente"));
							calendario.setStato(myProperties.getProperty("enabled.no"));
							listaErroriProgetto.add(erroreProgetto);
						}
						
						// test sulla corretta compilazione dell'orario della lezione di mattina
						if (calendario.getInizioMattina().trim().equalsIgnoreCase(myProperties.getProperty("assente"))&&
								!calendario.getFineMattina().trim().equalsIgnoreCase(myProperties.getProperty("assente"))||
								!calendario.getInizioMattina().trim().equalsIgnoreCase(myProperties.getProperty("assente"))&&
								 calendario.getFineMattina().trim().equalsIgnoreCase(myProperties.getProperty("assente"))){
							
								ErroreProgettoBean erroreProgetto = creaErrore(calendario.getIdPiano(),"Calendario "+nomeModulo+ " intervallo tempo mattina ", myProperties.getProperty("Not.interval"));
								calendario.setStato(myProperties.getProperty("enabled.no"));
								listaErroriProgetto.add(erroreProgetto);
									 
							}else{
								 if (!calendario.getInizioMattina().equalsIgnoreCase(myProperties.getProperty("assente"))){
									 if (!FormSecurityValidator.isTime(calendario.getInizioMattina())){
										 ErroreProgettoBean erroreProgetto = creaErrore(calendario.getIdPiano(),"Calendario "+nomeModulo+ " inizio mattina ", myProperties.getProperty("Not.time"));
										 calendario.setStato(myProperties.getProperty("enabled.no"));	
										 listaErroriProgetto.add(erroreProgetto);
									 }
									 
								 }
								 if (!calendario.getFineMattina().equalsIgnoreCase(myProperties.getProperty("assente"))){
									 if (!FormSecurityValidator.isTime(calendario.getFineMattina())){
										 ErroreProgettoBean erroreProgetto = creaErrore(calendario.getIdPiano(),"Calendario "+nomeModulo+ " fine mattina ", myProperties.getProperty("Not.time"));
										 calendario.setStato(myProperties.getProperty("enabled.no"));	
										 listaErroriProgetto.add(erroreProgetto);
									 }
									 
								 }
							}
							
						// test sulla corretta compilazione dell'orario della lezione di pomeriggio
							if (calendario.getInizioPomeriggio().trim().equalsIgnoreCase(myProperties.getProperty("assente"))&&
									!calendario.getFinePomeriggio().trim().equalsIgnoreCase(myProperties.getProperty("assente"))||
									!calendario.getInizioPomeriggio().trim().equalsIgnoreCase(myProperties.getProperty("assente"))&&
									calendario.getFinePomeriggio().trim().equalsIgnoreCase(myProperties.getProperty("assente"))){
									
									ErroreProgettoBean erroreProgetto = creaErrore(calendario.getIdPiano(),"Calendario "+nomeModulo+ " intervallo tempo pomeriggio ", myProperties.getProperty("Not.interval"));
									calendario.setStato(myProperties.getProperty("enabled.no"));
									listaErroriProgetto.add(erroreProgetto);
										 
							}else{
							
									 if (!calendario.getInizioPomeriggio().equalsIgnoreCase(myProperties.getProperty("assente"))){
										 if (!FormSecurityValidator.isTime(calendario.getInizioPomeriggio())){
											 ErroreProgettoBean erroreProgetto = creaErrore(calendario.getIdPiano(),"Calendario "+nomeModulo+ " inizio pomeriggio ", myProperties.getProperty("Not.time"));
											 calendario.setStato(myProperties.getProperty("enabled.no"));
											 listaErroriProgetto.add(erroreProgetto);
										 }
										 
									 }
									 if (!calendario.getFinePomeriggio().equalsIgnoreCase(myProperties.getProperty("assente"))){
										 if (!FormSecurityValidator.isTime(calendario.getFinePomeriggio())){
											 ErroreProgettoBean erroreProgetto = creaErrore(calendario.getIdPiano(),"Calendario "+nomeModulo+ " fine pomeriggio ", myProperties.getProperty("Not.time"));
											 calendario.setStato(myProperties.getProperty("enabled.no"));
											 listaErroriProgetto.add(erroreProgetto);
										 }
										 
									 }
							}
							
							// controllo esitenza orario lezione
							if(calendario.getInizioMattina().equals(myProperties.get("assente"))
							   &&calendario.getFineMattina().equals(myProperties.get("assente"))
							   &&calendario.getInizioPomeriggio().equals(myProperties.get("assente"))
							   &&calendario.getFinePomeriggio().equals(myProperties.get("assente"))){
								 ErroreProgettoBean erroreProgetto = creaErrore(calendario.getIdPiano(),"Calendario "+nomeModulo+ " giornata ", myProperties.getProperty("lezione.assente"));
								 calendario.setStato(myProperties.getProperty("enabled.no"));
								 listaErroriProgetto.add(erroreProgetto);
								   
							 }
							
							// controllo orario di inizio lezione duplicato
							if(calendario.getInizioMattina().equals(calendario.getInizioPomeriggio())&&!calendario.getInizioMattina().equals(myProperties.get("assente"))){
										 ErroreProgettoBean erroreProgetto = creaErrore(calendario.getIdPiano(),"Calendario "+nomeModulo+ " giornata ", myProperties.getProperty("lezione.duplicata"));
										 calendario.setStato(myProperties.getProperty("enabled.no"));
										 listaErroriProgetto.add(erroreProgetto);
										   
							}
							
							// aggiornamento del totale ore calendario con l'aggiunta della giornata esaminata (se la giornata ha errori non si effettua alcun aggiornamento)
							if(calendario.getStato()!=null&&calendario.getStato().equalsIgnoreCase(myProperties.getProperty("enabled.si"))){
								
								int totaleMinuti = 0;
								int oreCorso = 0;
								int testSovrapposizione = 0;
								// verifica se il nome modulo corrisponde con quello del piano
								if (nomiModuliOreCalendarioMap.containsKey(calendario.getNomeModulo())){
									totaleMinuti= nomiModuliOreCalendarioMap.get(calendario.getNomeModulo());
									
									// controllo sovrapposizione lezioni
									if(!(calendario.getFineMattina().trim().equalsIgnoreCase(myProperties.getProperty("assente"))
											||(calendario.getInizioPomeriggio().trim().equalsIgnoreCase(myProperties.getProperty("assente"))))){
											testSovrapposizione = Utils.calcolaIntervalloTempo(calendario.getFineMattina(),calendario.getInizioPomeriggio());
										    if (testSovrapposizione<0){
										    	ErroreProgettoBean erroreProgetto = creaErrore(calendario.getIdPiano(),"Calendario "+" giornata "+nomeModulo, myProperties.getProperty("lezioni.sovrapposte"));
												calendario.setStato(myProperties.getProperty("enabled.no"));
												listaErroriProgetto.add(erroreProgetto);
										    	
										    }
									}
									
									// verifica che l'orario della lezione di mattina sia valorizzato ed aggiorno il totale ore
									if(!(calendario.getInizioMattina().trim().equalsIgnoreCase(myProperties.getProperty("assente"))
											||(calendario.getFineMattina().trim().equalsIgnoreCase(myProperties.getProperty("assente"))))
											&&(calendario.getStato().equalsIgnoreCase(myProperties.getProperty("enabled.si")))){
										    oreCorso = Utils.calcolaIntervalloTempo(calendario.getInizioMattina(),calendario.getFineMattina());
										    if (oreCorso>=0){
										    	totaleMinuti= totaleMinuti+oreCorso;
										    }else{
										    	ErroreProgettoBean erroreProgetto = creaErrore(calendario.getIdPiano(),"Calendario "+nomeModulo+ " intervallo tempo mattina ", myProperties.getProperty("Not.interval"));
												calendario.setStato(myProperties.getProperty("enabled.no"));
												listaErroriProgetto.add(erroreProgetto);
										    }
									}
									
									// verifica che l'orario della lezione di pomeriggio sia valorizzato ed aggiorno il totale ore
									if(!(calendario.getInizioPomeriggio().trim().equalsIgnoreCase(myProperties.getProperty("assente"))
											||(calendario.getFinePomeriggio().trim().equalsIgnoreCase(myProperties.getProperty("assente"))))){
											oreCorso = Utils.calcolaIntervalloTempo(calendario.getInizioPomeriggio(),calendario.getFinePomeriggio());
											if (oreCorso>=0){
											    totaleMinuti= totaleMinuti+oreCorso;
											}else{
											    ErroreProgettoBean erroreProgetto = creaErrore(calendario.getIdPiano(),"Calendario "+nomeModulo+ " intervallo tempo pomeriggio ", myProperties.getProperty("Not.interval"));
												calendario.setStato(myProperties.getProperty("enabled.no"));
												listaErroriProgetto.add(erroreProgetto);
											}
												
									}	
								     
									nomiModuliOreCalendarioMap.put(calendario.getNomeModulo(), totaleMinuti) ;
								}
							}
						
					}
					
					// se il primo modulo è aula allora viene effettuato il confronto delle ore tra durata modulo e calendario associato.
					if (nomiModuliMap.containsKey(pianoFormazione.getModulo1())&&nomiModuliMap.get(pianoFormazione.getModulo1())){
							int minutiTotModulo = Utils.stringToMinutes(pianoFormazione.getDurataModulo1());
							if (minutiTotModulo>0){
								if(nomiModuliOreCalendarioMap.containsKey(pianoFormazione.getModulo1())){
									int totale = nomiModuliOreCalendarioMap.get(pianoFormazione.getModulo1()) - minutiTotModulo;
									if(totale!=0){
										String segno="";
										 if(totale>0){
											 segno="+";
										 }
										 ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Calendario "+pianoFormazione.getModulo1()+" "+segno+totale+" min", myProperties.getProperty("differenza.ore.modulo.calendario"));
										 listaErroriProgetto.add(erroreProgetto);
										
									}
								}
							}
					}
					
					// se il secondo modulo è aula allora viene effettuato il confronto delle ore tra durata modulo e calendario associato.
					if (nomiModuliMap.containsKey(pianoFormazione.getModulo2())&&nomiModuliMap.get(pianoFormazione.getModulo2())){
							int minutiTotModulo = Utils.stringToMinutes(pianoFormazione.getDurataModulo2());
							if (minutiTotModulo>0){
								if(nomiModuliOreCalendarioMap.containsKey(pianoFormazione.getModulo2())){
									int totale = nomiModuliOreCalendarioMap.get(pianoFormazione.getModulo2()) - minutiTotModulo;
									if(totale!=0){
										String segno="";
										 if(totale>0){
											 segno="+";
										 }
										 ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Calendario "+pianoFormazione.getModulo2()+" "+segno+totale+" min", myProperties.getProperty("differenza.ore.modulo.calendario"));
										 if(totale>0)
										 listaErroriProgetto.add(erroreProgetto);
										
									}
								}
							}
					}
					
					// verifica che se il modulo 1 è aula questo abbia un calendario associato
					if(nomiModuliMap.containsKey(pianoFormazione.getModulo1())){
						if (nomiModuliMap.get(pianoFormazione.getModulo1())==true
								&&!(nomiModuliMap.get(pianoFormazione.getModulo1())==nomiModuliGiornateMap.get(pianoFormazione.getModulo1()))){
							ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(), pianoFormazione.getModulo1(), myProperties.getProperty("calendario.non.associato"));
							listaErroriProgetto.add(erroreProgetto);
						}
					}
					
					// verifica che se il modulo 2 è aula questo abbia un calendario associato
					if(nomiModuliMap.containsKey(pianoFormazione.getModulo2())){
						if (nomiModuliMap.get(pianoFormazione.getModulo2())==true
								&&!(nomiModuliMap.get(pianoFormazione.getModulo2())==nomiModuliGiornateMap.get(pianoFormazione.getModulo2()))){
							ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(), pianoFormazione.getModulo2(), myProperties.getProperty("calendario.non.associato"));
							listaErroriProgetto.add(erroreProgetto);
						}
					}
					
					
					
				}else{
					ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Calendari ", myProperties.getProperty("assenti"));
					listaErroriProgetto.add(erroreProgetto);
				}
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
			
				for(LavoratoriBean lavoratore : listaLavoratori){
					lavoratore.setStato(myProperties.getProperty("enabled.si"));
					
					//controlla che il lavoratore abbia un modulo associato
					if(!(lavoratore.getNomeModulo()!=null&&!lavoratore.getNomeModulo().isEmpty()&&!lavoratore.getNomeModulo().equals("assente"))){
						lavoratore.setNomeModulo(myProperties.getProperty("assente"));
						
						ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Lavoratori modulo "+lavoratore.getNomeModulo()+" nome modulo ", myProperties.getProperty("assente"));
						listaErroriProgetto.add(erroreProgetto);
					}else{
						if(nomiModuliMap.containsKey(lavoratore.getNomeModulo())){
							nomiModuliMap.put(lavoratore.getNomeModulo(), true);
						}
						
					}
					
					//controllo matricola
					if(!(lavoratore.getMatricola()!=null&&!lavoratore.getMatricola().isEmpty()&&!lavoratore.getMatricola().equals("assente"))){
						lavoratore.setMatricola(myProperties.getProperty("assente"));
						lavoratore.setStato(myProperties.getProperty("enabled.no"));
						ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Lavoratori modulo "+lavoratore.getNomeModulo()+" matricola ", myProperties.getProperty("assente"));
						listaErroriProgetto.add(erroreProgetto);
					}
					
					//controllo ore presenza
					if(!(lavoratore.getOrePresenza()!=null&&!lavoratore.getOrePresenza().isEmpty()&&!lavoratore.getOrePresenza().equals("assente"))){
						lavoratore.setOrePresenza(myProperties.getProperty("assente"));
						lavoratore.setStato(myProperties.getProperty("enabled.no"));
						ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Lavoratori modulo "+lavoratore.getNomeModulo()+" ore presenza ", myProperties.getProperty("assente"));
						listaErroriProgetto.add(erroreProgetto);
					}else{
						if(!FormSecurityValidator.isOreMin(lavoratore.getOrePresenza())){
							ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Lavoratori modulo "+lavoratore.getNomeModulo()+" ore presenza ", myProperties.getProperty("Not.time"));
							listaErroriProgetto.add(erroreProgetto);
						}
					}
					
					//controllo esito test
					if(!(lavoratore.getEsitoTest()!=null&&!lavoratore.getEsitoTest().isEmpty())){
						lavoratore.setEsitoTest(myProperties.getProperty("assente"));
						lavoratore.setStato(myProperties.getProperty("enabled.no"));
						ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Lavoratori modulo "+lavoratore.getNomeModulo()+" esito test", myProperties.getProperty("assente"));
						listaErroriProgetto.add(erroreProgetto);
					}
					
					// controllo esistenza allegato
					if(!(lavoratore.getNomeAllegato()!=null&&!lavoratore.getNomeAllegato().isEmpty()&&!lavoratore.getNomeAllegato().equals("assente"))){
						lavoratore.setNomeAllegato(myProperties.getProperty("assente"));
						lavoratore.setStato(myProperties.getProperty("enabled.no"));
						ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Lavoratori modulo "+lavoratore.getNomeModulo()+" nome allegato ", myProperties.getProperty("assente"));
						listaErroriProgetto.add(erroreProgetto);
					}
					
					if(lavoratore.getStato()!=null&&lavoratore.getStato().equals(myProperties.getProperty("enabled.no"))){
						ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Lavoratori modulo "+lavoratore.getNomeModulo(), myProperties.getProperty("errore.lettura.excel"));
						listaErroriProgetto.add(erroreProgetto);
					}
					
					
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
				
				rendicontazione.setStato(myProperties.getProperty("enabled.si"));
				
				//codice
				if(!(rendicontazione.getCodice()!=null&&!rendicontazione.getCodice().isEmpty()&&!rendicontazione.getCodice().equalsIgnoreCase(myProperties.getProperty("assente"))	)){
					ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Rendicontazione  codice ", myProperties.getProperty("assente"));
					listaErroriProgetto.add(erroreProgetto);
					rendicontazione.setStato(myProperties.getProperty("enabled.no"));
				}
				
				//nominativo fornitore
				if(!(rendicontazione.getFornitoreNominativo()!=null&&!rendicontazione.getFornitoreNominativo().isEmpty()&&!rendicontazione.getFornitoreNominativo().equalsIgnoreCase(myProperties.getProperty("assente"))	)){
					ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Rendicontazione  nominativo fornitore  ", myProperties.getProperty("assente"));
					listaErroriProgetto.add(erroreProgetto);
					rendicontazione.setStato(myProperties.getProperty("enabled.no"));
				}
				
				//data giustificativo
				if(!(rendicontazione.getDataGiustificativo()!=null&&!Utils.formattaData(rendicontazione.getDataGiustificativo()).equals(myProperties.getProperty("data.errore")))){
					ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Rendicontazione data giustificativo ", myProperties.getProperty("assente"));
					listaErroriProgetto.add(erroreProgetto);
					rendicontazione.setStato(myProperties.getProperty("enabled.no"));
				}
				
				//valore complessivo
				if(!(rendicontazione.getValoreComplessivo()!=null&&!rendicontazione.getValoreComplessivo().isEmpty()&&!rendicontazione.getValoreComplessivo().equalsIgnoreCase(myProperties.getProperty("assente"))	)){
					ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Rendicontazione  codice ", myProperties.getProperty("assente"));
					listaErroriProgetto.add(erroreProgetto);
					rendicontazione.setStato(myProperties.getProperty("enabled.no"));
				}else if(!FormSecurityValidator.isCifra(rendicontazione.getValoreComplessivo())){
					
					ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Rendicontazione valore complessivo ", myProperties.getProperty("Not.budget"));
					listaErroriProgetto.add(erroreProgetto);
					rendicontazione.setStato(myProperties.getProperty("enabled.no"));
				}
				
				//contributo FBA
				if(!(rendicontazione.getContributoFBA()!=null&&!rendicontazione.getContributoFBA().isEmpty()&&!rendicontazione.getContributoFBA().equalsIgnoreCase( myProperties.getProperty("assente")))){
					ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Rendicontazione  contributo FBA ", myProperties.getProperty("assente"));
					listaErroriProgetto.add(erroreProgetto);
					rendicontazione.setStato(myProperties.getProperty("enabled.no"));
				}else if(!FormSecurityValidator.isCifra(rendicontazione.getContributoFBA())&&!rendicontazione.getContributoFBA().equalsIgnoreCase(myProperties.getProperty("assente"))){
					
					ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Rendicontazione contributo FBA ", myProperties.getProperty("Not.budget"));
					listaErroriProgetto.add(erroreProgetto);
					rendicontazione.setStato(myProperties.getProperty("enabled.no"));
				}
				
				//contributo privato
				if(!(rendicontazione.getContributoPrivato()!=null&&!rendicontazione.getContributoPrivato().isEmpty()&&!rendicontazione.getContributoPrivato().equalsIgnoreCase( myProperties.getProperty("assente")))){
					ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Rendicontazione  contributo privato ", myProperties.getProperty("assente"));
					listaErroriProgetto.add(erroreProgetto);
					rendicontazione.setStato(myProperties.getProperty("enabled.no"));
				}else if(!FormSecurityValidator.isCifra(rendicontazione.getContributoPrivato())&&!rendicontazione.getContributoPrivato().equalsIgnoreCase(myProperties.getProperty("assente"))){
					
					ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Rendicontazione contributo privato ",myProperties.getProperty("Not.budget"));
					listaErroriProgetto.add(erroreProgetto);
					rendicontazione.setStato(myProperties.getProperty("enabled.no"));
				}
				
				//tipologia giustificativo
				if(!(rendicontazione.getTipologiaGiustificativo()!=null&&!rendicontazione.getTipologiaGiustificativo().isEmpty()&&!rendicontazione.getTipologiaGiustificativo().equalsIgnoreCase(myProperties.getProperty("assente")))){
					ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Rendicontazione  tipologia giustificativo ", myProperties.getProperty("assente"));
					listaErroriProgetto.add(erroreProgetto);
					rendicontazione.setStato(myProperties.getProperty("enabled.no"));
					
				}
				
				//nome allegato
				if(!(rendicontazione.getNomeAllegato()!=null&&!rendicontazione.getNomeAllegato().isEmpty()&&!rendicontazione.getNomeAllegato().equalsIgnoreCase(myProperties.getProperty("assente")))){
					ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Rendicontazione  nome allegato ", myProperties.getProperty("assente"));
					listaErroriProgetto.add(erroreProgetto);
					rendicontazione.setStato(myProperties.getProperty("enabled.no"));
					
				}
				
				//stato
				if(rendicontazione.getStato()!=null&&rendicontazione.getStato().equals(myProperties.getProperty("enabled.no"))){
					ErroreProgettoBean erroreProgetto = creaErrore(pianoFormazione.getId(),"Rendicontazione ", myProperties.getProperty("errore.lettura.excel"));
					listaErroriProgetto.add(erroreProgetto);
					rendicontazione.setStato(myProperties.getProperty("enabled.no"));
					
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
	
	
	
	
	

}
