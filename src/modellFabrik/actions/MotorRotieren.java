package modellFabrik.actions;

import java.rmi.RemoteException;
import lejos.remote.ev3.RMIRegulatedMotor;

//Benutze diese Klasse für einen Thread zum drehen eines Motors um einen bestimmten Winkel.
public class MotorRotieren implements Runnable {
	
	private RMIRegulatedMotor m;
	private int w=0;
	private int s=0;
	
	public MotorRotieren (RMIRegulatedMotor m, int w){
		this.m=m;
		this.w=w;
	}
	
	public MotorRotieren (RMIRegulatedMotor m, int w, int s){
		this.m=m;
		this.w=w;
		this.s=s;
	}
	
	@Override
	public void run () {
		if (s!=0) {
			try {
				m.setSpeed(s);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
		System.out.println("Drehe um "+w+" Grad.");
		try {
			m.rotate(w);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
