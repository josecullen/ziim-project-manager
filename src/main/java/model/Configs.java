package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
	private static List<String> systemFolderNames = Arrays.asList(
			"01-EST","02-ARQ","03-AF-AC","04-CL-PL","05-GAS","06-ELEC","07-AA","08-INC","09-PUE","10-VENT","11-MOV"
			);
	private static List<String> systemNames = Arrays.asList(
			"EST","ARQ","AF-AC","CL-PL","GAS","ELEC","AA","INC","PUE","VENT","MOV"
			);
	
	static {		
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
			Configs.config = config;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Config getConfig(){
		return config;
	}

	public static List<String> getSystemFolderNames() {
		return systemFolderNames;
	}

	public static void setSystemFolderNames(List<String> systemFolderNames) {
		Configs.systemFolderNames = systemFolderNames;
	}

	public static List<String> getSystemNames() {
		return systemNames;
	}

	public static void setSystemNames(List<String> systemNames) {
		Configs.systemNames = systemNames;
	}
	
	
	
}
