//Client.java

// import java.net.Socket;
import java.io.*;

public class Client {
	public static void main(String[] args) {
		Client client = new Client();
		try {
			client.test();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void test() throws IOException {  // wenn nach 5 sec keine antwort kommt shicke nachricht nochmal

		String ip = "10.10.144.129";  //10.10.144.129
		int port = 33333;
		java.net.Socket socket = new java.net.Socket(ip, port); // verbindet
																// sich mit
																// Server
		String zuSendendeNachricht = "CF";
		schreibeNachricht(socket, zuSendendeNachricht);
		String empfangeneNachricht = leseNachricht(socket);
		System.out.println("empfangen : " +empfangeneNachricht);
	}

	void schreibeNachricht(java.net.Socket socket, String nachricht) throws IOException {
		PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		printWriter.print(nachricht);
		printWriter.flush();
	}

	String leseNachricht(java.net.Socket socket) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		char[] buffer = new char[200];
		int anzahlZeichen = bufferedReader.read(buffer, 0, 200); // blockiert
																	// bis
																	// Nachricht
																	// empfangen
		String nachricht = new String(buffer, 0, anzahlZeichen);
		return nachricht;
	}
}