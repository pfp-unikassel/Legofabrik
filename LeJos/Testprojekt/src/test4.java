import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RMIRemoteSampleProvider;
import lejos.remote.ev3.RMISampleProvider;
import lejos.remote.ev3.RemoteEV3;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;

public class test4 {

	static RemoteEV3 b107;
	static RemoteEV3 b110;

	public static void main(String[] Args) throws RemoteException {

		try {

			b110 = new RemoteEV3("192.168.0.110"); // TODO change to new brick
			b107 = new RemoteEV3("192.168.0.107"); // TODO change to new brick

		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("B1 not Found");

		}

		RMISampleProvider touch1 = b107.createSampleProvider("S1", "lejos.hardware.sensor.EV3TouchSensor", null);

		RMISampleProvider touch2 = b107.createSampleProvider("S2", "lejos.hardware.sensor.EV3TouchSensor", null);

		RMISampleProvider touch3 = b107.createSampleProvider("S3", "lejos.hardware.sensor.EV3TouchSensor", null);

		RMISampleProvider touch4 = b107.createSampleProvider("S4", "lejos.hardware.sensor.EV3TouchSensor", null);

		RMISampleProvider touch5 = b110.createSampleProvider("S1", "lejos.hardware.sensor.EV3TouchSensor", null);

		float[] Sensorarray1;
		float[] Sensorarray2;
		float[] Sensorarray3;
		float[] Sensorarray4;
		float[] Sensorarray5;

		// SensorMode color = b1073.getColorIDMode();
		// float[] Sensorarray5 = new float[color.sampleSize()];
		//
		// while (true) {
		// color.fetchSample(Sensorarray5, 0);
		//
		// if (Sensorarray5[0] != -1) {
		// System.out.println("Farbsensor erkennt farbe");
		// int coloIndex = (int) Sensorarray5[0];
		// String colorString = "";
		// switch (coloIndex) {
		//
		// case Color.BLACK:
		// colorString = "BLACK";
		// break;
		// case Color.BLUE:
		// colorString = "BLUE";
		// break;
		// case Color.GREEN:
		// colorString = "GREEN";
		// break;
		// case Color.YELLOW:
		// colorString = "YELLOW";
		// break;
		// case Color.RED:
		// colorString = "RED";
		// break;
		// case Color.WHITE:
		// colorString = "WHITE";
		// break;
		// case Color.BROWN:
		// colorString = "BROWN";
		// break;
		// }
		// System.out.println(colorString);
		//
		// }
		// }

		while (true) {

			Sensorarray1 = touch1.fetchSample();
			Sensorarray2 = touch2.fetchSample();
			Sensorarray3 = touch3.fetchSample();
			Sensorarray4 = touch4.fetchSample();
			Sensorarray5 = touch5.fetchSample();
			
			if(Sensorarray1[0] == 1) {
				System.out.println("1 fired");
			}
			if(Sensorarray2[0] == 1) {
				System.out.println("1 fired");
			}
			if(Sensorarray3[0] == 1) {
				System.out.println("1 fired");
			}
			if(Sensorarray4[0] == 1) {
				System.out.println("1 fired");
			}
			if(Sensorarray5[0] == 1) {
				System.out.println("1 fired");
			}
			
		}

	}

}
