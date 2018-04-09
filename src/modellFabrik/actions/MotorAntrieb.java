package modellFabrik.actions;

import java.rmi.RemoteException;

import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.utility.Delay;

//Diese Klasse erstellt einen Thread zum Antrieb eines Motors. Man kann die Antriebrichtung und ggf. eine Antriebdauer [ms] angeben.
public class MotorAntrieb implements Runnable {
	
	private RMIRegulatedMotor m;
	private int dauer = 0;
	private char richtung; //f-orward/b-ackward
		
	public MotorAntrieb (RMIRegulatedMotor m, char richtung) {
		this.m=m;
		this.richtung=richtung;
	}
	
	public MotorAntrieb (RMIRegulatedMotor m, int dauer, char richtung) {
		this.m=m;
		this.dauer=dauer;		
		this.richtung=richtung;
	}

	@Override
	public void run () {
		if (richtung=='f') {
			if (dauer==0) {
				try {
					m.forward();
					while (!Thread.currentThread().isInterrupted()){
						
					}
					m.stop(false);
				} 
				catch (RemoteException e) {
					e.printStackTrace();
				}
			}
				
			else {
				try {
					m.forward();
				} 
				catch (RemoteException e) {
					e.printStackTrace();
				}
				Delay.msDelay(dauer);
				try {
					m.stop(false);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				Thread.currentThread().interrupt();
			}
		}
		
		if (richtung=='b') {
			if (dauer==0) {
				try {
					m.backward();
					while (!Thread.currentThread().isInterrupted()){
						
					}
					m.stop(false);
				} 
				catch (RemoteException e) {
					e.printStackTrace();
				}
			}
				
			else {
				try {
					m.backward();
				} 
				catch (RemoteException e) {
					e.printStackTrace();
				}
				Delay.msDelay(dauer);
				try {
					m.stop(false);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				Thread.currentThread().interrupt();
			}
		}	
	}
}
