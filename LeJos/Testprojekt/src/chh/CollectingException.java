package chh;

public class CollectingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String desc;
	String location;
	String errorMessage;
	
	public CollectingException(String errorMessage) {

		super(errorMessage);
		this.errorMessage = errorMessage;
	}
	
	public CollectingException(String errorMessage, String location, String desc) {
		System.out.println("Exception in " + location + " with description " + desc);
		System.out.println(errorMessage);
	}

	public CollectingException() {
		
	}
	
}
