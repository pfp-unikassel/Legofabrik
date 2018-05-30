package stations;

import lejos.remote.ev3.RMIRegulatedMotor;

public class Airarms {

	private boolean grabStatus = true; // true is open
	private boolean grabPosition = true; // true is || over the line

	private boolean armPosition = true; // true is to line
	private boolean armStatus = true; // true is up false down

	RMIRegulatedMotor moveArm;
	RMIRegulatedMotor verticalArm;
	RMIRegulatedMotor turnGrab;
	RMIRegulatedMotor openCloseGrab;
	RMIRegulatedMotor turnArm1;
	RMIRegulatedMotor turnArm2;

	public Airarms(RMIRegulatedMotor airLine1, RMIRegulatedMotor airLine2, RMIRegulatedMotor airLine3,
			RMIRegulatedMotor airLine4, RMIRegulatedMotor turnArm1, RMIRegulatedMotor turnArm2) {

		this.moveArm = airLine1;
		this.verticalArm = airLine2;
		this.turnGrab = airLine3;
		this.openCloseGrab = airLine4;
		this.turnArm1 = turnArm1;
		this.turnArm2 = turnArm2;

	}

	public void armUp() {

		if (!getArmStatus()) { // if arm is down do
			setArmStatus(true);
		}
	}

	public void armDown() {

		if (getArmStatus()) { // if arm is up do
			setArmStatus(false);
		}
	}

	public void turnArm() {

		if (getArmPosition()) {
			// turn to get balls
			
			setArmPosition(false);
		} else {
			setArmPosition(true);
			// turn to lane
		}
	}

	public void grabOpen() {

		if (!getGrabStatus()) {
			setGrabStatus(true);
			// close
		}
	}

	public void grabClose() {

		if (!getGrabStatus()) {
			setGrabStatus(false);
			// open
		}
	}

	public void grabTurn() {

		if (getGrabPosition()) {
			// turn = to lane
			setGrabPosition(false);
		} else {
			// || to lane
			setGrabPosition(true);
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
