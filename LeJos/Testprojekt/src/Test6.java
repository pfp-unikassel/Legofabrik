import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.hardware.sensor.RFIDSensor;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RMISampleProvider;
import lejos.remote.ev3.RemoteEV3;
import stations.Chargier;

public class Test6 {

	static RemoteEV3 b106;
	static RemoteEV3 b105;

	static RMIRegulatedMotor b105a;
	static RMIRegulatedMotor b105b;
	static RMIRegulatedMotor b105c;
	static RMIRegulatedMotor b105d;

	static RMIRegulatedMotor b106a;
	static RMIRegulatedMotor b106b;
	static RMIRegulatedMotor b106c;
	static RMIRegulatedMotor b106d;

	static RMISampleProvider b1053;
	static RMISampleProvider b1054;

	static Chargier chargier;

	static float[] Sensorarray1 = new float[5];
	static float[] Sensorarray2 = new float[5];

	public static void main(String[] args) {

		// init

		try {
			b105 = new RemoteEV3("192.168.0.105");
			b106 = new RemoteEV3("192.168.0.106");
		} catch (RemoteException | MalformedURLException | NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		b105c = b105.createRegulatedMotor("C", 'L'); // Motor Drehtisch
		b105d = b105.createRegulatedMotor("D", 'L'); // Motor Räder Drehtisch

		b106a = b106.createRegulatedMotor("A", 'L'); // Laufband zum Drehtisch
		b106b = b106.createRegulatedMotor("B", 'L'); // Laufband vom Drehtisch
		b106d = b106.createRegulatedMotor("D", 'L'); // Laufband zur

		RFIDSensor rfid1 = new RFIDSensor(b105.getPort("S1")); // RFID SENSOR IN
																// PORT 1 Brick
																// 105

		b1053 = b105.createSampleProvider("S3", "lejos.hardware.sensor.EV3TouchSensor", null);
		b1054 = b105.createSampleProvider("S4", "lejos.hardware.sensor.EV3TouchSensor", null);

		chargier = new Chargier(b106a, b106d, b106b, b105d, b105c);

		// -----------------------------------------------------------
		// ausfuehrung

		new java.util.Timer().schedule(new java.util.TimerTask() {
			@Override
			public void run() {
				try {
					chargier.resetTable();
					chargier.startLineToTable(false);
					chargier.startTableLine(true);

					while (Sensorarray1[0] != 1) {
						System.out.println("hänge in schleife 1");
						Sensorarray1 = b1054.fetchSample();
					}

					chargier.stopLineToTable();
					chargier.stopTableLine();
					chargier.turnTable(660); // wartet bis drehung fertig ist

					chargier.startLineToLifter(false);
					chargier.startTableLine(false);

					while (Sensorarray2[0] != 1) {
						System.out.println("hänge in schleife 2");
						Sensorarray2 = b1053.fetchSample();
					}

					chargier.stopLineToLifter();
					chargier.stopTableLine();

					chargier.startLineToLifter(true);
					chargier.startTableLine(true);

					while (Sensorarray1[0] != 1) {
						System.out.println("hänge in schleife 3");
						Sensorarray1 = b1054.fetchSample();
					}

					chargier.stopLineToLifter();
					chargier.stopTableLine();

					chargier.turnTable(-1320);

					chargier.startLineToStore(false); // maybe falls
					chargier.startTableLine(false);

					Thread.sleep(3000);
					chargier.stopTableLine();
					chargier.stopLineToStorer();

					chargier.resetTable(); // turns 660 to much repair later

				} catch (RemoteException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, 1000);

		
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
