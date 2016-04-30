package webapp.vim_manager_app;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import view.ConfigViewController;

public class Main extends Application {
	WebView webView = new WebView();
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		App.main(new String[]{});
		
		StackPane stackPane; 
		BorderPane borderPane = new BorderPane();
		
		Button btn = new Button("Firebug");
		btn.setOnAction(action ->{
			webView.getEngine().executeScript(
					"if (!document.getElementById('FirebugLite')){E = document['createElement' + 'NS'] && document.documentElement.namespaceURI;E = E ? document['createElement' + 'NS'](E, 'script') : document['createElement']('script');E['setAttribute']('id', 'FirebugLite');E['setAttribute']('src', 'https://getfirebug.com/' + 'firebug-lite.js' + '#startOpened');E['setAttribute']('FirebugLite', '4');(document['getElementsByTagName']('head')[0] || document['getElementsByTagName']('body')[0]).appendChild(E);E = new Image;E['setAttribute']('src', 'https://getfirebug.com/' + '#startOpened');}");
		});
		ConfigViewController configView = new ConfigViewController();
		
		ToggleButton btnToggle = new ToggleButton("Config");
		stackPane = new StackPane(webView, configView);
		
		btnToggle.setSelected(true);
		configView.visibleProperty().bind(btnToggle.selectedProperty());
		
		WebEngine webEngine = webView.getEngine();
		webEngine.load("http://127.0.0.1:8010");
		
		
		borderPane.setCenter(stackPane);
		borderPane.setTop(new HBox(btn,btnToggle));
		Scene scene = new Scene(borderPane);

		primaryStage.setTitle("ZiiM Projects Manager");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setOnCloseRequest(value ->{
			try {
				App.close();
				Platform.exit();				
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
}
