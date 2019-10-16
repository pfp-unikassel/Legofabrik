package userInterface;

import java.net.URL;
import java.util.ResourceBundle;

import controller.Steuerung;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MotorSettingsController implements Initializable {

	private VBox vbox;
	public Button applyButton, closeButton, defaulButton;

	public TextField zahlbandv, sensorbandv, lieferbandv, fahrstuhlhorizont, fahrstuhlvertikal, drehgeschwindigkeit,
			hebebandv, filldrehgescwindigkeit, anzahldrehungen, bandzumarmv, auslieferbandv, hebegeschwindigkeit,
			shaker, carGeschwindigkeit, lineoncarGeschwindigkeit, horizontalturndegree,drehgeschwindigkeittransport,ausfahrgeschwindigkeit,drehungen;

	public ToggleButton lager1, lager2, lager3, lager4, lager11, lager12, lager13, lager14, zwillingOff, zwillingOn,
			kompressorAn, kompressorAus, airarms1, airarms2, airarms3, airarms4, airarms11, airarms12, airarms13,
			airarms14;

	public ToggleGroup g1, g2, g3, g4, g5, g6, g7, g8, g9, g10;

	public ComboBox<String> farbe, drehtischposition;

	private Steuerung s;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		farbe.getItems().addAll("Weiß", "Rot", "Grün", "Blau", "Braun");
		drehtischposition.getItems().addAll("Anlieferung", "Lager", "Lift");

	}

	public void start() {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		s = (Steuerung) stage.getUserData();

		updateFromLiveModel();
	}

	public void updateFromLiveModel() { // gets every value from Steuerung
		updateStockToggleButton();
		updateAirarmsToggleButton();
		fetchIoColor();
		fetchTablePosition();
		fetchMotorSettings();
		fetchKompressorToggleButton();
		fetchTwinToggleButton();

	}

	public void saveFromUiToLiveModel() { // sets everything from Ui in steuerung

		setIoColor();
		setStockFromButtons();
		setTablePosition();
		setMotorSettings();
		setKompressor();
		setTwin();
		setAirarmsFromUi();
	}

	public void applyButtonPushed() {

		saveFromUiToLiveModel();
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
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

		//Car
//		carGeschwindigkeit.setText(String.valueOf(s.getCar().getCarSpeed()));
//		lineoncarGeschwindigkeit.setText(String.valueOf(s.getCar().getLineSpeed()));
//		horizontalturndegree.setText(String.valueOf(s.getCar().getCarHorizontalDegree()));

		//transport
		// drehgeschwindigkeittransport.setText(String.valueOf(s.getTransport().getLiftlinespeed()));
		// ausfahrgeschwindigkeit.setText(String.valueOf(s.getTransport().getEjectlinespeed()));
		// drehungen.setText(String.valueOf(s.getTransport().getNumberOfRotations()));
		
	}

	public void fetchTwinToggleButton() {

		if (s.isConnected()) {
			zwillingOn.setSelected(true);
		} else {
			zwillingOff.setSelected(true);
		}
	}

	public void fetchKompressorToggleButton() {

		if (s.getKompressorStatus()) {
			kompressorAn.setSelected(true);
		} else {
			kompressorAus.setSelected(true);
		}
	}

	public void updateAirarmsToggleButton() { // gets Airarm values and updates it in UI

		if (s.getAirarms().getArmPosition()) {
			airarms11.setSelected(true);
		} else {
			airarms1.setSelected(true);
		}

		if (s.getAirarms().getArmStatus()) {
			airarms12.setSelected(true);
		} else {
			airarms1.setSelected(true);
		}

		if (s.getAirarms().getGrabPosition()) {
			airarms3.setSelected(true);
		} else {
			airarms13.setSelected(true);
		}

		if (s.getAirarms().getGrabStatus()) {
			airarms4.setSelected(true);
		} else {
			airarms14.setSelected(true);
		}

	}

	// -----------------------------From-ui-to--Code-----------------------------------------

	public void setKompressor() {
		if (kompressorAn.isSelected()) {
			s.turnKompressorOn();
		}
		if (kompressorAus.isSelected()) {
			s.turnKompressorOff();
		}
	}

	public void setTwin() {
		if (zwillingOn.isSelected()) {
			s.setOnline();
		}
		if (zwillingOff.isSelected()) {
			s.setOffline();
		}
	}

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
		
//		s.getCar().setCarHorizontalDegree(Integer.parseInt(horizontalturndegree.getText()));
//		s.getCar().setLineSpeed(Integer.parseInt(lineoncarGeschwindigkeit.getText()));

		// s.getTransport().setEjectlinespeed(Integer.parseInt(ausfahrgeschwindigkeit.getText()));
		// s.getTransport().setNumberOfRotations(Integer.parseInt(drehungen.getText()));
		// s.getTransport().setLiftlinespeed(Integer.parseInt(drehgeschwindigkeit.getText()));
		
		
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
		// if (farbe.getValue().equals("Schwarz")) { // Schwarz should not be avaiable
		// as io color becouse the line is black
		// s.getQuality().setIoColor("BLACK");
		// }
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

	public void setAirarmsFromUi() { // gets Airarm values and updates it in Programm

		if (airarms1.isSelected()) {
			s.getAirarms().setArmPosition(false);
		} else {
			s.getAirarms().setArmPosition(true);
		}

		if (airarms2.isSelected()) {
			s.getAirarms().setArmStatus(false);
		} else {
			s.getAirarms().setArmStatus(true);
		}

		if (airarms3.isSelected()) {
			s.getAirarms().setGrabPosition(true);
		} else {
			s.getAirarms().setGrabPosition(false);
		}

		if (airarms4.isSelected()) {
			s.getAirarms().setGrabStatus(true);
		} else {
			s.getAirarms().setGrabStatus(false);
		}

	}

}
