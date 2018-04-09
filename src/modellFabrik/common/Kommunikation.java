package modellFabrik.common;

public class Kommunikation {
	
	private static boolean kisteIstLeergut = false;
	private static boolean kisteBereit = false;
	private static boolean kisteGegriffen = false;
	private static boolean beginneWaschvorgang = false;
	private static int counter = 0;
	
	public static void resetValues () {
		kisteIstLeergut = false;
		kisteBereit = false;
		kisteGegriffen = false;
		beginneWaschvorgang = false;
		counter = 0;
	}

	public static boolean getKisteIstLeergut() {
		return kisteIstLeergut;
	}

	public static void setKisteIstLeergut(boolean kisteIstLeergut) {
		Kommunikation.kisteIstLeergut = kisteIstLeergut;
	}

	public static boolean getKisteBereit() {
		return kisteBereit;
	}

	public static void setKisteBereit(boolean kisteBereit) {
		Kommunikation.kisteBereit = kisteBereit;
	}

	public static boolean getKisteGegriffen() {
		return kisteGegriffen;
	}

	public static void setKisteGegriffen(boolean kisteGegriffen) {
		Kommunikation.kisteGegriffen = kisteGegriffen;
	}

	public static boolean getBeginneWaschvorgang() {
		return beginneWaschvorgang;
	}

	public static void setBeginneWaschvorgang(boolean beginneWaschvorgang) {
		Kommunikation.beginneWaschvorgang = beginneWaschvorgang;
	}

	public static int getCounter() {
		return counter;
	}

	public static void setCounter(int counter) {
		Kommunikation.counter = counter;
	}
	

}
