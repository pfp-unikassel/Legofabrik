import java.io.IOException;

public class x {

	public static void main(String[] args) {
		
//		MyServer my = new MyServer();
//		try {
//			my.start(3333);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		MyClient client = new MyClient();
		try {
			client.startConnection("10.10.190.74", 33333);
			client.sendMessage("test");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
