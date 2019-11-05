package stations;

import java.rmi.RemoteException;

import controller.Steuerung;
import lejos.remote.ev3.RMIRegulatedMotor;

public class Airarms { // schalter rechts rechts rechts links

	private boolean grabStatus = true; // true is open   just set to 
	private boolean grabPosition = false; // true is || over the line

	private boolean armPosition = true; // true ist ausgefahren
	private boolean armStatus = true; // true is up false down

	private boolean towerPosition = false; // to lane rigth one

	private int turnDegree = -750;  // default 750
	private int towerTurnDegree =- 100; //default 100
	private Steuerung s;

	RMIRegulatedMotor moveArm;
	RMIRegulatedMotor verticalArm;
	RMIRegulatedMotor turnGrab;
	RMIRegulatedMotor openCloseGrab;
	RMIRegulatedMotor turnArm1;
	RMIRegulatedMotor turnArm2;

	public Airarms(Steuerung s,RMIRegulatedMotor airLine1, RMIRegulatedMotor airLine2, RMIRegulatedMotor airLine3,
			RMIRegulatedMotor airLine4, RMIRegulatedMotor turnArm1, RMIRegulatedMotor turnArm2) {
		
		this.s = s;
		this.moveArm = airLine1; // ausfahren A B C D
		this.verticalArm = airLine2; 
		this.turnGrab = airLine3;
		this.openCloseGrab = airLine4;
		this.turnArm1 = turnArm1;
		this.turnArm2 = turnArm2;
		
		try {
			turnArm1.setSpeed(180);   // set Turnspeed 
			turnArm2.setSpeed(180);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	
	public void turnArm() { //A

		System.out.println("In turnArm");
		
		if (getArmPosition()) {
			// turn to get balls
			try {
				moveArm.rotate(turnDegree, false);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			setArmPosition(false);
			
		} else {
			setArmPosition(true);
			try {
				moveArm.rotate(-turnDegree, false);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// turn to lane
		}
	}
	
	public void armUp() { // B

		System.out.println("In armUp");
		
		if (!getArmStatus()) { // if arm is down do mit der achse gegen den uhrzeigersinn
			setArmStatus(true);
			try {
				verticalArm.rotate(-turnDegree, false); //450 490
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void armDown() { //B

		System.out.println("In armDown");
		
		if (getArmStatus()) { // if arm is up do
			setArmStatus(false);
			try {
				verticalArm.rotate(turnDegree, false); //490
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}




	public void grabTurn() { //C

		System.out.println("In grabTurn");
		
		if (getGrabPosition()) {
			// turn = to lane
			setGrabPosition(false);
			try {
				turnGrab.rotate(-turnDegree, false); 
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				// || to lane
				setGrabPosition(true);
				turnGrab.rotate(turnDegree, false); 
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		}
	}
	
	
	public void grabClose() { //D

		System.out.println("In grabClose");
		
		if (getGrabStatus()) {
			setGrabStatus(false);
			try {
				openCloseGrab.rotate(-turnDegree, false);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// open
		}
	}

	public void grabOpen() { //D

		System.out.println("In grabOpen");
		
		if (!getGrabStatus()) {
			setGrabStatus(true);
			try {
				openCloseGrab.rotate(turnDegree, false);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// close
		}
	}

	public void turnTower() {

		System.out.println("In turnTower");
		
		if (towerPosition) { // true ist ausgefahren
			try {
//				turnArm1.rotate(towerTurnDegree, false); // turn one after another
				turnArm2.rotate(towerTurnDegree, false); // falls es probleme gibt heir war vorher true testen
				towerPosition = false;
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				turnArm2.rotate(-towerTurnDegree, false);
//				turnArm1.rotate(-towerTurnDegree, false);
				towerPosition = true;
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void reset() {

		System.out.println("In reset");
		
		if (!getGrabPosition()) {
			grabTurn();
		}
		grabOpen();

		if (!getArmPosition()) {
			turnArm();
		}

		if (!isTowerPosition()) {
			turnTower();
		}
		
		armUp();
	}
	
	public void hardReset() { // turning motors but has no sa
		
		System.out.println("In hard reset");
		
		try {
			turnGrab.rotateTo(-420, false);
			openCloseGrab.rotate(turnDegree, false);
			moveArm.rotate(-turnDegree, false);
			verticalArm.rotateTo(450, false); //490
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void runAirArms() {
		
		System.out.println("In runAirArms");
		
		turnArm();                 // einfarhen / tower drehen / ausfahren / arm runter/ grab schlieﬂen / arm up / arm einfahren / turm drehen / arm ausfahren / grab drehen / runter / aufmachen 

		turnTower();
		
		turnArm();  

		armDown();

		grabClose() ;
		
		armUp();
		
		turnArm();
		
		turnTower();
		
		grabTurn();
		
		turnArm();
		
		armDown();
		
		grabOpen();

		armUp();
		
		grabTurn();  // just to cover the new start position
	}

	public boolean getGrabStatus() {
		System.out.println("In getGrabStatus");
		return grabStatus;
	}

	public void setGrabStatus(boolean grabStatus) {
		System.out.println("In setGrabStatus");
		this.grabStatus = grabStatus;
	}

	public boolean getGrabPosition() {
		System.out.println("In getGrabPosition");
		return grabPosition;
	}

	public void setGrabPosition(boolean grabPosition) {
		System.out.println("In setGrabPosition");
		this.grabPosition = grabPosition;
	}

	public boolean getArmPosition() {
		System.out.println("In getArmPosition");
		return armPosition;
	}

	public void setArmPosition(boolean armPosition) {
		this.armPosition = armPosition;
	}

	public boolean getArmStatus() {
		return armStatus;
	}

	public void setArmStatus(boolean armStatus) {
		this.armStatus = armStatus;
	}

	public boolean isTowerPosition() {
		return towerPosition;
	}

	public void setTowerPosition(boolean towerPosition) {
		this.towerPosition = towerPosition;
	}

	public int getTurnDegree() {
		return turnDegree;
	}

	public void setTurnDegree(int turnDegree) {
		this.turnDegree = turnDegree;
	}

	public int getTowerTurnDegree() {
		return towerTurnDegree;
	}

	public void setTowerTurnDegree(int towerTurnDegree) {
		this.towerTurnDegree = towerTurnDegree;
	}

	public RMIRegulatedMotor getTurnArm1() {
		return turnArm1;
	}

	public void setTurnArm1(RMIRegulatedMotor turnArm1) {
		this.turnArm1 = turnArm1;
	}

	public RMIRegulatedMotor getTurnArm2() {
		return turnArm2;
	}

	public void setTurnArm2(RMIRegulatedMotor turnArm2) {
		this.turnArm2 = turnArm2;
	}

	public RMIRegulatedMotor getMoveArm() {
		return moveArm;
	}

	public void setMoveArm(RMIRegulatedMotor moveArm) {
		this.moveArm = moveArm;
	}

	public RMIRegulatedMotor getVerticalArm() {
		return verticalArm;
	}

	public void setVerticalArm(RMIRegulatedMotor verticalArm) {
		this.verticalArm = verticalArm;
	}

	public RMIRegulatedMotor getTurnGrab() {
		return turnGrab;
	}

	public void setTurnGrab(RMIRegulatedMotor turnGrab) {
		this.turnGrab = turnGrab;
	}

	public RMIRegulatedMotor getOpenCloseGrab() {
		return openCloseGrab;
	}

	public void setOpenCloseGrab(RMIRegulatedMotor openCloseGrab) {
		this.openCloseGrab = openCloseGrab;
	}
}
