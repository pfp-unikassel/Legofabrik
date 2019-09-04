import java.awt.List;
import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;

import lejos.hardware.DeviceException;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;
import stations.Compressor;

public class AirarmsTest {
	
	private static class Motor {
		
		public String port;
		public char type;
		public RMIRegulatedMotor rmi;

		public Motor(String port, char type) {
			this.port = port;
			this.type = type;
		}
		
		public void createMotor(RemoteEV3 ev3) {
			rmi = ev3.createRegulatedMotor(this.port, this.type);
		}
		
		public String toString() {
			String result = this.port;
			
			try {
				result = result + ": " + rmi.getTachoCount() + " " + rmi.getLimitAngle();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RemoteEV3 b106;
		
		try {
			b106 = new RemoteEV3 ("192.168.0.106");
			
			ArrayList<Motor> motorList = new ArrayList<>(
					Arrays.asList(
							/* new Motor("A", 'M'),
							new Motor("B", 'M'), */
							new Motor("C", 'M'),
							new Motor("D", 'M')
					)		 
			);
			
			for (Motor m : motorList) {
				m.createMotor(b106);
				System.out.println(m);
			}

			System.out.println();
			 
			// TESTEN
			for (Motor m: motorList) {
				switch(m.port) {
				case "A": // Greifer ausfahren (positiver Winkel) und Einfahren (negativer Winkel)
					// Manuale Steuerung: Rückwärts -> einfahren; vorwärts -> ausfahren
					System.out.println(m);
					System.out.println("Rotiere hin");
					m.rmi.rotateTo(570, false); // hier dreht er weniger weit als in die andere richtung
					System.out.println(m);
					Thread.sleep(2000); 
					System.out.println("Rotiere zurück");
					m.rmi.rotateTo(0, false); // das hier dreht weiter zurück. motor knattert.
					System.out.println(m);
				break;

				case "B": // Anheben (positiver Winkel) und Absenken (negativer Winkel)
					// Am Besten ausgefahren lassen für den Test der anderen Motoren
					// Manuale Steuerung: B rückwärts -> runter ; vorwärts -> hoch
					System.out.println(m);
					System.out.println("Rotiere hin");
					m.rmi.rotateTo(-600, false);
					Thread.sleep(2000); 
					System.out.println("Rotiere zurück");
					m.rmi.rotateTo(0, false);
					// Mit den Werten funktioniert anheben und absenken ohne überdrehen / knacken
				break;
				
				case "C":
					// Manuele Steuerung: Drehung von einem Greifarm
					// Vorwärts -> parallel zu Förderband Rückwärts -> ...
					// Ausgangslage sie stehen parallel, Schalter ist rechts
					System.out.println(m);
					System.out.println("Rotiere hin");
					 m.rmi.rotateTo(-650, false);
					//m.rmi.rotateTo(-50, false);
					Thread.sleep(2000); 
					System.out.println("Rotiere zurück");
					m.rmi.rotateTo(100, false); // hier fährt er mit 0 als winkel nicht mehr in den ursprünglichen zustand zurück
					 m.rmi.rotateTo(-650, false);
					//m.rmi.rotateTo(-50, false);
					Thread.sleep(2000); 
					System.out.println("Rotiere zurück");
					m.rmi.rotateTo(100, false); // hier fährt er mit 0 als winkel nicht mehr in den ursprünglichen zustand zurück
				break;
				
				case "D":
					// Greifarme auf- und zumachen
					// Manuele Steuerung: Vorwärts -> schließen; Rückwärts -> öffnen
					System.out.println(m);
					System.out.println("Rotiere hin");
					m.rmi.rotateTo(650, false);
					// m.rmi.rotateTo(50, false);
					Thread.sleep(2000); 
					System.out.println("Rotiere zurück");
					m.rmi.rotateTo(-150, false);
					m.rmi.rotateTo(650, false);
					// m.rmi.rotateTo(50, false);
					Thread.sleep(2000); 
					System.out.println("Rotiere zurück");
					m.rmi.rotateTo(-150, false);
				break;
				
				default: break;
				}
			}			 
			 
			for (Motor m : motorList) {
				System.out.println(m);
				m.rmi.close();				
			}
		} catch (DeviceException e) {
			 // Brick ist in Verwendung von anderem Programm
			e.printStackTrace();
		} catch (ConnectException e) {
			// Brick ist aus oder nicht im Netzwerk
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 

	}

	public static void startCompressor() {
		
		// Compressor c = new Compressor();
	}
	
}
