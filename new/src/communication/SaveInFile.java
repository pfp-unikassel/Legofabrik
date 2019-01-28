package communication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class SaveInFile {

	

	public static void deleteFile(String fileName) {
		
		if(new File("/"+fileName).isFile()) {
			new File("/"+fileName).delete();
		}else {
			System.out.println("File does not exists");
		}
	}
	
	public static void saveInFile(String fileName, String s) {
		try {

			File recivedMessagesFile = new File("/"+fileName); // creates File if its not allready there

			if (recivedMessagesFile.isFile()) {
				PrintWriter out = new PrintWriter(s+"\n");
				out.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
