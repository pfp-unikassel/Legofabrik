package stations;

import java.rmi.RemoteException;

import controller.Steuerung;
import lejos.remote.ev3.RMIRegulatedMotor;

public class FillStation {

	private Steuerung s;
	private RMIRegulatedMotor wheel;
	
	public FillStation(Steuerung s, RMIRegulatedMotor m){
		this.s=s;
		this.wheel = m;
	}
	
	public void rotateWheel(int degree , boolean instantReturn){
		
		try {
			wheel.setSpeed(60);
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
		
		rotateWheel(300, false);
	}
	
	
}
