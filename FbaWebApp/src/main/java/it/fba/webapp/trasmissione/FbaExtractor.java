package it.fba.webapp.trasmissione;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;

public class FbaExtractor {
	 /**
     * @param args the command line arguments
     */
    
    static String url = "jdbc:mysql://10.0.0.73:3306/fba_docdb";
    static String username = "fbaroot";
    static String password = "Passw0rd";
    static String basePath = "/opt/FbaUploads/";
    static String myPath = "";
//      
//      static String url = "jdbc:mysql://localhost:3306/FBA_DOCDB";
//      static String username = "root";
//      static String password = "mysql";
//      static String basePath = "C:/FbaUploads/";
//      static String myPath = "";
    
//    public static void main(String[] args) {        
//        Connection conn = openConnection();
//        firstStep(conn);
//        closeConnection(conn);
//    }
    
      public static Connection  openConnection(){
          System.out.println("Connecting database...");
          Connection connection = null;
          try {
              Class.forName("com.mysql.jdbc.Driver");
              System.out.println("Driver loaded!");

              connection = DriverManager.getConnection(url, username, password);
              System.out.println("Database connected!");
          } catch (Exception e) {
              throw new IllegalStateException("Cannot connect the database!", e);
          }
          return connection;
      }
      
      
      /* 
       * 
       * Recupero idPiano e scrivo insert di mon_progetto_didattico
       * 
      */
      public static void firstStep(Connection connection)throws Exception{
          Statement statement = null;
          ResultSet resultSet = null;
          StringBuilder sb = null;
          BufferedWriter fw=null;  
          FileOutputStream fos=null;
          BufferedWriter fwReset=null;
          FileOutputStream fosReset=null;
          String formeaiuti = "";
          
          try{
              String firstQuery = "select piani_formazione.* from piani_formazione "
                  + " where not exists (select * from ett_load_status where ett_load_status.ett_id_piano = piani_formazione.id) "
                  + " and piani_formazione.enabled = 1 "
                  + " union "
                  + " select piani_formazione.* from piani_formazione "
                  + " where not exists  (select * from ett_load_status where ett_load_status.ett_id_piano = piani_formazione.id and ett_load_status.status = 1) "
                  + " and piani_formazione.enabled = 1; "; 
              statement = connection.createStatement();
              resultSet = statement.executeQuery(firstQuery);
              
//              Timestamp timestamp = new Timestamp(System.currentTimeMillis());
              File newResetFile = new File(basePath+"/resetTrasmissione.sql");
              fosReset = new FileOutputStream(newResetFile);
              fwReset = new BufferedWriter(new OutputStreamWriter(fosReset));
            
              String idpiano_client = "";
              while(resultSet.next()){
                  idpiano_client = resultSet.getString("id");
                  String titolo =  resultSet.getString("nomeprogetto") + "-"  + resultSet.getString("nuemroprotocollo");
                  File  dirDest = new File(basePath + "/Piano-" +idpiano_client );
                  dirDest.mkdir();                
                  myPath = dirDest.getPath();
                  File newTextFile = new File(myPath+"/"+titolo+".sql");
                  
                  formeaiuti = resultSet.getString("formeaiuti");
                  
                  String protocollo = resultSet.getString("nuemroprotocollo").replaceAll("PIVA", "").trim();
                  
                  
                  fos = new FileOutputStream(newTextFile);
                  fw = new BufferedWriter(new OutputStreamWriter(fos));
                  
                  
                  updateTrasmissione(connection, idpiano_client, fwReset);

                  sb = new StringBuilder();
                  
                  // Inizio file Creo Procedura
                  fw.write("USE `fba`;");
                  fw.newLine();
                  fw.write("DROP procedure IF EXISTS `ett_load_progetto`;");
                  fw.newLine();
                  fw.newLine();
                  fw.write("DELIMITER $$");
                  fw.newLine();
                  fw.write("USE `fba`$$");
                  fw.newLine();
                  fw.write("CREATE DEFINER=`root`@`localhost` PROCEDURE `ett_load_progetto`()");
                  fw.newLine();
                  
                  fw.write("ETT:BEGIN");
                  fw.newLine();
                                                  
                  fw.write("-- Variabili utilizzate");
                  fw.newLine();
                  fw.write("DECLARE idMonAttuatorePiano INT DEFAULT -1;");
                  fw.newLine();
                  fw.write("DECLARE idAttuatore INT DEFAULT -1;");
                  fw.newLine();
                  fw.write("DECLARE new_progetto_id INT DEFAULT -1;");
                  fw.newLine();
                  fw.write("DECLARE idPiano INT DEFAULT -1;");
                  fw.newLine();
                  fw.write("DECLARE new_id_mod1 INT DEFAULT -1;");
                  fw.newLine();
                  fw.write("DECLARE itest INT DEFAULT 1;");
                  fw.newLine();
                  fw.write("DECLARE checkLav INT DEFAULT 0;");
                  fw.newLine();
                  fw.write("DECLARE nFile INT DEFAULT 0;");
                  fw.newLine();
                  fw.write("DECLARE contributo INT DEFAULT 0;");
                  fw.newLine();
                  fw.write("DECLARE checkgenerale INT DEFAULT 0;");
                  fw.newLine();
                  
                  // Blocco gestione Errori
                  sb.append("DECLARE EXIT HANDLER FOR SQLEXCEPTION, SQLWARNING\r\n");
                  sb.append("BEGIN\r\n");
  		//sb.append("\tGET DIAGNOSTICS CONDITION 1 @sqlstate = RETURNED_SQLSTATE, @errno = MYSQL_ERRNO, @text = MESSAGE_TEXT;\r\n");
  		//sb.append("\tSET @full_error = CONCAT('ERROR ', @errno, ' (', @sqlstate, '): ', @text);\r\n");
                  sb.append("\tSET @full_error = 'Si è verificato un errore SQL.';\r\n" );
  		sb.append("\tcall ett_write_log("+idpiano_client+", @idPiano ,@errno, @full_error);\r\n");
  		sb.append("\tROLLBACK;\r\n");
                  sb.append("END;\r\n");
                  fw.write(sb.toString());
                  
                  fw.newLine();
                  fw.write("call p_elimina_piano_individuale('"+protocollo+"');");
                  fw.newLine();
                  
                  fw.write("START TRANSACTION;");
                  fw.newLine();
                  
                  fw.write("set @idAttuatore=84034;");
                  fw.newLine();
                  
                 // checkContributi(connection, fw, idpiano_client);
                  
                  attuatoreFiles(connection, fw, idpiano_client);
                  
                  sb = new StringBuilder();
                  sb.append("-- Check protocollo\r\n");
                  sb.append("select count(*) into @itest from mon_piano where id_soggetto_presentatore = 83830 AND nr_protocollo = '"+protocollo+"';\r\n");
                  sb.append("IF @itest < 1 then\r\n");
                  sb.append("\tcall ett_write_log("+idpiano_client+",-1, -1, 'Protocollo non Banca Intesa');\r\n");
                  sb.append("\tROLLBACK;\r\n");
                  sb.append("\tLEAVE ETT;\r\n");                
                  sb.append("END IF;\r\n");
                  
                  sb.append("-- Chekc su piano\r\n");
                  sb.append("select count(*) into @itest from mon_piano where nr_protocollo = '"+protocollo+"';\r\n");
                  sb.append("IF @itest < 1 then\r\n");
                  sb.append("\tcall ett_write_log("+idpiano_client+",-1, -1, 'Piano FBA non esistente');\r\n");
                  sb.append("\tROLLBACK;\r\n");
                  sb.append("\tLEAVE ETT;\r\n");                
                  sb.append("ELSE\r\n");
                  sb.append("\tselect id into @idPiano from mon_piano where nr_protocollo = '"+protocollo+"';\r\n");
                  sb.append("END IF;\r\n");
                  fw.write(sb.toString());
                  
                  
                  fw.write("-- Inserimento mon_attuatore_piano");
                  fw.newLine();
                  fw.write("insert into mon_attuatori_piano (ID_PIANO, ID_ATTUATORE) values ( @idPiano, @idAttuatore );");
                  fw.newLine();
                  fw.write("SET @idMonAttuatorePiano = LAST_INSERT_ID();");
                  fw.newLine();
                  
                  
                  allegatiPianoFiles(fw, resultSet.getString("nomeAllegato1"));
                  allegatiPianoFiles(fw, resultSet.getString("nomeAllegato2"));
                  allegatiPianoFiles(fw, resultSet.getString("nomeAllegato3"));
                  allegatiPianoFiles(fw, resultSet.getString("nomeAllegato4"));
                  
                  
                  fw.write("-- Inserimento mon_progetto_didattico");
                  fw.newLine();
                  fw.write("insert into mon_progetto_didattico (NOME_PROGETTO, ID_PIANO, ID_TIPOLOGIA_FORMAZIONE, TEMATICA_FORMATIVA_INDIVIDUALI) values (");
                  fw.write("'"+resultSet.getString("nomeprogetto")+"',");
                  fw.write(" @idPiano, ");
                  fw.write("'" +resultSet.getString("tipocorsopiano") + "',");
                  fw.write("'" +resultSet.getString("tematicaformativa") + "'");
                  fw.write(");");
                  fw.newLine();
                  fw.write("SET @new_progetto_id = LAST_INSERT_ID();");                
                  fw.newLine();
                  
                  
                  fw.write("-- Inserimento mon_modulo\r\n");
                  if (resultSet.getString("modulo1")!=null){
                      String modFormativa1 = "";
                      fw.write("-- Modulo 1 : " + resultSet.getString("modulo1") + "\r\n");
                      fw.write("insert into mon_modulo (NOME_MODULO, DURATA_TOTALE_ORE, DURATA_TOTALE_MINUTI, MODALITA_FORMAZIONE, ID_PROGETTO, TEMATICA_FORMATIVA) values (");
                      fw.write("'" + resultSet.getString("modulo1")+ "',");
                      String durata = resultSet.getString("duratamodulo1").toString();
                      if (durata.toUpperCase().equals("ASSENTE")){
                          fw.write("0,");
                          fw.write("0,");
                      }else{
                          String[] oreminuti = durata.split(":");
                          fw.write("" + Integer.parseInt(oreminuti[0]) + ",");
                          fw.write("" + Integer.parseInt(oreminuti[1]) + ",");
                      }
                      if (resultSet.getString("fadmod1").toUpperCase().equals("AULA") || resultSet.getString("fadmod1").toUpperCase().equals("A")){
                          modFormativa1 = "A";
                          fw.write("'A',");
                      }else{
                          modFormativa1 = "E";
                          fw.write("'E',");
                      }                    
                      fw.write("@new_progetto_id ,");                    
                      fw.write("'"+resultSet.getString("tematicaformativa")+ "');");
                      fw.newLine();
                      fw.write("SET @new_id_mod1 = LAST_INSERT_ID();");
                      fw.newLine();
                      
                      
                      calendario(connection, fw, idpiano_client, "@new_id_mod1", resultSet.getString("modulo1"), modFormativa1);
                      fw.newLine();                    
                      // Lavoratore 
                      lavoratori(connection, fw, idpiano_client, "@new_id_mod1", resultSet.getString("modulo1"), modFormativa1);
                  }
                  if (resultSet.getString("modulo2")!=null){
                      String modFormativa2 = "";
                      fw.write("-- Modulo 2 : " + resultSet.getString("modulo2") + "\r\n");
                      fw.write("insert into mon_modulo (NOME_MODULO, DURATA_TOTALE_ORE, DURATA_TOTALE_MINUTI, MODALITA_FORMAZIONE, ID_PROGETTO, TEMATICA_FORMATIVA) values (");
                      fw.write("'" + resultSet.getString("modulo2")+ "',");
                      String durata = resultSet.getString("duratamodulo2").toString();
                      if (durata.toUpperCase().equals("ASSENTE")){
                          fw.write("0,");
                          fw.write("0,");
                      }else{
                          String[] oreminuti = durata.split(":");
                          fw.write("" + Integer.parseInt(oreminuti[0]) + ",");
                          fw.write("" + Integer.parseInt(oreminuti[1]) + ",");
                      }
                      if (resultSet.getString("fadmod2").toUpperCase().equals("AULA") || resultSet.getString("fadmod2").toUpperCase().equals("A")){
                          modFormativa2 = "A";
                          fw.write("'A',");
                      }else{
                          modFormativa2 = "E";
                          fw.write("'E',");
                      }    
                      fw.write("@new_progetto_id ,");  
                      fw.write("'"+resultSet.getString("tematicaformativa")+ "');");
                    
                      fw.write("\r\n");
                      fw.write("SET @new_id_mod2 = LAST_INSERT_ID();");
                      fw.write("\r\n");
                      
                      
                      calendario(connection, fw, idpiano_client,  "@new_id_mod2", resultSet.getString("modulo2"), modFormativa2);
                      fw.write("\r\n");
                      lavoratori(connection, fw, idpiano_client, "@new_id_mod2", resultSet.getString("modulo2"), modFormativa2);
                      
                      fw.newLine();
                      fw.write("\tcall ett_write_log("+idpiano_client+",@idPiano, 1, 'piano didattico inserito');");
                      fw.newLine();
                  }
                  
                  
                  
                  rendicontazione(connection, fw, idpiano_client, "@idAttuatore", "@idPiano");
                  fw.newLine();
                  fw.write("COMMIT;\r\n");  
                  
                  fw.write("END$$");
                  fw.newLine();
                  fw.write("DELIMITER ;");
                                 
                  fw.newLine();
                  fw.write("call ett_load_progetto();");
                  fw.newLine();
                  fw.write("call ett_update_mon_piano_finanziario('"+protocollo+"', '"+formeaiuti+"' );");
                  fw.newLine();
                  fw.write("DROP function IF EXISTS `ett_load_progetto`;");
                  fw.newLine();
                  fw.close(); 
                  fos.close();
             }
             
          }catch(Exception e){
          	 
          	  throw e;
            }finally{
          	  statement = null; 
          	  if(connection!=null){
              	  connection.close();
                }
          	  if (fw!=null){
          		  fw.close(); 
          	  }
          	  if(resultSet!=null){
          		  resultSet.close();  
          	  }
          	  if(fwReset!=null){
          		  fwReset.close();
          	  }
          	  if(fos!=null){
          		  fos.close();
          	  }
            }
          
      }
      
