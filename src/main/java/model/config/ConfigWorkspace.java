package model.config;

import java.util.ArrayList;
import java.util.List;

public class ConfigWorkspace {
	
	String path;
	List<ConfigTargetDirectory> targetDirectories;
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public List<ConfigTargetDirectory> getTargetDirectories() {
		if(targetDirectories == null){
			targetDirectories = new ArrayList<>();
		}
		return targetDirectories;
	}
	public void setTargetDirectories(List<ConfigTargetDirectory> targetDirectories) {
		this.targetDirectories = targetDirectories;
	} 
}
