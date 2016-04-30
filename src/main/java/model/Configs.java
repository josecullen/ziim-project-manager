package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.config.Config;

public class Configs {
	
	public static final String CONFIG_PATH = "./config.txt";	
	private static Map<String, String> configs;
	private static Config config;
	private static Logger logger = Logger.getGlobal();
	private static File file;
	
	static{
		configs = new HashMap<>();
		
		file = new File(CONFIG_PATH);
		if(!file.exists()){
			try {
				file.createNewFile();
				config = new Config();
				save(config);
			} catch (IOException e) {
				logger.warning("No se puede crear el archivo de configuración: "+e.getMessage());
			}
		}else{
			setValuesFromFile();
		}

	}
	
	

	private static void setValuesFromFile() {
		try {
			
			config = new ObjectMapper().readValue(file, Config.class);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public static Map<String, String> configs() {
		return configs;
	}

	public static void save(Config config){
		try {
			new ObjectMapper().writeValue(file, config);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Config getConfig(){
		return config;
	}
	
}
