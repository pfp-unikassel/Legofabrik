package communication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SaveInFile {

	

	public static void deleteFile(String fileName) {
		
		if(new File("C:\\Users\\Mitarbeiter\\Documents\\new\\resources\\"+fileName).isFile()) {
			new File("C:\\Users\\Mitarbeiter\\Documents\\new\\resources\\"+fileName).delete();
		}else {
			System.out.println("File does not exists");
		}
	}
	
	public static void saveInFile(String fileName, String s) {
		try {

			File recivedMessagesFile = new File("C:\\Users\\Mitarbeiter\\Documents\\new\\resources\\"+fileName); // creates File if its not allready there
			try {
				recivedMessagesFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (recivedMessagesFile.isFile()) {
				
				PrintWriter out = new PrintWriter(recivedMessagesFile);
				out.print(s);
				out.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void saveInFile(String fileName, ArrayList<String> strings) {
		try {

			File recivedMessagesFile = new File("C:\\Users\\Mitarbeiter\\Documents\\new\\resources\\"+fileName); // creates File if its not allready there
			try {
				recivedMessagesFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (recivedMessagesFile.isFile()) {
				
				PrintWriter out = new PrintWriter(recivedMessagesFile);
				for(String s : strings){
					out.println(s);
				}
				out.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
