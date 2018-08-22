package rfidModules;

import java.util.Date;

public class Product extends Transponder {

	private Date productionDate = new Date(1970);
	private String brand = "unkown";

	public Product(long id, String origin, String destination) {
		super(id, origin, destination);
	}

	public Product(long id, String origin, String destination, Date date) {
		super(id, origin, destination);
		this.productionDate = date;
	}
	
	public Product(long id, String origin, String destination, Date date , String brand) {
		super(id, origin, destination);
		this.productionDate = date;
		this.setBrand(brand);
	}
	
	public Product(long id, String origin, String destination, String brand) {
		super(id, origin, destination);
		this.setBrand(brand);
	}

	public void setProductionDate(Date date) {

		productionDate = date;
	}

	public Date getProductionDate() {

		return productionDate;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
}
