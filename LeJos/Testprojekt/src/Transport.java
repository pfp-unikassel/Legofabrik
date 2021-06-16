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
			
			
			RemoteEV3 b110 = new RemoteEV3 ("192.168.0.110");
			 
			 RMIRegulatedMotor linetoEnd = b110.createRegulatedMotor("A", 'M');
			 RMIRegulatedMotor gateB  = b110.createRegulatedMotor("B", 'M');
			 RMIRegulatedMotor gateC = b110.createRegulatedMotor("C", 'M');
			 RMIRegulatedMotor gateD = b110.createRegulatedMotor("D", 'M');
			
			RemoteEV3 b114 = new RemoteEV3 ("192.168.0.114");
			 
			 RMIRegulatedMotor mA = b114.createRegulatedMotor("A", 'L');  // foerderband
			 RMIRegulatedMotor mB  = b114.createRegulatedMotor("B", 'L'); //foerderband
			 RMIRegulatedMotor mC = b114.createRegulatedMotor("C", 'L');
			 RMIRegulatedMotor mD = b114.createRegulatedMotor("D", 'L');
			 

//			RMISampleProvider b1151 = b115.createSampleProvider("S1", "lejos.hardware.sensor.EV3UltrasonicSensor", null); 
			

			float[] Sensorarray1 = new float[5];

			 		 
		    //----------------------------------------------------------------------
			int rotateDegree = 360*18;     // foerderband
			int turnDegree = 45;;  // Tor Gate
			
			
			mA.setSpeed(500);
			mB.setSpeed(500);
			mC.setSpeed(360);
			mD.setSpeed(360);
			linetoEnd.setSpeed(350);  // set speed degree per second
			
				
//		gateD.rotate(turnDegree);
			linetoEnd.backward();
		
//			mC.forward();
//			mD.forward();  //mB.backward();
//			mA.rotate(rotateDegree,true);
//			mB.rotate(-rotateDegree,false);
			
//			mC.rotate(180,true);
//			mD.rotate(180);
			
			
			//-----------------------------------------------------------------------
//			int counter = 0;
//			while(true){
//				
//				Sensorarray1 = b1151.fetchSample();
//				
//				System.out.println("Ultraschall Value:  "+ Sensorarray1[0]*100);  //x 100 cm
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				counter++;
//				if(counter > 40){           // Dauert des Tests in s
//					break;
//				}
//				
//			}
			
//			try {
//				Thread.sleep(10000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			
	 linetoEnd.stop(false);
//		gateD.rotate(-turnDegree);
			   
			
//		  mA.stop(false);
//		  mB.stop(false);
		   
	 mC.rotate(-180,true);
		mD.rotate(-180);
			   
		 linetoEnd.close();
		 gateC.close();
		 gateB.close();
		 gateD.close();
		  mA.close();
		  mB.close();
		  mC.close();
		  mD.close();
//	     b1151.close();
			 System.out.println("fertig");
		}
	}