      private static void allegatiPianoFiles(BufferedWriter fw, String nomeAllegato)throws Exception{
          try{
              if (nomeAllegato != null){
            	  fw.write("set @idUpload = 0;");
                  fw.newLine();
                  fw.write("select id into @idUpload from upload where username = 'ETT' and REAL_FILE_NAME = '"+nomeAllegato+"';" );
                  fw.newLine();
                  fw.write("IF @idUpload > 0 then");
                  fw.newLine();
                  fw.write("\tinsert into mon_allegati_piano (id_mon_piano, id_upload, tipo_allegato, modificabile) values (@idPiano, @idUpload, 'ALL_PN_IND', 1);");
                  fw.newLine();
                  fw.write("END IF;");
                  fw.newLine();
              }
          }catch(Exception e){
        	  try{
                  fw.write("Errore Funzione Allegati: " + e.getMessage());
              }catch(Exception e1){
            	  throw e;
              }
               throw e;
          }
          
          
      }
      
      
      private static void updateTrasmissione(Connection connection, String idpiano_client,BufferedWriter fw)throws Exception{
          Statement statement = null;        
          int risultato;
          try{
        	  String update = "update piani_formazione  set enabled = 2 where id = " + idpiano_client;            
              statement = connection.createStatement();
              risultato = statement.executeUpdate(update);
              //fw.write("update piani_formazione set enabled = 1 where id = "+ idpiano_client + ";");
              fw.write("call piani_formazione_status("+ idpiano_client + ");");
              fw.newLine();
          }catch(Exception e){
              try{
                  fw.write(e.getMessage());
              }catch(Exception e1){
  				 throw e;
  			}
              throw e;
          }
          statement = null;
          return;
      }
      
