package it.fba.webapp.form.validator;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.fba.webapp.beans.CalendarioBean;
import it.fba.webapp.beans.LavoratoriBean;
import it.fba.webapp.beans.PianoDIformazioneBean;
import it.fba.webapp.beans.UsersBean;
import it.fba.webapp.utils.Utils;

@Component
public class FormSecurityValidator implements Validator{
	
	
	private static final String TIME24HOURS_PATTERN = 
            "([01]?[0-9]|2[0-3]):[0-5][0-9]";
	
	private static final String HOURSMIN_PATTERN= 
            "([0-9]*):[0-5][0-9]";
	
	private static final String NUMBER_PATTERN = 
            "([0-9]*)";
	private static final String DATE_PATTERN = 
            "([0-3]?[0-9])/[01]?[0-2]/[ANNO]";
	
	private Pattern pattern;
	

	

	public boolean isValid(Object input, Errors errors) {
		// TODO Auto-generated method stub
		String ingresso = (String)input;
		if(input == null){
            return false;
        }
        
        if (!ingresso.matches("/</>")) return true;
       return false;
	}
	
	public static  void userFormValidator(Object userForm, Errors  errors) {
		// TODO Auto-generated method stub
		UsersBean form = (UsersBean)userForm;
		boolean future=true;
		future = Utils.dataFuture(form.getDataInizio(), form.getDataFine());
		 if (!future){
			 errors.rejectValue("dataFineStr", "errors", "Data.futura");
		 }
		
       
	}
	
	public static  void pianoFormazioneValidator(Object pianoFormazione, Errors  errors, Properties myProperties) {
		// TODO Auto-generated method stub
		PianoDIformazioneBean form = (PianoDIformazioneBean)pianoFormazione;
		
		 if (form.getModulo1().equalsIgnoreCase(form.getModulo2())){
			 errors.rejectValue("modulo1", "errors",myProperties.getProperty("Valori.diversi"));
			 errors.rejectValue("modulo2", "errors", myProperties.getProperty("Valori.diversi"));
		 }
		
       
	}
	
	public static boolean isTime (String ora)throws Exception{
		boolean bau = false;
		try{
			bau = Pattern.matches(TIME24HOURS_PATTERN, ora);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return bau;
	}
	
	public static  void calendarioValidator(Object calendarioBean, Errors  errors, Properties myProperties) throws Exception {
		// TODO Auto-generated method stub
		CalendarioBean calendario = (CalendarioBean)calendarioBean;
		try{
			if (calendario.getInizioMattina().equalsIgnoreCase("assente")&
				!calendario.getFineMattina().equalsIgnoreCase("assente")||
				!calendario.getInizioMattina().equalsIgnoreCase("assente")&
				 calendario.getFineMattina().equalsIgnoreCase("assente")){
				
						 errors.rejectValue("inizioMattina", "errors", myProperties.getProperty("Not.interval")); 
						 errors.rejectValue("fineMattina", "errors",  myProperties.getProperty("Not.interval")); 
					 
			}else{
				 if (!calendario.getInizioMattina().equalsIgnoreCase("assente")){
					 if (!isTime(calendario.getInizioMattina())){
						 errors.rejectValue("inizioMattina", "errors", myProperties.getProperty("Not.time")); 
					 }
					 
				 }
				 if (!calendario.getFineMattina().equalsIgnoreCase("assente")){
					 if (!isTime(calendario.getFineMattina())){
						 errors.rejectValue("fineMattina", "errors", myProperties.getProperty("Not.time")); 
					 }
					 
				 }
			}
			
			if (calendario.getInizioPomeriggio().equalsIgnoreCase("assente")&
					!calendario.getFinePomeriggio().equalsIgnoreCase("assente")||
					calendario.getInizioPomeriggio().equalsIgnoreCase("assente")&
					!calendario.getFinePomeriggio().equalsIgnoreCase("assente")){
					
							 errors.rejectValue("inizioMattina", "errors",  myProperties.getProperty("Not.interval")); 
							 errors.rejectValue("finePomeriggio", "errors",  myProperties.getProperty("Not.interval")); 
						 
			}else{
			
					 if (!calendario.getInizioPomeriggio().equalsIgnoreCase("assente")){
						 if (!isTime(calendario.getInizioPomeriggio())){
							 errors.rejectValue("inizioPomeriggio", "errors", myProperties.getProperty("Not.time")); 
						 }
						 
					 }
					 if (!calendario.getFinePomeriggio().equalsIgnoreCase("assente")){
						 if (!isTime(calendario.getFinePomeriggio())){
							 errors.rejectValue("finePomeriggio", "errors", myProperties.getProperty("Not.time")); 
						 }
						 
					 }
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
       
	}
	
	public static  void lavoratoriValidator(Object lavoratoriBean, Errors  errors) throws Exception {
		
		LavoratoriBean lavoratore = (LavoratoriBean ) lavoratoriBean;
		try {
            if(lavoratore.getMatricola()!=null&!lavoratore.getMatricola().isEmpty()){
            	errors.rejectValue("matricola", "errors", "Notnull"); 
			}
			if(lavoratore.getOrePresenza()!=null&!lavoratore.getOrePresenza().isEmpty()){
				errors.rejectValue("orePresenza", "errors", "Notnull"); 
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public static boolean isNumber (String numero)throws Exception{
		boolean bau = false;
		try{
			bau = Pattern.matches(NUMBER_PATTERN, numero);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return bau;
	}
	
	public static boolean isDate (String data)throws Exception{
		boolean bau = false;
		try{
			bau = Pattern.matches(DATE_PATTERN, data);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return bau;
	}
	
	public static boolean isOreMin (String numero)throws Exception{
		boolean bau = false;
		try{
			bau = Pattern.matches(HOURSMIN_PATTERN, numero);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return bau;
	}

	

}
