package modellFabrik.modules;

import java.rmi.RemoteException;

import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.utility.Delay;
import modellFabrik.common.Kommunikation;

public class Waschtrommel implements Runnable{
	
	RMIRegulatedMotor m;
	RMIRegulatedMotor b; //Zukünftig Band zur Trommel in eigenem Modul implementieren inkl. 2 Berührungssensoren.
	int t=30000; //30 Sekunden. Später mit Band koppeln. Kommen keine Bälle mehr nach 30 Sekunden aufhören.
	int s=300; //Motorgeschwindigkeit Trommel
	
	public Waschtrommel (RMIRegulatedMotor m, RMIRegulatedMotor b) {
		this.m=m;
		this.b=b;
	}
	
	@Override
	public void run () {
		
		while (!Kommunikation.getBeginneWaschvorgang()){
			//Warte auf Beginn Waschvorgang
		}
		
		Delay.msDelay(3000); //Verzögere Start des Bandes. 
		
		try {
			b.forward();
			Delay.msDelay(500); //Verzögere Start der Waschtrommel. Später soll der Start der Waschtrommel mit Sensor am Band gesteuert werden. Band in eigenes Modul auslagern
			m.setSpeed(s);
			m.forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		Delay.msDelay(t);  //S.o. zukünftig mit keinen weiteren Bällen auf dem Förderband und kürzerer Verzögerung koppeln
		
		Kommunikation.setBeginneWaschvorgang(false); 
		
	}
}

