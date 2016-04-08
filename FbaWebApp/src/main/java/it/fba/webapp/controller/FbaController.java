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

import it.fba.webapp.beans.FileBean;
import it.fba.webapp.beans.PianoDIformazioneBean;
import it.fba.webapp.beans.UsersBean;
import it.fba.webapp.dao.PianiFormazioneDao;
import it.fba.webapp.dao.UsersDao;
import it.fba.webapp.exception.CustomGenericException;
import it.fba.webapp.fileInputOutput.ImportServiceExcel;
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
			model.addObject("error", myProperties.getProperty("pagina.amministratore"));
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
			@RequestMapping(value = "/adminGestisciUtente", method = RequestMethod.POST)
			public ModelAndView adminGestisciUtente(ModelMap model, @ModelAttribute UsersBean gestioneUserForm) {
	             
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
				public ModelAndView adminModifyUser(ModelMap model, @ModelAttribute UsersBean userFormModify) {
		             
					ModelAndView modelWiev = new ModelAndView();
					UsersBean utente = new UsersBean();
					try{
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
					}catch(Exception e){
						e.printStackTrace();
						throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
					}
					Map<String, String> listaStati = Utils.getListaStati(myProperties);
					modelWiev.addObject("title", "Modifica Utente");
					modelWiev.addObject("message", myProperties.getProperty("pagina.amministratore"));
					modelWiev.addObject("listaStati", listaStati);
					modelWiev.addObject("disabled", true);
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
					String righeSbagliate = "";
					ApplicationContext context=null;
					try {
						//FileBean filebean = (FileBean)fileBean;
						//FileBean fileBean = new FileBean();
						//fileBean.setFileData(fileData);
						ImportServiceExcel importService = new ImportServiceExcel();
						listaExcel = importService.importFile(fileBean);
						HashMap<String, String> map = new LinkedHashMap<>();
						Iterator<HashMap<String, String>> hashIterator = listaExcel.listIterator();
						int i=0;
						
						while( hashIterator.hasNext()){
							i++;
							map = hashIterator.next();
							PianoDIformazioneBean pianoFormazione = new PianoDIformazioneBean();
							pianoFormazione.setUsername(fileBean.getUsername());
							pianoFormazione.setPianoDiFormazione(map.get("1"));
							pianoFormazione.setModulo1(map.get("2"));
							pianoFormazione.setModulo2(map.get("3"));
							if (map.get("4")!=null){
								pianoFormazione.setAttuatorePIVA(map.get("4"));
								if(pianoFormazione.getModulo1().equalsIgnoreCase(pianoFormazione.getModulo2())){
									righeSbagliate = righeSbagliate + " "+Integer.toString(i); 
								}
							}else{
								righeSbagliate = righeSbagliate + " "+Integer.toString(i); 
							}
							
							if (map.get("5")!=null){
								righeSbagliate = righeSbagliate + "colonna in piu"+Integer.toString(i);
							}
							pianoFormazione.setEnabled("0");
							listaPiani.add(pianoFormazione);
						}
						
						 context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
						 PianiFormazioneDao pianiFormazioneDao = (PianiFormazioneDao) context.getBean("PianiFormazioneDaoImpl");
						 pianiFormazioneDao.caricaPianiFormazione(listaPiani);
						 
						 if (!righeSbagliate.isEmpty()){
								
								modelWiev.addObject("error", myProperties.getProperty("errore.piano.formazione")+righeSbagliate);
								modelWiev.addObject("listaPiani", null);
								modelWiev.setViewName("pianiDiFormazioneTabella");
								
						}else{
								modelWiev.addObject("listaPiani", listaPiani);
								modelWiev.setViewName("pianiDiFormazioneTabella");
						}
						
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
						modelWiev.addObject("pianoFormazoneForm",piano);
						modelWiev.setViewName("pianiDiFormazioneTabella");
					
						return modelWiev;
					}
				
				// metodo modifica dati utentie
				@RequestMapping(value ={"/adminModifyPianoForm","/userModifyPianoForm"} , method = RequestMethod.POST)
				public ModelAndView modifyPianoForm(ModelMap model, @ModelAttribute("pianoFormazoneFormModify") PianoDIformazioneBean pianoFormazione) {
		             
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
				public ModelAndView modifyPiano(ModelMap model, @ModelAttribute("pianoFormazoneFormModify") PianoDIformazioneBean pianoDiFormazione, BindingResult bindingResult) {
		             
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
				
				

			
			

}