      private static void attuatoreFiles(Connection connection, BufferedWriter fw, String idpiano_client)throws Exception{
          Statement statement = null;        
          ResultSet resultSet = null;
          //Timestamp timestamp = new Timestamp(System.currentTimeMillis());
          
          try{
        	  String scaricaFile = "select a.* from attuatore a where a.attuatorepiva in (select b.pivaattuatore from piani_formazione b where b.id = "+ idpiano_client+" ) ";
              statement = connection.createStatement();
              resultSet = statement.executeQuery(scaricaFile);
              while(resultSet.next()){
                  // Scarico File su filesystem e proparo la insert
                  String RealFileName = "";
                  String query = "";
                  if (resultSet.getString("nomeallegato1") != null ){
                      RealFileName = "ETT--" + resultSet.getString("nomeallegato1");
                      fw.write("-- Verifica Esisteza allegato : " + resultSet.getString("nomeallegato1"));
                      fw.newLine();
                      fw.write("select count(*) into @nFile from upload where USERNAME = 'ETT' and REAL_FILE_NAME = '"+resultSet.getString("nomeallegato1")+"';");
                      fw.newLine();
                      fw.write("IF @nFile < 1 then");
                      fw.newLine();
                      query = "select allegatofile1 from attuatore where nomeallegato1 = '"+resultSet.getString("nomeallegato1")+"';";
                      getPDFData(connection, query, RealFileName, true);
                      fw.write("-- salvataggio allegato : " + resultSet.getString("nomeallegato1"));
                      fw.newLine();
                      fw.write("insert into upload (USERNAME, NOTE, FILE_NAME, REAL_FILE_NAME) values (");
                      fw.write("'ETT', 'note', ");
                      fw.write("'"+ RealFileName + "', '"+resultSet.getString("nomeallegato1")+"');");
                      fw.newLine();
                      fw.write("SET  @idFile = LAST_INSERT_ID();");
                      fw.newLine();
                      fw.write("END IF;");
                      fw.newLine();
                  }
                  if (resultSet.getString("nomeallegato2") != null ){
                      RealFileName = "ETT--" + resultSet.getString("nomeallegato2");
                      fw.write("-- Verifica Esisteza allegato : " + resultSet.getString("nomeallegato2"));
                      fw.newLine();
                      fw.write("select count(*) into @nFile from upload where USERNAME = 'ETT' and REAL_FILE_NAME = '"+resultSet.getString("nomeallegato2")+"';");
                      fw.newLine();
                      fw.write("IF @nFile < 1 then");
                      fw.newLine();
                      query = "select allegatofile2 from attuatore where nomeallegato2 = '"+resultSet.getString("nomeallegato2")+"';";
                      getPDFData(connection, query, RealFileName, true);
                      fw.write("-- salvataggio allegato : " + resultSet.getString("nomeallegato2"));
                      fw.newLine();
                      fw.write("insert into upload (USERNAME, NOTE, FILE_NAME, REAL_FILE_NAME) values (");
                      fw.write("'ETT', 'note', ");
                      fw.write("'"+ RealFileName + "', '"+resultSet.getString("nomeallegato2")+"');");
                      fw.newLine();
                      fw.write("SET  @idFile = LAST_INSERT_ID();");
                      fw.newLine();
                      fw.write("END IF;");
                      fw.newLine();
                  }
                  
                  if (resultSet.getString("nomeallegato3") != null ){
                      RealFileName = "ETT--" + resultSet.getString("nomeallegato3");
                      fw.write("-- Verifica Esisteza allegato : " + resultSet.getString("nomeallegato3"));
                      fw.newLine();
                      fw.write("select count(*) into @nFile from upload where USERNAME = 'ETT' and REAL_FILE_NAME = '"+resultSet.getString("nomeallegato3")+"';");
                      fw.newLine();
                      fw.write("IF @nFile < 1 then");
                      fw.newLine();
                      query = "select allegatofile3 from attuatore where nomeallegato3 = '"+resultSet.getString("nomeallegato3")+"';";
                      getPDFData(connection, query, RealFileName, true);
                      fw.write("-- salvataggio allegato : " + resultSet.getString("nomeallegato3"));
                      fw.newLine();
                      fw.write("insert into upload (USERNAME, NOTE, FILE_NAME, REAL_FILE_NAME) values (");
                      fw.write("'ETT', 'note', ");
                      fw.write("'"+ RealFileName + "', '"+resultSet.getString("nomeallegato3")+"');");
                      fw.newLine();
                      fw.write("SET  @idFile = LAST_INSERT_ID();");
                      fw.newLine();
                      fw.write("END IF;");
                      fw.newLine();
                  }
                  
                  if (resultSet.getString("nomeallegato4") != null ){
                      RealFileName = "ETT--" + resultSet.getString("nomeallegato4");
                      fw.write("-- Verifica Esisteza allegato : " + resultSet.getString("nomeallegato4"));
                      fw.newLine();
                      fw.write("select count(*) into @nFile from upload where USERNAME = 'ETT' and REAL_FILE_NAME = '"+resultSet.getString("nomeallegato4")+"';");
                      fw.newLine();
                      fw.write("IF @nFile < 1 then");
                      fw.newLine();
                      query = "select allegatofile4 from attuatore where nomeallegato1 = '"+resultSet.getString("nomeallegato4")+"';";
                      getPDFData(connection, query, RealFileName, true);
                      fw.write("-- salvataggio allegato : " + resultSet.getString("nomeallegato4"));
                      fw.newLine();
                      fw.write("insert into upload (USERNAME, NOTE, FILE_NAME, REAL_FILE_NAME) values (");
                      fw.write("'ETT', 'note', ");
                      fw.write("'"+ RealFileName + "', '"+resultSet.getString("nomeallegato4")+"');");
                      fw.newLine();
                      fw.write("SET  @idFile = LAST_INSERT_ID();");
                      fw.newLine();
                      fw.write("END IF;");
                      fw.newLine();
                  }
                  
              }
          }catch(Exception e){
        	  try{
                  fw.write("Errore Funzione Attuatori Files: " + e.getMessage());
              }catch(Exception e1){
               throw e;
              }
        	  throw e;
          }finally{
          	  statement = null; 
            }
          
      }
      
