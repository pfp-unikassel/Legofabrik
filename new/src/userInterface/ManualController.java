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

public class ManualController implements Initializable {

	private Steuerung s;
	public Button closeButton;

	public Button chargierVor1, chargierVor12, chargierVor13, chargierVor14, chargierVor15, chargierBack1,
			chargierBack12, chargierBack13, chargierBack14, chargierBack15, elevatorVor1, elevatorVor12, elevatorVor13,
			elevatorVor14, elevatorVor15, elevatorBack1, elevatorBack12, elevatorBack13, elevatorBack14, elevatorBack15,
			stockVor1, stockVor12, stockVor13, stockVor14, stockBack1, stockBack12, stockBack13, stockBack14, liftVor1,
			liftVor11, liftVor12, liftVor13, liftVor14, liftBack1, liftBack11, liftBack12, liftBack13, liftBack14,
			cleanerVor1, cleanerVor11, cleanerBack1, cleanerBack11, qualityVor1, qualityVor11, qualityVor12,
			qualityBack1, qualityBack11, qualityBack12, fillVor1, fillBack1, deliveryVor1, deliveryVor11, deliveryVor12,
			deliveryVor13, deliveryVor14, deliveryBack1, deliveryBack11, deliveryBack12, deliveryBack13, deliveryBack14,
			carVor1, carVor12, carVor13, carBack1, carBack12, carBack13, airarmsVor1, airarmsVor12, airarmsVor13,
			airarmsVor14, airarmsVor15, airarmsVor16, airarmsBack1, airarmsBack12, airarmsBack13, airarmsBack14,
			airarmsBack15, airarmsBack16, transportVor1,transportVor2,transportVor3,transportVor4,transportBack1,transportBack2,transportBack3,transportBack4;

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

	// try {
	// s.getChargier().stopLineToTable();
	// } catch (RemoteException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }

