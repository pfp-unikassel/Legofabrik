package modellFabrik.modules;

import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.utility.Delay;
import modellFabrik.actions.*;
import modellFabrik.common.*;

public class Hebevorrichtung implements Runnable {
	
	RMIRegulatedMotor hebenLinks;
	RMIRegulatedMotor hebenRechts;
	RMIRegulatedMotor greifenLinks;
	RMIRegulatedMotor greifenRechts;
	int winkelGreifen = 280;
	int winkelHeben = 3400;
	int hebenGeschwindigkeit=740;
		
	public Hebevorrichtung(	RMIRegulatedMotor greifenLinks,
			RMIRegulatedMotor greifenRechts,
			RMIRegulatedMotor hebenLinks, 
			RMIRegulatedMotor hebenRechts) {
		this.greifenLinks=greifenLinks;
		this.greifenRechts=greifenRechts;
		this.hebenLinks=hebenLinks;
		this.hebenRechts=hebenRechts;
	}
	
	@Override
	public void run () {
		
		System.out.println("Warte auf Kistenfreigabe");
		
		while(Kommunikation.getKisteBereit()==false){
			//Warte bis Kiste von Chargierstation bereitgestellt wird.
		}
		System.out.println("Kiste ist bereit zum heben");
		
		System.out.println("Fahre Greifer aus");
		Delay.msDelay(1000);
		Thread greifeLinks = new Thread (new MotorRotieren (greifenLinks, winkelGreifen));
		Thread greifeRechts = new Thread (new MotorRotieren (greifenRechts, -winkelGreifen));
		
		greifeLinks.start();
		greifeRechts.start();
		try {
			greifeLinks.join();
			greifeRechts.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Delay.msDelay(500);
		System.out.println("Greifer ausgefahren");
		
		Kommunikation.setKisteGegriffen(true);
		
		Delay.msDelay(500);
		System.out.println("Kiste Heben");
		Thread hebeLinks = new Thread (new MotorRotieren (hebenLinks, winkelHeben, hebenGeschwindigkeit));
		Thread hebeRechts = new Thread (new MotorRotieren (hebenRechts, winkelHeben, hebenGeschwindigkeit));
		
		hebeLinks.start();
		hebeRechts.start();
		
		try {
			hebeLinks.join();
			hebeRechts.join();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("Kiste leeren");
		Delay.msDelay(1000);
		
		System.out.println("Kiste senken");
		hebeLinks = new Thread (new MotorRotieren (hebenLinks, -winkelHeben, hebenGeschwindigkeit));
		hebeRechts = new Thread (new MotorRotieren (hebenRechts, -winkelHeben, hebenGeschwindigkeit));
		
		hebeLinks.start();
		hebeRechts.start();
		
		try {
			hebeLinks.join();
			hebeRechts.join();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Delay.msDelay(2500);
		System.out.println("Greifer einfahren");
		
		greifeLinks = new Thread (new MotorRotieren (greifenLinks, -winkelGreifen));
		greifeRechts = new Thread (new MotorRotieren (greifenRechts, winkelGreifen));
		
		greifeLinks.start();
		greifeRechts.start();
		try {
			greifeLinks.join();
			greifeRechts.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Kommunikation.setKisteGegriffen(false);
		Kommunikation.setKisteIstLeergut(true);
		
		System.out.println("Kiste bereit zum Abführen");
		
		
	}

}
