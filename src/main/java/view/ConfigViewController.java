package view;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

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
import model.Configs;
import model.config.*;

public class ConfigViewController extends BorderPane {
	
	@FXML TextField txtWorkspacePath;
	@FXML Button btnSelectWorkspace;
	@FXML Button btnAddTarget;
	@FXML Button btnSave;
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
		
		setFromConfig();
		
		btnSelectWorkspace.setOnAction(event ->{
			File chooseFile = directoryChooser.showDialog(null);
			txtWorkspacePath.setText(chooseFile.getAbsolutePath());
		});
		
		btnAddTarget.setOnAction(event ->{
			TargetExtensionController targetExtension = new TargetExtensionController();
			boxExtensions.getChildren().add(targetExtension);
			targetExtension.txtTargetDirectory.requestFocus();
		});
		
		btnSave.setOnAction(event ->{
			Config config = new Config();
			ConfigWorkspace workspace = new ConfigWorkspace();
			
			workspace.setPath(txtWorkspacePath.getText());
			config.setWorkspace(workspace);
			
			boxExtensions.getChildren().filtered(predicate ->{
				return predicate instanceof TargetExtensionController;
			}).forEach(targetExtension ->{
				
				TargetExtensionController extensionView = ((TargetExtensionController)targetExtension);
				
				ConfigTargetDirectory targetDir = new ConfigTargetDirectory();
				targetDir.setExtension(extensionView.txtExtension.getText());
				targetDir.setName(extensionView.txtTargetDirectory.getText());
				
				ConfigExtension extension = new ConfigExtension();
				extension.setName(extensionView.txtExtension.getText());
				extension.setProgramPath(extensionView.txtProgramPath.getText());
				
				config.getExtensions().add(extension);
				workspace.getTargetDirectories().add(targetDir);
			});
			
			Configs.save(config);
			
		});
		
	}

	private void setFromConfig() {
		Config config = Configs.getConfig();
		txtWorkspacePath.setText(config.getWorkspace().getPath());
		
		config.getWorkspace().getTargetDirectories().forEach(targetDirectory ->{
			TargetExtensionController targetExtension = new TargetExtensionController();
			targetExtension.txtExtension.setText(targetDirectory.getExtension());
			targetExtension.txtTargetDirectory.setText(targetDirectory.getName());
			targetExtension.txtProgramPath.setText(getProgramPathByExtensionName(targetDirectory.getExtension()));
			boxExtensions.getChildren().add(targetExtension);
		});
		
	}
	
	private String getProgramPathByExtensionName(String extensionName){
		Optional<String> option = Configs.getConfig().getExtensions().stream()
			.filter(
				predicate -> predicate.getName().equals(extensionName)
		)	.map(
				mapper -> mapper.getProgramPath())
			.findFirst();
		
		return option.orElse("");
	}
	
	
	
	

}
