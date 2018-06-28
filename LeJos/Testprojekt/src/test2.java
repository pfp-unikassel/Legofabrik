import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.RFIDSensor;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RMISampleProvider;
import lejos.remote.ev3.RemoteEV3;
import lejos.utility.Delay;

public class test2 {

	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		
		RemoteEV3 ev3 = new RemoteEV3 ("192.168.0.109");
		
		RFIDSensor rfid = new RFIDSensor(ev3.getPort("S1"));
		rfid.wakeUp();
		rfid.startFirmware();
	    
	    System.out.println(rfid.getProductID());
		System.out.println(rfid.getVendorID());
		System.out.println(rfid.getVersion());
		
	  long   id = rfid.readTransponderAsLong(true);

	  System.out.println(id);
		
//		Port p = ev3.getPort("S1");
//		
//		RFIDSensor rfidSensor = new RFIDSensor(p);
		
//		rfidSensor.
		
		
//		RMISampleProvider rfid = ev3.createSampleProvider("S1", "lejos.hardware.sensor.RFIDSensor", null);
//		float[] Sensorarray1;
		
		
		while(true){
			
			
//			value = rfidSensor.readTransponderAsLong(true);
//			System.out.println("value: " + value); 
			
//			Sensorarray1 = rfid.fetchSample();
			
		
		}
	}

}
