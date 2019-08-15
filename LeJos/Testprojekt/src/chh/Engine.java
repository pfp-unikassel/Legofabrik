package chh;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.util.concurrent.Callable;

import lejos.hardware.port.PortException;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;

public class Engine implements Comparable<Engine>{

	String desc;
	String port;
	char type;
	
	RMIRegulatedMotor rmi;
	Sensor dependency;
	
	boolean stopped = false;
	
	boolean direction = true;
	

	
	public Engine(String d, String p, char t) {
		desc = d;
		port = p;
		type = t;
	}
	
	// Engine's szenario is dependent on a sensor
	public void addDependency(Sensor s) {
		dependency = s;
		s.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				// TODO Auto-generated method stub
				String str = evt.getPropertyName();
				if (dependency.desc.equals(str)) {
					System.out.println("Setze stopper für " + desc);
					stopped = true;
				}
			}
			
		});
	}
	
	public void startProcess() throws RemoteException {
		if (getDirection())
			rmi.forward();
		else
			rmi.backward();
	}
	
	public Callable<Boolean> callMe() {
		return new Callable<Boolean>() {

			@Override
			public Boolean call() throws Exception {
				
				/* idea: set a boolean to false. when all the critical parts are taking care of 
				and there is no exception set it to true. return the boolean in every case. */
				Boolean success = false;

				System.out.println("Starte Motor " + desc);
				rmi.setSpeed(300);
				startProcess();
				if (dependency != null)
					while(!stopped);
				rmi.stop(false);
				success = true;
				System.out.println("Stoppe Motor " + desc);

				return success;
			}
			
		};
	}
	
	public Runnable getSzenario() {
		return new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("Starte Motor " + desc);
				try {
					rmi.setSpeed(300);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					// throw new CollectingException("RemoteException", "Engine", "Setting Speed");
				}
				try {
					rmi.backward();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				while(!stopped) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					rmi.stop(false);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				
				
				System.out.println("Stoppe Motor " + desc);
			}
			
		};
	}
	
	public void init(RemoteEV3 ev3) throws PortException {
		System.out.println("Initialisiere Motor " + desc);
		rmi = ev3.createRegulatedMotor(port, type);
	}

	public String getPort() {
		// TODO Auto-generated method stub
		return port;
	}

	public char getType() {
		// TODO Auto-generated method stub
		return type;
	}
	
	public void szenario() throws RemoteException, InterruptedException {
		System.out.println("Starte Motor " + desc);
		rmi.setSpeed(350);
		rmi.forward();
		Thread.sleep(8000);
		rmi.stop(false);
		System.out.println("Stoppe Motor " + desc);
	}

	public void shutdown() throws RemoteException {
		// TODO Auto-generated method stub
		if (rmi != null)
			rmi.close();
		
	}

	public void setDirection(boolean b) {
		direction = b;
	}
	
	public boolean getDirection() {
		return direction;
	}
	
	@Override
	public int compareTo(Engine o) {
		// TODO Auto-generated method stub
		return port.compareTo(o.getPort());
	}
	
}
