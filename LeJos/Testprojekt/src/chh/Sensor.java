package chh;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;

import lejos.hardware.port.PortException;
import lejos.remote.ev3.RMISampleProvider;
import lejos.remote.ev3.RemoteEV3;

public class Sensor implements Runnable, Comparable <Sensor>{
	
	boolean stateChanged;
	boolean stopped = false;
	boolean running = false;
	
	String desc;
	String port;
	
	RMISampleProvider rmi;
	
	static final String SENSTR = "lejos.hardware.sensor.EV3TouchSensor";
	
	// property change
	private PropertyChangeSupport changes = new PropertyChangeSupport( this );
	
	public Sensor(String desc, String port) {
		this.desc = desc;
		this.port = port;
		rmi = null;
	}
	
	public void init(RemoteEV3 ev3) throws PortException {
		System.out.println("Initialisiere Sensor " + desc);
		rmi = ev3.createSampleProvider(port, SENSTR, null);
	}

	public void start() throws RemoteException {
		if (rmi != null) {
			System.out.println("Starte Sensor " + desc);
			running = true;
			float currentValue = 0f;
			while (!stopped) {
				currentValue = rmi.fetchSample()[0];
				if (currentValue != 0) {
					stateChanged = true;
					System.out.println("fired!");
					currentValue = 0;
				}
			}
			running = false;
			System.out.println("Stoppe Sensor " + desc);
		} else System.out.println("Sensor wurde nicht initialisiert."); // CustomException
	}
	
	public void stop() {
		stopped = true;
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public void shutdown() throws RemoteException {
		System.out.println("Fahre Sensor " + desc + " herunter.");
		stopped = true;
		if (rmi != null) {
			running = false;
			rmi.close();
		}
	}

	@Override
	public int compareTo(Sensor s) {
		// TODO Auto-generated method stub
		return port.compareTo(s.getPort());
	}

	private String getPort() {
		// TODO Auto-generated method stub
		return port;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (rmi != null) {
			System.out.println("Starte Sensor " + desc);
			running = true;
			float currentValue = 0f;
			while (!stopped) {
				try {
					currentValue = rmi.fetchSample()[0];
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (currentValue != 0) {
					stateChanged = true;
					fire();
					currentValue = 0;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			running = false;
			System.out.println("Stoppe Sensor " + desc);
		} else System.out.println("Sensor wurde nicht initialisiert."); // CustomException
	}
	
	public void fire() {
		changes.firePropertyChange(desc, 0, 1);
		System.out.println("fired!");
	}
	
	  public void addPropertyChangeListener( PropertyChangeListener l ) {
		  changes.addPropertyChangeListener( l );
	  }

	  public void removePropertyChangeListener( PropertyChangeListener l ) {
		  changes.removePropertyChangeListener( l );
	  }
	
}
