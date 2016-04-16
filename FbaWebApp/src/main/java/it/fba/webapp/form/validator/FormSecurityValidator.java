package it.fba.webapp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.fba.webapp.beans.PianoDIformazioneBean;
import it.fba.webapp.beans.UsersBean;
import it.fba.webapp.utils.Utils;

@Component
public class FormSecurityValidator implements Validator{
	
	

	

	

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
	
	public static  void pianoFormazioneValidator(Object pianoFormazione, Errors  errors) {
		// TODO Auto-generated method stub
		PianoDIformazioneBean form = (PianoDIformazioneBean)pianoFormazione;
		
		 if (form.getModulo1().equalsIgnoreCase(form.getModulo2())){
			 errors.rejectValue("modulo1", "errors", "Valori.diversi");
			 errors.rejectValue("modulo2", "errors", "Valori.diversi");
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
	

	

}
