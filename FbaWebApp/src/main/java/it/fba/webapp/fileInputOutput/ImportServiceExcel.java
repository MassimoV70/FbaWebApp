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

import it.fba.webapp.beans.AttuatoreBean;
import it.fba.webapp.beans.DatiFIleUploadBean;
import it.fba.webapp.beans.FileBean;
import it.fba.webapp.beans.ImplementaPianoFormBean;
import it.fba.webapp.beans.ListaFileBean;
import it.fba.webapp.beans.PianoDIformazioneBean;
import it.fba.webapp.utils.Utils;

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
					 if (cell.getCellType()==0){
						 map.put(Integer.toString(i), Utils.convertDoubleToString(cell.getNumericCellValue()));
					 }else{
						 map.put(Integer.toString(i), cell.getStringCellValue()); 
					 }
					  
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
					 if (cell.getCellType()==0){
						 map.put(Integer.toString(i), Utils.convertDoubleToString(cell.getNumericCellValue()));
						
					 }else{
					   map.put(Integer.toString(i), cell.getStringCellValue());
					 }
					
					 
				 }
				 listaPiani.add(map) ;
			 }
		 }else{
			 throw new IllegalArgumentException("Il file "+file.fileData.getName()+" non è nel formato xls/xlsx atteso");
		 }
		 
		
		 
			
		 
		
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return listaPiani;
	}

	@Override
	public AttuatoreBean importaCertificati(ImplementaPianoFormBean implementaPianoFormBean) throws Exception {
		AttuatoreBean attuatoreBean = new AttuatoreBean();
		try {
			
			
			attuatoreBean.setAttuatorePIVA(implementaPianoFormBean.getAttuatorePIVA());
			if (implementaPianoFormBean.getFileData1()!=null&&!implementaPianoFormBean.getFileData1().isEmpty()){
				if(implementaPianoFormBean.getFileData1().getOriginalFilename().endsWith("pdf")){
					attuatoreBean.setNomeAllegato1(implementaPianoFormBean.getFileData1().getOriginalFilename());
				   
				    attuatoreBean.setAllegatoFile1(implementaPianoFormBean.getFileData1().getBytes());
				}
			}
			if (implementaPianoFormBean.getFileData2()!=null&&!implementaPianoFormBean.getFileData2().isEmpty()){
				if(implementaPianoFormBean.getFileData2().getOriginalFilename().endsWith("pdf")){
					attuatoreBean.setNomeAllegato2(implementaPianoFormBean.getFileData2().getOriginalFilename());
				   
				    attuatoreBean.setAllegatoFile2(implementaPianoFormBean.getFileData2().getBytes());
				}
			}
			if (implementaPianoFormBean.getFileData3()!=null&&!implementaPianoFormBean.getFileData1().isEmpty()){
				if(implementaPianoFormBean.getFileData3().getOriginalFilename().endsWith("pdf")){
					attuatoreBean.setNomeAllegato3(implementaPianoFormBean.getFileData3().getOriginalFilename());
				   
				    attuatoreBean.setAllegatoFile3(implementaPianoFormBean.getFileData3().getBytes());
				}
			}
			if (implementaPianoFormBean.getFileData4()!=null&&!implementaPianoFormBean.getFileData4().isEmpty()){
				if(implementaPianoFormBean.getFileData4().getOriginalFilename().endsWith("pdf")){
					attuatoreBean.setNomeAllegato4(implementaPianoFormBean.getFileData4().getOriginalFilename());
				   
				    attuatoreBean.setAllegatoFile4(implementaPianoFormBean.getFileData4().getBytes());
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
		}
		return attuatoreBean;
	}
	
	

	
	
	

}
