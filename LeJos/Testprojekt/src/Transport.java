	import java.net.MalformedURLException;
	import java.rmi.NotBoundException;
	import java.rmi.RemoteException;

	import lejos.hardware.port.Port;
	import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RMISampleProvider;
import lejos.remote.ev3.RemoteEV3;
	import lejos.utility.Delay;
	import stations.Airarms;

	public class Transport {
		public static void main (String args []) throws RemoteException, MalformedURLException, NotBoundException{
			
			RemoteEV3 b115 = new RemoteEV3 ("192.168.0.115");
			 
			 RMIRegulatedMotor mA = b115.createRegulatedMotor("A", 'L');  // foerderband
			 RMIRegulatedMotor mB  = b115.createRegulatedMotor("B", 'L'); //foerderband
			 RMIRegulatedMotor mC = b115.createRegulatedMotor("C", 'L');
			 RMIRegulatedMotor mD = b115.createRegulatedMotor("D", 'L');
			 
////			RMISampleProvider b1151 = b115.createSampleProvider("S4", "lejos.hardware.sensor.EV3TouchSensor", null); 
//			RMISampleProvider b1152 = b115.createSampleProvider("S4", "lejos.hardware.sensor.EV3TouchSensor", null); 
////			RMISampleProvider b1153 = b115.createSampleProvider("S4", "lejos.hardware.sensor.EV3UltrasonicSensor", 0); 
//			
//			//EV3UltrasonicSensor distance = new EV3UltrasonicSensor();
//			
//			float[] Sensorarray1 = new float[5];
//			float[] Sensorarray2 = new float[5];
			 		 
		    //----------------------------------------------------------------------
			int rotateDegree = 360*7;
			
			mA.setSpeed(360);
			mB.setSpeed(360);
			mC.setSpeed(360);
			mD.setSpeed(360);
			
			mA.forward();
			mB.forward();  //mB.backward();
			
			mC.rotate(rotateDegree);
			mD.rotate(rotateDegree);
			
			
			//-----------------------------------------------------------------------
		   try {
			   
			Thread.sleep(15000);    // zeit in milli
			// Time to wait
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		  mA.stop(false);
		  mB.stop(false);
		   
		  mA.close();
		  mB.close();
		  mC.close();
		  mD.close();
		
			 System.out.println("fertig");
		}
	}


