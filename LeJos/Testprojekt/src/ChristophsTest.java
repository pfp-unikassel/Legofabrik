import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;

public class ChristophsTest {
	
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
	
	
	
	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
		RemoteEV3 b110 = new RemoteEV3("192.168.0.110");
		
		 RMIRegulatedMotor linetoEnd = b110.createRegulatedMotor("A", 'M');

		 linetoEnd.setSpeed(350); 
		 linetoEnd.forward();
		 
		 int currentTacho = linetoEnd.getTachoCount();
			System.out.println(currentTacho);
			
			ScheduledFuture<?> scheduledFuture = ses.scheduleAtFixedRate(new MyRunnable(linetoEnd), 1, 1, TimeUnit.SECONDS);
			
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
	}

}
