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
	
	private boolean gateStatus = true; //TODO: true us open 
	private int counterLineSpeed = 360;
	private int lineSpeed = 360;
	private int countedBalls = 0;
	private int goodBalls = 0;
	private int badBalls = 0;
	private String colorString = "";
	
	
	




	public Quality(RMIRegulatedMotor band, RMIRegulatedMotor gate, RMIRegulatedMotor counterLine) {

		this.line = band;
		this.gate = gate;
		this.counterLine = counterLine;
		this.counter = counter;
	}


	
	
	public void closeGate() {
		
		if(gateStatus) {			// if gate is open close if allready closed stay closed
			
			gateStatus = false;
			try {
				gate.rotate(-40); // maybe +40 dont know what open and close is
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void openGate() {
		if(!gateStatus) {				// if gate is close open if allready open stay open
			
			gateStatus = true;
			try {
				gate.rotate(40); 	// maybe -40 dont know what open and close is
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		System.out.println(countedBalls);  //TODO: delete after debug
	}

	public void colorSensorFired(String colorString) {

		this.colorString = colorString;
		System.out.println("erkannte Farbe ist " + colorString);
		
		if(colorString == "White") {   // if ball is white close gate 
			closeGate();		
			setGoodBalls(getGoodBalls()+1);
		}else {						 // if ball is not white open gate 
			openGate();
			setBadBalls(getBadBalls()+1);
		}
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
		
		if(gateStatus) {			// closes gate if open
			closeGate();
		}
		setCountedBalls(0);
		resetColorString();
		setGoodBalls(0);
		setBadBalls(0);
	}
	
	public int getGoodBalls() {
		return goodBalls;
	}




	public void setGoodBalls(int goodBalls) {
		this.goodBalls = goodBalls;
	}




	public void setGateStatus(boolean gateStatus) {
		this.gateStatus = gateStatus;
	}




	public int getBadBalls() {
		return badBalls;
	}




	public void setBadBalls(int badBalls) {
		this.badBalls = badBalls;
	}




	public void setColorString(String colorString) {
		this.colorString = colorString;
		
	}
	public String getColorString() {
		return colorString;
	}

	public void resetColorString() {
		this.colorString = "";
		
	}
}