      private static void calendario(Connection connection, BufferedWriter fw, String idpiano_client, String idModulo, String nomeModulo, String modalitaFormativa)throws Exception{
          // Riferimento al modulo @new_id_mod1        
          Statement statement = null;        
          ResultSet resultSet = null;
          try{
              // Controllo Completezza Calendario
              String CheckCalendario = "";
              if (modalitaFormativa.toUpperCase().equals("A")){
                  if (idModulo.equals("@new_id_mod1")){
                      CheckCalendario = "SELECT "
                      + "((sum(TIME_TO_SEC(timediff(finemattina, iniziomattina))) + sum(TIME_TO_SEC(timediff(finepomeriggio, iniziopomeriggio))) ) / 60  "
                      + " - piani_formazione.duratamodulo1 * 60)  as checkdurata "
                      + " FROM calendario_modulo "
                      + " inner join piani_formazione on piani_formazione.id = calendario_modulo.idpiano "
                      + " where idpiano = "+idpiano_client+" ;";
                  }else{
                      CheckCalendario = "SELECT "
                      + "((sum(TIME_TO_SEC(timediff(finemattina, iniziomattina))) + sum(TIME_TO_SEC(timediff(finepomeriggio, iniziopomeriggio))) ) / 60  "
                      + " - piani_formazione.duratamodulo2 * 60)  as checkdurata "
                      + " FROM calendario_modulo "
                      + " inner join piani_formazione on piani_formazione.id = calendario_modulo.idpiano "
                      + " where idpiano = "+idpiano_client+" ;";
                  }
                  statement = connection.createStatement();
                  resultSet = statement.executeQuery(CheckCalendario);
                  int completo = 0;
                  while(resultSet.next()){
                      completo = resultSet.getInt("checkdurata");
                  }
                  if (completo != 0){
                	  fw.write("call ett_write_log("+idpiano_client+",-1, -2, 'Calendario non completo. Differenza con Durata modulo : "+completo+"');");
                      fw.newLine();
                      fw.write("ROLLBACK;");
                      fw.newLine();
                      fw.write("LEAVE ETT;");                    
                      fw.newLine();
                  }
                  resultSet.close();
                  statement = null;
              }
              
              String calendario = "select max(data) as dmax, min(data) as dmin from calendario_modulo where idPiano = " + idpiano_client + " and nomemodulo = '"+nomeModulo+"'";
              statement = connection.createStatement();
              resultSet = statement.executeQuery(calendario);
              String dataMax = "";
              String dataMin = "";
              
              while(resultSet.next()){
                  dataMax = resultSet.getString("dmax");
                  dataMin = resultSet.getString("dmin");
              }
              if (modalitaFormativa.toUpperCase().equals("A")){
                  // Controllo Congruenza Date
                  fw.write("-- Controllo Data .. \r\n");
                  fw.newLine();
                  fw.write("set @checkgenerale = 0;");
                  fw.newLine();
                  fw.write("select ett_check_date(@idPiano, "+idpiano_client+",'"+dataMin+"', '"+dataMax+"') into @checkgenerale;");
                  fw.newLine();
                  fw.write("IF @checkgenerale < 0 then\r\n");
                  fw.write("\tcall ett_write_log("+idpiano_client+", @idPiano, -13, 'Date non congruenti');\r\n");
                  fw.write("\tROLLBACK;\r\n");
                  fw.write("\tLEAVE ETT;\r\n");
                  fw.write("END IF;\r\n");
                  fw.newLine();
              }
              
              
              fw.write("-- Edizione per Modulo : "+nomeModulo+ "\r\n");
              fw.write("insert into mon_edizione (DATA_INIZIO, DATA_FINE, ID_MODULO, NOME, MODALITA_FORMAZIONE) values (");
              if (modalitaFormativa.toUpperCase().equals("A")){
                  fw.write("'"+dataMin+"',");
                  fw.write("'"+dataMax+"',");
              }else{
                  fw.write("null,");
                  fw.write("null,");
              }
              
              fw.write(idModulo + ", '1', ");
              
              //Qui ci va A o E 
              fw.write("'"+modalitaFormativa+"');");
              
              fw.write("\r\n");
              fw.write("SET @new_id_edizione = LAST_INSERT_ID();");
              fw.write("\r\n");
              
              resultSet.close();
              statement = null;
              calendario = "select * from calendario_modulo where idPiano = " + idpiano_client + " and nomemodulo = '"+nomeModulo+"'";
              statement = connection.createStatement();
              resultSet = statement.executeQuery(calendario);
              while(resultSet.next()){
                  // Creao Anagrafica Mon Orario Aula
                  fw.write("-- Orario Aula per Modulo : \r\n");
                  String iniziomattina = resultSet.getString("iniziomattina").toString();
                  String finemattina = resultSet.getString("finemattina").toString();
                  String iniziopomeriggio = resultSet.getString("iniziopomeriggio").toString();
                  String finepomeriggio = resultSet.getString("finepomeriggio").toString();
                  String[] imoreminuti = iniziomattina.split(":");
                  String[] fmoreminuti = finemattina.split(":");
                  String[] iporeminuti = iniziopomeriggio.split(":");
                  String[] fporeminuti = finepomeriggio.split(":");
                  
                  String realMattinaInizioOre = "0";
                  String realMattinaInizioMinuti = "0";
                  String realMattinaFineOre = "0";
                  String realMattinaFineMinuti = "0";
                  String realPomeriggioInizioOre = "0";
                  String realPomeriggioInizioMinuti = "0";
                  String realPomeriggioFineOre = "0";
                  String realPomeriggioFineMinuti = "0";
                  if (!iniziomattina.toUpperCase().equals("ASSENTE")){
                      if (Integer.parseInt(imoreminuti[0])>=7 && Integer.parseInt(imoreminuti[0]) <= 14){
                          realMattinaInizioOre = "" +Integer.parseInt(imoreminuti[0]);
                          realMattinaInizioMinuti = "" +Integer.parseInt(imoreminuti[1]);
                          realMattinaFineOre = "" +Integer.parseInt(fmoreminuti[0]);
                          realMattinaFineMinuti = "" +Integer.parseInt(fmoreminuti[1]);
                      }else{
                          realPomeriggioInizioOre = "" +Integer.parseInt(imoreminuti[0]);
                          realPomeriggioInizioMinuti = "" +Integer.parseInt(imoreminuti[1]);
                          realPomeriggioFineOre = "" +Integer.parseInt(fmoreminuti[0]);
                          realPomeriggioFineMinuti = "" +Integer.parseInt(fmoreminuti[1]);
                      }
                  }
                  if (!iniziopomeriggio.toUpperCase().equals("ASSENTE")){
                      if (Integer.parseInt(iporeminuti[0])>=14 && Integer.parseInt(iporeminuti[0]) <= 22){
                          realPomeriggioInizioOre = "" +Integer.parseInt(iporeminuti[0]);
                          realPomeriggioInizioMinuti = "" +Integer.parseInt(iporeminuti[1]);
                          realPomeriggioFineOre = "" +Integer.parseInt(fporeminuti[0]);
                          realPomeriggioFineMinuti = "" +Integer.parseInt(fporeminuti[1]);
                      }else{
                          realMattinaInizioOre = "" +Integer.parseInt(iporeminuti[0]);
                          realMattinaInizioMinuti = "" +Integer.parseInt(iporeminuti[1]);
                          realMattinaFineOre = "" +Integer.parseInt(fporeminuti[0]);
                          realMattinaFineMinuti = "" +Integer.parseInt(fporeminuti[1]);
                      }
                  }                
                  
                  fw.write("insert into mon_orario_aula (MATTINA_INIZIO_ORA, MATTINA_INIZIO_MINUTI, MATTINA_FINE_ORA, MATTINA_FINE_MINUTI, POMERIGGIO_INIZIO_ORA, POMERIGGIO_INIZIO_MINUTI, POMERIGGIO_FINE_ORA, POMERIGGIO_FINE_MINUTI, FL_FUORI_ORARIO) values (");
                  fw.write(realMattinaInizioOre + ",");
                  fw.write(realMattinaInizioMinuti + ",");
                  fw.write(realMattinaFineOre + ",");
                  fw.write(realMattinaFineMinuti + ",");
                  
                  fw.write(realPomeriggioInizioOre + ",");
                  fw.write(realPomeriggioInizioMinuti + ",");
                  fw.write(realPomeriggioFineOre + ",");
                  fw.write(realPomeriggioFineMinuti + ",");
                  
                  
                  fw.write("0);");
                  fw.write("\r\n");
                  fw.write("SET @new_id_orario = LAST_INSERT_ID();");
                  fw.write("\r\n");
                  
                  fw.write("-- Giornata per Modulo : \r\n");
                  
                  fw.write("insert into mon_giornata (DATA_GIORNATA, ID_ORARIO, ID_EDIZIONE, ID_SEDE) values (");
                  fw.write("'" + resultSet.getString("data") + "', @new_id_orario, @new_id_edizione , null);");
                  
                  fw.write("\r\n");
              }
              
             
          }catch(Exception e){
        	  try{
                  fw.write("Errore Funzione Calendario: " + e.getMessage());
              }catch(Exception e1){
            	  throw e;
              }
               throw e;
          }finally{
  			if(resultSet!=null){
          	 resultSet.close();
  			 }
              statement = null;
            }
          
      }
      
//      private static void checkContributi(Connection connection, BufferedWriter fw, String idpiano_client)throws Exception{
//          Statement statement = null;        
//          ResultSet resultSet = null;
//          try{
//              String mainLavoratore = "select sum(contributoprivato) - sum(contributofba) as contributo from rendicontazione where idpiano = "+idpiano_client+"";
//              statement = connection.createStatement();
//              resultSet = statement.executeQuery(mainLavoratore);
//              while(resultSet.next()){
//                  Double contributo = resultSet.getDouble("contributo");
//                  if (contributo<0){
//                	  fw.write("\tcall ett_write_log("+idpiano_client+",-1, -3, 'Contributo FBA maggiori dei Contributi privati');");
//                      fw.newLine();
//                      fw.write("\tROLLBACK;");         
//                      fw.newLine();
//                      fw.write("\tLEAVE ETT;");                    
//                      fw.newLine();
//                  }
//              }
//            
//          }catch(Exception e){
//        	  try{
//                  fw.write("Errore Funzione checkContributi: " + e.getMessage());
//              }catch(Exception e1){
//            	  throw e;
//              }
//               throw e;
//          }finally{
//        	  resultSet.close();
//          	  statement = null; 
//            }
//          
//      }
//      
      private static void lavoratori(Connection connection, BufferedWriter fw, String idpiano_client, String new_id_mod, String nomeModulo, String modalitaFormativa) throws Exception{
          //lavoratori file
          Statement statement = null;        
          ResultSet resultSet = null;
          try{
              String mainLavoratore = "select distinct(matricola) from lavoratori_modulo where idpiano = " + idpiano_client + " and nomemodulo = '"+nomeModulo+"'";
              statement = connection.createStatement();
              resultSet = statement.executeQuery(mainLavoratore);
              while(resultSet.next()){
                  fw.write("-- Lavoratore Matricola : " + resultSet.getString("matricola") + "\r\n");
                  fw.newLine();
                  fw.write("select ett_check_anag_lavoratore('"+resultSet.getString("matricola")+"', "+idpiano_client+") into @checkLav;");
                  fw.newLine();
                  fw.write("IF @checkLav < 1 then\r\n");
                  fw.write("\tcall ett_write_log("+idpiano_client+", -1, -10,'"+resultSet.getString("matricola")+" Lavoratore non esistente');\r\n");
                  fw.write("\tROLLBACK;\r\n");
                  fw.write("\tLEAVE ETT;\r\n");
                  
                  fw.write("END IF;\r\n");
                  fw.newLine();
              }
              statement = null; 
              resultSet.close();
              
              
              // Lavoratori file
              if (modalitaFormativa.toUpperCase().equals("E")){
  				String fileLavoratori = "select distinct a.* from lavoratorifile a, lavoratori_modulo b where a.nomeallegato = b.nomeallegato and idpiano = " + idpiano_client ;
  				statement = connection.createStatement();
  				resultSet = statement.executeQuery(fileLavoratori);
  				while(resultSet.next()){
  					//String RealFileName = resultSet.getString("nomeallegato");
  					//Timestamp timestamp = new Timestamp(System.currentTimeMillis());
  					//String RealFileName = "ETT" + timestamp.getTime() + "-" + resultSet.getString("nomeallegato");
  					String RealFileName = "ETT-" + idpiano_client + "-" + resultSet.getString("nomeallegato");
  					String query = "select nomeallegato from lavoratorifile where nomeallegato = '"+resultSet.getString("nomeallegato")+ "'";
  					getPDFData(connection, query, RealFileName, false);
  					fw.write("-- salvataggio allegato : " + resultSet.getString("nomeallegato"));
  					fw.newLine();
  					fw.write("insert into upload (USERNAME, NOTE, FILE_NAME, REAL_FILE_NAME) values (");
  					fw.write("'ETT', 'note', ");
  					fw.write("'"+ RealFileName + "', '"+resultSet.getString("nomeallegato")+"');");
  					fw.newLine();
  					fw.write("SET  @idFile = LAST_INSERT_ID();");
  					fw.newLine();
  					fw.write("INSERT INTO mon_attestato (NOME, TIPOLOGIA, ID_ATTUATORE, ID_DOCUMENTO, ID_PIANO) values (");
  					fw.write("'" +RealFileName+ "',");
  					fw.write("'ATTESTATO',");
  					fw.write("@idAttuatore,");
  					fw.write("@idFile,");
  					fw.write("@idPiano);");
  					fw.newLine();
  				}
  				statement = null; 
  				resultSet.close();
  			}
              
              String lavoratore = "select * from lavoratori_modulo where idpiano = " + idpiano_client + " and nomemodulo = '"+nomeModulo+"'";
              statement = connection.createStatement();
              resultSet = statement.executeQuery(lavoratore);
              while(resultSet.next()){
                  fw.write("-- Lavoratore Matricola : " + resultSet.getString("matricola") + "\r\n");
                  fw.write("select @idLavoratore:=id from lavoratore inner join dati_inps on dati_inps.ID_INPS=lavoratore.ID_MATRICOLA_INPS where lavoratore.MATRICOLA_LAVORATORE = '"+resultSet.getString("matricola")+"';");
                  fw.write("\r\n");
                  
                  fw.write("set @new_id_partecipante = -1;");
                  fw.newLine();
                  fw.write("select count(id) into @checkgenerale from mon_partecipante where id_piano = @idPiano and id_lavoratore = @idLavoratore;");
                  fw.newLine();
                  fw.write("IF @checkgenerale > 0 then");                
                  fw.newLine();
                  fw.write("select id into @new_id_partecipante from mon_partecipante where id_piano = @idPiano and id_lavoratore = @idLavoratore;");
                  fw.newLine();
                  fw.write("ELSE");                
                  fw.newLine();
                  fw.write("insert into mon_partecipante (ID_LAVORATORE, ID_PIANO) values (");
                  fw.write("@idLavoratore, @idPiano);");
                  fw.write("\r\n");
                  fw.write("SET @new_id_partecipante = LAST_INSERT_ID();");
                  fw.write("\r\n");
                  fw.write("END IF;");
                  fw.newLine();
                  
                  
                  fw.write("-- Lavoratore Presenze : " + resultSet.getString("matricola") + "\r\n");
                  
                  fw.write("insert into mon_registro_presenze (ID_PARTECIPANTE, ID_EDIZIONE, DURATA_ORE, DURATA_MINUTI, TEST_SUPERATO) values (");
                  fw.write("@new_id_partecipante, @new_id_edizione, ");
                  String orepresenza = resultSet.getString("orepresenza").toString();
                  String[] oreminuti = orepresenza.split(":");
                  fw.write("" + Integer.parseInt(oreminuti[0]) + ",");
                  fw.write("" + Integer.parseInt(oreminuti[1]) + ","); 
                  if (resultSet.getString("esitotest").toUpperCase().equals("ASSENTE")){
                      fw.write("null);");
                  }else if (resultSet.getString("esitotest").toUpperCase().equals("SUPERATO")){
                      fw.write("1);");
                  }else if (resultSet.getString("esitotest").toUpperCase().equals("1")){
                      fw.write("1);");
                  }else{
                      fw.write("0);");
                  }
                  fw.write("\r\n");
              }
              resultSet.close();
          }catch(Exception e){
        	  try{
                  fw.write("Errore Funzione Lavoratori: " + e.getMessage());
              }catch(Exception e1){
                  throw e;
              }
               throw e;
          }finally{
          	  statement = null; 
            }
          
      }
      
