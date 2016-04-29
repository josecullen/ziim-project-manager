package webapp.vim_manager_app;

import java.io.File;

import com.fasterxml.jackson.core.JsonProcessingException;

import model.Configs;
import model.WorkspaceRoot;
import model.config.Config;
import model.config.ConfigWorkspace;

public class FileFilter {
	
	private static Config config = Configs.getConfig();
	
	public static WorkspaceRoot getListFiles() throws JsonProcessingException{
		ConfigWorkspace configWs = config.getWorkspace();
		File wsRoot = new File(configWs.getPath());
		
		WorkspaceRoot ws = new WorkspaceRoot(wsRoot.getName(), wsRoot.getAbsolutePath());
		
		
		
		return ws;
	}
}
