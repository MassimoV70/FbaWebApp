package it.fba.webapp.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import it.fba.webapp.beans.AttuatoreBean;
import it.fba.webapp.beans.CalendarioBean;
import it.fba.webapp.beans.DatiFIleUploadBean;
import it.fba.webapp.beans.ErroreProgettoBean;
import it.fba.webapp.beans.EttLoadStatusBean;
import it.fba.webapp.beans.FileBean;
import it.fba.webapp.beans.ImplementaPianoFormBean;
import it.fba.webapp.beans.LavoratoriBean;
import it.fba.webapp.beans.LavoratoriFileBean;
import it.fba.webapp.beans.ListaFileBean;
import it.fba.webapp.beans.PianoDIformazioneBean;
import it.fba.webapp.beans.RendicontazioneBean;
import it.fba.webapp.beans.RendicontazioneFileBean;
import it.fba.webapp.beans.UsersBean;
import it.fba.webapp.dao.AttuatoriDao;
import it.fba.webapp.dao.CalendarioDao;
import it.fba.webapp.dao.ErroriProgettoDao;
import it.fba.webapp.dao.EttLoadStatusDao;
import it.fba.webapp.dao.LavoratoriDao;
import it.fba.webapp.dao.PianiFormazioneDao;
import it.fba.webapp.dao.RendicontazioneDao;
import it.fba.webapp.dao.UsersDao;
import it.fba.webapp.exception.CustomGenericException;
import it.fba.webapp.fileInputOutput.ImportServiceExcel;
import it.fba.webapp.form.validator.ExcelValidator;
import it.fba.webapp.form.validator.FormSecurityValidator;
import it.fba.webapp.form.validator.ValidaProgetto;
import it.fba.webapp.trasmissione.FbaExtractor;
import it.fba.webapp.utils.Utils;


@Controller
public class FbaController {
	
	private static final Logger logger = Logger.getLogger(FbaController.class);
	
	@Resource(name="myProperties")
	private Properties myProperties;
	
	@Autowired
	FormSecurityValidator validator;
	
	
	

	@RequestMapping(value = { "/welcome**" }, method = RequestMethod.GET)
	public ModelAndView defaultPage() {
		UsersBean user = new UsersBean();
		ModelAndView model = new ModelAndView();
		logger.debug("Add Your Application specific Log Messages here");
		model.addObject("user", user);
		model.addObject("title", "Autenticazione Intesa San Paolo FBA");
		model.addObject("message", "Home");
		model.setViewName("hello");
		return model;

	}

	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView adminPage() {

		ModelAndView model = new ModelAndView();
		model.addObject("title", "Autenticato come amministratore");
		model.addObject("message", "Pagina ad uso esclusivo dell'amministratore");
		model.setViewName("admin");
		return model;

	}
	
	@RequestMapping(value ={"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
		@RequestParam(value = "logout", required = false) String logout) {
        
		ModelAndView model = new ModelAndView();
		
		if (error != null&&!error.equalsIgnoreCase("sessione")) {
			model.addObject("error", myProperties.getProperty("username.password"));
		}
		
		if (error != null&&error.equalsIgnoreCase("sessione")) {
			model.addObject("error", myProperties.getProperty("errore.sessione"));
		}

		if (logout != null) {
			model.addObject("msg", "Logout con successo");
		}
		model.setViewName("login");

		return model;

	}

	// for 403 access denied page
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {

		ModelAndView model = new ModelAndView();

		// check if user is login
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			model.addObject("username", userDetail.getUsername());
		}

		model.setViewName("403");
		return model;

	}
	
	// metodo per andare alla pagina form per inserimento utente
	@RequestMapping(value = "/adminAddUserForm", method = RequestMethod.GET)
	public ModelAndView adminAddUserForm() {

		ModelAndView model = new ModelAndView();
		UsersBean user = new UsersBean();
		user.setDataInizioStr(Utils.dataOggi());
		model.addObject("title", "Nuovo Utente");
		model.addObject("message", myProperties.getProperty("pagina.amministratore"));
	    model.addObject("disabled", false);
        model.addObject("userForm", user);
		model.setViewName("addUserForm");
		return model;

	}
	
	// metodo per andare alla pagina form per inserimento utente
		@RequestMapping(value = "/adminAddUser", method = RequestMethod.POST)
		public ModelAndView adminAddUser(@Valid @ModelAttribute("userForm") UsersBean userForm, BindingResult bindingResult, ModelAndView model) {
			
			ApplicationContext context=null;
			try{
				
				 userForm.setDataInizioStr(Utils.dataOggi());
				 userForm.setDataInizio(Utils.dataDBFormatter(userForm.getDataInizioStr()));
				 if(userForm.getDataFineStr()!=null||!userForm.getDataFineStr().isEmpty()){
					 userForm.setDataFine(Utils.dataDBFormatter(userForm.getDataFineStr()));
				 }

				 validator.userFormValidator(userForm, bindingResult);
				 if(!bindingResult.hasErrors()){
							 context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
							UsersDao usersService = (UsersDao) context.getBean("UsersDaoImpl");
							    UsersBean user =usersService.findUserbyUsername(userForm.getUsername());
							    if (user!=null){
							    	model.addObject("message", "username già in uso");
							    	model.addObject("disabled", false);
							    }else{
									userForm.setEnabled("1");
									userForm.setRole(myProperties.getProperty("Ruolo.Utente"));
									usersService.addUser(userForm);
									model.addObject("message", "utente inserito correttamente");
									model.addObject("disabled", true);
							    }
								
							
						
				  }
			}catch(Exception e){
				e.printStackTrace();
				logger.error(e.getMessage());
				throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
			}finally{
				if(context!=null){
					((ConfigurableApplicationContext)context).close();
				}
			}
			model.addObject("userForm", userForm);
			model.setViewName("addUserForm");
	          
			return model;

		}
		
		// metodo per visualizzare tutti gli utenti 
		@RequestMapping(value = "/admingetAllUser", method = RequestMethod.GET)
		public ModelAndView admingetAllUser() {
			UsersBean utente = new UsersBean();
			ModelAndView model = new ModelAndView();
			ArrayList<UsersBean> usersList = new ArrayList<>();
			try{
				ApplicationContext context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
				UsersDao usersService = (UsersDao) context.getBean("UsersDaoImpl");
				      
					 usersList = usersService.getAllUsers();
					 usersList = Utils.userFormSettings(usersList);
					((ConfigurableApplicationContext)context).close();
			}catch(Exception e){
				e.printStackTrace();
				logger.error(e.getMessage());
				throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
			}
			
			model.addObject("title", "Gestione Utenti");
			model.addObject("message", myProperties.getProperty("pagina.amministratore"));   
			model.addObject("gestioneUserForm", utente);
			model.addObject("usersList", usersList);
			model.setViewName("usersTable");
		 return model;

		}
		
		// metodo per la gestione della tabella utenti
			@RequestMapping(value = "/adminGestisciUtente", method = RequestMethod.GET)
			public ModelAndView adminGestisciUtente(ModelMap model, @ModelAttribute("gestioneUserForm") UsersBean gestioneUserForm) {
	             
				
				ModelAndView modelWiev = new ModelAndView();
				UsersBean utente = new UsersBean();
				try{
					ApplicationContext context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
					UsersDao usersService = (UsersDao) context.getBean("UsersDaoImpl");
					       
					utente = usersService.findUserbyUsername(gestioneUserForm.getUsername());
					utente.setDataInizioStr(Utils.formattaData(utente.getDataInizio()));
					utente.setDataFineStr(Utils.formattaData(utente.getDataFine()));
					if(!gestioneUserForm.getAzione().equalsIgnoreCase(myProperties.getProperty("modifica.utente"))){
							utente.setEnabled(myProperties.getProperty("enabled.no"));
							if(gestioneUserForm.getAzione().equalsIgnoreCase(myProperties.getProperty("abilita.utente"))){
								utente.setEnabled(myProperties.getProperty("enabled.si"));
							}
							if (utente.getUsername()!=null){
								usersService.updateUser(utente);
							}
					}
					((ConfigurableApplicationContext)context).close();
				}catch(Exception e){
					e.printStackTrace();
					logger.error(e.getMessage());
					throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
				}
				if(!gestioneUserForm.getAzione().equalsIgnoreCase(myProperties.getProperty("modifica.utente"))){
					return new ModelAndView("forward:/admingetAllUser", model);
							
				}
				
				Map<String, String> listaStati = Utils.getListaStati(myProperties);
				modelWiev.addObject("title", "Modifica Utente");
				modelWiev.addObject("message", myProperties.getProperty("pagina.amministratore"));
				modelWiev.addObject("listaStati", listaStati);
				modelWiev.addObject("disabled", false);
				modelWiev.addObject("userForm",utente);
				modelWiev.setViewName("modificaUtente");
			 return modelWiev;

			}
			
			// metodo modifica dati utentie
				@RequestMapping(value = "/adminModifyUser", method = RequestMethod.POST)
				public ModelAndView adminModifyUser(ModelAndView modelWiev, @Valid @ModelAttribute("userForm") UsersBean userFormModify, BindingResult bindingResult) {
		             
					//ModelAndView modelWiev = new ModelAndView();
					UsersBean utente = new UsersBean();
					 
					try{
					
						 validator.userFormValidator(userFormModify, bindingResult);
						if(!bindingResult.hasErrors()){
							userFormModify.setDataInizio(Utils.dataDBFormatter(userFormModify.getDataInizioStr()));
							userFormModify.setDataFine(Utils.dataDBFormatter(userFormModify.getDataFineStr()));
							ApplicationContext context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
							UsersDao usersService = (UsersDao) context.getBean("UsersDaoImpl");
							       
							utente = usersService.findUserbyUsername(userFormModify.getUsername());
							userFormModify.setRole(utente.getRole());
							if (utente.getUsername()!=null){
								usersService.updateUser(userFormModify);
							}
							((ConfigurableApplicationContext)context).close();
						}
					}catch(Exception e){
						e.printStackTrace();
						logger.error(e.getMessage());
						throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
					}
					Map<String, String> listaStati = Utils.getListaStati(myProperties);
					modelWiev.addObject("title", "Modifica Utente");
					modelWiev.addObject("message", myProperties.getProperty("pagina.amministratore"));
					modelWiev.addObject("listaStati", listaStati);
					if(!bindingResult.hasErrors()){
						modelWiev.addObject("disabled", true);
					}else{
						modelWiev.addObject("disabled", false);
					}
					modelWiev.addObject("userForm",userFormModify);
					modelWiev.setViewName("modificaUtente");
				 return modelWiev;

				}
				
				// metodo carica la pagina per upload dei piani di formazione
				@RequestMapping(value ={ "/adminformUploadPiani","/userformUploadPiani"}, method = RequestMethod.GET)
				public ModelAndView formUploadPiani() {
					ModelAndView modelWiev = new ModelAndView();
					FileBean fileBean = new FileBean();
					modelWiev.addObject("title", "Upload Piani Di Formazione");
					modelWiev.addObject("message", "Pagina per il caricamento dei piani di formazione");
					modelWiev.addObject("fileBean", fileBean);
					modelWiev.setViewName("pianiFormazioneUpload");
					return modelWiev;
				}
				
