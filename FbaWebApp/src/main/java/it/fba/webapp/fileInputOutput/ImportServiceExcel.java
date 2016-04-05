package it.fba.webapp.fileInputOutput;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xslf.usermodel.XSLFSheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import it.fba.webapp.beans.FileBean;
import it.fba.webapp.beans.PianoDIformazioneBean;

public class ImportServiceExcel implements ImportService{

	@Override
	public  ArrayList<HashMap<String, String>> importFile(FileBean file) throws Exception {
		// TODO Auto-generated method stub
		 ArrayList<HashMap<String, String>> listaPiani = new ArrayList<>();
		try{
		ByteArrayInputStream byteInput = new ByteArrayInputStream(file.getFileData().getBytes());
		
		 if (file.getFileData().getOriginalFilename().endsWith("xls")){
			  HSSFWorkbook workbook= new HSSFWorkbook(byteInput);
			 HSSFSheet sheet = workbook.getSheetAt(0);
			 Iterator<Row> rowIterator = sheet.iterator();
			 while(rowIterator.hasNext()){
				 Row row = rowIterator.next();
				 Iterator<Cell> cellIterator = row.cellIterator();
				 
				  HashMap<String , String> map= new LinkedHashMap<>();
				   int i = 0;
				 while(cellIterator.hasNext()){
					 i++;
					 Cell cell = cellIterator.next();
					   map.put(Integer.toString(i), cell.getStringCellValue());
					
					
					 
				 }
				 listaPiani.add(map) ;
			 }
		
		 }
		 else if (file.getFileData().getOriginalFilename().endsWith("xlsx")) {
			 XSSFWorkbook workbook = new XSSFWorkbook(byteInput);
			 XSSFSheet sheet = workbook.getSheetAt(0);
			 Iterator<Row> rowIterator = sheet.iterator();
			 while(rowIterator.hasNext()){
				 Row row = rowIterator.next();
				 Iterator<Cell> cellIterator = row.cellIterator();
				 
				  HashMap<String , String> map= new LinkedHashMap<>();
				   int i = 0;
				 while(cellIterator.hasNext()){
					 i++;
					 Cell cell = cellIterator.next();
					   map.put(Integer.toString(i), cell.getStringCellValue());
					
					
					 
				 }
				 listaPiani.add(map) ;
			 }
		 }else{
			 throw new IllegalArgumentException("Received file does not have a standard excel extension.");
		 }
		 
		
		 
			
		 
		
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return listaPiani;
	}
	
	

}
