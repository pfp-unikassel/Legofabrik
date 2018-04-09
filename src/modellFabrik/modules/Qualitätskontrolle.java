package modellFabrik.modules;

import java.rmi.RemoteException;

import lejos.hardware.sensor.EV3ColorSensor;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.utility.Delay;
import modellFabrik.common.Kommunikation;

public class Qualitätskontrolle implements Runnable {

	RMIRegulatedMotor band;
	RMIRegulatedMotor schranke;
	//EV3ColorSensor s;
	//float farbe [];	
	
	public Qualitätskontrolle(RMIRegulatedMotor band, RMIRegulatedMotor schranke) {
		super();
		this.band = band;
		this.schranke = schranke;
	}
	
	public Qualitätskontrolle(RMIRegulatedMotor band, RMIRegulatedMotor schranke, EV3ColorSensor s) {
		super();
		this.band = band;
		this.schranke = schranke;
		//this.s = s;
		//farbe = new float [s.sampleSize()];
	}



	@Override
	public void run() {
		
		while (Kommunikation.getCounter()<1){
			//Warte bis wenigstens ein Ball da ist.
		}
		
		Delay.msDelay(500); //Pause..
		
		int [] produktion = new int [2];
		produktion[0]=produktion[1]=0;
		
		while(!Thread.currentThread().isInterrupted()) { 
			
			boolean ausschuss = false;
			
			Delay.msDelay(200);
		
			//s.fetchSample(farbe, 0);
			//System.out.println("<QKontrolle> Lese: "+farbe[0]);
			
			Delay.msDelay(500);
			
			/*if (farbe[0]==6){
				try {
					schranke.rotate(40);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ausschuss=true;
				System.out.println("<QKontrolle> Ausschuss erkannt");
				System.out.println("<QKontrolle> Schranke fährt auf.");
			}
			*/
			
			//Förderband drehen 
			try {
				band.rotate(144);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("Warte...");
			Delay.msDelay(200);
			
			if (ausschuss==true){
				try {
					schranke.rotate(-40);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("<QKontrolle> Schranke fährt zurück");
				produktion[1]++;
			}
			
			else {
				produktion[0]++;
			}
			
		}
		
		System.out.println("<QKontrolle> Es wurden "+produktion[0]+" i.O und "+produktion[1]+" n.i.O Teile produziert.");
			
		
		System.out.println("<QKontrolle> Anlage gestoppt.");
		

	}

}
