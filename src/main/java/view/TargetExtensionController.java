package view;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class TargetExtensionController extends HBox {
	
	private FileChooser fileChooser = new FileChooser();
	
	@FXML TextField txtTargetDirectory;
	@FXML TextField txtExtension;
	@FXML TextField txtProgramPath;
	@FXML Button btnSelectProgram;
	@FXML Button btnRemove;
	
	public TargetExtensionController() {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("TargetExtension.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		} catch (IOException exc) {
			exc.printStackTrace();
		}
		
		btnSelectProgram.setOnAction(event ->{
			File program = fileChooser.showOpenDialog(null);
			if(program != null)
				txtProgramPath.setText(program.getAbsolutePath());
		});
		
		btnRemove.setOnAction(event ->{
			((VBox)this.getParent()).getChildren().remove(this);
		});
		
	}
	
	
	
	
}
