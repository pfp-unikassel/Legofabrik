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
		
		addChild(new Box(id, destination, destination));  // default boxes and Baskets
		
		
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
