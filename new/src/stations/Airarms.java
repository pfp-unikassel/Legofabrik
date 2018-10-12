package stations;

import java.rmi.RemoteException;

import lejos.remote.ev3.RMIRegulatedMotor;

public class Airarms { // schalter rechts rechts Links links

	private boolean grabStatus = true; // true is open
	private boolean grabPosition = true; // true is || over the line

	private boolean armPosition = true; // true ist ausgefahren
	private boolean armStatus = true; // true is up false down

	private boolean towerPosition = true; // to lane

	private int turnDegree = -630;  // default/old -70 
	private int towerTurnDegree =- 100;

	RMIRegulatedMotor moveArm;
	RMIRegulatedMotor verticalArm;
	RMIRegulatedMotor turnGrab;
	RMIRegulatedMotor openCloseGrab;
	RMIRegulatedMotor turnArm1;
	RMIRegulatedMotor turnArm2;

	public Airarms(RMIRegulatedMotor airLine1, RMIRegulatedMotor airLine2, RMIRegulatedMotor airLine3,
			RMIRegulatedMotor airLine4, RMIRegulatedMotor turnArm1, RMIRegulatedMotor turnArm2) {

		this.moveArm = airLine1; // ausfahren
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

	public void armUp() {

		if (!getArmStatus()) { // if arm is down do mit der achse gegen den uhrzeigersinn
			setArmStatus(true);
			try {
				verticalArm.rotateTo(490, false);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void armDown() {

		if (getArmStatus()) { // if arm is up do
			setArmStatus(false);
			try {
				verticalArm.rotateTo(-490, false);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void turnArm() {

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

	public void grabOpen() {

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

	public void grabClose() {

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

	public void grabTurn() {

		if (getGrabPosition()) {
			// turn = to lane
			setGrabPosition(false);
			try {
				turnGrab.rotateTo(420, false);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				turnGrab.rotateTo(-420, false);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// || to lane
			setGrabPosition(true);
		}
	}

	public void turnTower() {

		if (towerPosition) { // true ist ausgefahren
			try {
				turnArm1.rotate(towerTurnDegree, false); // turn one after another
				turnArm2.rotate(towerTurnDegree, false); // falls es probleme gibt heir war vorher true testen
				towerPosition = false;
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				turnArm2.rotate(-towerTurnDegree, false);
				turnArm1.rotate(-towerTurnDegree, false);
				towerPosition = true;
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void reset() {

		if (!getGrabPosition()) {
			grabTurn();
		}
		grabOpen();

		if (!getArmPosition()) {
			turnArm();
		}

		armUp();
	}

	public boolean getGrabStatus() {
		return grabStatus;
	}

	public void setGrabStatus(boolean grabStatus) {
		this.grabStatus = grabStatus;
	}

	public boolean getGrabPosition() {
		return grabPosition;
	}

	public void setGrabPosition(boolean grabPosition) {
		this.grabPosition = grabPosition;
	}

	public boolean getArmPosition() {
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
}
