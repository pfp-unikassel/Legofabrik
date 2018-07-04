package rfidModules;

public class Basket extends Transponder {

	private String owner = "unknown";

	
	public Basket(long id, String origin, String destination, String owner){
		
		super(id, origin, destination);
		this.owner = owner;
				
	}
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
}
