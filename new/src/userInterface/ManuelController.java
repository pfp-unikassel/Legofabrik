package userInterface;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import controller.Steuerung;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ManuelController implements Initializable {

	private Steuerung s;
	public Button closeButton;

	public Button chargierVor1, chargierBack1;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	public void start() {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		s = (Steuerung) stage.getUserData();
	}

	public void updateTextfields() {

	}

	public void closeButtonPushed() {

		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();

	}

	public void chargierVorButtonPressed1() {
		
		try {
			s.getChargier().startLineToTable(true);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void chargierVorButtonReleased1() {
		
		try {
			s.getChargier().stopLineToTable();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void chargierBackButtonPressed1() {
		System.out.println("Rücktaste gedrückt");

	}

	public void chargierBackButtonReleased1() {
		System.out.println("Rücktaste losgelassen");
	}

}
