package stations;

import java.rmi.RemoteException;

import controller.Steuerung;
import lejos.remote.ev3.RMIRegulatedMotor;

public class Deliverylane {

	RMIRegulatedMotor lineToEnd;
	RMIRegulatedMotor gateB;
	RMIRegulatedMotor gateC;
	RMIRegulatedMotor gateD;
	RMIRegulatedMotor LineToArms;

	private Steuerung s;
	
	private int lineToArmsSpeed = 180;
	private int lineToEndSpeed = 120; // 90 before
	private int gateTurnDegree = 50;
	private int gateCounter = 0;
	private int gatesUsed = 0 ;
	private int gateDCounter =0;
	private int gateBCounter =0;
	private int gateCCounter =0;
	private int gateECounter= 0; // is no gate ist k3

	private boolean gateDStatus = true; // true is closed
	private boolean gateBStatus = true; // true is closed
	private boolean gateCStatus = true; // true is closed

	public Deliverylane(Steuerung s,RMIRegulatedMotor lineToEnd, RMIRegulatedMotor gateB, RMIRegulatedMotor gateC,
			RMIRegulatedMotor gateD, RMIRegulatedMotor LineToArms) {

		this.s = s;
		this.lineToEnd = lineToEnd;
		this.gateB = gateB; // stoping boxes
		this.gateC = gateC; // customer 1
		this.gateD = gateD; // customer 2
		this.LineToArms = LineToArms;
	}

	public void ultraSonicFired() {

		// is not needed, just turn the line a complete round after the Gate B ist
		// opend,
		// Gate B is Opend right after the Airarms placed the balls
		// if distance less then 30cm stop and rotate x (guess 180) degree and wait
		// until gate b opens

	}

	public void startLineToArms(boolean direction) throws RemoteException { // false is the right direction

		LineToArms.setSpeed(getLineToArmsSpeed());
		if (direction) {
			LineToArms.forward();
		} else {
			LineToArms.backward();

		}
	}

	public void turnLineToArms(int degree) throws RemoteException {
		LineToArms.setSpeed(getLineToArmsSpeed());
		LineToArms.rotate(degree, false);
		//  s.sendMessage("BL");
	}

	public void stopLineToArms() throws RemoteException {

		LineToArms.stop(true);
	}

	public void startLineToEnd(boolean direction) throws RemoteException { // false is the right direction

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
				s.sendMessage("K2");
				gateDCounter++;
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
				gateBCounter++;
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
				gateCCounter++;
				s.sendMessage("K1");
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
				gateC.rotate(-gateTurnDegree, true);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			gateCStatus = true;
		}
	}

	public void openEquallyGate() {

		if (gateCounter % 3 == 0) {
			openGateC();
		} else {
			if (gateCounter % 3 == 1) {
				openGateD();
			}else {
				gateECounter++;
				s.sendMessage("K3");
			}
		}
		gateCounter++; // not sure
		gatesUsed++;
	}
	
	public int getGatesUsed() {
		return gatesUsed;
	}

	public void closeGates() {
		closeGateB();
		closeGateC();
		closeGateD();
	}

	public void stop() throws RemoteException {
		stopLineToArms();
		stopLineToEnd();
		reset();
	}

	public void reset() {
		closeGates();
		gateCounter = 0;
		gatesUsed = 0;
		gateCCounter =0;
		gateBCounter =0;
		gateDCounter =0;
		gateECounter =0;
		
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

	public int getGateCounter() {
		return gateCounter;
	}

	public void setGateCounter(int gateCounter) {
		this.gateCounter = gateCounter;
	}

	public int getGateECounter() {
		return gateECounter;
	}

	public void setGateECounter(int gateECounter) {
		this.gateECounter = gateECounter;
	}

	public int getGateBCounter() {
		return gateBCounter;
	}

	public void setGateBCounter(int gateBCounter) {
		this.gateBCounter = gateBCounter;
	}

	public int getGateCCounter() {
		return gateCCounter;
	}

	public void setGateCCounter(int gateCCounter) {
		this.gateCCounter = gateCCounter;
	}

	public void setGatesUsed(int gatesUsed) {
		this.gatesUsed = gatesUsed;
	}

	public int getGateDCounter() {
		return gateDCounter;
	}

	public void setGateDCounter(int gateDCounter) {
		this.gateDCounter = gateDCounter;
	}

	public RMIRegulatedMotor getLineToEnd() {
		return lineToEnd;
	}

	public void setLineToEnd(RMIRegulatedMotor lineToEnd) {
		this.lineToEnd = lineToEnd;
	}

	public RMIRegulatedMotor getGateB() {
		return gateB;
	}

	public void setGateB(RMIRegulatedMotor gateB) {
		this.gateB = gateB;
	}

	public RMIRegulatedMotor getGateC() {
		return gateC;
	}

	public void setGateC(RMIRegulatedMotor gateC) {
		this.gateC = gateC;
	}

	public RMIRegulatedMotor getGateD() {
		return gateD;
	}

	public void setGateD(RMIRegulatedMotor gateD) {
		this.gateD = gateD;
	}

	public RMIRegulatedMotor getLineToArms() {
		return LineToArms;
	}

	public void setLineToArms(RMIRegulatedMotor lineToArms) {
		LineToArms = lineToArms;
	}

}
