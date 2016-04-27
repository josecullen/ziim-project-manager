package model;

import java.util.ArrayList;
import java.util.List;

public class ProjectRoot extends FileBase{
	
	private List<ProjectStructure> projectStructures = new ArrayList<>();

	public List<ProjectStructure> getProjectStructures() {
		return projectStructures;
	}

	public void setProjectStructures(List<ProjectStructure> projectStructures) {
		this.projectStructures = projectStructures;
	}
	
	
}
