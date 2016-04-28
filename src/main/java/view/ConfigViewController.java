package view;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;

public class ConfigViewController extends BorderPane {
	
	@FXML TextField txtWorkspacePath;
	@FXML TextField txtTargetDirectories;
	@FXML Button btnSelectWorkspace;
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
			//this.getChildren().add(hbox);
		} catch (IOException exc) {
			exc.printStackTrace();
		}
		
		btnSelectWorkspace.setOnAction(event ->{
			File chooseFile = directoryChooser.showDialog(null);
			txtWorkspacePath.setText(chooseFile.getAbsolutePath());
		});
		
		txtTargetDirectories.setOnKeyReleased(keyEvent ->{
			System.out.println(keyEvent.getCode());
			
			if(keyEvent.getCode() == KeyCode.SPACE){
				List<String> td =  Arrays.asList(txtTargetDirectories.getText().split(" "));
				targetDirectories.removeIf(
						filter -> !td.stream().anyMatch(predicate -> predicate.equals(filter))
				);
				td.forEach(target ->{
					if(!targetDirectories.stream().anyMatch(predicate -> target.equals(predicate)) && target.length() > 2){
						targetDirectories.get().add(target);
					}
				});
				targetDirectoryBox.getChildren().clear();
				targetDirectories.forEach(action -> {
					TextField lbl = new TextField(action+" ");
					lbl.setAlignment(Pos.CENTER);
					lbl.setEditable(false);
					lbl.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, new CornerRadii(5.0), new Insets(2.0))));
					targetDirectoryBox.getChildren().add(lbl);
				});
				
				
			}
		});
		
		targetDirectoryBox.getChildren().addListener(new ListChangeListener<Node>() {

			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Node> c) {				
//				boxExtensions.getChildren().remove(1, boxExtensions.getChildren().size()-1);
				targetDirectories.forEach(targetDirectory ->{
					boxExtensions.getChildren().add(new TargetExtensionController(targetDirectory));
				});
			}
			
		});
		
	}
	
	
	

}
