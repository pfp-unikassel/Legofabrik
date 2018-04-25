package userInterface;



import java.awt.Color;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import controller.Steuerung;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.util.Duration;


public class Controller implements Initializable {
	
	public Button startButton;
	public Button stopButton;
	public Button pauseButton;
	public Button emptyButton;
	public Button allButton;
	public Button szenarioButton1;
	public Button szenarioButton2;
	public Button szenarioButton3;
	public Label topLeftLabel;
	public Label readyLabel;
	public Label leftBottomLabel;
	public Label rightBottomLabel;
	public Circle showBall;
	public boolean paused;     // True if game paused right now
	public boolean running;
	private Timeline timeline;
	private float timer = 0;
	
	Steuerung s;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {  // gets startet with programm
		
		s = new Steuerung();
		Thread steuerung = new Thread (s);
		steuerung.start();
		
	
		 
		paused = false;
		running = false;
		
//		timeline = new Timeline(new KeyFrame(
//		        Duration.millis(1000),                  // do xx every 1000 millis
//		        ae -> updateTime()));
//		timeline.setCycleCount(Animation.INDEFINITE);
//		timeline.play();
	

	}
	




	public void startButtonClicked() {  
		if(running) {								// if game runs allready do nothing
			
		}else {										// start game if it is stoped
			System.out.println("startButtonClicked clicked");
			showBall.setFill(javafx.scene.paint.Color.GREEN);
			readyLabel.setText("Running ");
//			startTimer();
			running = true;		
			paused = false;
			
			try {
				s.getChargier().startLineToLifter(true);
				s.getChargier().startLineToStore(true);
				s.getChargier().startLineToTable(true);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}


	public void stopButtonClicked() {
		System.out.println("stopButtonClicked clicked");
		showBall.setFill(javafx.scene.paint.Color.LIGHTBLUE);
		readyLabel.setText("Stopped ");
//		stopTimer();
		running = false;
		paused = true;
		pauseButton.setText("Pause");

		try {
			s.getChargier().stopLineToLifter();
			s.getChargier().stopLineToStorer();
			s.getChargier().stopLineToTable();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void pauseButtonClicked() {
		
		System.out.println("pauseButtonClicked clicked");
		
		if(running) {										    // just can pause game if its allready runs
			if(paused) {										// if game is paused right now and button get clicked again
				readyLabel.setText("Running");
//				startTimer();
				showBall.setFill(javafx.scene.paint.Color.GREEN);
				pauseButton.setText("Pause");
				paused= false;
				
			}else {
				
				showBall.setFill(javafx.scene.paint.Color.YELLOW);
				readyLabel.setText("Paused ");
//				stopTimer();
				pauseButton.setText("Resume");
				paused = true;			
			}
			
		}else {  //pushed break without running game
			
		}
		
	}

	public void emptyButtonClicked() {
		System.out.println("emptyButtonClicked clicked");
		
	}

	public void allButtonClicked() {
		System.out.println("allButtonClicked clicked");
		
	}

	public void szenarioButton1Clicked() {
		System.out.println("szenarioButton1Clicked clicked");
		
	}

	public void szenarioButton2Clicked() {
		System.out.println("szenarioButton2Clicked clicked");
		
	}
	
	public void szenarioButton3Clicked() {
		System.out.println("szenarioButton3Clicked clicked");
		
	}

	public void error() {
		
		showBall.setFill(javafx.scene.paint.Color.RED);
		readyLabel.setText("ERROR !!" );
		stopTimer();
	}
	
	public void error(String msg) {    // error with Message 
		
		showBall.setFill(javafx.scene.paint.Color.RED);
		readyLabel.setText("ERROR !!" );                          //TODO: change ERROR!! to error code 
		System.out.println(msg);
		stopTimer();

	}

	private void startTimer() {
	
		if(paused) {
			timeline.play();
			//just start timer from last count
		}else {
			timeline.play();
			
			//restart timer from 0
		}
		
		
	}
	
	private void stopTimer() {
	
		
	}
	
	private void updateTime() {
		
		
		DateFormat timeFormat = new SimpleDateFormat( "mm:ss" );
		if ( timer < 0 ) {
			leftBottomLabel.setText( timeFormat.format( 0 ) );
            } else {
            	leftBottomLabel.setText( timeFormat.format( timer ) );
            }
		timer = timer+1000;
		
	}
}