				// metodo che elabora il file excel piani di formazione
				@RequestMapping(value ={ "/adminElaboraUploadPiani","/userElaboraUploadPiani"}, method = RequestMethod.POST)
				public ModelAndView elaboraUploadPiani(HttpServletRequest request,  @ModelAttribute("fileBean") FileBean fileBean) {
					ModelAndView modelWiev = new ModelAndView();
					ArrayList<HashMap<String, String>> listaExcel = new ArrayList<>();
					ArrayList<PianoDIformazioneBean> listaPiani = new ArrayList<>();
					PianoDIformazioneBean piano = new PianoDIformazioneBean(); 
					AttuatoreBean attuatoreBean = new AttuatoreBean();
					ApplicationContext context=null;
					try {
						
						
						ImportServiceExcel importService = new ImportServiceExcel();
						listaExcel = importService.importFile(fileBean);
						listaPiani = ExcelValidator.listaPianiFormazioneValidator(listaExcel, fileBean.getUsername(), myProperties);
						
						 context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
						 PianiFormazioneDao pianiFormazioneDao = (PianiFormazioneDao) context.getBean("PianiFormazioneDaoImpl");
						 AttuatoriDao attuatoriDao= (AttuatoriDao) context.getBean("AttuatoreDaoImpl");
						 AttuatoreBean attuatoreResult = new AttuatoreBean();
						  for(PianoDIformazioneBean singoloPiano :listaPiani){
							    
							    if (attuatoreResult==null||!(attuatoreResult.getAttuatorePIVA()!=null&&attuatoreBean.getAttuatorePIVA().equalsIgnoreCase(singoloPiano.getAttuatorePIVA()))){
							    	    attuatoreBean.setAttuatorePIVA(singoloPiano.getAttuatorePIVA());
							    	    attuatoreBean.setUsername(singoloPiano.getUsername());	
							    		attuatoreResult = attuatoriDao.leggiNomiAllegati(attuatoreBean);
							    }
							    	
							   if (attuatoreResult!=null){
									singoloPiano.setAttuatorePIVA(attuatoreResult.getAttuatorePIVA());
									singoloPiano.setNomeAllegato1(attuatoreResult.getNomeAllegato1());
									singoloPiano.setNomeAllegato2(attuatoreResult.getNomeAllegato2());
									singoloPiano.setNomeAllegato3(attuatoreResult.getNomeAllegato3());
									singoloPiano.setNomeAllegato4(attuatoreResult.getNomeAllegato4());
								}
							    
							 
						 }
						 pianiFormazioneDao.caricaPianiFormazione(listaPiani);
						 listaPiani = pianiFormazioneDao.getAllPiani(fileBean.getUsername());
						 
						
											
						
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						logger.error(e.getMessage());
						String messaggio="";
						if (!fileBean.getFileData().getOriginalFilename().endsWith("xls")&&!fileBean.getFileData().getOriginalFilename().endsWith("xlsx")){
							 messaggio =fileBean.getFileData().getOriginalFilename()+" "+ myProperties.getProperty("no.xls.xlsx");
							 messaggio= context.getMessage("no.pdf", new Object[]{fileBean.getFileData().getOriginalFilename()}, Locale.ITALY);
						}else{
							 messaggio = myProperties.getProperty("errore.file.excel")+" "+fileBean.getFileData().getOriginalFilename();
						}
						modelWiev.addObject("errorMessage",messaggio);
					    fileBean = new FileBean();
					    modelWiev.addObject("title", "Upload Piani Di Formazione");
					    modelWiev.addObject("message", "Pagina per il caricamento dei piani di formazione");
					    modelWiev.addObject("fileBean", fileBean);
					    modelWiev.setViewName("pianiFormazioneUpload");
					    return modelWiev;
					}finally{
						if(context!=null){
							((ConfigurableApplicationContext)context).close();
						}
						
						
					}
					modelWiev.addObject("pianoFormazioneForm",piano); 
					modelWiev.setViewName("pianiDiFormazioneTabella");
					modelWiev.addObject("listaPiani", listaPiani);
					return modelWiev;
				}
				
				
				// carica i piani salvati sul db per la tabella
				@RequestMapping(value ={ "/adminMostraPiani","/userMostraPiani"}, method = RequestMethod.GET)
				public ModelAndView mostraPiani(HttpServletRequest request,  @ModelAttribute("user") UsersBean user) {
					ModelAndView modelWiev = new ModelAndView();
					ArrayList<PianoDIformazioneBean> listaPiani = new ArrayList<>();
					ApplicationContext context=null;
					user.setUsername(request.getUserPrincipal().getName());
					try {
						 context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
						 PianiFormazioneDao pianiFormazioneDao = (PianiFormazioneDao) context.getBean("PianiFormazioneDaoImpl");
						 listaPiani = pianiFormazioneDao.getAllPiani(user.getUsername());
					
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						logger.error(e.getMessage());
						throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
					}finally{
						if(context!=null){
							((ConfigurableApplicationContext)context).close();
						}
					}	
					
						PianoDIformazioneBean piano = new PianoDIformazioneBean();
						modelWiev.addObject("title", "Tabella Piani Di Formazione");
						modelWiev.addObject("message", myProperties.getProperty("piani.formazione.elenco"));
						modelWiev.addObject("listaPiani", listaPiani);
						modelWiev.addObject("pianoFormazioneForm",piano);
						modelWiev.setViewName("pianiDiFormazioneTabella");
					
						return modelWiev;
					}
				
				// carica la pagina di modifica piano formazione
				@RequestMapping(value ={"/adminModifyPianoForm","/userModifyPianoForm"} , method = RequestMethod.POST)
				public ModelAndView modifyPianoForm(ModelMap model, @ModelAttribute("pianoFormazioneForm") PianoDIformazioneBean pianoFormazione) {
		             
					ModelAndView modelWiev = new ModelAndView();
					ApplicationContext context=null;
					PianoDIformazioneBean pianoDIformazioneBean = new PianoDIformazioneBean();
					
					try{
						context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
						PianiFormazioneDao pianiFormazioneDao = (PianiFormazioneDao) context.getBean("PianiFormazioneDaoImpl");
						
						pianoDIformazioneBean= pianiFormazioneDao.findPianiFormazione(pianoFormazione);
						
						
					
					}catch(Exception e){
						e.printStackTrace();
						logger.error(e.getMessage());
						throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
					}finally{
						if(context!=null){
							((ConfigurableApplicationContext)context).close();
						}
					}	
					Map<String, String> listaSelezione = Utils.getListaSiNo(myProperties);
					Map<String, String> listaSelezioneFad = Utils.getListaFadSiNo(myProperties);
					modelWiev.addObject("listaSelezione", listaSelezione);
					modelWiev.addObject("listaSelezioneFad", listaSelezioneFad);
					modelWiev.addObject("title", "Modifica Piano Di Formazione");
					modelWiev.addObject("message", myProperties.getProperty("piani.formazione.modifica"));
					modelWiev.addObject("pianoFormazioneForm", pianoDIformazioneBean);
					modelWiev.setViewName("modificaPianoFormazione");
				 return modelWiev;

				}
				
				// metodo modifica piano di formazione
				@RequestMapping(value = {"/adminModifyPiano","/userModifyPiano"} , method = RequestMethod.POST)
				public ModelAndView modifyPiano(ModelMap model, @Valid @ModelAttribute("pianoFormazioneForm") PianoDIformazioneBean pianoDiFormazione, BindingResult bindingResult) {
		             
					ModelAndView modelWiev = new ModelAndView();
					ApplicationContext context=null;
					try{
						validator.pianoFormazioneValidator(pianoDiFormazione, bindingResult, myProperties);
						if(!bindingResult.hasErrors()){
							pianoDiFormazione = Utils.formattaPiano(pianoDiFormazione);
							pianoDiFormazione.setAttuatorePIVA(Utils.eliminaSpaziTot(pianoDiFormazione.getAttuatorePIVA()));
							context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
							PianiFormazioneDao pianiFormazioneDao = (PianiFormazioneDao) context.getBean("PianiFormazioneDaoImpl");
							
							//aggiorno i nomi degli allegati del piano.
							AttuatoreBean attuatoreBean = new AttuatoreBean();
						    attuatoreBean.setAttuatorePIVA(pianoDiFormazione.getAttuatorePIVA());
						    attuatoreBean.setUsername(pianoDiFormazione.getUsername());	
							AttuatoriDao attuatoriDao= (AttuatoriDao) context.getBean("AttuatoreDaoImpl");
							AttuatoreBean attuatoreResult = attuatoriDao.leggiNomiAllegati(attuatoreBean);
							if (attuatoreResult!=null){
								pianoDiFormazione.setAttuatorePIVA(attuatoreResult.getAttuatorePIVA());
								pianoDiFormazione.setNomeAllegato1(attuatoreResult.getNomeAllegato1());
								pianoDiFormazione.setNomeAllegato2(attuatoreResult.getNomeAllegato2());
								pianoDiFormazione.setNomeAllegato3(attuatoreResult.getNomeAllegato3());
								pianoDiFormazione.setNomeAllegato4(attuatoreResult.getNomeAllegato4());
								
							}
							if(!pianoDiFormazione.getEnabled().equalsIgnoreCase(myProperties.getProperty("trasmissione.ko"))){	
								pianoDiFormazione.setEnabled(myProperties.getProperty("enabled.no"));
							}
							pianiFormazioneDao.updatePianoDiFormazione(pianoDiFormazione);
							
						
							// cancellazioni degli eventuali calendari dei moduli diventati fad
							ArrayList<CalendarioBean> listaCalendario = new ArrayList<>();
							CalendarioBean calendarioModuloBean = new CalendarioBean();
							calendarioModuloBean.setIdPiano(pianoDiFormazione.getId());
							CalendarioDao calendarioDao = (CalendarioDao) context.getBean("CalendarioDaoImpl");
							if (pianoDiFormazione.getFadMod1().equalsIgnoreCase("fad")){
								calendarioModuloBean.setNomeModulo(pianoDiFormazione.getModulo1());
								listaCalendario = calendarioDao.getAllCalednario(calendarioModuloBean);
							    if (listaCalendario!=null&&!listaCalendario.isEmpty()){
									 // cancello il calendario che era aula e adesso è diventato fad
									 calendarioDao.deleteAllCalednari(calendarioModuloBean);
									 
								 }
							}
							
							if (pianoDiFormazione.getFadMod2().equalsIgnoreCase("fad")){
								listaCalendario = null;
								calendarioModuloBean.setNomeModulo(pianoDiFormazione.getModulo2());
								listaCalendario = calendarioDao.getAllCalednario(calendarioModuloBean);
								 if (listaCalendario!=null&&!listaCalendario.isEmpty()){
									 // cancello il calendario che era aula e adesso è diventato fad
									 calendarioDao.deleteAllCalednari(calendarioModuloBean);
									 
								 }
							}
						}   
					
					}catch(Exception e){
						e.printStackTrace();
						logger.error(e.getMessage());
						throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
					}finally{
						if(context!=null){
							((ConfigurableApplicationContext)context).close();
						}
					}
					Map<String, String> listaSelezione = Utils.getListaSiNo(myProperties);
					Map<String, String> listaSelezioneFad = Utils.getListaFadSiNo(myProperties);
					modelWiev.addObject("listaSelezione", listaSelezione);
					modelWiev.addObject("listaSelezioneFad", listaSelezioneFad);
					modelWiev.addObject("title", "Modifica Piano Di Formazione");
					modelWiev.addObject("message", myProperties.getProperty("piani.formazione.modifica"));
					modelWiev.addObject("pianoFormazioneForm", pianoDiFormazione);
					if (!bindingResult.hasErrors()){
						modelWiev.addObject("disabled", true);
					}else{
						modelWiev.addObject("disabled", false);
					}
					modelWiev.setViewName("modificaPianoFormazione");
				 return modelWiev;

				}
				
				// eleimina il piano selezionato, i relativi modoli e lavoratori
				@RequestMapping(value ={ "/adminCancellaPiano","/userCancellaPiano"}, method = RequestMethod.POST)
				public ModelAndView cancellaPiano(HttpServletRequest request,  @ModelAttribute("pianoDIformazioneBean") PianoDIformazioneBean pianoDIformazioneBean) {
					ModelAndView modelWiev = new ModelAndView();
					ArrayList<PianoDIformazioneBean> listaPiani = new ArrayList<>();
					ApplicationContext context=null;
					try {
						 CalendarioBean calendario = new CalendarioBean();
						 LavoratoriBean lavoratori = new LavoratoriBean();
						 RendicontazioneBean rendicontazione = new RendicontazioneBean();
						 calendario.setIdPiano(pianoDIformazioneBean.getId());
						 lavoratori.setIdPiano(pianoDIformazioneBean.getId());
						 rendicontazione.setIdPiano(pianoDIformazioneBean.getId());
						 context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
						 PianiFormazioneDao pianiFormazioneDao = (PianiFormazioneDao) context.getBean("PianiFormazioneDaoImpl");
						 CalendarioDao calendarioDao = (CalendarioDao) context.getBean("CalendarioDaoImpl");
						 LavoratoriDao lavoratoriDao = (LavoratoriDao) context.getBean("LavoratoriDaoImpl");
						 RendicontazioneDao rendicontazioneDao = (RendicontazioneDao) context.getBean("RendicontazioneDaoImpl");
						 calendarioDao.deleteCalednariPiano(calendario);
						 lavoratoriDao.deleteLavoratoriPiano(lavoratori);
						 rendicontazioneDao.eliminaRendicontazionePiano(rendicontazione);
						 pianiFormazioneDao.deletePianoDiFormazione(pianoDIformazioneBean);
						 UsersBean user = new UsersBean();
						 user.setUsername(request.getUserPrincipal().getName());
						 listaPiani = pianiFormazioneDao.getAllPiani(user.getUsername());
						
					
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						logger.error(e.getMessage());
						throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
					}finally{
						if(context!=null){
							((ConfigurableApplicationContext)context).close();
						}
						
					}	
					 PianoDIformazioneBean pianoFormazioneForm = new PianoDIformazioneBean();
						modelWiev.addObject("title", "Tabella Piani  Di Formazione");
						modelWiev.addObject("message", myProperties.getProperty("piani.formazione.elenco"));
						modelWiev.addObject("listaPiani", listaPiani);
						modelWiev.addObject("pianoFormazioneForm", pianoFormazioneForm);
						modelWiev.setViewName("pianiDiFormazioneTabella");
					
						return modelWiev;
					}
				
