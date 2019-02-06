package stations;

import java.rmi.RemoteException;

import controller.Steuerung;
import lejos.remote.ev3.RMIRegulatedMotor;

public class FillStation {

	private Steuerung s;
	private RMIRegulatedMotor wheel;
	private int wheelspeed =60;
	private int numberOfTurns = 1; // 1 turn 360degree
	
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
		
		rotateWheel(360, false);
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
	
	
}
