
public class test3 {
	
	public static void main(String args []) {
		
		System.out.println("Main");
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
	        @Override
	        public void run() {
	            testMethode();
	        }
	    });
		
		
		System.out.println("should not be displayed");
	}

	public static void testMethode() {
		
		System.out.println("testMethode");
		
	}
}