				// per richieste future
				@RequestMapping(value ={ "/adminCancellaTuttiPiani","/userCancellaTuttiPiani"}, method = RequestMethod.POST)
				public ModelAndView cancellaTuttiPiani(HttpServletRequest request, @ModelAttribute("pianoDIformazioneBean") PianoDIformazioneBean pianoFormazione) {
					ModelAndView modelWiev = new ModelAndView();
					ArrayList<PianoDIformazioneBean> listaPiani = new ArrayList<>();
					ApplicationContext context=null;
					try {
						 context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
						 PianiFormazioneDao pianiFormazioneDao = (PianiFormazioneDao) context.getBean("PianiFormazioneDaoImpl");
						 
						 pianiFormazioneDao.deleteAllPianiDIFormazione(pianoFormazione.getUsername());
						 pianiFormazioneDao.caricaPianiFormazione(listaPiani);
					
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						logger.error(e.getMessage());
						throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
					}finally{
						if(context!=null){
							((ConfigurableApplicationContext)context).close();
						}
						
					}	
						modelWiev.addObject("title", "Tabella Piani  Di Formazione");
						modelWiev.addObject("message", myProperties.getProperty("piani.formazione.elenco"));
						modelWiev.addObject("listaPiani", listaPiani);
						modelWiev.setViewName("pianiDiFormazioneTabella");
					
						return modelWiev;
					}
				
                //metodo che inoltra sulla pagina di gestione del moduoo selezionato
				@RequestMapping(value = { "/adminGestioneModulo","/userGestioneModulo"}, method = RequestMethod.POST)
				public ModelAndView gestioneModulo(HttpServletRequest request, @ModelAttribute("pianoFormazioneForm") PianoDIformazioneBean pianoDiFormazione) {
					
					ModelAndView model = new ModelAndView();
					CalendarioBean calendarioModuloBean = new CalendarioBean();
					LavoratoriBean lavoratoriBean = new LavoratoriBean();
					calendarioModuloBean.setIdPiano(pianoDiFormazione.getId());
					calendarioModuloBean.setNomeModulo(pianoDiFormazione.getModulo1());
					ArrayList<CalendarioBean> listaCalendario = new ArrayList<>();
					ApplicationContext context=null;
					if (pianoDiFormazione.getFadMod1().equalsIgnoreCase("fad")){
						try{
						 context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
						 CalendarioDao calendarioDao = (CalendarioDao) context.getBean("CalendarioDaoImpl");
						 listaCalendario = calendarioDao.getAllCalednario(calendarioModuloBean);
						 if (listaCalendario!=null&&!listaCalendario.isEmpty()){
							 // cancello il calendario che era aula e adesso è diventato fad
							 calendarioDao.deleteAllCalednari(calendarioModuloBean);
							 
						 }
						}catch(Exception e){
							e.printStackTrace();
							logger.error(e.getMessage());
							throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
						}
						finally{
							if(context!=null){
								((ConfigurableApplicationContext)context).close();
							}
							
						}
						
					}
					lavoratoriBean.setIdPiano(pianoDiFormazione.getId());
					lavoratoriBean.setNomeModulo(pianoDiFormazione.getModulo1());
					lavoratoriBean.setModalitaFormatvia(pianoDiFormazione.getFadMod1());
					if (pianoDiFormazione.getFadMod1().equalsIgnoreCase("fad")){
						model.addObject("disabled", true);
					}else{
						model.addObject("disabled", false);
					}
					model.addObject("calendarioModuloBean", calendarioModuloBean);
					model.addObject("lavoratoriBean", lavoratoriBean);
					model.addObject("title", "Modulo "+calendarioModuloBean.getNomeModulo()+" Modalità Di Formazione "+pianoDiFormazione.getFadMod1());
					model.addObject("message", "pagina di gestione del modulo associato al progetto");
					model.addObject("calendario", "Sezione Calendario");
					model.addObject("lavoratori", "Sezione Lavoratori");
					model.setViewName("visualizzaModulo");
					return model;

				}
				
				// metodo che elabora il file excel piani di formazione
				@RequestMapping(value ={ "/adminElaboraUploadModuloCalendario","/userElaboraUploadModuloCalendario"}, method = RequestMethod.POST)
				public ModelAndView elaboraUploadModuloCalendario(HttpServletRequest request,  @ModelAttribute("calendarioModuloBean") CalendarioBean calendarioBean,
						@ModelAttribute("lavoratoriBean") LavoratoriBean lavoratoriBean	) {
					ModelAndView modelWiev = new ModelAndView();
					ArrayList<HashMap<String, String>> listaExcel = new ArrayList<>();
					ArrayList<CalendarioBean> listaCalendario = new ArrayList<>();
					FileBean fileBean = new FileBean();
					fileBean.setFileData(calendarioBean.fileData);
					String nomeModulo ="";
					ApplicationContext context=null;
					try {
						
						ImportServiceExcel importService = new ImportServiceExcel();
						listaExcel = importService.importFile(fileBean);
						listaCalendario = ExcelValidator.listaCalendarioValidator(listaExcel, calendarioBean);
						context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
						CalendarioDao calendarioDao = (CalendarioDao) context.getBean("CalendarioDaoImpl");
						calendarioDao.caricaCalendari(listaCalendario);
						listaCalendario = calendarioDao.getAllCalednario(calendarioBean);
						listaCalendario = Utils.calendarioModuloFormSetting(listaCalendario);
						
						 
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						logger.error(e.getMessage());
						modelWiev.addObject("errorMessage", myProperties.getProperty("errore.file.excel"));
						modelWiev.addObject("calendarioModuloBean", calendarioBean);
						modelWiev.addObject("lavoratoriBean", lavoratoriBean);
						modelWiev.addObject("title", "Gestione Modulo "+calendarioBean.getNomeModulo()+" Tipologia Aula");
						modelWiev.addObject("message", "pagina di gestione del modulo associato al piano");
						modelWiev.addObject("calendario", "Sezione Calendario");
						modelWiev.addObject("lavoratori", "Sezione Lavoratori");
						modelWiev.setViewName("visualizzaModulo");
						return modelWiev;
					}finally{
						if(context!=null){
							((ConfigurableApplicationContext)context).close();
						}
						
						
					}
					if (listaCalendario!=null&!listaCalendario.isEmpty()){
						nomeModulo = listaCalendario.get(0).getNomeModulo();
					}
					CalendarioBean calendarioBeanForm = new CalendarioBean();
					PianoDIformazioneBean pianoFormazioneForm = new PianoDIformazioneBean();
					pianoFormazioneForm.setId(calendarioBean.getIdPiano());
					pianoFormazioneForm.setModulo1(calendarioBean.getNomeModulo());
					modelWiev.addObject("title", "Tabella Calendario Modulo "+nomeModulo);
					modelWiev.addObject("message", myProperties.getProperty("calendario.elenco"));
					modelWiev.addObject("listaCalendari", listaCalendario);
					modelWiev.addObject("calendarioBeanForm",calendarioBean);
					modelWiev.addObject("pianoFormazioneForm",pianoFormazioneForm);
					modelWiev.setViewName("calendarioModuloTabella");
					return modelWiev;
				}
				
				
				// metodo che carica e proietta il calendario associato al modulo
				@RequestMapping(value ={ "/adminMostraCalendario","/userMostraCalendario"}, method = RequestMethod.POST)
				public ModelAndView mostraCalendario( @ModelAttribute("calendarioBeanForm") CalendarioBean calendarioBean) {
					ModelAndView modelWiev = new ModelAndView();
					ArrayList<CalendarioBean> listaCalendario = new ArrayList<>();
					String nomeModulo ="";
					ApplicationContext context=null;
					try {
						context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
						 CalendarioDao calendarioDao = (CalendarioDao) context.getBean("CalendarioDaoImpl");
						 listaCalendario = calendarioDao.getAllCalednario(calendarioBean);
						 listaCalendario = Utils.calendarioModuloFormSetting(listaCalendario);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						logger.error(e.getMessage());
						throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
					}finally{
						if(context!=null){
							((ConfigurableApplicationContext)context).close();
						}
					}	
					if(listaCalendario!= null&!listaCalendario.isEmpty()){
						nomeModulo=listaCalendario.get(0).getNomeModulo();
					}
  					PianoDIformazioneBean pianoDIformazioneBean = new PianoDIformazioneBean();
 					pianoDIformazioneBean.setId(calendarioBean.getIdPiano());
 					pianoDIformazioneBean.setModulo1(calendarioBean.getNomeModulo());
					
					modelWiev.addObject("title", "Tabella Calendario Modulo "+nomeModulo);
					modelWiev.addObject("message", myProperties.getProperty("calendario.elenco"));
					modelWiev.addObject("listaCalendari", listaCalendario);
					modelWiev.addObject("calendarioBeanForm",calendarioBean);
					modelWiev.addObject("pianoFormazioneForm", pianoDIformazioneBean);
					modelWiev.setViewName("calendarioModuloTabella");
					return modelWiev;
				}
				
				
				// metodo modifica il giorno del calendario 
				@RequestMapping(value ={"/adminModificaGiornoForm","/userModificaGiornoForm"} , method = RequestMethod.POST)
				public ModelAndView modificaGiornoForm(ModelMap model, @ModelAttribute("calendarioBeanForm") CalendarioBean calendarioBean) {
		             
					ModelAndView modelWiev = new ModelAndView();
					ApplicationContext context=null;
					CalendarioBean calendarioBeanForm = new CalendarioBean();
					
					try{
						context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
						CalendarioDao calendarioDao = (CalendarioDao) context.getBean("CalendarioDaoImpl");
						
						calendarioBeanForm= calendarioDao.findGiornoCalendario(calendarioBean);
						calendarioBeanForm.setDataStr(Utils.formattaData(calendarioBeanForm.getData()));
					
					}catch(Exception e){
						e.printStackTrace();
						logger.error(e.getMessage());
						throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
					}finally{
						if(context!=null){
							((ConfigurableApplicationContext)context).close();
						}
					}	
					
					modelWiev.addObject("title", "Modifica Calendario Giorno");
					modelWiev.addObject("message", myProperties.getProperty("calendario.giorno.modifica"));
					modelWiev.addObject("calendarioBeanForm", calendarioBeanForm);
					
					modelWiev.setViewName("modificaGiornoCalendario");
				 return modelWiev;

				}
				
				// metodo acquisizione modifica giorno calendario
				@RequestMapping(value = {"/adminModificaGiorno","/userModificaGiorno"} , method = RequestMethod.POST)
				public ModelAndView adminModificaGiorno(ModelMap model,@Valid @ModelAttribute("calendarioBeanForm") CalendarioBean calendarioBean, BindingResult bindingResult) {
		             
					ModelAndView modelWiev = new ModelAndView();
					
				ApplicationContext context=null;
				
					try{
						validator.calendarioValidator(calendarioBean, bindingResult, myProperties);
						if(!bindingResult.hasErrors()){
							calendarioBean.setStato("1");
							context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
							 CalendarioDao calendarioDao = (CalendarioDao) context.getBean("CalendarioDaoImpl");
							 calendarioBean.setData(Utils.dataDBFormatter(calendarioBean.getDataStr()));
							 calendarioDao.updateCalednario(calendarioBean);
							 }   
					
					}catch(Exception e){
						e.printStackTrace();
						logger.error(e.getMessage());
						throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
					}finally{
						if(context!=null){
							((ConfigurableApplicationContext)context).close();
						}
					}	
					modelWiev.addObject("title", "Modifica Calendario Giorno");
					modelWiev.addObject("message", myProperties.getProperty("calendario.giorno.modifica"));
					modelWiev.addObject("calendarioBeanForm", calendarioBean);
					if(!bindingResult.hasErrors()){
						modelWiev.addObject("disabled", true);
					}else{
						modelWiev.addObject("disabled", false);
					}
					modelWiev.setViewName("modificaGiornoCalendario");
				 return modelWiev;
				
				}
				
				// cancellazione giornata calendario
				@RequestMapping(value = {"/adminEliminaGiornoForm","/userEliminaGiornoForm"} , method = RequestMethod.POST)
				public ModelAndView eliminaGiornoForm(HttpServletRequest request, ModelMap model, @ModelAttribute("calendarioBeanForm") CalendarioBean calendarioBean, BindingResult bindingResult) {
		             
				ModelAndView modelWiev = new ModelAndView();
				ArrayList<CalendarioBean> listaCalendario = new ArrayList<>();
				String nomeModulo ="";
				ApplicationContext context=null;
					try{
						if(!bindingResult.hasErrors()){
							context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
							 CalendarioDao calendarioDao = (CalendarioDao) context.getBean("CalendarioDaoImpl");
							 calendarioDao.deleteCalednario(calendarioBean);
							 listaCalendario = calendarioDao.getAllCalednario(calendarioBean);
							 listaCalendario = Utils.calendarioModuloFormSetting(listaCalendario);
							 if (!(listaCalendario!=null&&!listaCalendario.isEmpty())){
								 PianiFormazioneDao pianiFormazioneDao = (PianiFormazioneDao) context.getBean("PianiFormazioneDaoImpl");
								 PianoDIformazioneBean pianoFormazione = new PianoDIformazioneBean();
								 pianoFormazione.setId(calendarioBean.getIdPiano());
								 pianoFormazione.setUsername(request.getUserPrincipal().getName());
								 pianoFormazione.setEnabled(myProperties.getProperty("enabled.no"));
								 pianiFormazioneDao.updateStatoPianoDiFormazione(pianoFormazione);
							 }
						}   
					
					}catch(Exception e){
						e.printStackTrace();
						logger.error(e.getMessage());
						throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
					}finally{
						if(context!=null){
							((ConfigurableApplicationContext)context).close();
						}
					}	
					if(listaCalendario!= null&!listaCalendario.isEmpty()){
						nomeModulo=listaCalendario.get(0).getNomeModulo();
					}
  					PianoDIformazioneBean pianoDIformazioneBean = new PianoDIformazioneBean();
 					pianoDIformazioneBean.setId(calendarioBean.getIdPiano());
 					pianoDIformazioneBean.setModulo1(calendarioBean.getNomeModulo());
					
					
					modelWiev.addObject("title", "Tabella Calendario Modulo "+nomeModulo);
					modelWiev.addObject("message", myProperties.getProperty("calendario.elenco"));
					modelWiev.addObject("listaCalendari", listaCalendario);
					modelWiev.addObject("calendarioBeanForm",calendarioBean);
					modelWiev.addObject("pianoFormazioneForm", pianoDIformazioneBean);
					modelWiev.setViewName("calendarioModuloTabella");
					return modelWiev;
				
				}
				
