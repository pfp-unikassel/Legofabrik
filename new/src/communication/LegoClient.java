package communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.UnknownHostException;

public class LegoClient {
	
	private String targetIP = "localhost";
	private int targetPort = 11111;
	
	
	public LegoClient() {
		
	}
	
	public LegoClient(String ip) {
		targetIP = ip;
	}
	
	public LegoClient(String ip, int port) {
		targetIP = ip;
		targetPort = port;
	}

	public static void main(String[] args) {
//		LegoClient client = new LegoClient();
//		try {
//			client.test();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	void test() throws IOException {
		String ip = targetIP;
		int port = targetPort;
		java.net.Socket socket = new java.net.Socket(ip, port); // verbindet
																// sich mit
																// Server
		String zuSendendeNachricht = "hallo";
		schreibeNachricht(socket, zuSendendeNachricht);
		String empfangeneNachricht = leseNachricht(socket);
		System.out.println(empfangeneNachricht);
	}
	
	public String sendMessage(String message) {
	
		String ip = targetIP;
		int port = targetPort;
		String empfangeneNachricht;
		try {
			java.net.Socket socket = new java.net.Socket(ip, port);
			schreibeNachricht(socket,message);
			 empfangeneNachricht = leseNachricht(socket);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return empfangeneNachricht;
	}

	public void schreibeNachricht(java.net.Socket socket, String nachricht) throws IOException {
		PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		printWriter.print(nachricht);
		printWriter.flush();
	}

	public String leseNachricht(java.net.Socket socket) throws IOException {
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
