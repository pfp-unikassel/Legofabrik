import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RMISampleProvider;
import lejos.remote.ev3.RemoteEV3;
import stations.Compressor;

public class compressorTest {

	
	static RemoteEV3 b113;
	
	static RMIRegulatedMotor b113a;
	static RMIRegulatedMotor b113b;
	static RMIRegulatedMotor b113c;
	static RMIRegulatedMotor b113d;
	
	static RMISampleProvider b1131 ;
	
	static float[] Sensorarray6 = new float[5];
	
	static Compressor compressor;
	
	public static void main(String[] args) {
	
		try {
			b113 = new RemoteEV3("192.168.0.113");
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		b113a = b113.createRegulatedMotor("A", 'L');
		b113b = b113.createRegulatedMotor("B", 'L');
		b113c = b113.createRegulatedMotor("C", 'L');
		b113d = b113.createRegulatedMotor("D", 'L');
	
		compressor = new Compressor(b113a,b113b,b113c,b113d);
		
		 b1131 = b113.createSampleProvider("S1", "lejos.hardware.sensor.EV3TouchSensor", null); // kompressor

	
	
		while(true){
		
			try {
				Sensorarray6 = b1131.fetchSample();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (Sensorarray6[0] == 1) {
				 compressor.pressureButtonfired(true);
				 Sensorarray6[0] = 0;
				 } else {
					 compressor.pressureButtonfired(false);
				 }
		}
	}
}
