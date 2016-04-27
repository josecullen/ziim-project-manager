package model;

import java.util.ArrayList;
import java.util.List;

public class TargetDirectory extends FileBase{
	private List<FileBase> files = new ArrayList<>();

	public List<FileBase> getFiles() {
		return files;
	}

	public void setFiles(List<FileBase> files) {
		this.files = files;
	}
	
	
	
}
