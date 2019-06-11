package stations;

import java.rmi.RemoteException;

import controller.Steuerung;
import lejos.remote.ev3.RMIRegulatedMotor;

public class Compressor {

	private boolean status = true; // On = true -- off = false
	private boolean onPressure = false;
	RMIRegulatedMotor m1;
	RMIRegulatedMotor m2;
	RMIRegulatedMotor m3;
	RMIRegulatedMotor m4;

	private Steuerung s;

	public Compressor(Steuerung s, RMIRegulatedMotor m1, RMIRegulatedMotor m2, RMIRegulatedMotor m3,
			RMIRegulatedMotor m4) {

		this.s = s;
		this.m1 = m1;
		this.m2 = m2;
		this.m3 = m3;
		this.m4 = m4;

		try { // Set Compressor speed
			m1.setSpeed(800);
			m2.setSpeed(800);
			m3.setSpeed(800);
			m4.setSpeed(800);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void startCompressor() {

		if (status) {

			if (!getOnPressure()) {

				try {

					m1.forward(); // TODO:2 for 2 back
					m2.forward();
					m3.backward();
					m4.backward();

				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void stopCompressor() {

		try {
			m1.stop(true);
			m2.stop(true);
			m3.stop(true);
			m4.stop(true);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void pressureButtonfired(boolean button) {

		if (!button) { // wenn der Knopf nicht gedrueckt ist starte komp
			setOnPressure(false);
			startCompressor();

		} else {
			setOnPressure(true);
			stopCompressor();
		}

	}

	public boolean getOnPressure() {
		return onPressure;
	}

	public void setOnPressure(boolean onPressure) {
		this.onPressure = onPressure;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
		resetCompressor();
	}

	private void resetCompressor() {
		// TODO Auto-generated method stub
		if(status) {
			startCompressor();
		}else {
			stopCompressor();
		}
	}

}
