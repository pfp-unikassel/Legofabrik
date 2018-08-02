package rfidModules;

import java.lang.Math;
import java.util.ArrayList;

public class Transponder {

	private long id;

	private int intId;
	private String name;

	private String origin;

	private String destination;

	private ArrayList<Transponder> childs = new ArrayList<>(); // every

	public Transponder(long id, String origin, String destination) {

		this.id = id;
		this.name = name;
		this.destination = destination;

	}
	
	public void createDefaultChildren() { // TODO: add more default boxes and Baskets
		
		
		//baskets
//				addChild(new Basket(5l, "origin", "destination", "owner"));  // default basket
				addChild(new Basket(1042050255873l, "Berlin", "Berlin"));
				addChild(new Basket(1041429498881l, "Berlin", "Berlin"));
				addChild(new Basket(327701823605l, "Berlin", "Berlin"));
				
				//boxes
//				addChild(new Box(1111111l , "origin", "destination"));  // default box
				addChild(new Box(954362363984l , "Dortmund", "Dortmund")); 
				addChild(new Box(141875150928l , "Dortmund", "Dortmund")); 
				addChild(new Box(301613383760l , "Dortmund", "Dortmund")); 
				addChild(new Box(477723820112l , "Berlin", "Berlin")); 
				addChild(new Box(120788549712l , "Berlin", "Berlin")); 
				addChild(new Box(473009422416l , "Kassel", "Kassel")); 
				addChild(new Box(825230295120l , "Kassel", "Kassel")); 
				
	}
	
	public void createFabicChildren(){ // same boxes different baskets
		
		addChild(new Basket(1042050255873l, "fabric", null ));
		addChild(new Basket(1041429498881l, "fabric", null));
		addChild(new Basket(327701823605l, "fabric", null));
		

	}

	public void addChild(Transponder t) {

		childs.add(t);
	}

	public void removeChild(Transponder t) {
		childs.remove(t);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public int getIntId() {
		return intId;
	}

	public void setIntId(int intId) {
		// this.intId = (int) getId();

		this.intId = Math.toIntExact(intId); // maybe wont work in java 7
	}

	public ArrayList<Transponder> getChilds() {
		return childs;
	}

}
