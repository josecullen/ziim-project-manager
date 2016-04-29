package model.config;

import java.util.ArrayList;
import java.util.List;

public class Config {
	private ConfigWorkspace workspace = new ConfigWorkspace();
	private List<ConfigExtension> extensions;
	
	public ConfigWorkspace getWorkspace() {
		return workspace;
	}
	public void setWorkspace(ConfigWorkspace workspace) {
		this.workspace = workspace;
	}
	public List<ConfigExtension> getExtensions() {
		if(extensions == null) extensions = new ArrayList<>();
		return extensions;
	}
	public void setExtensions(List<ConfigExtension> extensions) {
		this.extensions = extensions;
	}
	
	
}
