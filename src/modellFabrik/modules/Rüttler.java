package modellFabrik.modules;

import java.rmi.RemoteException;

import lejos.remote.ev3.RMIRegulatedMotor;
import modellFabrik.common.Kommunikation;

public class Rüttler implements Runnable {
	
	RMIRegulatedMotor m;
	int t = 30000; //30 Sekunden
	
	public Rüttler (RMIRegulatedMotor m) {
		this.m=m;
	}
	
	@Override
	public void run () {
		
		while (!Kommunikation.getKisteGegriffen()){
			//Warte bis Kiste bereit zum Heben
		}
		
		Kommunikation.setBeginneWaschvorgang(true);
		
		try {
			m.setSpeed(700);
			m.forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
				
	}

}