				// metodo che elabora il file excel dei lavoratori
				@RequestMapping(value ={ "/adminElaboraUploadModuloLavoratori","/userElaboraUploadModuloLavoratori"}, method = RequestMethod.POST)
				public ModelAndView elaboraUploadModuloLavoratori(HttpServletRequest request,  @ModelAttribute("calendarioModuloBean") CalendarioBean calendarioBean,
					@ModelAttribute("lavoratoriBean") LavoratoriBean lavoratoriBean	) {
					ModelAndView modelWiev = new ModelAndView();
					ArrayList<HashMap<String, String>> listaExcel = new ArrayList<>();
					ArrayList<LavoratoriBean> listaLavoratori = new ArrayList<>();
					FileBean fileBean = new FileBean();
					fileBean.setFileData(calendarioBean.fileData);
					String nomeModulo ="";
					ApplicationContext context=null;
					try {
						
						ImportServiceExcel importService = new ImportServiceExcel();
						listaExcel = importService.importFile(fileBean);
						listaLavoratori = ExcelValidator.listaLavoratoriValidator(listaExcel, lavoratoriBean);
						context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
						LavoratoriDao lavoratoriDao = (LavoratoriDao) context.getBean("LavoratoriDaoImpl");
						lavoratoriDao.caricaLavoratori(listaLavoratori);
						listaLavoratori = lavoratoriDao.getAllLavoratori(lavoratoriBean); 
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						logger.error(e.getMessage());
						modelWiev.addObject("errorMessage", myProperties.getProperty("errore.file.excel"));
						modelWiev.setViewName("visualizzaModulo");
						return modelWiev;
						
					}finally{
						if(context!=null){
							((ConfigurableApplicationContext)context).close();
						}
						
						
					}
					if (listaLavoratori!=null&!listaLavoratori.isEmpty()){
						nomeModulo = listaLavoratori.get(0).getNomeModulo();
					}
					LavoratoriBean lavoratoreBean = new LavoratoriBean();
					PianoDIformazioneBean pianoFormazioneForm = new PianoDIformazioneBean();
					pianoFormazioneForm.setId(lavoratoriBean.getIdPiano());
					pianoFormazioneForm.setModulo1(lavoratoriBean.getNomeModulo());
					// risetto la modalità formativa del modulo selezionato per disabilitare in caso di fad il tasto visualizzacalendario
					pianoFormazioneForm.setFadMod1(lavoratoriBean.getModalitaFormatvia());
					modelWiev.addObject("title", "Tabella Lavoratori Modulo "+nomeModulo);
					modelWiev.addObject("message", myProperties.getProperty("lavoratori.elenco"));
					modelWiev.addObject("listaLavoratori", listaLavoratori);
					modelWiev.addObject("lavoratoreBeanForm",lavoratoreBean);
					modelWiev.addObject("pianoFormazioneForm",pianoFormazioneForm);
					modelWiev.setViewName("lavoratoriModuloTabella");
					return modelWiev;
				}
				
				
				// carica i lavoratori inseriti
				@RequestMapping(value ={ "/adminMostraLavoratori","/userMostraLavoratori"}, method = RequestMethod.POST)
				public ModelAndView mostraLavoratori( @ModelAttribute("lavoratoriBean") LavoratoriBean lavoratoriBean) {
					ModelAndView modelWiev = new ModelAndView();
					ArrayList<LavoratoriBean> listaLavoratori = new ArrayList<>();


					ApplicationContext context=null;
					try {
						context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
						LavoratoriDao lavoratoriDao = (LavoratoriDao) context.getBean("LavoratoriDaoImpl");
						listaLavoratori = lavoratoriDao.getAllLavoratori(lavoratoriBean);
						
						 
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						logger.error(e.getMessage());
						throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
					}finally{
						if(context!=null){
							((ConfigurableApplicationContext)context).close();
						}
					}	
					
  					PianoDIformazioneBean pianoDIformazioneBean = new PianoDIformazioneBean();
 					pianoDIformazioneBean.setId(lavoratoriBean.getIdPiano());
 					pianoDIformazioneBean.setModulo1(lavoratoriBean.getNomeModulo());
 					pianoDIformazioneBean.setFadMod1(lavoratoriBean.getModalitaFormatvia());
 					
					
					modelWiev.addObject("title", "Tabella Lavoratori Modulo "+lavoratoriBean.getNomeModulo());
					modelWiev.addObject("message", myProperties.getProperty("lavoratori.elenco"));
					modelWiev.addObject("lavoratoreBeanForm", lavoratoriBean);
					modelWiev.addObject("listaLavoratori",listaLavoratori);
					modelWiev.addObject("pianoFormazioneForm", pianoDIformazioneBean);
					modelWiev.setViewName("lavoratoriModuloTabella");
					return modelWiev;
					
						
					}
				
				// metodo modifica lavoratore form
				@RequestMapping(value ={"/adminModificaLavoratoreForm","/userModificaLavoratoreForm"} , method = RequestMethod.POST)
				public ModelAndView modificaLavoratoreForm(ModelMap model, @ModelAttribute("lavoratoriBean") LavoratoriBean lavoratoriBean) {
		             
					ModelAndView modelWiev = new ModelAndView();
					ApplicationContext context=null;
					LavoratoriBean lavoratoriBeanForm = new LavoratoriBean();
					
					try{
						context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
						LavoratoriDao lavoratoriDao = (LavoratoriDao) context.getBean("LavoratoriDaoImpl");
						lavoratoriBeanForm = lavoratoriDao.findLavoratore(lavoratoriBean);
						lavoratoriBeanForm.setModalitaFormatvia(lavoratoriBean.getModalitaFormatvia());
					((ConfigurableApplicationContext)context).close();
					}catch(Exception e){
						e.printStackTrace();
						logger.error(e.getMessage());
						throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
					}finally{
						if(context!=null){
							((ConfigurableApplicationContext)context).close();
						}
					}	
					
					modelWiev.addObject("title", "Modifica dati lavoratore");
					modelWiev.addObject("message", myProperties.getProperty("lavoratori.modifica"));
					modelWiev.addObject("lavoratoriBean", lavoratoriBeanForm);
					
					modelWiev.setViewName("modificaLavoratore");
				 return modelWiev;

				}
				
				// metodo modifica lavoratore
				@RequestMapping(value = {"/adminModificaLavoratore","/userModificaLavoratore"} , method = RequestMethod.POST)
				public ModelAndView modificaLavoratore(ModelMap model,@Valid @ModelAttribute("lavoratoriBean") LavoratoriBean lavoratoriBean, BindingResult bindingResult) {
		             
					ModelAndView modelWiev = new ModelAndView();
					
				ApplicationContext context=null;
				
					try{
						validator.lavoratoriValidator(lavoratoriBean, bindingResult, myProperties);
						if(!bindingResult.hasErrors()){
							lavoratoriBean= Utils.formattaLavoratore(lavoratoriBean);
							lavoratoriBean.setStato("1");
							context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
							LavoratoriDao lavoratoriDao = (LavoratoriDao) context.getBean("LavoratoriDaoImpl");
							
							lavoratoriDao.updateLavoratore(lavoratoriBean);
						 }   
					
					}catch(Exception e){
						e.printStackTrace();
						logger.error(e.getMessage());
						throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
					}finally{
						if(context!=null){
							((ConfigurableApplicationContext)context).close();
						}
					}	
					modelWiev.addObject("title", "Modifica dati lavoratore");
					modelWiev.addObject("message", myProperties.getProperty("lavoratori.modifica"));
					modelWiev.addObject("lavoratoriBean", lavoratoriBean);
					if(!bindingResult.hasErrors()){
						modelWiev.addObject("disabled", true);
					}else{
						modelWiev.addObject("disabled", false);
					}
					modelWiev.setViewName("modificaLavoratore");
				 return modelWiev;
				
				}
				
				// cancellazione lavoratore
				@RequestMapping(value = {"/adminEliminaLavoratore","/userEliminaLavoratore"} , method = RequestMethod.POST)
				public ModelAndView eliminaLavoratore(HttpServletRequest request,ModelMap model, @ModelAttribute("lavoratoreBeanForm") LavoratoriBean lavoratoriBean, BindingResult bindingResult) {
		             
				ModelAndView modelWiev = new ModelAndView();
				ArrayList<LavoratoriBean> listaLavoratori = new ArrayList<>();
				String nomeModulo ="";
				ApplicationContext context=null;
					try{
						if(!bindingResult.hasErrors()){
							context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
							LavoratoriDao lavoratoriDao = (LavoratoriDao) context.getBean("LavoratoriDaoImpl");
							lavoratoriDao.deleteLavoratore(lavoratoriBean);
							 
							listaLavoratori = lavoratoriDao.getAllLavoratori(lavoratoriBean);
							 if (!(listaLavoratori!=null&&!listaLavoratori.isEmpty())){
								 PianiFormazioneDao pianiFormazioneDao = (PianiFormazioneDao) context.getBean("PianiFormazioneDaoImpl");
								 PianoDIformazioneBean pianoFormazione = new PianoDIformazioneBean();
								 pianoFormazione.setId(lavoratoriBean.getIdPiano());
								 pianoFormazione.setUsername(request.getUserPrincipal().getName());
								 pianoFormazione.setEnabled(myProperties.getProperty("enabled.no"));
								 pianiFormazioneDao.updateStatoPianoDiFormazione(pianoFormazione);
							 }
							 
						}   
					
					}catch(Exception e){
						e.printStackTrace();
						logger.error(e.getMessage());
						throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
					}finally{
						if(context!=null){
							((ConfigurableApplicationContext)context).close();
						}
					}	
					if(listaLavoratori!= null&!listaLavoratori.isEmpty()){
						nomeModulo=listaLavoratori.get(0).getNomeModulo();
					}
  					PianoDIformazioneBean pianoDIformazioneBean = new PianoDIformazioneBean();
 					pianoDIformazioneBean.setId(lavoratoriBean.getIdPiano());
 					pianoDIformazioneBean.setModulo1(lavoratoriBean.getNomeModulo());
 					pianoDIformazioneBean.setFadMod1(lavoratoriBean.getModalitaFormatvia());
					
					
					modelWiev.addObject("title", "Modifica dati lavoratore "+nomeModulo);
					modelWiev.addObject("message", myProperties.getProperty("lavoratori.modifica"));
					modelWiev.addObject("listaLavoratori", listaLavoratori);
					modelWiev.addObject("lavoratoriBeanForm",lavoratoriBean);
					modelWiev.addObject("pianoFormazioneForm", pianoDIformazioneBean);
					modelWiev.setViewName("lavoratoriModuloTabella");
					return modelWiev;
				
				}
				
				// metodo che completa il piano con i moduli e la rendicontazione
				@RequestMapping(value ={"/adminImplementaPianoForm","/userImplementaPianoForm"} , method = RequestMethod.POST)
				public ModelAndView implementaPianoForm(ModelMap model, @ModelAttribute("pianoFormazioneForm") PianoDIformazioneBean pianoFormazione) {
		             
					ModelAndView modelWiev = new ModelAndView();
					ApplicationContext context=null;
					ImplementaPianoFormBean implementaPianoFormBean = new ImplementaPianoFormBean();
					implementaPianoFormBean.setId(pianoFormazione.getId());
					implementaPianoFormBean.setModulo1(pianoFormazione.getModulo1());
					implementaPianoFormBean.setModulo2(pianoFormazione.getModulo2());
					ArrayList<LavoratoriBean> listaLavoratori = new ArrayList<>();
					ArrayList<CalendarioBean> listaCalendario = new ArrayList<>();
					ArrayList<RendicontazioneBean> listaRendicontazione= new ArrayList<>();
					try{
					
							context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
							CalendarioDao calendarioDao = (CalendarioDao) context.getBean("CalendarioDaoImpl");
							LavoratoriDao lavoratoriDao = (LavoratoriDao) context.getBean("LavoratoriDaoImpl");
							RendicontazioneDao rendicontazioneDao = (RendicontazioneDao) context.getBean("RendicontazioneDaoImpl");
							listaCalendario = calendarioDao.esisteCalendario(pianoFormazione);
							listaLavoratori = lavoratoriDao.esistonoLavoratori(pianoFormazione);
							listaRendicontazione = rendicontazioneDao.getAllrendicontazione(pianoFormazione);
							if (listaCalendario!=null&&!listaCalendario.isEmpty()){
								modelWiev.addObject("calendario", myProperties.getProperty("calendari.esistono"));
							}
							if (listaLavoratori!=null&&!listaLavoratori.isEmpty()){
								modelWiev.addObject("lavoratori", myProperties.getProperty("lavoratori.esistono"));
							}
							if (listaRendicontazione!=null&&!listaRendicontazione.isEmpty()){
								modelWiev.addObject("rendicontazione", myProperties.getProperty("rendicontazione.esiste"));
							}
					}catch(Exception e){
						e.printStackTrace();
						logger.error(e.getMessage());
						throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
					}finally{
						if(context!=null){
							((ConfigurableApplicationContext)context).close();
						}
					}	
					modelWiev.addObject("title", "Inserimento dei collegati al progetto");
					modelWiev.addObject("message", myProperties.getProperty("implementazione.form"));
					modelWiev.addObject("calendariTitolo", "Sezione Calendario");
					modelWiev.addObject("lavoratoriTitolo", "Sezione Lavoratori");
					modelWiev.addObject("rendicontazioneTitolo", "Sezione Rendicontazione");
					modelWiev.addObject("implementaPianoFormBean", implementaPianoFormBean);
					modelWiev.setViewName("implementaProgetto");
				 return modelWiev;

				}
				
				

