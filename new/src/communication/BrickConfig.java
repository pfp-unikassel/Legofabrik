package communication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import controller.Steuerung;

public class BrickConfig { // TODO: make a Brickconfig object in steuerung

	private Steuerung s;
	
	/* liest Brick Ips bei erstellung ein, falls datei nicht existiert erzeugt sie die gespeicherten standart Werte
	 * */
	 

	public BrickConfig(Steuerung s) {
		this.s = s;
		try {
			if(new File("/Brickconfig.txt").exists()) {
				readIps();
			}else {
				if(new File("/BrickDefaultconfig.txt").exists()) {
					readDefaultIps();					
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private ArrayList<String> brickips = new ArrayList<>();
	private ArrayList<String> defaultBrickips = new ArrayList<>();

	public void readIps() throws FileNotFoundException, IOException {

		File f = new File("/Brickconfig.txt");
		try (BufferedReader br = new BufferedReader(new FileReader(f))) {
			String line;
			while ((line = br.readLine()) != null) {

				brickips.add(line);
			}
		}
	}

	public void writeIps() {

		SaveInFile.deleteFile("/Brickconfig.txt");

		for (String c : brickips) {

			SaveInFile.saveInFile("Brickconfig.txt", c);
		}
	}
	
	public void writeDefaultIps() { // change default Ips in config, not be used

		SaveInFile.deleteFile("/BrickDefaultconfig.txt");

		for (String c : brickips) {
			SaveInFile.saveInFile("BrickDefaultconfig.txt", c);
		}
	}

	public void readDefaultIps() {

		File f = new File("/BrickDefaultconfig.txt");

		try (BufferedReader br = new BufferedReader(new FileReader(f))) {
			String line;
			while ((line = br.readLine()) != null) {

				defaultBrickips.add(line);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<String> getDefaultBrickips() {
		return defaultBrickips;
	}
	
	public ArrayList<String> getBrickips() {
		return brickips;
	}

	public void setBrickips(ArrayList<String> brickips) {
		this.brickips = brickips;
	}

}
