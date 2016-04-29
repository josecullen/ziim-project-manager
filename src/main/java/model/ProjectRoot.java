package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProjectRoot extends FileBase{
	private String target;	
	private List<TargetDirectory> targetDirectories = new ArrayList<>();
	
	public ProjectRoot() {	}
	
	public ProjectRoot(String name, String path) {
		super(name, path);
	}
	
	public ProjectRoot(String name, String path, String target) {
		super(name, path);
		setTarget(target);
	}
	
	
	
	public ProjectRoot(File projectRootFile){
		super(projectRootFile.getAbsolutePath());
	
		filterByTarget(file).forEach(targetFile ->{
			System.out.println("  project "+targetFile.getName());
			this.targetDirectories.add(new TargetDirectory(targetFile));
		});
	}
	
	private List<File> filterByTarget(File file){
		return Arrays.asList(file.listFiles((dir, name) ->	dir.isDirectory()));
			
//		Configs.getConfig().getWorkspace().getTargetDirectories().stream().anyMatch(targetDirConfig -> 
//		targetDirConfig.equals(name))));
	}
	
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public List<TargetDirectory> getTargetDirectories() {
		return targetDirectories;
	}
	public void setTargetDirectories(List<TargetDirectory> targetDirectories) {
		this.targetDirectories = targetDirectories;
	}
	
	
	
}
