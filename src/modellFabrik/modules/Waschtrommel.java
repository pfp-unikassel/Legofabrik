package modellFabrik.modules;

import java.rmi.RemoteException;

import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.utility.Delay;
import modellFabrik.common.Kommunikation;

public class Waschtrommel implements Runnable{
	
	RMIRegulatedMotor m;
	RMIRegulatedMotor b; //Zuk�nftig Band zur Trommel in eigenem Modul implementieren inkl. 2 Ber�hrungssensoren.
	int t=30000; //30 Sekunden. Sp�ter mit Band koppeln. Kommen keine B�lle mehr nach 30 Sekunden aufh�ren.
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
		
		Delay.msDelay(3000); //Verz�gere Start des Bandes. 
		
		try {
			b.forward();
			Delay.msDelay(500); //Verz�gere Start der Waschtrommel. Sp�ter soll der Start der Waschtrommel mit Sensor am Band gesteuert werden. Band in eigenes Modul auslagern
			m.setSpeed(s);
			m.forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		Delay.msDelay(t);  //S.o. zuk�nftig mit keinen weiteren B�llen auf dem F�rderband und k�rzerer Verz�gerung koppeln
		
		Kommunikation.setBeginneWaschvorgang(false); 
		
	}
}

