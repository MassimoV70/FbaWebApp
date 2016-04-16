package it.fba.webapp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.fba.webapp.beans.CalendarioBean;
import it.fba.webapp.beans.FileBean;
import it.fba.webapp.beans.LavoratoriBean;
import it.fba.webapp.beans.PianoDIformazioneBean;
import it.fba.webapp.beans.UsersBean;
import it.fba.webapp.dao.CalendarioDao;
import it.fba.webapp.dao.PianiFormazioneDao;
import it.fba.webapp.dao.UsersDao;
import it.fba.webapp.exception.CustomGenericException;
import it.fba.webapp.fileInputOutput.ImportServiceExcel;
import it.fba.webapp.form.validator.ExcelValidator;
import it.fba.webapp.form.validator.FormSecurityValidator;
import it.fba.webapp.utils.Utils;


@Controller
public class FbaController {

	@Resource(name="myProperties")
	private Properties myProperties;
	
	@Autowired
	FormSecurityValidator validator;
	
	

	@RequestMapping(value = { "/welcome**" }, method = RequestMethod.GET)
	public ModelAndView defaultPage() {
		UsersBean user = new UsersBean();
		ModelAndView model = new ModelAndView();
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

		if (error != null) {
			model.addObject("error", myProperties.getProperty("username.password"));
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
				modelWiev.addObject("userFormModify",utente);
				modelWiev.setViewName("modificaUtente");
			 return modelWiev;

			}
			
			// metodo modifica dati utentie
				@RequestMapping(value = "/adminModifyUser", method = RequestMethod.POST)
				public ModelAndView adminModifyUser(ModelAndView modelWiev, @Valid @ModelAttribute UsersBean userFormModify, BindingResult bindingResult) {
		             
					//ModelAndView modelWiev = new ModelAndView();
					UsersBean utente = new UsersBean();
					 validator.userFormValidator(userFormModify, bindingResult);
					try{
						if(!bindingResult.hasErrors()){
							ApplicationContext context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
							UsersDao usersService = (UsersDao) context.getBean("UsersDaoImpl");
							       
							utente = usersService.findUserbyUsername(userFormModify.getUsername());
							userFormModify.setDataInizio(Utils.dataDBFormatter(userFormModify.getDataInizioStr()));
							userFormModify.setDataFine(Utils.dataDBFormatter(userFormModify.getDataFineStr()));
							userFormModify.setRole(utente.getRole());
							if (utente.getUsername()!=null){
								usersService.updateUser(userFormModify);
							}
							((ConfigurableApplicationContext)context).close();
						}
					}catch(Exception e){
						e.printStackTrace();
						throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
					}
					Map<String, String> listaStati = Utils.getListaStati(myProperties);
					modelWiev.addObject("title", "Modifica Utente");
					modelWiev.addObject("message", myProperties.getProperty("pagina.amministratore"));
					modelWiev.addObject("listaStati", listaStati);
					if(!bindingResult.hasErrors()){
						modelWiev.addObject("disabled", true);
					}
					modelWiev.addObject("userFormModify",userFormModify);
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
				@RequestMapping(value ={ "/adminElaboraUploadPiani","/userElaboraformPiani"}, method = RequestMethod.POST)
				public ModelAndView elaboraUploadPiani(HttpServletRequest request,  @ModelAttribute("fileBean") FileBean fileBean) {
					ModelAndView modelWiev = new ModelAndView();
					ArrayList<HashMap<String, String>> listaExcel = new ArrayList<>();
					ArrayList<PianoDIformazioneBean> listaPiani = new ArrayList<>();
				
					ApplicationContext context=null;
					try {
						
						ImportServiceExcel importService = new ImportServiceExcel();
						listaExcel = importService.importFile(fileBean);
						listaPiani = ExcelValidator.listaPianiFormazioneValidator(listaExcel, fileBean.getUsername());
						
						 context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
						 PianiFormazioneDao pianiFormazioneDao = (PianiFormazioneDao) context.getBean("PianiFormazioneDaoImpl");
						 pianiFormazioneDao.caricaPianiFormazione(listaPiani);
						 PianoDIformazioneBean piano = new PianoDIformazioneBean(); 
						modelWiev.addObject("pianoFormazioneForm",piano); 
						modelWiev.addObject("listaPiani", listaPiani);
						modelWiev.setViewName("pianiDiFormazioneTabella");
						
						
					} catch (Exception e) {
						// TODO: handle exception
												e.printStackTrace();
						modelWiev.addObject("error", myProperties.getProperty("errore.file.excel"));
						modelWiev.setViewName("pianiFormazioneUpload");
						
					}finally{
						if(context!=null){
							((ConfigurableApplicationContext)context).close();
						}
						
						
					}
					
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
						 listaPiani = Utils.pianoFormazioneFormSetting(listaPiani);
					
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
					}finally{
						if(context!=null){
							((ConfigurableApplicationContext)context).close();
						}
					}	
					
						PianoDIformazioneBean piano = new PianoDIformazioneBean();
						modelWiev.addObject("title", "Tabella Piani  Di Formazione");
						modelWiev.addObject("message", myProperties.getProperty("piani.formazione.elenco"));
						modelWiev.addObject("listaPiani", listaPiani);
						modelWiev.addObject("pianoFormazioneForm",piano);
						modelWiev.setViewName("pianiDiFormazioneTabella");
					
						return modelWiev;
					}
				
				// metodo modifica dati utentie
				@RequestMapping(value ={"/adminModifyPianoForm","/userModifyPianoForm"} , method = RequestMethod.POST)
				public ModelAndView modifyPianoForm(ModelMap model, @ModelAttribute("pianoFormazioneForm") PianoDIformazioneBean pianoFormazione) {
		             
					ModelAndView modelWiev = new ModelAndView();
					ApplicationContext context=null;
					PianoDIformazioneBean pianoDIformazioneBean = new PianoDIformazioneBean();
					
					try{
						context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
						PianiFormazioneDao pianiFormazioneDao = (PianiFormazioneDao) context.getBean("PianiFormazioneDaoImpl");
						
						pianoDIformazioneBean= pianiFormazioneDao.findPianiFormazione(pianoFormazione);  
						
					((ConfigurableApplicationContext)context).close();
					}catch(Exception e){
						e.printStackTrace();
						throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
					}finally{
						if(context!=null){
							((ConfigurableApplicationContext)context).close();
						}
					}	
					
					modelWiev.addObject("title", "Modifica Piano Di Formazione");
					modelWiev.addObject("message", myProperties.getProperty("piani.formazione.modifica"));
					modelWiev.addObject("pianoFormazioneForm", pianoDIformazioneBean);
					modelWiev.setViewName("modificaPianoFormazione");
				 return modelWiev;

				}
				
				// metodo modifica piano di formazione
				@RequestMapping(value = {"/adminModifyPiano","/userModifyPiano"} , method = RequestMethod.POST)
				public ModelAndView modifyPiano(ModelMap model, @ModelAttribute("pianoFormazioneForm") PianoDIformazioneBean pianoDiFormazione, BindingResult bindingResult) {
		             
					ModelAndView modelWiev = new ModelAndView();
					validator.pianoFormazioneValidator(pianoDiFormazione, bindingResult);
				ApplicationContext context=null;
					try{
						if(!bindingResult.hasErrors()){
							context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
							PianiFormazioneDao pianiFormazioneDao = (PianiFormazioneDao) context.getBean("PianiFormazioneDaoImpl");
							pianiFormazioneDao.updatePianoDiFormazione(pianoDiFormazione);
						}   
					((ConfigurableApplicationContext)context).close();
					}catch(Exception e){
						e.printStackTrace();
						throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
					}finally{
						if(context!=null){
							((ConfigurableApplicationContext)context).close();
						}
					}	
					modelWiev.addObject("title", "Modifica Piano Di Formazione");
					modelWiev.addObject("message", myProperties.getProperty("piani.formazione.modifica"));
					modelWiev.addObject("pianoFormazioneForm", pianoDiFormazione);
					modelWiev.addObject("disabled", true);
					modelWiev.setViewName("modificaPianoFormazione");
				 return modelWiev;

				}
				
				@RequestMapping(value ={ "/adminCancellaPiano","/userCancellaPiano"}, method = RequestMethod.POST)
				public ModelAndView cancellaPiano(HttpServletRequest request,  @ModelAttribute("pianoDIformazioneBean") PianoDIformazioneBean pianoDIformazioneBean) {
					ModelAndView modelWiev = new ModelAndView();
					ArrayList<PianoDIformazioneBean> listaPiani = new ArrayList<>();
					ApplicationContext context=null;
					try {
						 context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
						 PianiFormazioneDao pianiFormazioneDao = (PianiFormazioneDao) context.getBean("PianiFormazioneDaoImpl");
						 pianiFormazioneDao.deletePianoDiFormazione(pianoDIformazioneBean);
						 pianiFormazioneDao.caricaPianiFormazione(listaPiani);
					
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
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
				

				@RequestMapping(value = { "/adminGestioneModulo","/userGestioneModulo"}, method = RequestMethod.POST)
				public ModelAndView gestioneModulo(HttpServletRequest request, @ModelAttribute("pianoFormazioneForm") PianoDIformazioneBean pianoDiFormazione) {
					
					ModelAndView model = new ModelAndView();
					CalendarioBean calendarioModuloBean = new CalendarioBean();
					LavoratoriBean lavoratoriBean = new LavoratoriBean();
					calendarioModuloBean.setIdPiano(pianoDiFormazione.getId());
					calendarioModuloBean.setNomeModulo(pianoDiFormazione.getModulo1());
					lavoratoriBean.setIdPiano(pianoDiFormazione.getId());
					lavoratoriBean.setNomeModulo(pianoDiFormazione.getModulo1());
					model.addObject("calendarioModuloBean", calendarioModuloBean);
					model.addObject("lavoratoriBean", lavoratoriBean);
					model.addObject("title", "Gestione Modulo");
					model.addObject("message", "pagina di gestione del modulo associato al piano");
					model.addObject("calendario", "Sezione Calendario");
					model.addObject("lavoratori", "Sezione Lavoratori");
					model.setViewName("moduloUpload");
					return model;

				}
				
				// metodo che elabora il file excel piani di formazione
				@RequestMapping(value ={ "/adminElaboraUploadModuloCalendario","/userElaboraUploadModuloCalendario"}, method = RequestMethod.POST)
				public ModelAndView elaboraUploadModuloCalendario(HttpServletRequest request,  @ModelAttribute("calendarioModuloBean") CalendarioBean calendarioBean,
						@ModelAttribute("lavoratoriBean") LavoratoriBean lavoratoriBean	) {
					ModelAndView modelWiev = new ModelAndView();
					ArrayList<HashMap<String, String>> listaExcel = new ArrayList<>();
					ArrayList<CalendarioBean> listaCalendari = new ArrayList<>();
					FileBean fileBean = new FileBean();
					fileBean.setFileData(calendarioBean.fileData);
					String righeSbagliate = "";
					String nomeModulo ="";
					ApplicationContext context=null;
					try {
						
						ImportServiceExcel importService = new ImportServiceExcel();
						listaExcel = importService.importFile(fileBean);
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
							if (i==1){
								nomeModulo = calendarioBean.getNomeModulo();
							}
							
							calendario.setDataStr(map.get("1"));
								try {
									calendario.setData(Utils.dataDBFormatter(calendario.getDataStr()));
								} catch (Exception e) {
									// TODO: handle exception
									e.printStackTrace();
									modelWiev.addObject("error", myProperties.getProperty("data.excel.non.valida")+i);
									modelWiev.setViewName("moduloUpload");
									return modelWiev;
								}
								calendario.setInizioMattina(map.get("2"));
								calendario.setFineMattina(map.get("3"));
								calendario.setInizioPomeriggio(map.get("4"));
								if (map.get("5")!=null){
									calendario.setFinePomeriggio(map.get("6"));
								}else{
									righeSbagliate = righeSbagliate + " "+Integer.toString(i);
									calendario.setStato("0");
								}
								
								if (map.get("6")!=null){
									righeSbagliate = righeSbagliate + "colonna in piu"+Integer.toString(i);
									calendario.setStato("0");
								}
								//validazione bean per vedere se i dati inseriti nel file sono corretti.
								
						
							listaCalendari.add(calendario);
						}
						
						 context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
						 CalendarioDao calendarioDao = (CalendarioDao) context.getBean("CalendarioDaoImpl");
						 calendarioDao.caricaCalendari(listaCalendari);
						 
						 if (!righeSbagliate.isEmpty()){
								
								modelWiev.addObject("error", myProperties.getProperty("errore.piano.formazione")+righeSbagliate);
								modelWiev.addObject("listaCalendari", null);
								modelWiev.setViewName("calendarioModuloTabella");
								
						}else{
								modelWiev.addObject("listaCalendari", listaCalendari);
								modelWiev.setViewName("calendarioModuloTabella");
						}
						
					} catch (Exception e) {
						// TODO: handle exception
												e.printStackTrace();
						modelWiev.addObject("error", myProperties.getProperty("errore.file.excel"));
						modelWiev.setViewName("moduloUpload");
						
					}finally{
						if(context!=null){
							((ConfigurableApplicationContext)context).close();
						}
						
						
					}
					CalendarioBean calendarioBeanForm = new CalendarioBean();
					PianoDIformazioneBean pianoFormazioneForm = new PianoDIformazioneBean();
					modelWiev.addObject("title", "Tabella Calendario Modulo "+nomeModulo);
					modelWiev.addObject("message", myProperties.getProperty("calendario.elenco"));
					modelWiev.addObject("listaCalendari", listaCalendari);
					modelWiev.addObject("calendarioBeanForm",calendarioBeanForm);
					modelWiev.addObject("pianoFormazioneForm",pianoFormazioneForm);
					modelWiev.setViewName("calendarioModuloTabella");
					return modelWiev;
				}
				
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
					
					CalendarioBean calendarioBeanForm = new CalendarioBean();
					modelWiev.addObject("title", "Tabella Calendario Modulo "+nomeModulo);
					modelWiev.addObject("message", myProperties.getProperty("calendario.elenco"));
					modelWiev.addObject("listaCalendari", listaCalendario);
					modelWiev.addObject("calendarioBeanForm",calendarioBean);
					modelWiev.addObject("pianoFormazioneForm", pianoDIformazioneBean);
					modelWiev.setViewName("calendarioModuloTabella");
					return modelWiev;
					
						
					}
				
				
				// metodo modifica dati utentie
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
					((ConfigurableApplicationContext)context).close();
					}catch(Exception e){
						e.printStackTrace();
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
				
				// metodo modifica piano di formazione
				@RequestMapping(value = {"/adminModificaGiorno","/userModificaGiorno"} , method = RequestMethod.POST)
				public ModelAndView adminModificaGiorno(ModelMap model,@Valid @ModelAttribute("calendarioBeanForm") CalendarioBean calendarioBean, BindingResult bindingResult) {
		             
					ModelAndView modelWiev = new ModelAndView();
					
				ApplicationContext context=null;
					try{
						if(!bindingResult.hasErrors()){
							context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
							 CalendarioDao calendarioDao = (CalendarioDao) context.getBean("CalendarioDaoImpl");
							 calendarioBean.setData(Utils.dataDBFormatter(calendarioBean.getDataStr()));
							 calendarioDao.updateCalednario(calendarioBean);
							 }   
					((ConfigurableApplicationContext)context).close();
					}catch(Exception e){
						e.printStackTrace();
						throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
					}finally{
						if(context!=null){
							((ConfigurableApplicationContext)context).close();
						}
					}	
					modelWiev.addObject("title", "Modifica Calendario Giorno");
					modelWiev.addObject("message", myProperties.getProperty("calendario.giorno.modifica"));
					modelWiev.addObject("calendarioBeanForm", calendarioBean);
					modelWiev.addObject("disabled", true);
					modelWiev.setViewName("modificaGiornoCalendario");
				 return modelWiev;
				
				}
				
				// cancellazione giornata calendario
				@RequestMapping(value = {"/adminEliminaGiornoForm","/userEliminaGiornoForm"} , method = RequestMethod.POST)
				public ModelAndView eliminaGiornoForm(ModelMap model, @ModelAttribute("calendarioBeanForm") CalendarioBean calendarioBean, BindingResult bindingResult) {
		             
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
							 }   
					((ConfigurableApplicationContext)context).close();
					}catch(Exception e){
						e.printStackTrace();
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


			
			

}
