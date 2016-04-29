package model;

import java.io.File;

public class FileBase {
	protected File file;
	private String name;
	private String path;
	
	public FileBase() {}
	public FileBase(String name, String path){
		this.file = new File(path);
		this.name = name;
		this.path = path;
	}
	public FileBase(String path){
		this.file = new File(path);
		this.name = this.file.getName();
		this.path = path;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