      private static void rendicontazione(Connection connection, BufferedWriter fw, String idpiano_client, String idAttuatore, String idPiano)throws Exception{
          Statement statement = null;        
          ResultSet resultSet = null;
          
          //Timestamp timestamp = new Timestamp(System.currentTimeMillis());

          try{
              String lavoratore = "select * from rendicontazione where idpiano = " + idpiano_client +";";
              statement = connection.createStatement();
              resultSet = statement.executeQuery(lavoratore);
              Double cfbatot = 0.0;
              while(resultSet.next()){
                  // Scarico File su filesystem e proparo la insert
            	//String RealFileName = "ETT" + timestamp.getTime() + "-" + resultSet.getString("nomeallegato");
                  String RealFileName = "ETT-" + idpiano_client + "-" + resultSet.getString("nomeallegato");
                  String query = "select allegatofile from RendicontazioneFile where nomeallegato = '"+resultSet.getString("nomeallegato")+"'";
                  getPDFData(connection, query, RealFileName, false);
                  fw.write("-- salvataggio allegato : " + resultSet.getString("nomeallegato"));
                  fw.write("\r\n");
                  fw.write("insert into upload (USERNAME, NOTE, FILE_NAME, REAL_FILE_NAME) values (");
                  fw.write("'ETT', 'note', ");
                  fw.write("'"+ RealFileName + "', '"+resultSet.getString("nomeallegato")+"');");
                  fw.write("\r\n");
                  fw.write("SET  @idFile = LAST_INSERT_ID();");
                  fw.write("\r\n");
                  //
                  
                  cfbatot += resultSet.getDouble("contributofba");
                  
                  fw.write("-- giustificativo : " + resultSet.getString("codice") + "\r\n");
                  fw.write("insert into mon_giustificativo (CODICE, TIPOLOGIA, DATA, ID_FORNITORE,DESTINATARIO, VALORE_FONDO, VALORE_IMPRESA, ID_SCANSIONE, ID_PIANO, VALORE_COMPLESSIVO, OGGETTO,VALORE_RICONOSCIUTO_FONDO, COSTO_PIANO,IVA_COMPLESSIVA) values (");
                  fw.write("'"+resultSet.getString("codice")+"', ");
                  fw.write("'"+resultSet.getString("tipologiagiustificativo")+"', ");
                  fw.write("'"+resultSet.getString("datagiustificativo")+"', ");
                  if (resultSet.getString("tipologiagiustificativo").toUpperCase().equals("A")){
                      fw.write(idAttuatore + ", '" + resultSet.getString("fornitorenominativo") +"',");
                  }else{
                      fw.write(" null, '" + resultSet.getString("fornitorenominativo") +"', ");
                  }
                  fw.write( resultSet.getString("contributofba") + ", ");
                  fw.write( resultSet.getString("contributoprivato") + ", ");
                  fw.write( " @idFile, @idPiano, ");
                  fw.write( resultSet.getString("valorecomplessivo") + ", ");
                  fw.write("'-', 0, ");
                  if (resultSet.getString("tipologiagiustificativo").toUpperCase().equals("C")){
                      fw.write( resultSet.getString("valorecomplessivo") + ", 0 );");
                  }else{
                	  Double valorec = resultSet.getDouble("valorecomplessivo");
                      Double iva = 22.0;
                      Double valorenoIva = (100*valorec)/100 + iva ;
                      DecimalFormat newFormat = new DecimalFormat("#.##");
                      //valorenoIva = Double.valueOf(newFormat.format(valorenoIva));
                      fw.write( ""+ newFormat.format(valorenoIva).replace(",", ".") + ", "+iva+" );");
                  }
                  fw.write("\r\n");
              }
              
              fw.write("select ett_check_costi(@idPiano, "+idpiano_client+", "+cfbatot+" );");
              fw.newLine();

              resultSet.close();
          }catch(Exception e){
        	  try{
                  fw.write("Errore Funzione rendicontazione: " + e.getMessage());
              }catch(Exception e1){
            	  throw e;
              }
               throw e;
          }finally{
          	  statement = null; 
           } 
          
      }
      
      
      
