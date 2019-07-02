import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import lejos.hardware.port.Port;
import lejos.hardware.port.PortException;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RMISampleProvider;
import lejos.remote.ev3.RemoteEV3;

import controller.Steuerung;
import stations.Transport;

public class ChristophsTest {
	
	ArrayList<String> testArr = new ArrayList();
	
	private static class MyThread extends Thread {
		float oldValue;
		String name;
		boolean stop = false;
		RMISampleProvider sensor;
		
		private PropertyChangeSupport changes = new PropertyChangeSupport( this );
		
		public MyThread(String name, RMISampleProvider rmi) {
			this.name = name;
			this.sensor = rmi;
		}
		
		public void stopMe() {
			stop = true;
		}
		
		public void fire(float f) {
			if (oldValue != 1)
				changes.firePropertyChange(name, oldValue, f);
			oldValue = f;
		}
		
		@Override
		public void run() {
			while(!stop) {
				try {
					float reiz = sensor.fetchSample()[0];
					if (reiz != oldValue)
						fire(reiz);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					System.out.println("Interrupted: closing ports");
					try {
						sensor.close();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			System.out.println("Closing ports");
			try {
				sensor.close();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		  public void addPropertyChangeListener( PropertyChangeListener l )
		  {
		    changes.addPropertyChangeListener( l );
		  }

		  public void removePropertyChangeListener( PropertyChangeListener l )
		  {
		    changes.removePropertyChangeListener( l );
		  }
		
	}
	
	private static class MyCallable<T> implements Callable<T> {

		public boolean stopped = false;
		
		@Override
		public T call() throws Exception {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	private static class MyRunnable implements Runnable {
		
		RMIRegulatedMotor line;
		
		public MyRunnable(RMIRegulatedMotor line) {
			this.line = line;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("TachoStand");
			try {
				System.out.println(line.getTachoCount());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void ChristophsTest () {
		testArr.add("Hans");
		testArr.add("Franz");
	}
	
	public ArrayList<String> getA() {
		return testArr;
	}
	
	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		//ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
		//ses.shutdown();
		
		RemoteEV3 b105 = new RemoteEV3("192.168.0.102");
		System.out.println(b105.getName());
		
		RMISampleProvider b1054 = b105.createSampleProvider("S4", "lejos.hardware.sensor.EV3TouchSensor", null);
		
		ExecutorService executor = Executors.newCachedThreadPool();
		
		MyThread thr = new MyThread("sensor", b1054);
		thr.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				// TODO Auto-generated method stub
				String s = evt.getPropertyName();
				if ("sensor".equals(s)) {
					System.out.println("oldValue: " + evt.getOldValue() + " newValue: " + evt.getNewValue());
				}
			}
			
		});
		
		executor.submit(thr);
		
		boolean stop = false;
		int counter = 0;
		while (!stop) {
			try { Thread.sleep(1);
			counter++;;
			if (counter > 10000)
				stop = true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			b1054.close();
		}
		}
		thr.stopMe();
		Port p;
		try {
		p =  b105.getPort("S4");
		if (p == null)
			System.out.println("p ist null");
		else
			System.out.println("p ist nicht null");
		b1054.close();
		p = b105.getPort("S4");
		if (p == null)
			System.out.println("p ist null");
		else
			System.out.println("p ist nicht null");
		} catch (IllegalArgumentException e) {
			b1054.close();
		}
		executor.shutdown();
		/*
		ChristophsTest c = new ChristophsTest();
		for (String s : c.testArr)
			System.out.println(s);
		ArrayList<String> test = c.getA();
		test.clear();
		System.out.println("After copying");
		for (String s : c.testArr)
			System.out.println(s);
		
		RemoteEV3 b115 = new RemoteEV3 ("192.168.0.115");
		
		 RMIRegulatedMotor mA = b115.createRegulatedMotor("A", 'L');  // foerderband
		 RMIRegulatedMotor mB  = b115.createRegulatedMotor("B", 'L'); //foerderband
		 RMIRegulatedMotor mC = b115.createRegulatedMotor("C", 'L');
		 RMIRegulatedMotor mD = b115.createRegulatedMotor("D", 'L');
		
		Transport t = new Transport(null, mA, mB, mC, mD);
		
		
		/*
		RMIRegulatedMotor line;
		
		try {
			line = b102.createRegulatedMotor("A", 'M');
			int currentTacho = line.getTachoCount();
			 line.setSpeed(50); 
			 // line.forward();
			 Thread.sleep(1000);
			 line.stop(false);
			System.out.println(currentTacho);
			System.out.println(b102.getPort("A"));
			line.close();
		} catch (Exception e) {
			System.out.println("failed");
		} */
		
		// RMIRegulatedMotor linetoEnd = b102.createRegulatedMotor("A", 'M');

		
		 // linetoEnd.setSpeed(350); 
		 // linetoEnd.forward();
		 
		
			
			// ScheduledFuture<?> scheduledFuture = ses.scheduleAtFixedRate(new MyRunnable(linetoEnd), 1, 1, TimeUnit.SECONDS);
			/*
			// gateD.rotate(turnDegree);
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			linetoEnd.setSpeed(150);
			linetoEnd.backward();
			
			currentTacho = linetoEnd.getTachoCount(); // chh
			System.out.println(currentTacho);
			
			   linetoEnd.stop(false);
			   //gateD.rotate(-turnDegree);
			   
				linetoEnd.close();
				//gateC.close();
				//gateB.close();
				//gateD.close();
			System.out.println("fertig");
			ses.shutdown();
			*/
	}

}
