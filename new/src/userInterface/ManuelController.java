package userInterface;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import controller.Steuerung;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/* 
 * Controller for manual.fxml loaded in Controller.java
 * every Station has a vor and Back Button counted from bottom up 
 * 
 */

public class ManuelController implements Initializable {

	private Steuerung s;
	public Button closeButton;

	public Button chargierVor1,chargierVor12,chargierVor13,chargierVor14,chargierVor15,
	chargierBack1, chargierBack12, chargierBack13, chargierBack14, chargierBack15,
	elevatorVor1,elevatorVor12,elevatorVor13,elevatorVor14,elevatorVor15
	,elevatorBack1,elevatorBack12,elevatorBack13,elevatorBack14,elevatorBack15
	,stockVor1,stockVor12,stockVor13,stockVor14,stockBack1,stockBack12,stockBack13,stockBack14
	,liftVor1,liftVor11,liftVor12,liftVor13,liftVor14,liftBack1,liftBack11,liftBack12,liftBack13,liftBack14
	,cleanerVor1,cleanerVor11,cleanerBack1,cleanerBack11
	,qualityVor1,qualityVor11,qualityVor12,qualityBack1,qualityBack11,qualityBack12
	,fillVor1,fillBack1
	,deliveryVor1,deliveryVor11,deliveryVor12,deliveryVor13,deliveryVor14
	,deliveryBack1,deliveryBack11,deliveryBack12,deliveryBack13,deliveryBack14;

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