      private static void getPDFData(Connection conn, String query, String realName, boolean isAttuatoreFile)throws Exception {
          byte[] fileBytes=null;
          ResultSet rs=null;
          OutputStream targetFile=null;
          String currPath = myPath;
          if (isAttuatoreFile){
              currPath = basePath;
          }
          try {
              Statement state = conn.createStatement();
               rs = state.executeQuery(query);
              if (rs.next()) {
            	  fileBytes = rs.getBytes(1);
                  String ext = "";
                  try{
                      ext =(realName.substring(realName.length()-4));
                  }catch(Exception e1){
                	  throw e1;
                  }
                  if (ext.equals(".pdf")){
                      targetFile = new FileOutputStream(currPath + "/"+realName);
                  }else{
                      targetFile = new FileOutputStream(currPath + "/"+realName+".pdf");
                  }
                  targetFile.write(fileBytes);
                  targetFile.close();
                 
              }
          } catch (Exception e) {
               throw e;
          }
          finally{
        	  if (rs!=null){
        		  rs.close();
        	  }
        	  if(targetFile!=null){
        		  targetFile.flush();
        		  targetFile.close();
        	  }
        	  
          }
      }

      public static void closeConnection(Connection connection)throws Exception{
          try{
              connection.close();
          }catch(Exception e){
               throw e;
          }
      }
}
