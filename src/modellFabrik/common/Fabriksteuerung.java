package modellFabrik.common;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;
import lejos.utility.Delay;
import modellFabrik.modules.*;

public class Fabriksteuerung {
	
	/////////////////////////////////////////////////////////////////////////////////////
	//Statische Variablen////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////
	static Kommunikation kommunikation = new Kommunikation ();
	
	public static void main (String args []) throws RemoteException, MalformedURLException, NotBoundException, InterruptedException {
		

		/////////////////////////////////////////////////////////////////////////////////
		//Deklaration der Bricks, Sensoren und Motoren://////////////////////////////////
		//Bennenung der Bricks: bX, X=Nummer des Bricks//////////////////////////////////
		//Sensoren und Motoren: Portbezeichnung nach Bricknamen//////////////////////////
		//Beispiel: Brick b101; Sensor an 1: b1011; Motor an A: b101a////////////////////
		//Bei Portobjekten vor Portnummer "port" anh�ngen (z.B. b101port1)///////////////
		//Immer sofort! neue Ports unten wieder schlie�en////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////
		System.out.print("Initialisiere Hardware.");
			
		//Brick 101
		RemoteEV3 b101 = new RemoteEV3 ("192.168.0.101");
		RMIRegulatedMotor b101a =b101.createRegulatedMotor("A", 'M');
		RMIRegulatedMotor b101b =b101.createRegulatedMotor("B", 'M');
		RMIRegulatedMotor b101c =b101.createRegulatedMotor("C", 'M');
		RMIRegulatedMotor b101d =b101.createRegulatedMotor("D", 'M');
		
		//Brick 102
		RemoteEV3 b102 = new RemoteEV3 ("192.168.0.102");
		RMIRegulatedMotor b102a = b102.createRegulatedMotor("A", 'M');
		RMIRegulatedMotor b102b = b102.createRegulatedMotor("B", 'L');
		RMIRegulatedMotor b102c = b102.createRegulatedMotor("C", 'M');
		
		//Brick 104
		RemoteEV3 b104 = new RemoteEV3 ("192.168.0.104");
		RMIRegulatedMotor b104a = b104.createRegulatedMotor("A", 'L');	//Laufband zum Drehtisch
		RMIRegulatedMotor b104b = b104.createRegulatedMotor("B", 'L');	//Laufband vom Drehtisch
		RMIRegulatedMotor b104d = b104.createRegulatedMotor("D", 'L');	//Laufband zur Kippvorrichtung
		Port b104port1 = b104.getPort("S1");							//Sensor Ultraschall FTS-Erkennnung
		EV3UltrasonicSensor b1041 = new EV3UltrasonicSensor (b104port1);
		b1041.getDistanceMode();
		
		//Brick 105
		RemoteEV3 b105 = new RemoteEV3 ("192.168.0.105");
		RMIRegulatedMotor b105c = b105.createRegulatedMotor("C", 'L');	//Motor Drehtisch
		RMIRegulatedMotor b105d = b105.createRegulatedMotor("D", 'L');	//Motor R�der Drehtisch
		Port b105port3 = b105.getPort("S3");						
		EV3TouchSensor b1053 = new EV3TouchSensor (b105port3);	//Sensor F�rderband
		Port b105port4 = b105.getPort("S4");													
		EV3TouchSensor b1054 = new EV3TouchSensor (b105port4);	//Sensor Drehtisch
		
		//Brick 106
		RemoteEV3 b106 = new RemoteEV3 ("192.168.0.106");
		RMIRegulatedMotor b106b = b106.createRegulatedMotor("B", 'M');
		RMIRegulatedMotor b106c = b106.createRegulatedMotor("C", 'L');
		RMIRegulatedMotor b106d = b106.createRegulatedMotor("D", 'L');
		Port b106port2 = b106.getPort("S2");
		EV3TouchSensor b1062 = new EV3TouchSensor (b106port2); //Sensor Z�hler	
		
		//Brick 107
		/*RemoteEV3 b107 = new RemoteEV3 ("192.168.0.107");
		Port b107port1 = b107.getPort("S1");
		EV3ColorSensor b1071 = new EV3ColorSensor (b107port1);
		*/
		
		/////////////////////////////////////////////////////////////////////////////////
		//Eigenschaften der Sensoren/Aktoren ver�ndern///////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////
		System.out.print(".");
		
		/////////////////////////////////////////////////////////////////////////////////
		//Variablen f�r den Produktionsablauf////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////
		System.out.println(".");
		
		/////////////////////////////////////////////////////////////////////////////////
		//Deklaration Threads////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////
		Thread chargierStation = new Thread (new Chargierstation (b1041, b1054, b1053, b104a, b104d, b104b, b105d, b105c));
		Thread heben = new Thread (new Hebevorrichtung (b101a, b101b, b101c, b101d));
		Thread r�tteln = new Thread (new R�ttler (b102a));
		Thread waschen = new Thread (new Waschtrommel (b102b, b102c));
		Thread z�hlen = new Thread (new BandZ�hler (b106d, b1062));
		Thread kontrollieren = new Thread (new Qualit�tskontrolle (b106c, b106b));
		/*
		 * Thread f�rdernZurTrommel = new Thread (new BandZurTrommel (......)); DIESE KLASSE NOCH NICHT IMPLEMENTIEREN
		 */
		
		
		
		/////////////////////////////////////////////////////////////////////////////////
		//Ab hier folgt das Produktionsprogramm//////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////
		
		chargierStation.start();
		heben.start();
		r�tteln.start();
		waschen.start();
		z�hlen.start();
		kontrollieren.start();
		
		chargierStation.join();
		r�tteln.join();
		waschen.join();
		z�hlen.join();
		Delay.msDelay(30000);
		kontrollieren.interrupt();
		
		
		
		/////////////////////////////////////////////////////////////////////////////////
		//Ports Schlie�en////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////
		
		System.out.println("Schlie�e Ports");
		//Brick 101
		b101a.close();	b101b.close();	b101c.close();	b101d.close();
		//b1011.close();	b1012.close();	b1013.close();	b1014.close();
		
		//Brick 102
		b102a.close();	b102b.close();	b102c.close();	//b102d.close();
		//b1021.close();	b1022.close();	b1023.close();	b1024.close();
		
		//Brick 103
		//b103a.close();	b103b.close();	b103c.close();	b103d.close();
		//b1031.close();	b1032.close();	b1033.close();	b1034.close();
		
		//Brick 104
		b104a.close();	b104b.close();	b104d.close();	//b104c.close();
		b1041.close();	//b1042.close();	b1043.close();	b1044.close();
		
		//Brick 105
		b105c.close();	b105d.close();	//b105a.close();	b105b.close();	
		b1053.close();	b1054.close();	//b1051.close();	b1052.close();	
		
		//Brick 106
		b106d.close();	b106b.close();	b106c.close();	//b106a.close();	
		b1062.close();	//b1064.close();	b1061.close();		b1063.close();	
		
		//Brick 107
		//
		//b1071.close();
				
		System.out.println("Programm beendet.");
		
	}

}
