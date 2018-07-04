package rfidModules;

public class Box extends Transponder {

	private int stockPlace = 0; // if 0 => on the way

	private boolean boxEmpty = false;

	public Box(long id, String origin, String destination){
		
		super(id, origin, destination);
				
		
	}
	public boolean isBoxEmpty() {
		return boxEmpty;
	}

	public void setBoxEmpty(boolean boxEmpty) {
		this.boxEmpty = boxEmpty;
	}

	public int getStockPlace() {
		return stockPlace;
	}

	public void setStockPlace(int stockPlace) {
		this.stockPlace = stockPlace;
	}
}