				// metodo che elabora il file excel dei lavoratori
				@RequestMapping(value ={ "/adminImplementaPiano","/userImplementaPiano"}, method = RequestMethod.POST)
				public ModelAndView adminImplementaPiano (HttpServletRequest request,  @ModelAttribute("implementaPianoFormBean") ImplementaPianoFormBean implementaPianoFormBean,
					@ModelAttribute("lavoratoriBean") LavoratoriBean lavoratoriBean	) {
					ModelAndView modelWiev = new ModelAndView();
					PianoDIformazioneBean pianoFormazione = new PianoDIformazioneBean();
					pianoFormazione.setId(implementaPianoFormBean.getId());
					ArrayList<HashMap<String, String>> listaExcel = new ArrayList<>();
					ArrayList<PianoDIformazioneBean> listaPiani= new ArrayList<>();
					ArrayList<CalendarioBean> listaCalendario1= new ArrayList<>();
					ArrayList<CalendarioBean> listaCalendario2= new ArrayList<>();
					ArrayList<LavoratoriBean> listaLavoratori1 = new ArrayList<>();
					ArrayList<LavoratoriBean> listaLavoratori2 = new ArrayList<>();
					ArrayList<RendicontazioneBean> listaRendicontazione = new ArrayList<>();
					FileBean fileBean = new FileBean();
					ImportServiceExcel importService = new ImportServiceExcel();
					String nomeModulo ="";
					ApplicationContext context=null;
					try {
						
					    //sezione caricamento file calendario
						if(implementaPianoFormBean.getFileData1()!=null&&!implementaPianoFormBean.getFileData1().isEmpty()){
							// carico i calendari per i due moduli
							fileBean.setFileData(implementaPianoFormBean.fileData1);
							
							try{
								listaExcel = importService.importFile(fileBean);
								listaCalendario1 = ExcelValidator.listaCalendariModuliValidator(listaExcel, implementaPianoFormBean.getId(), implementaPianoFormBean.getModulo1(), myProperties);
								listaCalendario2 = ExcelValidator.listaCalendariModuliValidator(listaExcel, implementaPianoFormBean.getId(), implementaPianoFormBean.getModulo2(), myProperties);
							}catch(Exception e){
								if (!implementaPianoFormBean.getFileData1().getOriginalFilename().endsWith("xls")&&!implementaPianoFormBean.getFileData1().getOriginalFilename().endsWith("xlsx")){
									modelWiev.addObject("errorMessage",implementaPianoFormBean.getFileData1().getOriginalFilename()+" "+ myProperties.getProperty("no.xls.xlsx",implementaPianoFormBean.fileData1.getName()));
								}else{
									modelWiev.addObject("errorMessage", myProperties.getProperty("errore.file.excel",implementaPianoFormBean.fileData1.getName())+" "+implementaPianoFormBean.getFileData1().getOriginalFilename());
								}
								
								throw e;
							}
						}
						
						//sezione caricamento file lavoratori
						if(implementaPianoFormBean.getFileData2()!=null&&!implementaPianoFormBean.getFileData2().isEmpty()){
							// carico i lavortori per i due moduli
							fileBean.setFileData(implementaPianoFormBean.fileData2);
							
							try{
								listaExcel = importService.importFile(fileBean);
								listaLavoratori1 = ExcelValidator.listaLavoratoriModuliValidator(listaExcel,implementaPianoFormBean.getId(), implementaPianoFormBean.getModulo1(), myProperties);
								listaLavoratori2 = ExcelValidator.listaLavoratoriModuliValidator(listaExcel,implementaPianoFormBean.getId(), implementaPianoFormBean.getModulo2(), myProperties);
							}catch(Exception e){
								if (!implementaPianoFormBean.getFileData2().getOriginalFilename().endsWith("xls")&&!implementaPianoFormBean.getFileData2().getOriginalFilename().endsWith("xlsx")){
									modelWiev.addObject("errorMessage",implementaPianoFormBean.getFileData2().getOriginalFilename()+" "+ myProperties.getProperty("no.xls.xlsx",implementaPianoFormBean.fileData1.getName()));
								}else{
									modelWiev.addObject("errorMessage", myProperties.getProperty("errore.file.excel",implementaPianoFormBean.fileData1.getName())+" "+implementaPianoFormBean.getFileData2().getOriginalFilename());
								}
								throw e;
							}
						}
						
						//sezione caricamento file rendicontazione
						if(implementaPianoFormBean.getFileData3()!=null&&!implementaPianoFormBean.getFileData3().isEmpty()){
							// carico i lavortori per i due moduli
							fileBean.setFileData(implementaPianoFormBean.fileData3);
							
							try{
								listaExcel = importService.importFile(fileBean);
								listaRendicontazione = ExcelValidator.listaRendicontazioneValidator(listaExcel, implementaPianoFormBean.getId(), myProperties);
							}catch(Exception e){
								if (!implementaPianoFormBean.getFileData3().getOriginalFilename().endsWith("xls")&&!implementaPianoFormBean.getFileData3().getOriginalFilename().endsWith("xlsx")){
									modelWiev.addObject("errorMessage",implementaPianoFormBean.getFileData3().getOriginalFilename()+" "+ myProperties.getProperty("no.xls.xlsx",implementaPianoFormBean.fileData1.getName()));
								}else{
									modelWiev.addObject("errorMessage", myProperties.getProperty("errore.file.excel",implementaPianoFormBean.fileData1.getName())+" "+implementaPianoFormBean.getFileData3().getOriginalFilename());
								}
								throw e;
							}
							
						}
						
						context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
						PianiFormazioneDao pianiFormazioneDao = (PianiFormazioneDao) context.getBean("PianiFormazioneDaoImpl");
						CalendarioDao calendarioDao = (CalendarioDao) context.getBean("CalendarioDaoImpl");
						LavoratoriDao lavoratoriDao = (LavoratoriDao) context.getBean("LavoratoriDaoImpl");
						RendicontazioneDao rendicontazioneDao = (RendicontazioneDao) context.getBean("RendicontazioneDaoImpl");
						
						try{
							//costante che serve per aggiornare lo stato del piano nel caso di aggiornamento da excel.
							boolean aggiornato =false;
							if (listaCalendario1!=null&&!listaCalendario1.isEmpty()){
								calendarioDao.caricaCalendari(listaCalendario1);
								aggiornato=true;
							}
							if (listaCalendario2!=null&&!listaCalendario2.isEmpty()){
								calendarioDao.caricaCalendari(listaCalendario2);
								aggiornato=true;
							}
							
							if (listaLavoratori1!=null&&!listaLavoratori1.isEmpty()){
								lavoratoriDao.caricaLavoratori(listaLavoratori1);
								aggiornato=true;
								
							}
							if (listaLavoratori2!=null&&!listaLavoratori2.isEmpty()){
								lavoratoriDao.caricaLavoratori(listaLavoratori2);
								aggiornato=true;
							}
							if (listaRendicontazione!=null&&!listaRendicontazione.isEmpty()){
								rendicontazioneDao.caricaRendicontazione(listaRendicontazione);
								aggiornato=true;
							}
							
							if (aggiornato){
								pianoFormazione.setId(implementaPianoFormBean.getId());
								pianoFormazione.setEnabled(myProperties.getProperty("enabled.no"));
								pianoFormazione.setUsername(request.getUserPrincipal().getName());
								pianiFormazioneDao.updateStatoPianoDiFormazione(pianoFormazione);
							}
						}catch(Exception e){
							modelWiev.addObject("errorMessage", myProperties.getProperty("errore.scrittura.db"));
							throw e;
						}
						
						listaPiani = pianiFormazioneDao.getAllPiani(request.getUserPrincipal().getName());
						
						
					} catch (Exception e) {
						// TODO: handle exception
						ArrayList<LavoratoriBean> listaLavoratori = new ArrayList<>();
						ArrayList<CalendarioBean> listaCalendario = new ArrayList<>();
						try{
						
								context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
								CalendarioDao calendarioDao = (CalendarioDao) context.getBean("CalendarioDaoImpl");
								LavoratoriDao lavoratoriDao = (LavoratoriDao) context.getBean("LavoratoriDaoImpl");
								RendicontazioneDao rendicontazioneDao = (RendicontazioneDao) context.getBean("RendicontazioneDaoImpl");
								listaCalendario = calendarioDao.esisteCalendario(pianoFormazione);
								listaLavoratori = lavoratoriDao.esistonoLavoratori(pianoFormazione);
								listaRendicontazione = rendicontazioneDao.getAllrendicontazione(pianoFormazione);
								if (listaCalendario!=null&&!listaCalendario.isEmpty()){
									modelWiev.addObject("calendario", myProperties.getProperty("calendari.esistono"));
								}
								if (listaLavoratori!=null&&!listaLavoratori.isEmpty()){
									modelWiev.addObject("lavoratori", myProperties.getProperty("lavoratori.esistono"));
								}
								if (listaRendicontazione!=null&&!listaRendicontazione.isEmpty()){
									modelWiev.addObject("rendicontazione", myProperties.getProperty("rendicontazione.esiste"));
								}
						}catch(Exception b){
							
							logger.error(b.getMessage());
							throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
						}
						e.printStackTrace();
						logger.error(e.getMessage());
						modelWiev.setViewName("implementaProgetto");
						return modelWiev;
						
					}finally{
						if(context!=null){
							((ConfigurableApplicationContext)context).close();
						}
						
						
					}
					PianoDIformazioneBean piano = new PianoDIformazioneBean();
					modelWiev.addObject("title", "Tabella Piani Di Formazione");
					modelWiev.addObject("message", myProperties.getProperty("piani.formazione.elenco"));
					modelWiev.addObject("listaPiani", listaPiani);
					modelWiev.addObject("pianoFormazioneForm",piano);
					modelWiev.setViewName("pianiDiFormazioneTabella");
				
					return modelWiev;
				}
				
				// metodo che esegue l'upload degli allegati degli attuatori associandoli ai piani.
				@RequestMapping(value ={ "/adminUploadAttuatoreForm","/userUploadAttuatoreForm"}, method = RequestMethod.POST)
				public ModelAndView uploadAttuatoreForm (HttpServletRequest request,ModelMap model, @ModelAttribute("pianoFormazioneForm") PianoDIformazioneBean pianoFormazione){
					ModelAndView modelWiev = new ModelAndView();
					ImplementaPianoFormBean implementaPianoFormBean = new ImplementaPianoFormBean();
					implementaPianoFormBean.setAttuatorePIVA(pianoFormazione.getAttuatorePIVA());
					ArrayList<PianoDIformazioneBean> listaPiani = new ArrayList<>();
					AttuatoreBean attuatoreBean = new AttuatoreBean();
					ApplicationContext context=null;
					
					
					try {
						context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
						if (pianoFormazione.getAttuatorePIVA()!=null&&!pianoFormazione.getAttuatorePIVA().isEmpty()){
							attuatoreBean.setAttuatorePIVA(pianoFormazione.getAttuatorePIVA());
							attuatoreBean.setUsername(request.getUserPrincipal().getName());
							AttuatoriDao attuatoriDao= (AttuatoriDao) context.getBean("AttuatoreDaoImpl");
							boolean esiste = attuatoriDao.esisteAttuatore(attuatoreBean);
							if(esiste){
								modelWiev.addObject("attuatore", "L'operazione sostituirà tutti gli allegati della partita IVA");
							}
						}else{
							PianiFormazioneDao pianiFormazioneDao = (PianiFormazioneDao) context.getBean("PianiFormazioneDaoImpl");
							listaPiani = pianiFormazioneDao.getAllPiani(request.getUserPrincipal().getName());
							PianoDIformazioneBean piano = new PianoDIformazioneBean();
							modelWiev.addObject("errorMessage", "Il piano selezionato non ha una partita IVA");
							modelWiev.addObject("title", "Tabella Piani Di Formazione");
							modelWiev.addObject("message", myProperties.getProperty("piani.formazione.elenco"));
							modelWiev.addObject("listaPiani", listaPiani);
							modelWiev.addObject("pianoFormazioneForm",piano);
							modelWiev.setViewName("pianiDiFormazioneTabella");
						
							return modelWiev;
							
						}
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						logger.error(e.getMessage());
						throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
					}finally {
						if(context!=null){
							((ConfigurableApplicationContext)context).close();
						}
					}
					modelWiev.addObject("title", "Upload Allegati Attuatore");
					modelWiev.addObject("message", myProperties.getProperty("attuatore.allegati"));
					modelWiev.addObject("implementaPianoFormBean", implementaPianoFormBean);
					modelWiev.setViewName("attuatoreUpload");
					return modelWiev;
				}


