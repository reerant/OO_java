import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Scanner;

public class Sanakirja {
	
	/*NOTE:
	 * T‰m‰ versio kattaa kotiteht‰v‰n kohdat A-C.
	 * Versio2Sanakirja.java sis‰lt‰‰ kohdan D.
	 */
	static 	String tiedostoNimi = "hashmap.txt";
	public static void main(String[] args) throws Exception {

		Scanner lukija = new Scanner(System.in);

		// haetaan HashMapin tallennettu oliodata ja palautetaan tiedot
		// parit-muuttujaan. Kun ohjelmaa k‰ytet‰‰n ekaa kertaa ja ko. tiedostoa ei viel‰ ole, 
		// luodaan uusi (tallennusvaiheessa), jonne sanoja aletaan tallentamaan ja parit-muuttujaan palautetaan 
		//tyhj‰ HashMap. Tiedosto sijaitsee samassa hakemistossa koodin kanssa. 
		HashMap<String, String> parit = haetaanHM();

		// (itelle muistiin: parit.clear(); jos haluaa tyhjent‰‰ hashmapin.)

		// Alustetaan Hashmap-Toiminto j‰tetty pois kun tehty teht‰v‰‰ eteenp‰in.
		// Ihan alkuun HM on siis tyhj‰, ennenkuin aletaan syˆtt‰m‰‰n uutta dataa sinne,
		// joka tallentuu muistiin.
		// alustaHashMap(parit);

		// kysell‰‰n uudet sanat ja niiden k‰‰nnˆkset, jotka lis‰t‰‰n HashMappiin.
		kyseleUudetSanat(parit);

		// Kirjoitetaan uusi versio HashMapista talteen.
		kirjoitaTalteen(parit);

		System.out.println();
		System.out.print("Sanakirjan sis‰ltˆ: " + parit.toString());

		System.out.println();
		System.out.println();

		// Kysell‰‰n sanojen k‰‰nnˆksi‰.
		kyseleKaannoksia(parit);

	}

	private static void alustaHashMap(HashMap<String, String> parit) {
		String[] suomi = { "kissa", "koira", "hevonen", "auto", "vene" };
		String[] englanti = { "cat", "dog", "horse", "car", "boat" };

		for (int i = 0; i < suomi.length; i++) {
			parit.put(suomi[i], englanti[i]);
		}

	}

	private static void kyseleUudetSanat(HashMap<String, String> parit) {
		String uusiSana;
		String kaannos;
		Scanner lukija = new Scanner(System.in);
		System.out.println("Lis‰t‰‰n uusia sanoja HashMappiin.");
		do {
			System.out.print("Sana alkukielell‰? (tyhj‰ lopettaa) ");
			uusiSana = lukija.nextLine();

			if (uusiSana.isEmpty()) {
				break;
			} else

				System.out.print("Sana k‰‰nnetyn‰? (tyhj‰ lopettaa) ");
			kaannos = lukija.nextLine();

			parit.put(uusiSana, kaannos);
		} while (!uusiSana.isEmpty());

	}

	private static void kyseleKaannoksia(HashMap<String, String> parit) {
		String sana;
		String value;
		Scanner lukija = new Scanner(System.in);
		do {
			System.out.println("Mink‰ sanan k‰‰nnˆksen haluat tiet‰‰? (tyhj‰ sana lopettaa) ");
			sana = lukija.nextLine();

			if (sana.isEmpty()) {
				System.out.println("Ohjelma lopetetaan, kiitos k‰ynnist‰!");
			} else {
				String arvo = parit.get(sana);
				if (arvo == null) {
					System.out.println("Sanan \"" + sana + "\" k‰‰nnˆs on tuntematon!\n");
				} else {

					value = (String) parit.get(sana);
					System.out.println("Sanan \"" + sana + "\" k‰‰nnˆs on \"" + value + "\"\n");
				}
			}
		} while (!sana.isEmpty());

	}

	private static void kirjoitaTalteen(HashMap<String, String> parit) {
		try {
			FileOutputStream tied = new FileOutputStream("hashmap.txt");
			ObjectOutputStream kirjoitetaanTiedostoon = new ObjectOutputStream(tied);
			kirjoitetaanTiedostoon.writeObject(parit);
			tied.close();
			kirjoitetaanTiedostoon.flush();
			System.out.println("Data tallennettu tiedostoon: " + tiedostoNimi);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

	private static HashMap<String, String> haetaanHM() throws ClassNotFoundException {
		HashMap<String, String> parit = new HashMap<String, String>();
	
		try {
			FileInputStream tiedosto = new FileInputStream(tiedostoNimi);
			ObjectInputStream otetaanTallesta = new ObjectInputStream(tiedosto);
			parit = (HashMap) otetaanTallesta.readObject();
			otetaanTallesta.close();
			tiedosto.close();
			System.out.println("K‰ytet‰‰n tallennettua HashMapia tiedostosta: " + tiedostoNimi);
		} catch (IOException ioe) {
			System.out.println("HashMap tiedostoa ei lˆytynyt. Luodaan tallennusvaiheessa uusi nimell‰: " + tiedostoNimi);
		}
		return parit;
	}

}
