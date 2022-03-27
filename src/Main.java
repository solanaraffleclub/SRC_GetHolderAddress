import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		//excel file
        String filename = "./NFT_Addresses.csv";  
        //creating an instance of HSSFWorkbook class  
        HSSFWorkbook workbook = new HSSFWorkbook();
        
        try 
        {  
        	//json File
        	String resourceName = "./response.json";
            InputStream is = Main.class.getResourceAsStream(resourceName);
            if (is == null) {
                throw new NullPointerException("Cannot find resource file " + resourceName);
            }
            JSONTokener tokener = new JSONTokener(is);
            JSONArray array = new JSONArray(tokener);
             
	        //invoking creatSheet() method and passing the name of the sheet to be created   
	        HSSFSheet sheet = workbook.createSheet("Sheet1");   
	        //creating the 0th row using the createRow() method  
	        HSSFRow rowhead = sheet.createRow((short)0);  
	        //creating cell by using the createCell() method and setting the values to the cell by using the setCellValue() method  
	        rowhead.createCell(0).setCellValue("Name");  
	        rowhead.createCell(1).setCellValue("Mint Address");
	        rowhead.createCell(2).setCellValue("Owner");
            
            for(int i =0; i< array.length(); i++) {
            	JSONObject object = array.getJSONObject(i);
                JSONObject nft_metadata = object.getJSONObject("nft_metadata");
                JSONObject data = nft_metadata.getJSONObject("data");
                
                System.out.println(nft_metadata.getString("mint"));
                String jsonOwner = getOwner(nft_metadata.getString("mint"));
                JSONObject ownerObject = new JSONObject(jsonOwner);
                String owner = ownerObject.getString("nft_owner");
                
                System.out.println(i + " " + data.getString("name") + " " + nft_metadata.getString("mint") + " " + owner);
                
                HSSFRow row = sheet.createRow((short)i+1);  
                row.createCell(0).setCellValue(data.getString("name"));  
    	        row.createCell(1).setCellValue(nft_metadata.getString("mint"));
    	        row.createCell(2).setCellValue(owner);
                
    	        TimeUnit.SECONDS.sleep(3);
            }
            
	        FileOutputStream fileOut = new FileOutputStream(filename);  
	        workbook.write(fileOut);  
	        //closing the Stream  
	        fileOut.close();  
	        //closing the workbook  
	        workbook.close();  
	        //prints the message on the console  
	        System.out.println("Excel file has been generated successfully.");  
        }   
        catch (Exception e)   {  
        	e.printStackTrace(); 
        	FileOutputStream fileOut = new FileOutputStream(filename);  
	        workbook.write(fileOut);  
	        //closing the Stream  
	        fileOut.close();  
	        //closing the workbook  
	        workbook.close();  
        }  

       
	}
	
	public static String getOwner(String mintAdd) {
		URL url;
		try {
			url = new URL("https://api.blockchainapi.com/v1/solana/nft/mainnet-beta/" + mintAdd + "/owner");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("APIKeyID", "ndoV5Co2OiDwaFd");
			con.setRequestProperty("APISecretKey", "mzGfsRP5gfImc9Z");
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			
			int status = con.getResponseCode();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
					
			con.disconnect();
			return content.toString();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
