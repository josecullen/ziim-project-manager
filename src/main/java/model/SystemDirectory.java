package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SystemDirectory extends FileBase {
	private List<FileBase> files = new ArrayList<>();

	public SystemDirectory() {
		// TODO Auto-generated constructor stub
	}

	public SystemDirectory(File file) {
		super(file.getAbsolutePath());
		filterByExtension(this.file).forEach(programFile ->{
			System.out.println("      systemDir "+programFile.getName());
			if(!programFile.isDirectory()){
				this.files.add(new FileBase(programFile.getAbsolutePath()));
			}				
		});
	}
	
	private List<File> filterByExtension(File file) {

		return Arrays.asList(file.listFiles());
		
//		Configs.getConfig().getExtensions().stream().anyMatch(extension -> 
//		name.endsWith(extension.getName()))
//	)
	}

	public List<FileBase> getFiles() {
		return files;
	}

	public void setFiles(List<FileBase> files) {
		this.files = files;
	}

}
