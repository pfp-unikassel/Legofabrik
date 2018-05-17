package stations;

import java.rmi.RemoteException;

import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.remote.ev3.RMIRegulatedMotor;

public class Quality {

	RMIRegulatedMotor line; // TODO: rename, dont know wich lane atm
	RMIRegulatedMotor gate; // schranke
	RMIRegulatedMotor counterLine;
	EV3TouchSensor counter; // from Bandzaehler class
	EV3ColorSensor s;
	float color[];
	
	private boolean gateStatus = false; //TODO: anote what is open what is closed
	private int counterLineSpeed = 360;
	private int lineSpeed = 360;
	private int countedBalls = 0;

	
	
	public Quality(RMIRegulatedMotor band, RMIRegulatedMotor gate, RMIRegulatedMotor counterLine,
			EV3TouchSensor counter) {

		this.line = band;
		this.gate = gate;
		this.counterLine = counterLine;
		this.counter = counter;
	}

	public Quality(RMIRegulatedMotor band, RMIRegulatedMotor gate, EV3ColorSensor s, RMIRegulatedMotor counterLine,
			EV3TouchSensor counter) {

		this.line = band;
		this.gate = gate;
		this.s = s;
		color = new float[s.sampleSize()];
		this.counterLine = counterLine;
		this.counter = counter;
	}

	
	
	public void closeGate() {
		gateStatus = false;
		try {
			gate.rotate(-40); // maybe +40 dont know what open and close is
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void openGate() {
		gateStatus = true;
		try {
			gate.rotate(40); 	// maybe -40 dont know what open and close is
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void startCounterLine(boolean direction) throws RemoteException {

		counterLine.setSpeed(counterLineSpeed);

		if (direction) {
			counterLine.forward();
		} else {
			counterLine.backward();
		}

	}

	public void stopCounterLine() throws RemoteException {

		counterLine.stop(false);
	}

	public void startLine(boolean direction) throws RemoteException {

		line.setSpeed(lineSpeed);

		if (direction) {
			line.forward();
		} else {
			line.backward();
		}

	}

	public void stopLine() throws RemoteException {

		line.stop(false);
	}

	public void counterSensorFired() {

		countedBalls++;
	}

	public void colorSensorFired(float color) {

		this.color[0] = color;
	}

	public int getCounterLineSpeed() {
		return counterLineSpeed;
	}

	public void setCounterLineSpeed(int counterlineSpeed) {
		counterLineSpeed = counterlineSpeed;
	}

	public int getLineSpeed() {
		return lineSpeed;
	}

	public void setLineSpeed(int lineSpeed) {
		this.lineSpeed = lineSpeed;
	}
	public int getCountedBalls() {
		return countedBalls;
	}

	public void setCountedBalls(int countedBalls) {
		this.countedBalls = countedBalls;
	}
	public boolean getGateStatus() {
		return gateStatus;
	}
	public void reset() {
		
		if(gateStatus) {
			closeGate();
		}
		setCountedBalls(0);
	}
}
