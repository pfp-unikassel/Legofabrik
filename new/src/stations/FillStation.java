package stations;

import java.rmi.RemoteException;

import controller.Steuerung;
import lejos.remote.ev3.RMIRegulatedMotor;

public class FillStation {

	private Steuerung s;
	private RMIRegulatedMotor wheel;
	private int wheelspeed =90; //60
	private int numberOfTurns = 0; // 1 turn 360degree
	private int numberOfDeliveredBalls =0;
	
	public FillStation(Steuerung s, RMIRegulatedMotor m){
		this.s=s;
		this.wheel = m;
	}
	
	public void rotateWheel(int degree , boolean instantReturn){
		
		try {
			wheel.setSpeed(wheelspeed);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			wheel.rotate(degree,instantReturn);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void loadCar(){
		
		rotateWheel(720, false); //360 
		numberOfTurns++;
		numberOfDeliveredBalls = numberOfDeliveredBalls+12*2; // ca 12 balls per 360°
	}

	public int getWheelspeed() {
		return wheelspeed;
	}

	public void setWheelspeed(int wheelspeed) {
		this.wheelspeed = wheelspeed;
	}

	public int getNumberOfTurns() {
		return numberOfTurns;
	}

	public void setNumberOfTurns(int numberOfTurns) {
		this.numberOfTurns = numberOfTurns;
	}

	public int getNumberOfDeliveredBalls() {
		return numberOfDeliveredBalls;
	}

	public void setNumberOfDeliveredBalls(int numberOfDeliveredBalls) {
		this.numberOfDeliveredBalls = numberOfDeliveredBalls;
	}

	public RMIRegulatedMotor getWheel() {
		return wheel;
	}

	public void setWheel(RMIRegulatedMotor wheel) {
		this.wheel = wheel;
	}
	
	
}
