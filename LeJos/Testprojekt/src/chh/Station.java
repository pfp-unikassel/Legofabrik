package chh;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class Station {

	ArrayList<State> states;
	
	public void addState(String desc, Callable procedure) {
		states.add(new State(desc, procedure));
	}
	
	public void listStates() {
		for (State s : states)
			System.out.println(s);
	}
	
}
