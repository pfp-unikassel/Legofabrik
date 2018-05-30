package stations;

import java.rmi.RemoteException;

import lejos.remote.ev3.RMIRegulatedMotor;

public class Deliverylane {

	private int lineSpeed = 300;
	private boolean gateDStatus = true; // true is closed
	private boolean gateBStatus = true; // true is closed
	private boolean gateCStatus = true; // true is closed
	RMIRegulatedMotor lineToEnd;
	RMIRegulatedMotor gateB;
	RMIRegulatedMotor gateC;
	RMIRegulatedMotor gateD;
	RMIRegulatedMotor LineToArms;

	public Deliverylane(RMIRegulatedMotor lineToEnd, RMIRegulatedMotor gateB, RMIRegulatedMotor gateC,
			RMIRegulatedMotor gateA, RMIRegulatedMotor LineToArms) {

		this.lineToEnd = lineToEnd;
		this.gateB = gateB; // stoping boxes
		this.gateC = gateC; // customer 1
		this.gateD = gateD; // customer 2
		this.LineToArms = LineToArms;
	}

	public void ultraSonicFired() {

		// if distance less then 30cm stop and rotate x (guess 180) degree and wait until gate b opens
		
	}

	public void startLineToArms(boolean direction) throws RemoteException {

		LineToArms.setSpeed(getLineSpeed());
		if (direction) {
			LineToArms.forward();
		} else {
			LineToArms.backward();

		}
	}

	public void stopLineToArms() throws RemoteException {

		LineToArms.stop(false);
	}

	public void startLineToEnd(boolean direction) throws RemoteException {

		lineToEnd.setSpeed(getLineSpeed());
		if (direction) {
			lineToEnd.forward();
		} else {
			lineToEnd.backward();

		}
	}

	public void stopLineToEnd() throws RemoteException {

		lineToEnd.stop(false);
	}

	public void openGateD() {

		if (!getGateDStatus()) {
			// open
		}
	}

	public void closeGateD() {

		if (getGateDStatus()) {
			// close
		}
	}

	public void openGateB() {

		if (!getGateBStatus()) {
			// open
		}
	}

	public void closeGateB() {

		if (getGateBStatus()) {
			// close
		}
	}

	public void openGateC() {

		if (!getGateCStatus()) {
			// open
		}
	}

	public void closeGateC() {

		if (getGateCStatus()) {
			// close
		}
	}

	public void stop() throws RemoteException {
		stopLineToArms();
		stopLineToEnd();
		reset();
	}

	public void reset() {
		closeGateD();
		closeGateB();
		closeGateC();

	}

	public int getLineSpeed() {
		return lineSpeed;
	}

	public void setLineSpeed(int lineSpeed) {
		this.lineSpeed = lineSpeed;
	}

	public boolean getGateDStatus() {
		return gateDStatus;
	}

	public void setGateDStatus(boolean gateAStatus) {
		this.gateDStatus = gateAStatus;
	}

	public boolean getGateBStatus() {
		return gateBStatus;
	}

	public void setGateBStatus(boolean gateBStatus) {
		this.gateBStatus = gateBStatus;
	}

	public boolean getGateCStatus() {
		return gateCStatus;
	}

	public void setGateCStatus(boolean gateCStatus) {
		this.gateCStatus = gateCStatus;
	}

}