				// metodo che esegue l'upload degli allegati degli attuatori associandoli ai piani.
				@RequestMapping(value ={ "/adminUploadAttuatore","/userUploadAttuatore"}, method = RequestMethod.POST)
				public ModelAndView uploadAttuatore (HttpServletRequest request,@ModelAttribute("implementaPianoFormBean") ImplementaPianoFormBean implementaPianoFormBean, BindingResult bindingResult){
					ModelAndView modelWiev = new ModelAndView();
					ArrayList<PianoDIformazioneBean> listaPiani = new ArrayList<>();
					AttuatoreBean attuatoreBean = new AttuatoreBean();
					PianoDIformazioneBean pianoDiFormazione = new PianoDIformazioneBean();
					ApplicationContext context=null;
					try {
						ImportServiceExcel service = new ImportServiceExcel();
						validator.attuatoriValidator(implementaPianoFormBean, bindingResult, myProperties);
						attuatoreBean = service.importaCertificati(implementaPianoFormBean);
						context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
						PianiFormazioneDao pianiFormazioneDao = (PianiFormazioneDao) context.getBean("PianiFormazioneDaoImpl");
						AttuatoriDao attuatoriDao= (AttuatoriDao) context.getBean("AttuatoreDaoImpl");
						attuatoreBean.setAttuatorePIVA(implementaPianoFormBean.getAttuatorePIVA());
						attuatoreBean.setUsername(request.getUserPrincipal().getName());
						if (!bindingResult.hasErrors()){
							boolean esiste = attuatoriDao.esisteAttuatore(attuatoreBean);
							if (!esiste){
								attuatoriDao.creaAttuatore(attuatoreBean);
							}else{
								attuatoriDao.updateAttuatore(attuatoreBean);
							}
							AttuatoreBean attuatoreResult = attuatoriDao.leggiNomiAllegati(attuatoreBean);
							if (attuatoreResult!=null){
								pianoDiFormazione.setAttuatorePIVA(attuatoreResult.getAttuatorePIVA());
								pianoDiFormazione.setNomeAllegato1(attuatoreResult.getNomeAllegato1());
								pianoDiFormazione.setNomeAllegato2(attuatoreResult.getNomeAllegato2());
								pianoDiFormazione.setNomeAllegato3(attuatoreResult.getNomeAllegato3());
								pianoDiFormazione.setNomeAllegato4(attuatoreResult.getNomeAllegato4());
								
							}
							pianiFormazioneDao.updatePianoDiFormazioneAllegati(pianoDiFormazione);
							listaPiani = pianiFormazioneDao.getAllPiani(request.getUserPrincipal().getName());
						}else{
							String errori=" ";
							for (ObjectError errore : bindingResult.getAllErrors() ){
								errori = errori.concat(errore.getDefaultMessage());
								errori= errori.concat("<br>");
							
							}
							modelWiev.addObject("errorMessage", errori);
							modelWiev.addObject("implementaPianoFormBean", implementaPianoFormBean);
							modelWiev.setViewName("attuatoreUpload");
							return modelWiev;
						}
						
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						logger.error(e.getMessage());
						modelWiev.addObject("errorMessage", myProperties.getProperty("errore.filePdf.multipli"));
						modelWiev.addObject("implementaPianoFormBean", implementaPianoFormBean);
						modelWiev.setViewName("attuatoreUpload");
						return modelWiev;
					}finally{
						if(context!=null){
							((ConfigurableApplicationContext)context).close();
						}
					}
					
					PianoDIformazioneBean piano = new PianoDIformazioneBean();
					modelWiev.addObject("title", "Tabella Piani Di Formazione");
					modelWiev.addObject("message", myProperties.getProperty("piani.formazione.elenco"));
					modelWiev.addObject("listaPiani", listaPiani);
					modelWiev.addObject("pianoFormazioneForm",piano);
					modelWiev.setViewName("pianiDiFormazioneTabella");
				
					return modelWiev;
				}
				
				
				// carica la rendicontazione relativa al progetto
				@RequestMapping(value ={ "/adminMostraRendicontazione","/userMostraRendicontazione"}, method = RequestMethod.POST)
				public ModelAndView mostraRendicontazione(HttpServletRequest request,  @ModelAttribute("pianoFormazioneForm") PianoDIformazioneBean pianoFormazione) {
					ModelAndView modelWiev = new ModelAndView();
					ArrayList<RendicontazioneBean> listaRendicontazione = new ArrayList<>();
					ApplicationContext context=null;
					
					try {
						 context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
						 RendicontazioneDao rendicontazioneDao = (RendicontazioneDao) context.getBean("RendicontazioneDaoImpl");
						 listaRendicontazione = rendicontazioneDao.getAllrendicontazione(pianoFormazione);
						 listaRendicontazione = Utils.rendicontazioneFormSetting(listaRendicontazione);
					
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						logger.error(e.getMessage());
						throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
					}finally{
						if(context!=null){
							((ConfigurableApplicationContext)context).close();
						}
					}	
					
						
						RendicontazioneBean rendicontazione = new RendicontazioneBean();
						modelWiev.addObject("title", "Tabella Rendicontazione Progetto");
						modelWiev.addObject("message", myProperties.getProperty("rendicontazione.elenco"));
						modelWiev.addObject("listaRendicontazione", listaRendicontazione);
						modelWiev.addObject("pianoFormazioneForm",pianoFormazione);
						modelWiev.addObject("rendicontazioneBeanForm",rendicontazione);
						modelWiev.setViewName("rendicontazioneTabella");
					
						return modelWiev;
					}
				
				
				// carica la pagina di modifica della rendicontazione
				@RequestMapping(value ={ "/adminModificaRendForm","/userModificaRendForm"}, method = RequestMethod.POST)
				public ModelAndView modificaRendicontazioneForm(@ModelAttribute("rendicontazioneBeanForm") RendicontazioneBean rendicontazioneBeanForm) {
					ModelAndView modelWiev = new ModelAndView();
					
					ApplicationContext context=null;
					
					try {
						 context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
						 RendicontazioneDao rendicontazioneDao = (RendicontazioneDao) context.getBean("RendicontazioneDaoImpl");
						 rendicontazioneBeanForm = rendicontazioneDao.findRendicontazioneById(rendicontazioneBeanForm);
						 rendicontazioneBeanForm.setDataGiustificativoStr(Utils.formattaData(rendicontazioneBeanForm.getDataGiustificativo()));
					
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						logger.error(e.getMessage());
						throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
					}finally{
						if(context!=null){
							((ConfigurableApplicationContext)context).close();
						}
					}	
					
						
						PianoDIformazioneBean pianoDIformazioneBean = new PianoDIformazioneBean();
						pianoDIformazioneBean.setId(rendicontazioneBeanForm.getIdPiano());
						modelWiev.addObject("title", "Pagina di modifica soggetto rendicontato");
						modelWiev.addObject("message", myProperties.getProperty("rendicontazione.modifica"));
						modelWiev.addObject("pianoFormazioneForm", pianoDIformazioneBean);
						modelWiev.addObject("rendicontazioneBeanForm", rendicontazioneBeanForm);
						modelWiev.setViewName("modificaRendicontazione");
					
						return modelWiev;
					}
				
				// modifica il soggetto di rendicontazione
				@RequestMapping(value ={ "/adminModificaRendicontazione","/userModificaRendicontazione"}, method = RequestMethod.POST)
				public ModelAndView modificaRendicontazione( @Valid @ModelAttribute("rendicontazioneBeanForm") RendicontazioneBean rendicontazioneBeanForm, BindingResult bindingResult) {
					ModelAndView modelWiev = new ModelAndView();
					
					ApplicationContext context=null;
					boolean diasbled = false;
					try {
						validator.rendicontazioneValidator(rendicontazioneBeanForm, bindingResult, myProperties);
						if (!bindingResult.hasErrors()){
							 context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
							 RendicontazioneDao rendicontazioneDao = (RendicontazioneDao) context.getBean("RendicontazioneDaoImpl");
							 rendicontazioneBeanForm.setDataGiustificativo(Utils.dataDBFormatter(rendicontazioneBeanForm.getDataGiustificativoStr()));
							 rendicontazioneBeanForm.setStato(myProperties.getProperty("enabled.si"));
							 rendicontazioneDao.updateRendicontazione(rendicontazioneBeanForm);
							 rendicontazioneBeanForm = rendicontazioneDao.findRendicontazioneById(rendicontazioneBeanForm);
							 rendicontazioneBeanForm.setDataGiustificativoStr(Utils.formattaData(rendicontazioneBeanForm.getDataGiustificativo()));
							 diasbled=true;
						}
					
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						logger.error(e.getMessage());
						throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
					}finally{
						if(context!=null){
							((ConfigurableApplicationContext)context).close();
						}
					}	
					
						
					    PianoDIformazioneBean pianoDIformazioneBean = new PianoDIformazioneBean();
					    pianoDIformazioneBean.setId(rendicontazioneBeanForm.getIdPiano());
						modelWiev.addObject("title", "Pagina di modifica soggetto rendicontato");
						modelWiev.addObject("message", myProperties.getProperty("rendicontazione.modifica"));
						modelWiev.addObject("rendicontazioneBeanForm", rendicontazioneBeanForm);
						modelWiev.addObject("pianoFormazioneForm", pianoDIformazioneBean);
						modelWiev.addObject("disabled", diasbled);
						modelWiev.setViewName("modificaRendicontazione");
					
						return modelWiev;
					}
				
				// Elimina il soggetto rendicontato
				@RequestMapping(value ={ "/adminEliminaSoggettoRend","/userEliminaSoggettoRend"}, method = RequestMethod.POST)
				public ModelAndView eliminaSoggettoRend(HttpServletRequest request, @ModelAttribute("rendicontazioneBeanForm") RendicontazioneBean rendicontazioneBeanForm) {
					ModelAndView modelWiev = new ModelAndView();
					ArrayList<RendicontazioneBean> listaRendicontazione = new ArrayList<>();
					ApplicationContext context=null;
					PianoDIformazioneBean pianoFormazione = new PianoDIformazioneBean();
					pianoFormazione.setId(rendicontazioneBeanForm.getIdPiano());
					try {
						 context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
						 RendicontazioneDao rendicontazioneDao = (RendicontazioneDao) context.getBean("RendicontazioneDaoImpl");
						 rendicontazioneDao.eliminaSoggettoRendicontato(rendicontazioneBeanForm);
						 listaRendicontazione = rendicontazioneDao.getAllrendicontazione(pianoFormazione);
						 listaRendicontazione = Utils.rendicontazioneFormSetting(listaRendicontazione);
						 if (!(listaRendicontazione!=null&&!listaRendicontazione.isEmpty())){
							 PianiFormazioneDao pianiFormazioneDao = (PianiFormazioneDao) context.getBean("PianiFormazioneDaoImpl");
							 pianoFormazione.setEnabled(myProperties.getProperty("enabled.no"));
							 pianoFormazione.setUsername(request.getUserPrincipal().getName());
							 pianiFormazioneDao.updateStatoPianoDiFormazione(pianoFormazione);
						 }
					
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						logger.error(e.getMessage());
						throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
					}finally{
						if(context!=null){
							((ConfigurableApplicationContext)context).close();
						}
					}	
					
						
						RendicontazioneBean rendicontazione = new RendicontazioneBean();
						modelWiev.addObject("title", "Tabella Rendicontazione Progetto");
						modelWiev.addObject("message", myProperties.getProperty("rendicontazione.elenco"));
						modelWiev.addObject("listaRendicontazione", listaRendicontazione);
						modelWiev.addObject("pianoFormazioneForm",pianoFormazione);
						modelWiev.addObject("rendicontazioneBeanForm",rendicontazione);
						modelWiev.setViewName("rendicontazioneTabella");
					
						return modelWiev;
					}
				
				
				
