package userInterface;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import controller.Steuerung;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class IpconfigController implements Initializable {

	public Button applyButton, closeButton, defaulButton;
	public TextField Bricktextfield1, Bricktextfield2, Bricktextfield3, Bricktextfield4, Bricktextfield5,
			Bricktextfield6, Bricktextfield7, Bricktextfield8, Bricktextfield9, Bricktextfield10, Bricktextfield11,
			Bricktextfield12, Bricktextfield13, Bricktextfield14;
	private Steuerung s;
	private ArrayList<String> brickIps;
	private ArrayList<TextField> textFields = new ArrayList<>();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

	public void start() {
		Stage stage = (Stage) closeButton.getScene().getWindow();

		s = (Steuerung) stage.getUserData();
		brickIps = s.getBrickIpsFromConfig();
		updateTextfields();
	}

	public void updateTextfields() {

		for (int i = 0; i < textFields.size(); i++) {

			if(brickIps.get(i)!=null){
			textFields.get(i).setText(brickIps.get(i)); // hol text aus dem
														// Array und zeig ihn im
														// Fenster
			}
		}

		// TODO: show BrickIp in Textfields
	}

	public void applyButtonPushed() {

		for (int i = 0; i < textFields.size(); i++) {

			if(textFields.get(i).equals(null)){ // textfield is empty
				
			}else{
			
			brickIps.set(i, textFields.get(i).getText()); // hol aus den
															// Textfenstern den
															// Text und speicher
															// ihn im array
			}

		}
		s.changeBrickIps(brickIps);
		
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}

	public void closeButtonPushed() {

		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}

	public void defaultButtonPushed() {

		for (int i = 0; i < textFields.size(); i++) {

			if(s.getDefaultIps().get(i).equals(null)){
				
			}else{
				textFields.get(i).setText(s.getDefaultIps().get(i)); // hol text aus
				// dem Array
				// und zeig
				// ihn im
				// Fenster
			}
			
		}
	}

	public void addTextFieldsToList() {

		textFields.add(Bricktextfield1);
		textFields.add(Bricktextfield2);
		textFields.add(Bricktextfield3);
		textFields.add(Bricktextfield4);
		textFields.add(Bricktextfield5);
		textFields.add(Bricktextfield6);
		textFields.add(Bricktextfield7);
		textFields.add(Bricktextfield8);
		textFields.add(Bricktextfield9);
		textFields.add(Bricktextfield10);
		textFields.add(Bricktextfield11);
		textFields.add(Bricktextfield12);
		textFields.add(Bricktextfield13);
		textFields.add(Bricktextfield14);
	}

}
