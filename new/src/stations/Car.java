package stations;

import controller.Steuerung;

public class Car{

	private boolean carPostition = true; // True infront of line, false infront of Fillstation
	private Steuerung s;  
	private int carHorizontalDegree = 0;   // TODO: find motor turn degree, to move car left right
	private int carSpeed = 360; // degree per second
	private int lineSpeed = 360;
	
	public Car(Steuerung s){
		
		this.s=s;
	}
	
	
	public void carToLeft(boolean instantReturn){
		if(carPostition == true){
			// car is allready left
		}else{
			//TODO: move car to left
			moveCar(carHorizontalDegree,instantReturn);
		}
	}
	
	public void carToRight(boolean instantReturn){
		
		if(carPostition == false){
			// car is allready right
		}else{
			//TODO: move car to right
			moveCar(-carHorizontalDegree,instantReturn);
		}
	}
	
	public void moveCar(int degree,boolean instantReturn){
		//TODO set car movement and speed
		
	}
	
	public void startLineOnCar(boolean direction){
		
		// set linespeed
		if(direction= true){
			//TODO: move line to table
		}else{
			//TODO:move line toCar
		}
	}
	
	public void stopLineOnCar(){
		
	}
	
	
	
	
}
