package it.fba.webapp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.validation.Valid;

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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.fba.webapp.beans.FileBean;
import it.fba.webapp.beans.PianoDIformazioneBean;
import it.fba.webapp.beans.UsersBean;
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

		ModelAndView model = new ModelAndView();
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
			model.addObject("error", "Invalid username and password!");
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
		model.addObject("message", "pagina ad uso esclusivo dell'amministratore");
	    model.addObject("disabled", false);
        model.addObject("userForm", user);
		model.setViewName("addUserForm");
		return model;

	}
	
	// metodo per andare alla pagina form per inserimento utente
		@RequestMapping(value = "/adminAddUser", method = RequestMethod.POST)
		public ModelAndView adminAddUser(@Valid @ModelAttribute("userForm") UsersBean userForm, BindingResult bindingResult, ModelAndView model) {
			
			boolean future = true;
			try{
				
				 userForm.setDataInizioStr(Utils.dataOggi());
				 userForm.setDataInizio(Utils.dataDBFormatter(userForm.getDataInizioStr()));
				 if(userForm.getDataFineStr()!=null||!userForm.getDataFineStr().isEmpty()){
					 userForm.setDataFine(Utils.dataDBFormatter(userForm.getDataFineStr()));
				 }

				 validator.userFormValidator(userForm, bindingResult);
				 if(!bindingResult.hasErrors()){
							ApplicationContext context = new ClassPathXmlApplicationContext("spring\\spring-jpa.xml","spring\\spring-utils.xml");
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
								((ConfigurableApplicationContext)context).close();
							
						
				  }
			}catch(Exception e){
				e.printStackTrace();
				throw new CustomGenericException(new Date(), myProperties.getProperty("errore.generale"));
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
			model.addObject("message", "pagina ad uso esclusivo dell'amministratore");   
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
				modelWiev.addObject("message", "pagina ad uso esclusivo dell'amministratore");
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
					modelWiev.addObject("message", "pagina ad uso esclusivo dell'amministratore");
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
					modelWiev.addObject("fileBean", fileBean);
					modelWiev.setViewName("pianiFormazioneUpload");
					return modelWiev;
				}
				
				// metodo che elabora il file excel piani di formazione
				@RequestMapping(value ={ "/adminElaboraUploadPiani","/userElaboraformPiani"}, method = RequestMethod.POST)
				public ModelAndView elaboraUploadPiani(ModelAndView modelWiev, @ModelAttribute FileBean file ) {
					ArrayList<PianoDIformazioneBean> listaPiani = new ArrayList<>();
					try {
						ImportServiceExcel importService = new ImportServiceExcel();
						listaPiani = importService.importFile(file);
						
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					
					modelWiev.addObject("listaPiani", listaPiani);
					modelWiev.setViewName("pianiFormazioneUpload");
					return modelWiev;
				}
			
			

}
