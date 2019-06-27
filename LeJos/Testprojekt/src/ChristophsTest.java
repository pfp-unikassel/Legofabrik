import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import lejos.hardware.port.PortException;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;

public class ChristophsTest {
	
	ArrayList<String> testArr = new ArrayList();
	
	
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
	
	public ChristophsTest () {
		testArr.add("Hans");
		testArr.add("Franz");
	}
	
	public ArrayList<String> getA() {
		return testArr;
	}
	
	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
		RemoteEV3 b102 = new RemoteEV3("192.168.0.102");
		
		ChristophsTest c = new ChristophsTest();
		for (String s : c.testArr)
			System.out.println(s);
		ArrayList<String> test = c.getA();
		test.clear();
		System.out.println("After copying");
		for (String s : c.testArr)
			System.out.println(s);
		
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
		}
		
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
