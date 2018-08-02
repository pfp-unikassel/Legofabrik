import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.hardware.sensor.RFIDSensor;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;

public class Test5 {

	static RemoteEV3 b107;
	static RMIRegulatedMotor b107d;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			b107 = new RemoteEV3("192.168.0.107");
		} catch (RemoteException | MalformedURLException | NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		b107d = b107.createRegulatedMotor("D", 'L');
		
		RFIDSensor rfid1 = new RFIDSensor(b107.getPort("S1"));            // make sure RFID is in Port 1 in Brick 107
		
		
		try {
			b107d.setSpeed(160);
			b107d.forward();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		long[] filterArray = new long[15];
		int count = 0;
		long id = 0;

		while (true) {

			id = rfid1.readTransponderAsLong(true);

			System.out.println(id);

			if(id != 0){
				filterArray[count] = id;
				count++;
				if( count == 5){                // set number of values
					filterIds(filterArray);
					
					try {
						Thread.sleep(5000);                               // warte 5 sekunden nachdem 5 werte eingelesen wurden 
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					count= 0;
					for(int m = 0; m<5 ; m++){
						filterArray[m] = 0;
					}
					
				}
			}
			
		}

	}

	
	public static  long filterIds(long[] filterArray) { 

		long firstId = filterArray[0];
		int firstCounter = 0;
		long secondId = 0;
		int secondCounter = 0;
		
		for (int i = 0; i < 5; i++) { 

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
