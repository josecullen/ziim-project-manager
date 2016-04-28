package view;

import java.io.File;
import java.io.IOException;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

public class ConfigViewController extends BorderPane {
	
	@FXML TextField txtWorkspacePath;
	@FXML Button btnSelectWorkspace;
	@FXML Button btnAddTarget;
	@FXML HBox targetDirectoryBox;
	
	@FXML VBox boxExtensions;
	
	DirectoryChooser directoryChooser = new DirectoryChooser();
	ListProperty<String> targetDirectories = new  SimpleListProperty<>(FXCollections.observableArrayList());
	
	public ConfigViewController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ConfigView.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		} catch (IOException exc) {
			exc.printStackTrace();
		}
		
		btnSelectWorkspace.setOnAction(event ->{
			File chooseFile = directoryChooser.showDialog(null);
			txtWorkspacePath.setText(chooseFile.getAbsolutePath());
		});
		
		btnAddTarget.setOnAction(event ->{
			TargetExtensionController targetExtension = new TargetExtensionController();
			boxExtensions.getChildren().add(targetExtension);
			targetExtension.txtTargetDirectory.requestFocus();
		});
		
	}
	
	
	

}
