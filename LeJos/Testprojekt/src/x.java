import java.io.IOException;

public class x {

	public static void main(String[] args) {
		
		MyServer my = new MyServer();
		try {
			my.start(33333);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MyClient client = new MyClient();
		try {
			client.startConnection("localhost", 33333);
			client.sendMessage("Hallo");
			client.stopConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