				//Carica il form per l'upload dei file di rendicontazione
				@RequestMapping(value ={ "/adminRendicontazioneFile","/userRendicontazioneFile"}, method = RequestMethod.GET)
				public ModelAndView caricaFileRendForm (HttpServletRequest request,  @ModelAttribute("rendicontazioneBeanForm") RendicontazioneBean rendicontazioneBeanForm) {
					ModelAndView modelWiev = new ModelAndView();
					ApplicationContext context =null;
					ArrayList<RendicontazioneFileBean> listaFileRendicontazione = new ArrayList<>();
					String username = request.getUserPrincipal().getName();
					try {
					
						context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
						RendicontazioneDao rendicontazioneDao = (RendicontazioneDao) context.getBean("RendicontazioneDaoImpl");
						listaFileRendicontazione = rendicontazioneDao.getElencoFileRendicontazione(username);
					
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						logger.error(e.getMessage());
						throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
					}finally{
						if(context!=null){
							((ConfigurableApplicationContext)context).close();
						}
					}	
					RendicontazioneFileBean rendicontazioneBean = new RendicontazioneFileBean();
						modelWiev.addObject("title", "Upload File Rendicontazione");
						modelWiev.addObject("rendicontazioneBean", rendicontazioneBean);
						modelWiev.addObject("listaFileRendicontazione", listaFileRendicontazione);
						modelWiev.addObject("message", myProperties.getProperty("rendicontazione.file"));
						modelWiev.setViewName("rendicontazioneFileUpload");
					
						return modelWiev;
					}
				
				
				//Esegue l'upload dei file di rendicontazione
				@RequestMapping(value ={ "/adminRendicontUpload","/userRendicontUpload"}, method = RequestMethod.POST)
				public ModelAndView caricaFileRend (HttpServletRequest request,  @ModelAttribute("uploadForm") ListaFileBean rendicontazioneFileList) {
					ModelAndView modelWiev = new ModelAndView();
					ApplicationContext context=null;
					ArrayList<DatiFIleUploadBean> listaDatiFiles = new ArrayList<>();
					try {
						
						String username = request.getUserPrincipal().getName();
						context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
						RendicontazioneDao rendicontazioneDao = (RendicontazioneDao) context.getBean("RendicontazioneDaoImpl");
						for(CommonsMultipartFile fileItem : rendicontazioneFileList.getListaFile()){
							
						 if (fileItem!=null&&(!fileItem.getOriginalFilename().equalsIgnoreCase(""))){
							RendicontazioneFileBean rendicontazioneFileBean = new RendicontazioneFileBean();
							DatiFIleUploadBean datiFile = new DatiFIleUploadBean();
							datiFile.setNomeFile(fileItem.getOriginalFilename());
							if(fileItem.getSize()!=0){
								datiFile.setSizeFile(Long.toString(fileItem.getSize()/1000)+" KB");
								if(fileItem.getOriginalFilename().endsWith("pdf")){
									rendicontazioneFileBean.setUsername(username);
									rendicontazioneFileBean.setNomeAllegato(fileItem.getOriginalFilename());
								    // verifica se il file esiste sul DB
									boolean trovato = false;
									trovato = rendicontazioneDao.esisteFile(rendicontazioneFileBean.getNomeAllegato(),username);
									if (!trovato){
										rendicontazioneFileBean.setAllegatoFile(fileItem.getBytes());
										if(rendicontazioneFileBean.getClass()!=null){
										
											rendicontazioneDao.caricaFileRendicontazione(rendicontazioneFileBean);
											 
										}else{
											datiFile.setErrore("Impossibile caricare il file.");
										}
										
									}else{
										datiFile.setErrore("Il file è già presente nel database.");
									}
								}else{
									datiFile.setErrore("Non è un file PDF.");
								}
							}else{
								datiFile.setErrore("Non è stato possibile accedere al file.");
							}
							listaDatiFiles.add(datiFile);
						 }
						}
						
						
						
						
						
						
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						logger.error(e.getMessage());
						throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
					}finally{
						if(context!=null){
							((ConfigurableApplicationContext)context).close();
						}
					}	
						modelWiev.addObject("title", " Riepilogo Upload File Rendicontazione");
						modelWiev.addObject("message", myProperties.getProperty("riepilogo.upload"));
						modelWiev.addObject("listaDatiFiles", listaDatiFiles);
						modelWiev.setViewName("fileUploadReuslt");
					
						return modelWiev;
					}
			
				//Elina il file di rendicontazione
				@RequestMapping(value ={ "/adminCancellaRendiconFile","/userCancellaRendiconFile"}, method = RequestMethod.POST)
				public ModelAndView cancellaRendiconFile (HttpServletRequest request,  @ModelAttribute("rendicontazioneBean") RendicontazioneFileBean rendicontazioneFileBean) {
					ModelAndView modelWiev = new ModelAndView();
					String username = request.getUserPrincipal().getName();
					ApplicationContext context =null;
					ArrayList<RendicontazioneFileBean> listaFileRendicontazione = new ArrayList<>();
					try {
					
						context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
						RendicontazioneDao rendicontazioneDao = (RendicontazioneDao) context.getBean("RendicontazioneDaoImpl");
						PianiFormazioneDao pianiFormazioneDao = (PianiFormazioneDao) context.getBean("PianiFormazioneDaoImpl");
						String nomeFile = rendicontazioneDao.getNomeFileByID(rendicontazioneFileBean.getId(), username);
						if(nomeFile!=null&&!nomeFile.isEmpty()){
							ArrayList<Integer> listaIdPiani = rendicontazioneDao.findRendicontazioneByFile(nomeFile);
							if (listaIdPiani!=null&&!listaIdPiani.isEmpty()){
								pianiFormazioneDao.aggiornaStatoPianoByEliminazioneFile(listaIdPiani, myProperties.getProperty("enabled.no"),username);
							}
						}
						rendicontazioneDao.eliminaFile(rendicontazioneFileBean);
						
						listaFileRendicontazione = rendicontazioneDao.getElencoFileRendicontazione(username);
					
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						logger.error(e.getMessage());
					}finally{
						if(context!=null){
							((ConfigurableApplicationContext)context).close();
						}
					}	
					RendicontazioneFileBean rendicontazioneBean = new RendicontazioneFileBean();
						modelWiev.addObject("title", "Upload File Rendicontazione");
						modelWiev.addObject("rendicontazioneBean", rendicontazioneBean);
						modelWiev.addObject("listaFileRendicontazione", listaFileRendicontazione);
						modelWiev.addObject("message", myProperties.getProperty("rendicontazione.file"));
						modelWiev.setViewName("rendicontazioneFileUpload");
					
						return modelWiev;
					}
				
				//Carica il form per l'upload dei file dei lavoratori
				@RequestMapping(value ={ "/adminLavoratoriFile","/userLavoratoriFile"}, method = RequestMethod.GET)
				public ModelAndView caricaFileLavoratoriForm (HttpServletRequest request) {
					ModelAndView modelWiev = new ModelAndView();
					ApplicationContext context =null;
					ArrayList<LavoratoriFileBean> listaFileLavoratori = new ArrayList<>();
					String username = request.getUserPrincipal().getName();
					try {
					
						context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
						LavoratoriDao lavoratoriDao = (LavoratoriDao) context.getBean("LavoratoriDaoImpl");
						listaFileLavoratori = lavoratoriDao.getElencoFileLavoratori(username);
					
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						logger.error(e.getMessage());
						throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
					}finally{
						if(context!=null){
							((ConfigurableApplicationContext)context).close();
						}
					}	
					 LavoratoriFileBean lavoratoriFileBean = new LavoratoriFileBean();
						modelWiev.addObject("title", "Upload File Lavoratori");
						modelWiev.addObject("lavoratoriFileBean", lavoratoriFileBean);
						modelWiev.addObject("listaFileLavoratori", listaFileLavoratori);
						modelWiev.addObject("message", myProperties.getProperty("lavoratori.file"));
						modelWiev.setViewName("lavoratoriFileUpload");
					
						return modelWiev;
					}
				
				
				//Esegue l'upload dei file dei lavoratori
				@RequestMapping(value ={ "/adminLavoratoriUpload","/userLavoratoriUpload"}, method = RequestMethod.POST)
				public ModelAndView caricaFileLavoratori (HttpServletRequest request,  @ModelAttribute("uploadForm") ListaFileBean lavoratoriFileList) {
					ModelAndView modelWiev = new ModelAndView();
					ApplicationContext context=null;
					ArrayList<DatiFIleUploadBean> listaDatiFiles = new ArrayList<>();
					try {
						
						String username = request.getUserPrincipal().getName();
						context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
						LavoratoriDao lavoratoriDao = (LavoratoriDao) context.getBean("LavoratoriDaoImpl");
						for(CommonsMultipartFile fileItem : lavoratoriFileList.getListaFile()){
							
						 if (fileItem!=null&&(!fileItem.getOriginalFilename().equalsIgnoreCase(""))){
							LavoratoriFileBean lavoratoriFileBean = new LavoratoriFileBean();
							DatiFIleUploadBean datiFile = new DatiFIleUploadBean();
							datiFile.setNomeFile(fileItem.getOriginalFilename());
							if(fileItem.getSize()!=0){
								datiFile.setSizeFile(Long.toString(fileItem.getSize()/1000)+" KB");
								if(fileItem.getOriginalFilename().endsWith("pdf")){
									lavoratoriFileBean.setUsername(username);
									lavoratoriFileBean.setNomeAllegato(fileItem.getOriginalFilename());
								    // verifica se il file esiste sul DB
									boolean trovato = false;
									trovato = lavoratoriDao.esisteFile(lavoratoriFileBean.getNomeAllegato(),username);
									if (!trovato){
										lavoratoriFileBean.setAllegatoFile(fileItem.getBytes());
										if(lavoratoriFileBean.getClass()!=null){
										
											lavoratoriDao.caricaFileLavoratori(lavoratoriFileBean);
											 
										}else{
											datiFile.setErrore("Impossibile caricare il file.");
										}
										
									}else{
										datiFile.setErrore("Il file è già presente nel database.");
									}
								}else{
									datiFile.setErrore("Non è un file PDF.");
								}
							}else{
								datiFile.setErrore("Non è stato possibile accedere al file.");
							}
							listaDatiFiles.add(datiFile);
						 }
						}
						
						
						
						
						
						
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						logger.error(e.getMessage());
						throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
					}finally{
						if(context!=null){
							((ConfigurableApplicationContext)context).close();
						}
					}	
						modelWiev.addObject("title", " Riepilogo Upload File Lavoratori");
						modelWiev.addObject("message", myProperties.getProperty("riepilogo.upload"));
						modelWiev.addObject("listaDatiFiles", listaDatiFiles);
						modelWiev.setViewName("fileUploadReuslt");
					
						return modelWiev;
					}
			
				//Elimina il file del lavoratore
				@RequestMapping(value ={ "/adminCancellaLavoratoriFile","/userCancellaLavoratoriFile"}, method = RequestMethod.POST)
				public ModelAndView cancellaLavoratoriFile (HttpServletRequest request,  @ModelAttribute("lavoratoriFileBean") LavoratoriFileBean lavoratoriFileBean) {
					ModelAndView modelWiev = new ModelAndView();
					ApplicationContext context =null;
					ArrayList<LavoratoriFileBean> listaFileLavoratori = new ArrayList<>();
					String username = request.getUserPrincipal().getName();
					try {
					
						context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
						LavoratoriDao lavoratoriDao = (LavoratoriDao) context.getBean("LavoratoriDaoImpl");
						PianiFormazioneDao pianiFormazioneDao = (PianiFormazioneDao) context.getBean("PianiFormazioneDaoImpl");
						String nomeFile = lavoratoriDao.getNomeFileByID(lavoratoriFileBean.getId(), username);
						if(nomeFile!=null&&!nomeFile.isEmpty()){
							ArrayList<Integer> listaIdPiani = lavoratoriDao.findLavoratoriByFile(nomeFile);
							if (listaIdPiani!=null&&!listaIdPiani.isEmpty()){
								pianiFormazioneDao.aggiornaStatoPianoByEliminazioneFile(listaIdPiani, myProperties.getProperty("enabled.no"),username);
							}
						}
						lavoratoriDao.eliminaFile(lavoratoriFileBean);
						listaFileLavoratori = lavoratoriDao.getElencoFileLavoratori(username);
					
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						logger.error(e.getMessage());
						throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
					}finally{
						if(context!=null){
							((ConfigurableApplicationContext)context).close();
						}
					}	
						
					  LavoratoriFileBean lavoratoriFileBeanForm = new LavoratoriFileBean();
						modelWiev.addObject("title", "Upload File Lavoratori");
						modelWiev.addObject("lavoratoriFileBean", lavoratoriFileBeanForm);
						modelWiev.addObject("listaFileLavoratori", listaFileLavoratori);
						modelWiev.addObject("message", myProperties.getProperty("lavoratori.file"));
						modelWiev.setViewName("lavoratoriFileUpload");
				
					return modelWiev;
					
						
					}
				
