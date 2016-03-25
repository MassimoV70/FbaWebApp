package it.fba.webapp.fileInputOutput;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import it.fba.webapp.beans.FileBean;
import it.fba.webapp.beans.PianoDIformazioneBean;

public class ImportServiceExcel implements ImportService{

	@Override
	public ArrayList<PianoDIformazioneBean> importFile(FileBean file)  {
		// TODO Auto-generated method stub
		 ArrayList<PianoDIformazioneBean> listaPiani = new ArrayList<>();
		try{
		ByteArrayInputStream byteInput = new ByteArrayInputStream(file.getFileData().getBytes());
		Workbook workbook;
		 if (file.getFileData().getOriginalFilename().endsWith("xls")){
			 workbook= new HSSFWorkbook(byteInput);
		 }
		 else if (file.getFileData().getOriginalFilename().endsWith("xlsx")) {
			 workbook = new XSSFWorkbook(byteInput);
		 }else{
			 throw new IllegalArgumentException("Received file does not have a standard excel extension.");
		 }
		 
		
		 for (Row row : workbook.createSheet()){
			 PianoDIformazioneBean piano = new PianoDIformazioneBean();
			 piano.setPianoDiFormazione(row.getCell(1).getStringCellValue());
			 piano.setModulo1(row.getCell(2).getStringCellValue());
			 piano.setModulo2(row.getCell(3).getStringCellValue());
			 piano.setAttuatorePIVA(row.getCell(4).getStringCellValue());
			 listaPiani.add(piano) ;
		 }
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return listaPiani;
	}
	
	

}
