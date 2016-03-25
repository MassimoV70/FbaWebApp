package it.fba.webapp.exception;

import java.util.Date;

public class CustomGenericException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7987760832927837210L;
	
	private Date date;
    private String message;
     
    public CustomGenericException(Date date, String message) {
        super();
        this.date = date;
        this.message = message;
    }

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
    
	@Override
    public String toString() {
        return "CustomGenericException [date=" + date + ", message=" + message + "]";
    }
     
}
