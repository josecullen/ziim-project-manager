package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Configs {
	
	public static final String CONFIG_PATH = "./config.txt";	
	private static Map<String, String> configs;	
	private static Logger logger = Logger.getGlobal();
	private static File file;
	
	static{
		configs = new HashMap<>();
		file = new File(CONFIG_PATH);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				logger.warning("No se puede crear el archivo de configuración: "+e.getMessage());
			}
		}else{
			setValuesFromFile();
		}

	}
	
	

	private static void setValuesFromFile() {
		try {
			FileReader reader = new FileReader(file);
			BufferedReader bufferReader = new BufferedReader(reader);
			
			String keyValue = bufferReader.readLine();
			
			while(keyValue != null){
				String key = keyValue.split("=")[0];
				String value = keyValue.split("=")[1];
				
				configs.put(key, value);
				
				keyValue = bufferReader.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}



	public static Map<String, String> configs() {
		return configs;
	}

	
	
	
	
}
