package view;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

public class TargetExtensionController extends HBox {
	
	private FileChooser fileChooser;
	
	@FXML TextField txtTargetDirectory;
	@FXML TextField txtExtension;
	@FXML TextField txtProgramPath;
	@FXML Button btnSelectProgram;
	
	public TargetExtensionController(String targetDirectory) {
		
		
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("TargetExtension.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		} catch (IOException exc) {
			exc.printStackTrace();
		}
		txtTargetDirectory.setText(targetDirectory);
		
		btnSelectProgram.setOnAction(event ->{
			File program = fileChooser.showOpenDialog(null);
			txtProgramPath.setText(program.getAbsolutePath());
		});
		
		
	}
	
	
	
	
}
