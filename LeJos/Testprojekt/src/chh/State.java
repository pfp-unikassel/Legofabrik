package chh;

import java.util.concurrent.Callable;

public class State {
	String description;
	Callable procedure;
	
	public State(String desc, Callable procedure) {
		this.description = desc;
		this.procedure = procedure;
	}
	
	@Override
	public String toString() {
		return this.description;
	}
	
}
