package modellFabrik.modules;

import java.rmi.RemoteException;

import lejos.hardware.sensor.EV3TouchSensor;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.utility.Delay;
import modellFabrik.actions.Z�hlen;
import modellFabrik.common.Kommunikation;

public class BandZ�hler implements Runnable {
	
	RMIRegulatedMotor m;
	EV3TouchSensor s;
	float [] con;
	int v = 200; //Geschwindigkeit Band

	public BandZ�hler (RMIRegulatedMotor m, EV3TouchSensor s){
		this.m=m;
		this.s=s;
		con = new float [s.sampleSize()];
	}
	
	@Override
	public void run() {
		while (!Kommunikation.getBeginneWaschvorgang()) {
			//Warte dass Waschvorgang beginnt
		}
		
		Thread t = new Thread (new Z�hlen (s, con));
		t.start();
		
		Delay.msDelay(7000); //Warte 7 Sekunden
		
		try {
			m.setSpeed(v);
			m.backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		Delay.msDelay(30000); //Warte 30 Sekunden. Sp�ter dies mit irgendeinem Ereignis oder Bedingung verkn�pfen.
		t.interrupt(); //Beende Z�hler. 
		
	}

}
