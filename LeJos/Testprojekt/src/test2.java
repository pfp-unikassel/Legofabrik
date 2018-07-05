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

		RemoteEV3 ev3 = new RemoteEV3("192.168.0.109");

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

		System.out.println(rfid1.getSerialNo()); // eindeutige Erkennung
//		System.out.println(rfid2.getSerialNo()); // eindeutige Erkennung
//		System.out.println(rfid3.getSerialNo()); // eindeutige Erkennung
//		System.out.println(rfid4.getSerialNo()); // eindeutige Erkennung

		long id = 0;

		// Sensor soll nach einer erfolgreichen Lesung für 1 min stoppen zu
		// lesen
		while (true) {

			while (true) {

				id = rfid1.readTransponderAsLong(true);
				System.out.println(id);
				System.out.println("TEST");

			}
		}

	}

}
