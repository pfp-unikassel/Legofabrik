package userInterface;

import java.net.URL;
import java.util.ResourceBundle;

import controller.Steuerung;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class MotorSettingsController implements Initializable {

	public Button applyButton, closeButton, defaulButton;

	public TextField zahlbandv, sensorbandv, lieferbandv, fahrstuhlhorizont, fahrstuhlvertikal, drehgeschwindigkeit,
			hebebandv, filldrehgescwindigkeit, anzahldrehungen, bandzumarmv, auslieferbandv, hebegeschwindigkeit,
			shaker;

	public ToggleButton lager1, lager2, lager3, lager4, lager11, lager12, lager13, lager14,zwillingOff,zwillingOn;

	public ToggleGroup g1, g2, g3, g4,g5;

	public ComboBox<String> farbe, drehtischposition;

	private Steuerung s;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		farbe.getItems().addAll("Weiß", "Schwarz", "Rot", "Grün", "Blau", "Braun");
		drehtischposition.getItems().addAll("Anlieferung", "Lager", "Lift");

		Stage stage = (Stage) closeButton.getScene().getWindow();
		s = (Steuerung) stage.getUserData();

		updateFromLiveModel();

	}
	
	public void setOffline() {
		s.setOffline();
		zwillingOff.setSelected(true);
	}
	
	public void setOnline() {
		s.setOnline();
		zwillingOn.setSelected(true);
	}

	public void updateFromLiveModel() { // gets every value from Steuerung
		updateStockToggleButton();
		fetchIoColor();
		fetchTablePosition();
		fetchMotorSettings();
	}

	public void saveFromUiToLiveModel() { // sets everything from Ui in steuerung

		setIoColor();
		setStockFromButtons();
		setTablePosition();
		setMotorSettings();
	}

	public void applyButtonPushed() {

		saveFromUiToLiveModel();
	}

	public void closeButtonPushed() {

		Stage stage = (Stage) closeButton.getScene().getWindow();
	    stage.close();
	}

	public void defaultButtonPushed() {
		
	}

	// ------------------From Code to
	// Ui-------------------------------------------------------------------
	public void updateStockToggleButton() { // gets Stock values and updates it in UI

		if (s.getStock().isStock1()) { // wenn in Lager1 was steht dann toggle voll button sonst leer
			lager1.setSelected(true);
		} else {
			lager11.setSelected(true);
		}

		if (s.getStock().isStock2()) { // wenn in Lager2 was steht dann toggle voll button
			lager2.setSelected(true);
		} else {
			lager12.setSelected(true);
		}

		if (s.getStock().isStock3()) { // wenn in Lager3 was steht dann toggle voll button
			lager3.setSelected(true);
		} else {
			lager13.setSelected(true);
		}

		if (s.getStock().isStock4()) { // wenn in Lager4 was steht dann toggle voll button
			lager4.setSelected(true);
		} else {
			lager14.setSelected(true);
		}
	}

	public void fetchIoColor() { // gets IO color from steuerung and updates Ui

		if (s.getQuality().getIoColor().equals("WHITE")) {
			farbe.setValue("Weiß");
		}
		if (s.getQuality().getIoColor().equals("BLACK")) {
			farbe.setValue("Schwarz");
		}
		if (s.getQuality().getIoColor().equals("RED")) {
			farbe.setValue("Rot");
		}
		if (s.getQuality().getIoColor().equals("GREEN")) {
			farbe.setValue("Grün");
		}
		if (s.getQuality().getIoColor().equals("Brown")) {
			farbe.setValue("Braun");
		}
		if (s.getQuality().getIoColor().equals("BLUE")) {
			farbe.setValue("Blau");
		}
	}

	public void fetchTablePosition() { // gets table position from steuerung and updates Ui

		if (s.getChargier().getTablePostion() == 660) {
			drehtischposition.setValue("Lift");
		} else if (s.getChargier().getTablePostion() == -660) {
			drehtischposition.setValue("Lager");
		} else if (s.getChargier().getTablePostion() == 0) {
			drehtischposition.setValue("Anlieferung");
		} else {
			drehtischposition.getItems().add(String.valueOf(s.getChargier().getTablePostion())); // Falls kein
																									// Standartwinkel
																									// anliegt zeige
																									// diesen statt den
																									// Namen an
			drehtischposition.setValue(String.valueOf(s.getChargier().getTablePostion()));
		}
	}

	public void fetchMotorSettings() { // gets every used static programmed motor value and updates UI

		// quality
		zahlbandv.setText(String.valueOf(s.getQuality().getCounterLineSpeed()));
		sensorbandv.setText(String.valueOf(s.getQuality().getLineSpeed()));
		// chargier
		lieferbandv.setText(String.valueOf(s.getChargier().getLineSpeed()));
		// stock
		fahrstuhlhorizont.setText(String.valueOf(s.getStock().getElevatorHorizontalSpeed()));
		fahrstuhlvertikal.setText(String.valueOf(s.getStock().getElevatorVerticalSpeed()));
		// cleaner
		drehgeschwindigkeit.setText(String.valueOf(s.getCleaner().getCleanerSpeed()));
		hebebandv.setText(String.valueOf(s.getCleaner().getLiftLaneSpeed()));
		// fillstation
		filldrehgescwindigkeit.setText(String.valueOf(s.getFillStation().getWheelspeed()));
		anzahldrehungen.setText(String.valueOf(s.getFillStation().getNumberOfTurns()));
		// deliverystation
		bandzumarmv.setText(String.valueOf(s.getDelivery().getLineToArmsSpeed()));
		auslieferbandv.setText(String.valueOf(s.getDelivery().getLineToEndSpeed()));
		// lift
		hebegeschwindigkeit.setText(String.valueOf(s.getLift().getliftSpeed()));
		shaker.setText(String.valueOf(s.getLift().getShakerSpeed()));

	}

	// -----------------------------From-ui-to--Code-----------------------------------------

	public void setMotorSettings() {
		
		s.getQuality().setCounterLineSpeed(Integer.parseInt(zahlbandv.getText()));
		s.getQuality().setLineSpeed(Integer.parseInt(sensorbandv.getText()));
		
		s.getChargier().setLineSpeed((Integer.parseInt(zahlbandv.getText())));
		
		s.getStock().setElevatorHorizontalSpeed((Integer.parseInt(fahrstuhlhorizont.getText())));
		s.getStock().setElevatorVerticalSpeed((Integer.parseInt(fahrstuhlvertikal.getText())));
		
		s.getCleaner().setCleanerSpeed(Integer.parseInt(drehgeschwindigkeit.getText()));
		s.getCleaner().setLiftLaneSpeed(Integer.parseInt(anzahldrehungen.getText()));
		
		s.getFillStation().setWheelspeed(Integer.parseInt(bandzumarmv.getText()));
		s.getFillStation().setNumberOfTurns(Integer.parseInt(auslieferbandv.getText()));
		
		s.getLift().setliftSpeedt(Integer.parseInt(hebegeschwindigkeit.getText()));
		s.getLift().setShakerSpeed(Integer.parseInt(shaker.getText()));
		
	}
	public void setTablePosition() { 

		if (drehtischposition.getValue().equals("Lift")) {

			s.getChargier().setTablePosition(660);
		}

		if (drehtischposition.getValue().equals("Lager")) {

			s.getChargier().setTablePosition(-660);
		}
		if (drehtischposition.getValue().equals("Anlieferung")) {

			s.getChargier().setTablePosition(0);
		}
		
	}

	public void setStockFromButtons() { // gets Stock values and updates it in UI

		if (lager1.isSelected()) { // wenn voll button is pushed dann ist lager1 voll
			s.getStock().setStock1(true);
		} else {
			s.getStock().setStock1(false);
		}

		if (lager2.isSelected()) {
			s.getStock().setStock2(true);
		} else {
			s.getStock().setStock2(false);
		}

		if (lager3.isSelected()) {
			s.getStock().setStock3(true);
		} else {
			s.getStock().setStock3(false);
		}

		if (lager4.isSelected()) {
			s.getStock().setStock4(true);
		} else {
			s.getStock().setStock4(false);
		}
	}

	public void setIoColor() { // gets IO color from ui and updates steuerung

		if (farbe.getValue().equals("Weiß")) {
			s.getQuality().setIoColor("WHITE");
		}
		if (farbe.getValue().equals("Schwarz")) {
			s.getQuality().setIoColor("BLACK");
		}
		if (farbe.getValue().equals("Rot")) {
			s.getQuality().setIoColor("RED");
		}
		if (farbe.getValue().equals("Grün")) {
			s.getQuality().setIoColor("GREEN");
		}
		if (farbe.getValue().equals("Braun")) {
			s.getQuality().setIoColor("BROWN");
		}
		if (farbe.getValue().equals("Blau")) {
			s.getQuality().setIoColor("BLUE");
		}
	}

}
