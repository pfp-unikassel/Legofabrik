package rfidModules;

import java.lang.Math;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Transponder {

	private int counter = 0; // 0 nicht da / 1 Eingang / 2 Lager / 3 Versand
	private Date firstScan;
	private Date secondScan;
	private Date thirdScan;

	private long id;
	private int intId;
	private String name;
	private String origin;
	private String destination;
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss ");

	private ArrayList<Transponder> childs = new ArrayList<>(); // every

	public Transponder(long id, String origin, String destination) {

		this.id = id;
		this.name = name;
		this.destination = destination;

	}

	public void createDefaultChildren() { // TODO: add more default boxes and Baskets

		// baskets
		// addChild(new Basket(5l, "origin", "destination", "owner")); // default basket
		addChild(new Basket(1042050255873l, "Berlin", "Berlin"));
		addChild(new Basket(1041429498881l, "Berlin", "Berlin"));
		addChild(new Basket(327701823605l, "Berlin", "Berlin"));

		// boxes
		// addChild(new Box(1111111l , "origin", "destination")); // default box
		addChild(new Box(954362363984l, "Dortmund", "Dortmund"));
		addChild(new Box(141875150928l, "Dortmund", "Dortmund"));
		addChild(new Box(301613383760l, "Dortmund", "Dortmund"));
		addChild(new Box(477723820112l, "Berlin", "Berlin"));
		addChild(new Box(120788549712l, "Berlin", "Berlin"));
		addChild(new Box(473009422416l, "Kassel", "Kassel"));
		addChild(new Box(825230295120l, "Kassel", "Kassel"));

	}

	public void createFabicChildren() { // same boxes different baskets

		addChild(new Basket(1042050255873l, "fabric", null));
		addChild(new Basket(1041429498881l, "fabric", null));
		addChild(new Basket(327701823605l, "fabric", null));

	}

	public void addChild(Transponder t) {

		childs.add(t);
	}

	public void removeChild(Transponder t) {
		childs.remove(t);
	}

	public void saveScantime() {

		counter++;

		switch (counter) {
		case 1:
			firstScan = new Date();
			break;
		case 2:
			secondScan = new Date();
			break;
		case 3:
			thirdScan = new Date();
			break;
		}
	}

	public void printScanDates() {
		if (getFirstScan() != null) {
			System.out.println(getId() + " Eingang : " + dateFormatter.format(getFirstScan()));
		}
		if (getSecondScan() != null) {
			System.out.println(getId() + " Lager : " + dateFormatter.format(getSecondScan()));	

		}
		if (getThirdScan() != null) {
			System.out.println( getId() +  " Ausgang : " + dateFormatter.format(getThirdScan()));
		}
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

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public Date getFirstScan() {
		return firstScan;
	}

	public void setFirstScan(Date firstScan) {
		this.firstScan = firstScan;
	}

	public Date getSecondScan() {
		return secondScan;
	}

	public void setSecondScan(Date secondScan) {
		this.secondScan = secondScan;
	}

	public Date getThirdScan() {
		return thirdScan;
	}

	public void setThirdScan(Date thirdScan) {
		this.thirdScan = thirdScan;
	}

	public SimpleDateFormat getDateFormatter() {
		return dateFormatter;
	}

	public void setDateFormatter(SimpleDateFormat dateFormatter) {
		this.dateFormatter = dateFormatter;
	}
}
