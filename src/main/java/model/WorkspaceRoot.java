package model;


import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.config.Config;

public class WorkspaceRoot extends FileBase{
	private List<ProjectRoot> projectRoots = new ArrayList<>();

	public WorkspaceRoot() {}
	public WorkspaceRoot(String name, String path) {
		super(name, path);		
	}
	
	public WorkspaceRoot(Config config){
		super(config.getWorkspace().getPath());
		
		Arrays.asList(getTargetFiles(config))			
			.forEach(projectRootFile -> {
				System.out.println("workspace "+projectRootFile.getName());
				this.projectRoots.add(new ProjectRoot(projectRootFile));
			});
		
	}
	
	private File[] getTargetFiles(Config config) {
		return file.listFiles((dir) -> dir.isDirectory());		
	}
	private boolean hasTargetDir(Config config, File dir) {
		return Arrays.asList(dir.listFiles()).stream().anyMatch(targetFile ->
			config.getWorkspace().getTargetDirectories().stream().anyMatch(
					targetConfig -> targetConfig.getName().equals(targetFile.getName()))
		);
	}
	public List<ProjectRoot> getProjectRoots() {
		return projectRoots;
	}

	public void setProjectRoots(List<ProjectRoot> projectRoots) {
		this.projectRoots = projectRoots;
	}
	
	
}
