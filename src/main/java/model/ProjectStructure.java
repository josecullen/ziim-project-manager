package model;

import java.util.ArrayList;
import java.util.List;

public class ProjectStructure extends FileBase{
	private String target;	
	private List<TargetDirectory> targetDirectories = new ArrayList<>();
	
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