				//Vaidazione progetto
				@Transactional
				@RequestMapping(value ={ "/adminValidaPiano","/userValidaPiano"}, method = RequestMethod.POST)
				public ModelAndView validaPiano(HttpServletRequest request, ModelMap model, @ModelAttribute("pianoFormazioneForm") PianoDIformazioneBean pianoFormazione) {
					ModelAndView modelWiev = new ModelAndView();
					ApplicationContext context =null;
					
					String username = request.getUserPrincipal().getName();
					PianoDIformazioneBean pianoDIformazioneBean = new PianoDIformazioneBean();
					AttuatoreBean attuatoreBean = new AttuatoreBean();
					ArrayList<CalendarioBean> listaCalendari = new ArrayList<>();
					ArrayList<LavoratoriBean> listaLavoratori = new ArrayList<>();
					ArrayList<RendicontazioneBean> listaRendicontazione = new ArrayList<>();
					ArrayList<ErroreProgettoBean> listaErroriProgetto = new ArrayList<>();
					ArrayList<String> listaFileLavoratori = new ArrayList<>();
					ArrayList<String> listaFileRendicontazione = new ArrayList<>();
					try {
					    
						context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
						PianiFormazioneDao pianiFormazioneDao = (PianiFormazioneDao) context.getBean("PianiFormazioneDaoImpl");
						EttLoadStatusDao ettLoadStatusDao = (EttLoadStatusDao) context.getBean("EttLoadStatusDaoImpl");
						ettLoadStatusDao.deletePianoTrasmesso(pianoFormazione.getId());
						pianoDIformazioneBean= pianiFormazioneDao.findPianiFormazione(pianoFormazione);
						LavoratoriDao lavoratoriDao = (LavoratoriDao) context.getBean("LavoratoriDaoImpl");
						listaLavoratori = lavoratoriDao.esistonoLavoratori(pianoDIformazioneBean);
						//validazione piano
						listaErroriProgetto = ValidaProgetto.validaProgettoTop(pianoDIformazioneBean,listaLavoratori, myProperties);
						
						//validazione file attuatore
						if (!(Utils.eliminaSpazi(pianoDIformazioneBean.getAttuatorePIVA()).isEmpty())&&
								!pianoDIformazioneBean.getAttuatorePIVA().equalsIgnoreCase(myProperties.getProperty("assente"))){	
									attuatoreBean.setAttuatorePIVA(pianoDIformazioneBean.getAttuatorePIVA());
									attuatoreBean.setUsername(username);
									AttuatoriDao attuatoriDao= (AttuatoriDao) context.getBean("AttuatoreDaoImpl");
									AttuatoreBean attuatoreResult = attuatoriDao.leggiNomiAllegati(attuatoreBean);
									listaErroriProgetto = ValidaProgetto.validaFileAttuatore(pianoDIformazioneBean, attuatoreResult, listaErroriProgetto, myProperties);
									
									
						}
						
						
						// validazione calendario
						if (!(pianoDIformazioneBean.getModulo1()==null&&pianoDIformazioneBean.getModulo2()==null&&
							 pianoDIformazioneBean.getModulo1().isEmpty()&&pianoDIformazioneBean.getModulo2().isEmpty())){
							CalendarioDao calendarioDao = (CalendarioDao) context.getBean("CalendarioDaoImpl");
							listaCalendari = calendarioDao.getCalendari(pianoDIformazioneBean);
							listaErroriProgetto = ValidaProgetto.validaCalendari(listaCalendari,pianoDIformazioneBean, listaErroriProgetto, myProperties);
						
						
						// validazione lavoratori
					
							listaFileLavoratori = lavoratoriDao.leggiNomiAllegati(username);
							listaErroriProgetto = ValidaProgetto.validaLavoratori(listaLavoratori, pianoDIformazioneBean, listaErroriProgetto, myProperties);
							 
							// chiamo il metodo di validazione dei file lavoratori
							listaErroriProgetto = ValidaProgetto.validaFileLavoratori(pianoDIformazioneBean.getId(), listaLavoratori, listaFileLavoratori, listaErroriProgetto, myProperties);
							
						//valida rendicontazione
							RendicontazioneDao rendicontazioneDao = (RendicontazioneDao) context.getBean("RendicontazioneDaoImpl");
							listaRendicontazione = rendicontazioneDao.getAllrendicontazione(pianoDIformazioneBean);
							listaErroriProgetto = ValidaProgetto.validaRendicontazione(listaRendicontazione, pianoDIformazioneBean, listaErroriProgetto, myProperties);
							
							// chiamo il metodo di validazione dei file
							listaFileRendicontazione = rendicontazioneDao.leggiNomiAllegati(username);
							
							listaErroriProgetto = ValidaProgetto.validaFileRendicontazione(pianoDIformazioneBean.getId(), listaRendicontazione, listaFileRendicontazione, listaErroriProgetto, myProperties);
						
						//	aggiorno lo stato del progetto
							if (listaErroriProgetto!=null&&!listaErroriProgetto.isEmpty()){
								pianoDIformazioneBean.setEnabled(myProperties.getProperty("enabled.no"));
							}else{
								pianoDIformazioneBean.setEnabled(myProperties.getProperty("enabled.si"));
							}
							
							pianiFormazioneDao.updateStatoPianoDiFormazione(pianoDIformazioneBean);
							
						// aggiorno la lista errori del progetto
							ErroriProgettoDao erroriProgettoDao = (ErroriProgettoDao) context.getBean("ErroriProgettoDaoImpl");
							erroriProgettoDao.deleteErroriProgett(pianoDIformazioneBean.getId());
							if (listaErroriProgetto != null &&!listaErroriProgetto.isEmpty()){
								erroriProgettoDao.salvaErroriProgetto(listaErroriProgetto);
							}
							listaErroriProgetto = erroriProgettoDao.getErroriProgetto(pianoDIformazioneBean.getId());
					     }
					
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						logger.error(e.getMessage());
						throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
					}finally{
						if(context!=null){
							((ConfigurableApplicationContext)context).close();
						}
					}	
						
					 
						modelWiev.addObject("title", "Riassunto Errori Progetto");
					    modelWiev.addObject("listaErroriProgetto", listaErroriProgetto);
						modelWiev.addObject("message", myProperties.getProperty("errori.riassunto")+pianoDIformazioneBean.getNuemroProtocollo());
						modelWiev.setViewName("erroriProgetto");
				
					return modelWiev;
					
						
					}
				

				//Carica gli erorri del progetto
				@RequestMapping(value ={ "/adminErroriPiano","/userErroriPiano"}, method = RequestMethod.POST)
				public ModelAndView erroriPiano( ModelMap model, @ModelAttribute("pianoFormazioneForm") PianoDIformazioneBean pianoFormazione) {
					ModelAndView modelWiev = new ModelAndView();
					ApplicationContext context =null;
					PianoDIformazioneBean pianoFormazioneBean = new PianoDIformazioneBean();
					ArrayList<ErroreProgettoBean> listaErroriProgetto = new ArrayList<>();
					try {
						context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
						PianiFormazioneDao pianiFormazioneDao = (PianiFormazioneDao) context.getBean("PianiFormazioneDaoImpl");
						ErroriProgettoDao erroriProgettoDao = (ErroriProgettoDao) context.getBean("ErroriProgettoDaoImpl");
						listaErroriProgetto = erroriProgettoDao.getErroriProgetto(pianoFormazione.getId());
						pianoFormazioneBean = pianiFormazioneDao.findPianiFormazione(pianoFormazione);
						if (pianoFormazioneBean.getEnabled().equalsIgnoreCase(myProperties.getProperty("enabled.no"))){
							if (!(listaErroriProgetto!=null&&!listaErroriProgetto.isEmpty())){
								ErroreProgettoBean erroreProgetto = new ErroreProgettoBean();
								erroreProgetto.setId(0);
								erroreProgetto.setIdPiano(pianoFormazioneBean.getId());
								erroreProgetto.setErrore(myProperties.getProperty("errore.progetto.messaggio"));
								erroreProgetto.setOggettoErrore(myProperties.getProperty("errore.progetto"));
								listaErroriProgetto.add(erroreProgetto);
							}
						}
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						logger.error(pianoFormazione.getId());
						logger.error(e.getMessage());
						throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
					}finally{
						if(context!=null){
							((ConfigurableApplicationContext)context).close();
						}
					}	
				
				
					modelWiev.addObject("title", "Riassunto Errori Piano");
				    modelWiev.addObject("listaErroriProgetto", listaErroriProgetto);
					modelWiev.addObject("message", myProperties.getProperty("errori.riassunto")+pianoFormazioneBean.getNuemroProtocollo());
					modelWiev.setViewName("erroriProgetto");
			
				return modelWiev;
				}
				
				//Generazione dei file per la trasmissione
				@Transactional
				@RequestMapping(value ={ "/adminTrasmettiPiani","/userTrasmettiPiani"}, method = RequestMethod.POST)
				public ModelAndView trasmettiPiano(HttpServletRequest request, ModelMap model, @ModelAttribute("pianoFormazioneForm") PianoDIformazioneBean pianoFormazione) {
					ModelAndView modelWiev = new ModelAndView();
					Connection connection= null;
					ApplicationContext context =null;
					ArrayList<PianoDIformazioneBean> listaPiani= null;
					try {
						context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
						FbaExtractor fbaExtractor = new FbaExtractor();
						connection = fbaExtractor.openConnection();
						fbaExtractor.firstStep(connection);
						fbaExtractor.closeConnection(connection);
						
						PianiFormazioneDao pianiFormazioneDao = (PianiFormazioneDao) context.getBean("PianiFormazioneDaoImpl");
						listaPiani = pianiFormazioneDao.getAllPiani(request.getUserPrincipal().getName());
					
					} catch (Exception e) {
						// TODO: handle exception
						
						logger.error(e.getMessage());
						throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
					}finally{
						if(context!=null){
							((ConfigurableApplicationContext)context).close();
						}
					}	
					
						PianoDIformazioneBean piano = new PianoDIformazioneBean();
						modelWiev.addObject("title", "Tabella Piani Di Formazione");
						modelWiev.addObject("message", myProperties.getProperty("piani.formazione.elenco"));
						modelWiev.addObject("listaPiani", listaPiani);
						modelWiev.addObject("pianoFormazioneForm",piano);
						modelWiev.setViewName("pianiDiFormazioneTabella");
					
						return modelWiev;
					}
				
				
				//Carica gli erorri del progetto
				@Transactional
				@RequestMapping(value ={ "/adminErroriTrasm","/userErroriTrasm"}, method = RequestMethod.POST)
				public ModelAndView erroriTrasmissine( ModelMap model, @ModelAttribute("pianoFormazioneForm") PianoDIformazioneBean pianoFormazione) {
					ModelAndView modelWiev = new ModelAndView();
					ApplicationContext context =null;
					PianoDIformazioneBean pianoFormazioneBean = new PianoDIformazioneBean();
					ArrayList<EttLoadStatusBean> listaErroriProgetto = new ArrayList<>();
					try {
						context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
						PianiFormazioneDao pianiFormazioneDao = (PianiFormazioneDao) context.getBean("PianiFormazioneDaoImpl");
						EttLoadStatusDao ettLoadStatusDao = (EttLoadStatusDao) context.getBean("EttLoadStatusDaoImpl");
						 listaErroriProgetto = ettLoadStatusDao.getErroriTrasmPiano(pianoFormazione.getId());
						pianoFormazioneBean = pianiFormazioneDao.findPianiFormazione(pianoFormazione);
						
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						logger.error(e.getMessage());
						throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
					}finally{
						if(context!=null){
							((ConfigurableApplicationContext)context).close();
						}
					}	
				
				
					modelWiev.addObject("title", "Riassunto Errori Trasmissione Piano");
				    modelWiev.addObject("listaErroriProgetto", listaErroriProgetto);
					modelWiev.addObject("message", myProperties.getProperty("errori.riassunto")+pianoFormazioneBean.getNuemroProtocollo());
					modelWiev.setViewName("erroriTrasmissionePiano");
			
				return modelWiev;
				}
				
				//Carica gli erorri del progetto
				@RequestMapping(value ={ "/adminSbloccaPiano","/userSbloccaPiano"}, method = RequestMethod.POST)
				public ModelAndView sbloccaPiano( HttpServletRequest request,ModelMap model, @ModelAttribute("pianoFormazioneForm") PianoDIformazioneBean pianoFormazione) {
					ModelAndView modelWiev = new ModelAndView();
					ApplicationContext context =null;
					pianoFormazione.setEnabled(myProperties.getProperty("enabled.si"));
					ArrayList<PianoDIformazioneBean> listaPiani = new ArrayList<>();
					String username = request.getUserPrincipal().getName();
					try {
						context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
						PianiFormazioneDao pianiFormazioneDao = (PianiFormazioneDao) context.getBean("PianiFormazioneDaoImpl");
						pianiFormazioneDao.updateStatoPianoDiFormazione(pianoFormazione);
						listaPiani = pianiFormazioneDao.getAllPiani(username);
							
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						logger.error(e.getMessage());
						throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
					}finally{
						if(context!=null){
							((ConfigurableApplicationContext)context).close();
						}
					}	
					
						PianoDIformazioneBean piano = new PianoDIformazioneBean();
						modelWiev.addObject("title", "Tabella Piani Di Formazione");
						modelWiev.addObject("message", myProperties.getProperty("piani.formazione.elenco"));
						modelWiev.addObject("listaPiani", listaPiani);
						modelWiev.addObject("pianoFormazioneForm",piano);
						modelWiev.setViewName("pianiDiFormazioneTabella");
					
						return modelWiev;
					}
				
				
				
			

}
