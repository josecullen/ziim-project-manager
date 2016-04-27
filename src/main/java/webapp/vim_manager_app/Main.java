package webapp.vim_manager_app;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Main extends Application {
	WebView webView = new WebView();
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		App.main(new String[]{});
		BorderPane borderPane = new BorderPane();
		Button btn = new Button("Firebug");
		btn.setOnAction(action ->{
			webView.getEngine().executeScript(
					"if (!document.getElementById('FirebugLite')){E = document['createElement' + 'NS'] && document.documentElement.namespaceURI;E = E ? document['createElement' + 'NS'](E, 'script') : document['createElement']('script');E['setAttribute']('id', 'FirebugLite');E['setAttribute']('src', 'https://getfirebug.com/' + 'firebug-lite.js' + '#startOpened');E['setAttribute']('FirebugLite', '4');(document['getElementsByTagName']('head')[0] || document['getElementsByTagName']('body')[0]).appendChild(E);E = new Image;E['setAttribute']('src', 'https://getfirebug.com/' + '#startOpened');}");
		});

		
		
		WebEngine webEngine = webView.getEngine();
		webEngine.load("http://127.0.0.1:8010");
		
		
		borderPane.setCenter(webView);
		borderPane.setTop(btn);
		Scene scene = new Scene(borderPane);
        

		primaryStage.setTitle("ZiiM Projects Manager");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
