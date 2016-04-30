package webapp.vim_manager_app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.impl.StaticHandlerImpl;
import io.vertx.ext.web.impl.RouterImpl;
import model.Configs;
import model.WorkspaceRoot;
import model.config.Config;
import model.config.ConfigTargetDirectory;

/**
 * Hello world!
 *
 */
public class App {

	static Logger logger = Logger.getGlobal();
	private static final int PORT = 8010;
	private static HttpServer server;
	private static Vertx vertx;

	enum PATH {
		run, project_from_string, project_directory, new_project
	}

	public static void main( String[] args ) throws IOException {
		
		FileHandler fh;   
		fh = new FileHandler("./className.log");   
		logger.addHandler(fh); 		
        SimpleFormatter formatter = new SimpleFormatter();  
        fh.setFormatter(formatter);  
        
		logger.info("Inicio");
		
    	vertx = Vertx.factory.vertx();
    	
		server = vertx.createHttpServer();
		
		Router router = new RouterImpl(vertx);
		
		System.out.println(Configs.getConfig());
		
		router.route("/").handler(handler -> {
			handler.response().sendFile("webroot/index.html");
		});

		
		router.route("/"+PATH.run).handler(context ->{
			logger.info(PATH.run.name());
			String path = context.request().params().get("path");
			
			try {
				File file =	 new File(path);
				String[] splitPoint = file.getName().split("\\.");
				String ext = splitPoint[splitPoint.length-1];
				Optional<String> programPath = Configs.getConfig().getExtensions()
					.stream()
					.filter(extension -> extension.getName().equals(ext))
					.map(extension -> extension.getProgramPath())
					.findFirst();
				String command = "\""+programPath.get() +"\""+" \""+path+"\"";

				logger.info(command);
				Runtime rt = Runtime.getRuntime();
				Process pr = rt.exec(command);			
				
//				Process process = new ProcessBuilder(path).start();
			} catch (Exception e) {
				logger.warning(e.getMessage());
			}
			context.response().end();
		});
		
		router.route("/"+PATH.project_from_string).handler(context ->{
			logger.info(PATH.run.name());
			
			String path = context.request().params().get("path");
			String target = context.request().params().get("target");
			
			JsonObject files = null;
			try {
//				files = FileFilter.getListFiles(path, target);

			} catch (Exception e) {
				e.printStackTrace();
			}			
			context.response().end(files.encode());
		});
		
		router.route("/"+PATH.project_directory).handler(context ->{
			String workspaceDirectory = Configs.getConfig().getWorkspace().getPath();//configs.get("project-directory");
			List<ConfigTargetDirectory> targets = Configs.getConfig().getWorkspace().getTargetDirectories();
			
			JsonObject files = new JsonObject();
			if(workspaceDirectory != null && !targets.isEmpty()){
				try {
					String result = new ObjectMapper().writeValueAsString(new WorkspaceRoot(Configs.getConfig()));
					files = new JsonObject(result);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			System.out.println(files.encodePrettily());
			context.response().end(files.encode());
		
		});
		
		router.route("/"+PATH.new_project).handler(context ->{		
			
			NewProjectConfig newProjectConfig = new NewProjectConfig(context.request().params());

			File projectRoot = 
					new File(
							Configs.getConfig().getWorkspace().getPath()+
							File.separator+
							newProjectConfig.getProjectName());
			
			if(projectRoot.exists()){
				logger.warning("El projecto ya existe");
			}else{
				projectRoot.mkdir();
				Configs.getConfig()
					.getWorkspace()
					.getTargetDirectories()
					.forEach(targetDirectory ->{
						File targetFolder = new File(
								projectRoot.getAbsolutePath()+
								File.separator+
								targetDirectory.getName());
						
						targetFolder.mkdir();
						
						Configs.getSystemFolderNames().forEach(systemFolderName ->{
							File systemFolder = new File(
									targetFolder.getAbsolutePath()+
									File.separator+
									systemFolderName);
							
							systemFolder.mkdir();
							
							File template = new File(
									Configs.getConfig()
									.getWorkspace().getPath()
									+File.separator+
									"template."+targetDirectory.getExtension());
							System.out.println(template.getAbsolutePath());
							if(template.exists()){
								for(int subs = 0; subs < newProjectConfig.getSubLevels(); subs++){
									File levelFile = new File(systemFolder+File.separator+
											"S0"+(subs+1)+"-"+systemFolderName.substring(3)+"."+targetDirectory.getExtension());
									try {
										Files.copy(template.toPath(), levelFile.toPath());
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								for(int level = 0; level < newProjectConfig.getSubLevels()+1; level++){
									File levelFile = null;
									if(level < 10){
										levelFile = new File(systemFolder+
												File.separator+
												"0"+level+"-"+systemFolderName.substring(3)+"."+targetDirectory.getExtension());
									}else{
										levelFile = new File(""+systemFolder+level+"-"+systemFolderName.substring(3)+"."+targetDirectory.getExtension());
									}
									try {
										Files.copy(template.toPath(), levelFile.toPath());
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								
								
							}
							
						});
					});
			}
			
			context.response().end("OK");
		});
		
		StaticHandlerImpl staticHandler = new StaticHandlerImpl();
		staticHandler.setCachingEnabled(false);
		staticHandler.setMaxAgeSeconds(StaticHandlerImpl.DEFAULT_MAX_AGE_SECONDS);

		
		router.route("/*").handler(staticHandler);
		server.requestHandler(router::accept).listen(PORT);
		logger.info("Server open in port " + PORT);
		
    }

	public static void close() {
		vertx.close();
		server.close();
	}

	private static String getExtension(String path) {
		String result = "";
		String[] split = path.split("\\.");
		System.out.println(path);

		if (split != null) {
			result = split[split.length - 1];
		}
		System.out.println(result);
		return result;
	}

}

class NewProjectConfig {
	private String projectName;
	private int levels;
	private int subLevels;

	public NewProjectConfig(MultiMap params) {
		projectName = params.get("projectName");
		levels = Integer.parseInt(params.get("levels"));
		subLevels = Integer.parseInt(params.get("subLevels"));
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public int getLevels() {
		return levels;
	}

	public void setLevels(int levels) {
		this.levels = levels;
	}

	public int getSubLevels() {
		return subLevels;
	}

	public void setSubLevels(int subLevels) {
		this.subLevels = subLevels;
	}
}
