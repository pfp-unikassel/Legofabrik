package modellFabrik.modules;

import java.rmi.RemoteException;

import lejos.remote.ev3.RMIRegulatedMotor;

public class BandZurTrommel implements Runnable{
	
	RMIRegulatedMotor m;
	
	public BandZurTrommel (RMIRegulatedMotor m) {
		this.m=m;
	}
	
	@Override
	public void run () {
		
		
		try {
			m.forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
