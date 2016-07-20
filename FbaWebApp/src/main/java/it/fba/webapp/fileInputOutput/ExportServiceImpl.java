package it.fba.webapp.fileInputOutput;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.springframework.web.multipart.commons.CommonsMultipartFile;



public class ExportServiceImpl implements ExportService{

	private static final String path = "c:\\FbaWebAppOutput\\";
	@Override
	public void ottieniFile(String nomeFile, byte[] corpoFile) throws Exception {
		// TODO Auto-generated method stub
		FileOutputStream fileOutputStream =null;
		try{
		
			
			fileOutputStream = new FileOutputStream(path+nomeFile);
			fileOutputStream.write(corpoFile);
			
			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			if (fileOutputStream!=null){
				fileOutputStream.close();
			}
		}
		
		
		
	}

}
