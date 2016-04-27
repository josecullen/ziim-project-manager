package webapp.vim_manager_app;

import java.io.IOException;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.impl.StaticHandlerImpl;
import io.vertx.ext.web.impl.RouterImpl;
import model.Configs;

/**
 * Hello world!
 *
 */
public class App {
	
	static Logger logger = Logger.getGlobal();
    private static final int PORT = 8010;
    private static Map<String, String> configs = Configs.configs();
    
    
    enum PATH{
    	run, project_from_string
    }
	public static void main( String[] args ) throws IOException {
		
		FileHandler fh;   
		fh = new FileHandler("./className.log");   
		logger.addHandler(fh); 		
        SimpleFormatter formatter = new SimpleFormatter();  
        fh.setFormatter(formatter);  

		logger.info("Inicio");
		
    	Vertx vertx = Vertx.factory.vertx();
    	
		HttpServer server = vertx.createHttpServer();
		
		Router router = new RouterImpl(vertx);
		
		router.route("/").handler(handler -> {
			handler.response().sendFile("webroot/index.html");
		});

		router.route("/"+PATH.run).handler(context ->{
			logger.info(PATH.run.name());
			String path = context.request().params().get("path");
			
			try {
				System.out.println(configs.get("txt"));
				String command = "\""+configs.get(getExtension(path))+"\" "+ "\""+path+"\"";
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
				files = FileFilter.getListFiles(path, target);

			} catch (Exception e) {
				e.printStackTrace();
			}			
			context.response().end(files.encode());
		});
		
		
		StaticHandlerImpl staticHandler = new StaticHandlerImpl();
		staticHandler.setCachingEnabled(false);
		staticHandler.setMaxAgeSeconds(StaticHandlerImpl.DEFAULT_MAX_AGE_SECONDS);

		
		router.route("/*").handler(staticHandler);
		server.requestHandler(router::accept).listen(PORT);
		logger.info("Server open in port " + PORT);
		logger.info("No arranca esto...");
    }
	
	private static String getExtension(String path) {
		String result = "";
		String[] split = path.split("\\.");
		System.out.println(path);
		
		if(split != null){
			result = split[split.length-1];
		}
		System.out.println(result);
		return result;
	}
}
