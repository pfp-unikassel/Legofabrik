import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.hardware.sensor.RFIDSensor;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;
import stations.Deliverylane;

public class Rfidreadexample {

	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {

		RemoteEV3 ev3 = new RemoteEV3("192.168.0.109");
		
		RemoteEV3 b116 = new RemoteEV3("192.168.0.116");
		
		RMIRegulatedMotor	b116a = b116.createRegulatedMotor("A", 'M');
		RMIRegulatedMotor	b116b = b116.createRegulatedMotor("B", 'M');
		RMIRegulatedMotor b116c = b116.createRegulatedMotor("C", 'M');
		RMIRegulatedMotor b116d = b116.createRegulatedMotor("D", 'M');
		
		b116a.setSpeed(90);
		b116a.backward();
		
		Deliverylane deliverylane;
		// Positionen der Sensoren
		// rfid1 = Anlieferung Kisten Serial.No: [B@421faab1
		RFIDSensor rfid1 = new RFIDSensor(ev3.getPort("S1"));
//		RFIDSensor rfid2 = new RFIDSensor(ev3.getPort("S2"));
//		RFIDSensor rfid3 = new RFIDSensor(ev3.getPort("S3"));
//		RFIDSensor rfid4 = new RFIDSensor(ev3.getPort("S4"));

		// rfid1.wakeUp();
		// rfid1.startFirmware();

		// [B@6e5e91e4 2 und 3 tausch
		// [B@2cdf8d8a
		// [B@30946e09
		// [B@5cb0d902
//
//		System.out.println(rfid1.getSerialNo()); // eindeutige Erkennung
//		System.out.println(rfid2.getSerialNo()); // eindeutige Erkennung
//		System.out.println(rfid3.getSerialNo()); // eindeutige Erkennung
//		System.out.println(rfid4.getSerialNo()); // eindeutige Erkennung

		long[] filterArray = new long[15];
		int count = 0;
		long id = 0;

		rfid1.startContinuousRead();


			while (true) {

				id = rfid1.readTransponderAsLong(true);
//				if((int)id != 0){
					System.out.println(id);
					
//				}
				

				if(id != 0){
					filterArray[count] = id;
					count++;
					if( count == 5){
						filterIds(filterArray);
						count= 0;
						
						for(int m = 0; m<5 ; m++){
							filterArray[m] = 0;
						}
					}
				}
				

			}
		

	}

	
	
	public static  long filterIds(long[] filterArray) { // TODO: Make sure there are no 0 coming, there is not allowed to have morem the 2 differten errors at the beginning

		long firstId = filterArray[0];
		int firstCounter = 0;
		long secondId = 0;
		int secondCounter = 0;
		
		for (int i = 0; i < 5; i++) { //müsste es hier nicht bei 1 beginnen?

			if(filterArray[i] != 0) {
			if (firstId == filterArray[i]) {
				firstCounter++;
			} else { 											// wenn id unleich 1. Id
				 secondId = filterArray[i];					// setze 2 id gleich diese Id
				if (secondId == filterArray[i]) {				
					secondCounter++;
				} 

			}
		}
		}
		
		if(firstCounter > secondCounter) {
			System.out.println("gefiltert : " +firstId);

			return firstId;
		}else {
			System.out.println("gefiltert : " + secondId);
			return secondId;
		}
	}
	
}
