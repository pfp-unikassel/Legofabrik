package stations;

import java.rmi.RemoteException;

import lejos.remote.ev3.RMIRegulatedMotor;

public class Compressor {

	private boolean onPressure = false;
	RMIRegulatedMotor m1;
	RMIRegulatedMotor m2;
	RMIRegulatedMotor m3;
	RMIRegulatedMotor m4;

	public Compressor(RMIRegulatedMotor m1, RMIRegulatedMotor m2, RMIRegulatedMotor m3, RMIRegulatedMotor m4) {
		this.m1 = m1;
		this.m2 = m2;
		this.m3 = m3;
		this.m4 = m4;

	}

	public void startCompressor() {

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

	public void stopCompressor() {

		try {
			m1.stop(false);
			m2.stop(false);
			m3.stop(false);
			m4.stop(false);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void pressureButtonfired(boolean button) {
		
		if(!button ) {				// wenn der Knopf nicht gedrueckt ist starte komp
			setOnPressure(false);
			startCompressor();
			
		}else {
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

}
