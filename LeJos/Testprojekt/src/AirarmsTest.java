import java.awt.List;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;

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
							new Motor("A", 'M'),
							new Motor("B", 'M'),
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
					System.out.println(m);
					System.out.println("Rotiere hin");
					m.rmi.rotateTo(300, false); // hier dreht er weniger weit als in die andere richtung
					System.out.println(m);
					Thread.sleep(2000); 
					System.out.println("Rotiere zur�ck");
					m.rmi.rotateTo(0, false); // das hier dreht weiter zur�ck. motor knattert.
					System.out.println(m);
				break;

				case "B": // Anheben (positiver Winkel) und Absenken (negativer Winkel)
					// Am Besten ausgefahren lassen f�r den Test der anderen Motoren
					System.out.println(m);
					System.out.println("Rotiere hin");
					m.rmi.rotateTo(-200, false);
					Thread.sleep(2000); 
					System.out.println("Rotiere zur�ck");
					m.rmi.rotateTo(0, false);
					// Mit den Werten funktioniert anheben und absenken ohne �berdrehen / knacken
				break;
				
				case "C":
					
					System.out.println(m);
					System.out.println("Rotiere hin");
					m.rmi.rotateTo(-150, false);
					Thread.sleep(2000); 
					System.out.println("Rotiere zur�ck");
					m.rmi.rotateTo(100, false); // hier f�hrt er mit 0 als winkel nicht mehr in den urspr�nglichen zustand zur�ck
				break;
				
				case "D":
					System.out.println(m);
					System.out.println("Rotiere hin");
					m.rmi.rotateTo(150, false);
					Thread.sleep(2000); 
					System.out.println("Rotiere zur�ck");
					m.rmi.rotateTo(0, false);
				break;
				
				default: break;
				}
			}			 
			 
			for (Motor m : motorList) {
				System.out.println(m);
				m.rmi.close();				
			}
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