	// --------------------------------------------------------------------Turn
	// Table
	public void chargierVorButtonPressed1() {

		try {
			s.getChargier().getDrehtischRotieren().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void chargierVorButtonReleased1() {
		try {
			s.getChargier().getDrehtischRotieren().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void chargierBackButtonPressed1() {
		try {
			s.getChargier().getDrehtischRotieren().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void chargierBackButtonReleased1() {
		try {
			s.getChargier().getDrehtischRotieren().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------- Line
	// on
	// Table
	public void chargierVorButtonPressed12() {
		try {
			s.getChargier().startTableLine(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void chargierVorButtonReleased12() {
		try {
			s.getChargier().stopTableLine();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void chargierBackButtonPressed12() {
		try {
			s.getChargier().startTableLine(true);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void chargierBackButtonReleased12() {
		try {
			s.getChargier().stopTableLine();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------- Line
	// to
	// store
	public void chargierVorButtonPressed13() {
		try {
			s.getChargier().startLineToStore(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void chargierVorButtonReleased13() {
		try {
			s.getChargier().stopLineToStorer();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void chargierBackButtonPressed13() {
		try {
			s.getChargier().startLineToStore(true);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void chargierBackButtonReleased13() {
		try {
			s.getChargier().stopLineToStorer();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------- Line
	// to
	// production
	public void chargierVorButtonPressed14() {
		try {
			s.getChargier().getAntriebDrehtisch().forward();;
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void chargierVorButtonReleased14() {
		try {
			s.getChargier().getAntriebDrehtisch().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void chargierBackButtonPressed14() {
		try {
			s.getChargier().getAntriebDrehtisch().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void chargierBackButtonReleased14() {
		try {
			s.getChargier().getAntriebDrehtisch().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------- Line
	// to
	// Table
	public void chargierVorButtonPressed15() {
		try {
			s.getChargier().getDrehtischRotieren().forward();;
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void chargierVorButtonReleased15() {
		try {
			s.getChargier().getDrehtischRotieren().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void chargierBackButtonPressed15() {
		try {
			s.getChargier().getDrehtischRotieren().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void chargierBackButtonReleased15() {
		try {
			s.getChargier().getDrehtischRotieren().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------- Line
	// on
	// Elevator
	public void elevatorVorButtonPressed1() {
		try {
			s.getStock().startLineToStock(true);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void elevatorVorButtonReleased1() {
		try {
			s.getStock().stopLaneToStock();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void elevatorBackButtonPressed1() {
		try {
			s.getStock().startLineToStock(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void elevatorBackButtonReleased1() {
		try {
			s.getStock().stopLaneToStock();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------
	// Elevator
	// to left
	public void elevatorVorButtonPressed12() {
		try {
			s.getStock().getElevatorHorizontal1().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void elevatorVorButtonReleased12() {
		try {
			s.getStock().getElevatorHorizontal1().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void elevatorBackButtonPressed12() {
		try {
			s.getStock().getElevatorHorizontal1().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void elevatorBackButtonReleased12() {
		try {
			s.getStock().getElevatorHorizontal1().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------
	// Elevator
	// to right
	public void elevatorVorButtonPressed13() {
		try {
			s.getStock().getElevatorHorizontal2().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void elevatorVorButtonReleased13() {
		try {
			s.getStock().getElevatorHorizontal2().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void elevatorBackButtonPressed13() {
		try {
			s.getStock().getElevatorHorizontal2().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void elevatorBackButtonReleased13() {
		try {
			s.getStock().getElevatorHorizontal2().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------
	// Elevator
	// down
	public void elevatorVorButtonPressed14() {
		try {
			s.getStock().getElevatorVertical1().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void elevatorVorButtonReleased14() {
		try {
			s.getStock().getElevatorVertical1().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void elevatorBackButtonPressed14() {
		try {
			s.getStock().getElevatorVertical1().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void elevatorBackButtonReleased14() {
		try {
			s.getStock().getElevatorVertical1().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------
	// Elevator
	// up
	public void elevatorVorButtonPressed15() {
		try {
			s.getStock().getElevatorVertical2().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void elevatorVorButtonReleased15() {
		try {
			s.getStock().getElevatorVertical2().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void elevatorBackButtonPressed15() {
		try {
			s.getStock().getElevatorVertical2().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void elevatorBackButtonReleased15() {
		try {
			s.getStock().getElevatorVertical2().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------
	// Stock1
	public void stockVorButtonPressed1() {
		try {
			s.getStock().getStockPlace1().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void stockVorButtonReleased1() {
		try {
			s.getStock().getStockPlace1().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void stockBackButtonPressed1() {
		try {
			s.getStock().getStockPlace1().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void stockBackButtonReleased1() {
		try {
			s.getStock().getStockPlace1().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------
	// Stock2
	public void stockVorButtonPressed12() {
		try {
			s.getStock().getStockPlace2().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void stockVorButtonReleased12() {
		try {
			s.getStock().getStockPlace2().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void stockBackButtonPressed12() {
		try {
			s.getStock().getStockPlace2().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void stockBackButtonReleased12() {
		try {
			s.getStock().getStockPlace2().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------
	// Stock3
	public void stockVorButtonPressed13() {
		try {
			s.getStock().getStockPlace3().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void stockVorButtonReleased13() {
		try {
			s.getStock().getStockPlace3().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void stockBackButtonPressed13() {
		try {
			s.getStock().getStockPlace3().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void stockBackButtonReleased13() {
		try {
			s.getStock().getStockPlace3().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------
	// Stock4
	public void stockVorButtonPressed14() {
		try {
			s.getStock().getStockPlace4().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void stockVorButtonReleased14() {
		try {
			s.getStock().getStockPlace4().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void stockBackButtonPressed14() {
		try {
			s.getStock().getStockPlace4().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void stockBackButtonReleased14() {
		try {
			s.getStock().getStockPlace4().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------
	// shaker
	public void liftVorButtonPressed1() {
		try {
			s.getLift().getShaker().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void liftVorButtonReleased1() {
		try {
			s.getLift().getShaker().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void liftBackButtonPressed1() {
		try {
			s.getLift().getShaker().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void liftBackButtonReleased1() {
		try {
			s.getLift().getShaker().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------- lift
	// right in out
	public void liftVorButtonPressed11() {
		try {
			s.getLift().getGreifenRechts().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void liftVorButtonReleased11() {
		try {
			s.getLift().getGreifenRechts().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void liftBackButtonPressed11() {
		try {
			s.getLift().getGreifenRechts().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void liftBackButtonReleased11() {
		try {
			s.getLift().getGreifenRechts().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	// -------------------------------------------------------------------- lift
	// left in out

	public void liftVorButtonPressed12() {
		try {
			s.getLift().getGreifenLinks().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void liftVorButtonReleased12() {
		try {
			s.getLift().getGreifenLinks().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void liftBackButtonPressed12() {
		try {
			s.getLift().getGreifenLinks().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void liftBackButtonReleased12() {
		try {
			s.getLift().getGreifenLinks().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------- lift
	// right up down
	public void liftVorButtonPressed13() {
		try {
			s.getLift().getHebenRechts().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void liftVorButtonReleased13() {
		try {
			s.getLift().getHebenRechts().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void liftBackButtonPressed13() {
		try {
			s.getLift().getHebenRechts().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void liftBackButtonReleased13() {
		try {
			s.getLift().getHebenRechts().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------- lift
	// left up down
	public void liftVorButtonPressed14() {
		try {
			s.getLift().getHebenLinks().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void liftVorButtonReleased14() {
		try {
			s.getLift().getHebenLinks().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void liftBackButtonPressed14() {
		try {
			s.getLift().getHebenLinks().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void liftBackButtonReleased14() {
		try {
			s.getLift().getHebenLinks().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------
	// liftline
	public void cleanerVorButtonPressed1() {
		try {
			s.getCleaner().startLiftLine(true);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void cleanerVorButtonReleased1() {
		try {
			s.getCleaner().stopLiftLine();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void cleanerBackButtonPressed1() {
		try {
			s.getCleaner().startLiftLine(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void cleanerBackButtonReleased1() {
		try {
			s.getCleaner().stopLiftLine();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	// --------------------------------------------------------------------
	// cleaner

	public void cleanerVorButtonPressed11() {
		try {
			s.getCleaner().startCleaner(true);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void cleanerVorButtonReleased11() {
		try {
			s.getCleaner().stopCleaner();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void cleanerBackButtonPressed11() {
		try {
			s.getCleaner().startCleaner(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void cleanerBackButtonReleased11() {
		try {
			s.getCleaner().stopCleaner();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------
	// colorsensor gate
	public void qualityVorButtonPressed1() {
		try {
			s.getQuality().getGate().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void qualityVorButtonReleased1() {
		try {
			s.getQuality().getGate().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void qualityBackButtonPressed1() {
		try {
			s.getQuality().getGate().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void qualityBackButtonReleased1() {
		try {
			s.getQuality().getGate().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------
	// color
	// line
	public void qualityVorButtonPressed11() {
		try {
			s.getQuality().getLine().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void qualityVorButtonReleased11() {
		try {
			s.getQuality().getLine().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void qualityBackButtonPressed11() {
		try {
			s.getQuality().getLine().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void qualityBackButtonReleased11() {
		try {
			s.getQuality().getLine().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------
	// counter
	// line
	public void qualityVorButtonPressed12() {
		try {
			s.getQuality().getCounterLine().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void qualityVorButtonReleased12() {
		try {
			s.getQuality().getCounterLine().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void qualityBackButtonPressed12() {
		try {
			s.getQuality().getCounterLine().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void qualityBackButtonReleased12() {
		try {
			s.getQuality().getCounterLine().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------
	// Airarms
	public void airarmsVorButtonPressed1() {
		try {
			s.getAirarms().getMoveArm().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void airarmsVorButtonReleased1() {
		try {
			s.getAirarms().getMoveArm().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void airarmsBackButtonPressed1() {
		try {
			s.getAirarms().getMoveArm().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void airarmsBackButtonReleased1() {
		try {
			s.getAirarms().getMoveArm().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------
	// Airarms
	public void airarmsVorButtonPressed12() {
		try {
			s.getAirarms().getVerticalArm().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void airarmsVorButtonReleased12() {
		try {
			s.getAirarms().getVerticalArm().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void airarmsBackButtonPressed12() {
		try {
			s.getAirarms().getVerticalArm().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void airarmsBackButtonReleased12() {
		try {
			s.getAirarms().getVerticalArm().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------
	// Airarms
	public void airarmsVorButtonPressed13() {
		try {
			s.getAirarms().getTurnGrab().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void airarmsVorButtonReleased13() {
		try {
			s.getAirarms().getTurnGrab().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void airarmsBackButtonPressed13() {
		try {
			s.getAirarms().getTurnGrab().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void airarmsBackButtonReleased13() {
		try {
			s.getAirarms().getTurnGrab().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------
	// Airarms
	public void airarmsVorButtonPressed14() {
		try {
			s.getAirarms().getOpenCloseGrab().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void airarmsVorButtonReleased14() {
		try {
			s.getAirarms().getOpenCloseGrab().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void airarmsBackButtonPressed14() {
		try {
			s.getAirarms().getOpenCloseGrab().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void airarmsBackButtonReleased14() {
		try {
			s.getAirarms().getOpenCloseGrab().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------
	// Airarms
	public void airarmsVorButtonPressed15() {
		try {
			s.getAirarms().getTurnArm1().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void airarmsVorButtonReleased15() {
		try {
			s.getAirarms().getTurnArm1().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void airarmsBackButtonPressed15() {
		try {
			s.getAirarms().getTurnArm1().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void airarmsBackButtonReleased15() {
		try {
			s.getAirarms().getTurnArm1().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------
	// Airarms
	public void airarmsVorButtonPressed16() {
		try {
			s.getAirarms().getTurnArm2().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void airarmsVorButtonReleased16() {
		try {
			s.getAirarms().getTurnArm2().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void airarmsBackButtonPressed16() {
		try {
			s.getAirarms().getTurnArm2().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void airarmsBackButtonReleased16() {
		try {
			s.getAirarms().getTurnArm2().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------- fill
	public void fillVorButtonPressed1() {
		try {
			s.getFillStation().getWheel().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void fillVorButtonReleased1() {
		try {
			s.getFillStation().getWheel().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void fillBackButtonPressed1() {
		try {
			s.getFillStation().getWheel().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void fillBackButtonReleased1() {
		try {
			s.getFillStation().getWheel().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------- Gate
	// D
	public void deliveryVorButtonPressed1() {
		try {
			s.getDelivery().getGateD().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void deliveryVorButtonReleased1() {
		try {
			s.getDelivery().getGateD().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void deliveryBackButtonPressed1() {
		try {
			s.getDelivery().getGateD().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void deliveryBackButtonReleased1() {
		try {
			s.getDelivery().getGateD().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------- gate
	// C
	public void deliveryVorButtonPressed11() {
		try {
			s.getDelivery().getGateC().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void deliveryVorButtonReleased11() {
		try {
			s.getDelivery().getGateC().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void deliveryBackButtonPressed11() {
		try {
			s.getDelivery().getGateC().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void deliveryBackButtonReleased11() {
		try {
			s.getDelivery().getGateC().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------- Box
	// door
	public void deliveryVorButtonPressed12() {
		try {
			s.getDelivery().getGateB().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void deliveryVorButtonReleased12() {
		try {
			s.getDelivery().getGateB().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void deliveryBackButtonPressed12() {
		try {
			s.getDelivery().getGateB().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void deliveryBackButtonReleased12() {
		try {
			s.getDelivery().getGateB().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------- line
	// to
	// customers
	public void deliveryVorButtonPressed13() {
		try {
			s.getDelivery().getLineToEnd().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void deliveryVorButtonReleased13() {
		try {
			s.getDelivery().getLineToEnd().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void deliveryBackButtonPressed13() {
		try {
			s.getDelivery().getLineToEnd().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void deliveryBackButtonReleased13() {
		try {
			s.getDelivery().getLineToEnd().stop(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------- line
	// from puffer to arms
	public void deliveryVorButtonPressed14() {
		try {
			s.getDelivery().getLineToArms().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void deliveryVorButtonReleased14() {
		try {
			s.getDelivery().getLineToArms().stop(false);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deliveryBackButtonPressed14() {
		try {
			s.getDelivery().getLineToArms().forward();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deliveryBackButtonReleased14() {
		try {
			s.getDelivery().getLineToArms().stop(false);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// -------------------------------------------------------------------- CAR
	// Line

	 public void carVorButtonPressed1() {
	 try {
	 s.getCar().getLineOnCar().forward();
	 } catch (RemoteException e) {
	 e.printStackTrace();
	 }
	 }
	
	 public void carVorButtonReleased1() {
	 try {
	 s.getCar().getLineOnCar().stop(false);
	 } catch (RemoteException e) {
	 // TODO Auto-generated catch block
	 e.printStackTrace();
	 }
	 }
	
	 public void carBackButtonPressed1() {
	 try {
	 s.getCar().getLineOnCar().backward();
	 } catch (RemoteException e) {
	 // TODO Auto-generated catch block
	 e.printStackTrace();
	 }
	 }
	
	 public void carBackButtonReleased1() {
	 try {
	 s.getCar().getLineOnCar().stop(false);
	 } catch (RemoteException e) {
	 // TODO Auto-generated catch block
	 e.printStackTrace();
	 }
	 }

	// -------------------------------------------------------------------- CAR
	// Lenkung

	public void carVorButtonPressed12() {
		try {
			s.getCar().getLenkung().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void carVorButtonReleased12() {
		try {
			s.getCar().getLenkung().stop(false);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void carBackButtonPressed12() {
		try {
			s.getCar().getLenkung().backward();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void carBackButtonReleased12() {
		try {
			s.getCar().getLenkung().stop(false);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// -------------------------------------------------------------------- CAR
	// antrieb

	public void carVorButtonPressed13() {
		try {
			s.getCar().getAntrieb().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void carVorButtonReleased13() {
		try {
			s.getCar().getAntrieb().stop(false);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void carBackButtonPressed13() {
		try {
			s.getCar().getAntrieb().backward();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void carBackButtonReleased13() {
		try {
			s.getCar().getAntrieb().stop(false);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//------------------------------------------------------------------------- Transport 
	public void transportVorButtonPressed1() {
		try {
			s.getTransport().getLiftlineleft().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void  transportVorButtonReleased1() {
		try {
			s.getTransport().getLiftlineleft().stop();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void  transportBackButtonPressed1() {
		try {
			s.getTransport().getLiftlineleft().backward();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void  transportBackButtonReleased1() {
		try {
			s.getTransport().getLiftlineleft().stop();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//------------------------------------------------------------------------- Transport 
		public void transportVorButtonPressed2() {
			try {
				s.getTransport().getLiftlineright().forward();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		public void  transportVorButtonReleased2() {
			try {
				s.getTransport().getLiftlineright().stop();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void  transportBackButtonPressed2() {
			try {
				s.getTransport().getLiftlineright().backward();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void  transportBackButtonReleased2() {
			try {
				s.getTransport().getLiftlineright().stop();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		//------------------------------------------------------------------------- Transport 
				public void transportVorButtonPressed3() {
					try {
						s.getTransport().getEjectlineleft().forward();
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}

				public void  transportVorButtonReleased3() {
					try {
						s.getTransport().getEjectlineleft().stop();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				public void  transportBackButtonPressed3() {
					try {
						s.getTransport().getEjectlineleft().backward();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				public void  transportBackButtonReleased3() {
					try {
						s.getTransport().getEjectlineleft().stop();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				//------------------------------------------------------------------------- Transport 
				public void transportVorButtonPressed4() {
					try {
						s.getTransport().getEjectlineright().forward();
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}

				public void  transportVorButtonReleased4() {
					try {
						s.getTransport().getEjectlineright().stop();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				public void  transportBackButtonPressed4() {
					try {
						s.getTransport().getEjectlineright().backward();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				public void  transportBackButtonReleased4() {
					try {
						s.getTransport().getEjectlineright().stop();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

}
