package stations;

import java.rmi.RemoteException;

import lejos.remote.ev3.RMIRegulatedMotor;

public class Deliverylane {

	
	RMIRegulatedMotor lineToEnd;
	RMIRegulatedMotor gateB;
	RMIRegulatedMotor gateC;
	RMIRegulatedMotor gateD;
	RMIRegulatedMotor LineToArms;
	
	private int lineToArmsSpeed = 180;
	private int lineToEndSpeed = 120; // 90 before
	private int gateTurnDegree = 50;
	
	private boolean gateDStatus = true; // true is closed
	private boolean gateBStatus = true; // true is closed
	private boolean gateCStatus = true; // true is closed

	public Deliverylane(RMIRegulatedMotor lineToEnd, RMIRegulatedMotor gateB, RMIRegulatedMotor gateC,
			RMIRegulatedMotor gateD, RMIRegulatedMotor LineToArms) {

		this.lineToEnd = lineToEnd;
		this.gateB = gateB; // stoping boxes
		this.gateC = gateC; // customer 1
		this.gateD = gateD; // customer 2
		this.LineToArms = LineToArms;
	}

	public void ultraSonicFired() {

		// is not needed, just turn the line a complete round after the Gate B ist opend, 
		// Gate B is Opend right after the Airarms placed the balls
		// if distance less then 30cm stop and rotate x (guess 180) degree and wait
		// until gate b opens

	}

	public void startLineToArms(boolean direction) throws RemoteException {  //false is the right direction

		LineToArms.setSpeed(getLineToArmsSpeed());
		if (direction) {
			LineToArms.forward();
		} else {
			LineToArms.backward();

		}
	}

	public void stopLineToArms() throws RemoteException {

		LineToArms.stop(true);
	}

	public void startLineToEnd(boolean direction) throws RemoteException {  // false is the right direction

		lineToEnd.setSpeed(getLineToEndSpeed());
		if (direction) {
			lineToEnd.forward();
		} else {
			lineToEnd.backward();

		}
	}

	public void stopLineToEnd() throws RemoteException {

		lineToEnd.stop(true);
	}

	public void openGateD() {

		if (getGateDStatus()) {
			// open
			try {
				gateD.rotate(gateTurnDegree, true);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			gateDStatus = false;
		}
	}

	public void closeGateD() {

		if (!getGateDStatus()) {
			// close
			try {
				gateD.rotate(-gateTurnDegree, true);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		gateDStatus = true;
	}

	public void openGateB() {

		if (getGateBStatus()) {
			// open
			try {
				gateB.rotate(-gateTurnDegree, true);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			gateBStatus = false;
		}
	}

	public void closeGateB() {

		if (!getGateBStatus()) {
			// close

			try {
				gateB.rotate(gateTurnDegree, true);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			gateBStatus = true;
		}
	}

	public void openGateC() {

		if (getGateCStatus()) {
			// open
			try {
				gateC.rotate(gateTurnDegree, true);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			gateCStatus = false;
		}
	}

	public void closeGateC() {

		if (!getGateCStatus()) {
			// close
			try {
				gateD.rotate(-gateTurnDegree, true);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			gateCStatus = true;
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

	public int getLineToArmsSpeed() {
		return lineToArmsSpeed;
	}

	public void setLineSpeed(int lineSpeed) {
		this.lineToArmsSpeed = lineSpeed;
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

	public int getLineToEndSpeed() {
		return lineToEndSpeed;
	}

	public void setLineToEndSpeed(int lineToEndSpeed) {
		this.lineToEndSpeed = lineToEndSpeed;
	}

}
