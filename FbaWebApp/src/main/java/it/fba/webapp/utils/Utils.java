package it.fba.webapp.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.validation.Errors;

import it.fba.webapp.beans.UsersBean;


public class Utils {
	
	@Resource(name="myProperties")
	private static Properties myProperties;
	
	
    final static SimpleDateFormat formatter= new SimpleDateFormat("dd/MM/yyyy");
	final static SimpleDateFormat formatterDB= new SimpleDateFormat("dd/MM/yyyy");
	
	public static String dataOggi() {
		Date dataOggi=null;
		String dataOggiStr="";
		try {
			dataOggi = new Date();
			dataOggiStr = formattaData(dataOggi);
		} catch (Exception e) {
			// TODO: handle exception
			return dataOggiStr;
		}
		return dataOggiStr;
	}
	
	public static String formattaData (Date data)throws Exception{
		String formattedDate="";
		if (data!=null){
			formattedDate = formatter.format(data);
		}
		return formattedDate;		
	}
	
	public static Date dataDBFormatter(String data){
		Date dataFormattata=null;
		try {
			if (data!=null&&!data.isEmpty()){
				dataFormattata = formatterDB.parse(data);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		return dataFormattata;
	}
	
	
	
	public static ArrayList<UsersBean> userFormSettings (ArrayList<UsersBean> listaUtenti){
		try{
			if (listaUtenti!=null&&!listaUtenti.isEmpty()){
				for(UsersBean user :listaUtenti ){
					user.setDataInizioStr(Utils.formattaData(user.getDataInizio()));
					user.setDataFineStr(Utils.formattaData(user.getDataFine()));
					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return listaUtenti;
	}
	
	public static Map<String, String> getListaStati(Properties myProperty) {
		
		Map<String, String> listaStati = new LinkedHashMap<String, String>();
		listaStati.put(myProperty.getProperty("enabled.si"), "abilitato");
		listaStati.put(myProperty.getProperty("enabled.no"), "disabilitato");
		return listaStati;
		
	}
	
	public static boolean dataFuture(Date dataInizio, Date  dataFine) {
		// TODO Auto-generated method stub
		
		if (dataInizio!=null){
			if(dataFine!=null){
				if (dataFine.before(dataInizio)){
					return false;
				}
				
			}
		}
		return true;
		
       
	}

}
