package webapp.vim_manager_app;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.vertx.core.json.JsonObject;
import model.FileBase;
import model.ProjectRoot;
import model.ProjectStructure;
import model.TargetDirectory;

public class FileFilter {
	
	
	public static JsonObject getListFiles(String path, String target) throws JsonProcessingException{

		
		File file = new File(path);
		
		List<File> rootFiles = Arrays.asList(file.listFiles());
		ProjectRoot projectRoot = new ProjectRoot();
		projectRoot.setName(file.getName());
		projectRoot.setPath(file.getAbsolutePath());
		ObjectMapper mapper = new ObjectMapper();
		
		rootFiles.forEach(rootfile ->{			
			Arrays.asList(rootfile.listFiles()).forEach(subfile ->{
				if(subfile.isDirectory() && subfile.getName().equals(target)){
					ProjectStructure projectStructure = new ProjectStructure();
					projectStructure.setName(rootfile.getName());
					projectStructure.setPath(rootfile.getAbsolutePath());
					projectStructure.setTarget(target);
					
					Arrays.asList(subfile.listFiles()).forEach(targetDir ->{
						TargetDirectory targetDirectory = new TargetDirectory();
					
						targetDirectory.setName(targetDir.getName());
						targetDirectory.setPath(targetDir.getAbsolutePath());
						Arrays.asList(targetDir.listFiles()).forEach(targetFiles ->{
							targetDirectory.getFiles()
								.add(new FileBase(targetFiles.getName(), targetFiles.getAbsolutePath()));
						});						
						projectStructure.getTargetDirectories().add(targetDirectory);
					});						
					projectRoot.getProjectStructures().add(projectStructure);
					
				}
			});
		});
		
		JsonObject projectRootJson = new JsonObject(mapper.writeValueAsString(projectRoot));
		System.out.println(projectRootJson.encodePrettily());
		
		return projectRootJson;
		
	}
}
