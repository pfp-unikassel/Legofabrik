package communication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.util.ArrayList;

//import controller.RemoteEV3;

public class LegoClient {

	private String targetIP = "localhost";
	private int targetPort = 11111;
	private int sendErrorCounter = 0;
	private int numberOfSendTrys = 3;
	private static ArrayList<String> recivedMessages = new ArrayList<>();
	private static ArrayList<String> sentMessages = new ArrayList<>();
	
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
		// LegoClient client = new LegoClient();
		// try {
		// client.test();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
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

		/**@param sendet Nachricht and Server
		 *  versucht dies X und meldet Fehler falls es nicht funktionier
		 *  gibt die empfangene antwort zurück ans Hauptprogramm
		 *  Speichert gesendete und empfangen Nachrichten im Array
		 */
		String ip = targetIP;
		int port = targetPort;
		String empfangeneNachricht = "";
		
		try {
			java.net.Socket socket = new java.net.Socket(ip, port);
			schreibeNachricht(socket, message);
			empfangeneNachricht = leseNachricht(socket);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (!empfangeneNachricht.equals("")) {
			sendErrorCounter = 0;
			recivedMessages.add(empfangeneNachricht);          // add recivedmessages to Arraylist
			sentMessages.add(message);
		}

		if (empfangeneNachricht.equals("") && sendErrorCounter > numberOfSendTrys) { // try again recursiv
			sendErrorCounter++;
			empfangeneNachricht = sendMessage(message);
			sentMessages.add("nicht empfangen: " +message);
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

	public void writeSentMessageInFile() {

		/** @param opens SaveInFile.java und speicher gesendete Nachrichten in SentMessages.txt
0		 * 
		 */
		for (String s : getSentMessages()) {
			SaveInFile.saveInFile("SentMessages.txt", s);
		}
	}
	
	public void writeRecivedMessageInFile() {

		for (String s : getRecivedMessages()) {
			SaveInFile.saveInFile("RecivedMessages.txt", s);
		}
	}
	
	public void deleteRecivedMessages() {
		getRecivedMessages().clear();
	}
	public void deleteSentMessages() {
		getRecivedMessages().clear();
	}
	
	public void deleteSentMessagesInFile() {
		SaveInFile.deleteFile("SentMessages.txt");
	}
	public void deleteRecivedMessagesInFile() {
		SaveInFile.deleteFile("RecivedMessages.txt");
	}

	public String getTargetIP() {
		return targetIP;
	}

	public void setTargetIP(String targetIP) {
		this.targetIP = targetIP;
	}

	public int getTargetPort() {
		return targetPort;
	}

	public void setTargetPort(int targetPort) {
		this.targetPort = targetPort;
	}

	public static ArrayList<String> getRecivedMessages() {
		return recivedMessages;
	}

	public static void setRecivedMessages(ArrayList<String> recivedMessages) {
		LegoClient.recivedMessages = recivedMessages;
	}

	public static ArrayList<String> getSentMessages() {
		return sentMessages;
	}

	public static void setSentMessages(ArrayList<String> sentMessages) {
		LegoClient.sentMessages = sentMessages;
	}

}
