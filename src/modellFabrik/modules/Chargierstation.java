package modellFabrik.modules;

import java.rmi.RemoteException;

import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;
import lejos.utility.Delay;
import modellFabrik.common.*;

public class Chargierstation implements Runnable{

	EV3UltrasonicSensor schranke;
	EV3TouchSensor touch;
	EV3TouchSensor touchEnde;
	RMIRegulatedMotor antriebBandZumDT;
	RMIRegulatedMotor antriebBandProd;
	RMIRegulatedMotor antriebBandLeergut;
	RMIRegulatedMotor antriebDrehtisch;
	RMIRegulatedMotor drehtischRotieren;
	
	public Chargierstation (EV3UltrasonicSensor schranke, 
			EV3TouchSensor touch, 
			EV3TouchSensor touchEnde,
			RMIRegulatedMotor antriebBandZumDT, 
			RMIRegulatedMotor antriebBandProd,
			RMIRegulatedMotor antriebBandLeergut, 
			RMIRegulatedMotor antriebDrehtisch, 
			RMIRegulatedMotor drehtischRotieren){
		this.schranke=schranke;
		this.touch=touch;
		this.touchEnde=touchEnde;
		this.antriebBandZumDT=antriebBandZumDT; 
		this.antriebBandProd=antriebBandProd;
		this.antriebBandLeergut=antriebBandLeergut; 
		this.antriebDrehtisch=antriebDrehtisch;
		this.drehtischRotieren=drehtischRotieren;
	}
	
	public void run () {
		int drehwinkel = 660;
		
		System.out.println("Warte auf Fahrzeug");
		
		SampleProvider distance = schranke.getDistanceMode();
    	SampleProvider average = new MeanFilter (distance, 5);
    	float [] sampleSchranke = new float [average.sampleSize()];
    	average.fetchSample(sampleSchranke, 0);
    	
    	float abstand = 0.1f;
    	while (abstand < sampleSchranke [0]){
    		average.fetchSample(sampleSchranke, 0);
    	}
    	Delay.msDelay(1000);
    	System.out.println("Fahrzeug in Position");
    	
    	try {
			antriebDrehtisch.forward();
			antriebBandZumDT.backward();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	System.out.println("Hole Kiste");
    	
    	float [] sensorDrehtisch = new float [touch.sampleSize()];
    	while (sensorDrehtisch[0]!=1) {
    		touch.fetchSample(sensorDrehtisch, 0);
    	}
    	try {
			antriebDrehtisch.stop(false);
			antriebBandZumDT.stop(false);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
    	System.out.println("Kiste auf Tisch");
    	
    	Delay.msDelay(500);
    	System.out.println("Drehe Drehtisch");
    	try {
			drehtischRotieren.rotate(-drehwinkel);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println("Tisch in Position");
    	
    	Delay.msDelay(500);
    	System.out.println("Fahre Kiste");
    	try {
			antriebDrehtisch.backward();
			antriebBandProd.backward();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	float [] sensorEnde = new float [touchEnde.sampleSize()];
    	while (sensorEnde[0]!=1) {
    		touchEnde.fetchSample(sensorEnde, 0);
    	}
    	
    	try {
			antriebDrehtisch.stop(false);
			antriebBandProd.stop(false);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	Kommunikation.setKisteIstLeergut(false); //Variable resetten
    	
    	
    	System.out.println("Kiste bereit zum Heben");
    	Kommunikation.setKisteBereit(true);
    	Delay.msDelay(1000);
    	
    	while (!Kommunikation.getKisteIstLeergut()){
    		//Warte solange Kiste von Hebevorrichtung freigegeben wird.
    	}
    	
    	Kommunikation.setKisteBereit(false);
    	
    	
    	System.out.println("Kiste zurückfahren");
    	
    	try {
			antriebDrehtisch.forward();
			antriebBandProd.forward();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	sensorDrehtisch[0] = 0;
    	while (sensorDrehtisch[0]!=1) {
    		touch.fetchSample(sensorDrehtisch, 0);
    	}
    	
    	try {
			antriebDrehtisch.stop(false);
			antriebBandProd.stop(false);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	
    	
    	System.out.println("Kiste in Pos");
    	
    	Delay.msDelay(500);
    	System.out.println("Drehe Drehtisch");
    	try {
			drehtischRotieren.rotate(2*drehwinkel);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	System.out.println("Bereit zum Abführen");
    	Delay.msDelay(500);
    	System.out.println("Leergut wird abgeführt");
    	
    	try {
			antriebDrehtisch.backward();
			antriebBandLeergut.backward();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	Delay.msDelay(1500);
    	System.out.println("Leergut abgeführt");
    	
    	try {
			antriebDrehtisch.stop(false);
			antriebBandLeergut.stop(false);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
 
    	Delay.msDelay(500);
    	System.out.println("Fahre Tisch in ausgangsposition.");
    	try {
			drehtischRotieren.rotate(-drehwinkel);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   	}
	
	
	
}
