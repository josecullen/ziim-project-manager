package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TargetDirectory extends FileBase{
	private List<SystemDirectory> systemDirectories = new ArrayList<>();

	public TargetDirectory() {	}
	public TargetDirectory(File targetFile) {
		super(targetFile.getAbsolutePath());
		
		filterByExtension(this.file).forEach(systemFile ->{
			System.out.println("    targetDir "+systemFile.getName());
			if(systemFile.isDirectory()){
				this.systemDirectories.add(new SystemDirectory(systemFile));
			}				
		});
	}

	private List<File> filterByExtension(File file) {
		return Arrays.asList(file.listFiles());
		
//		Configs.getConfig().getExtensions().stream().anyMatch(extension -> 
//		name.endsWith(extension.getName()))
//	)
	}
	
	private List<File> filterByNotDirectory(File file){
		return Arrays.asList(file.listFiles((dir) -> !dir.isDirectory()));
	}
	public List<SystemDirectory> getSystemDirectories() {
		return systemDirectories;
	}
	public void setSystemDirectories(List<SystemDirectory> systemDirectories) {
		this.systemDirectories = systemDirectories;
	}
	
	
	
	
}
