import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Versio2Sanakirja {

	/*
	 * NOTE: Tämä versio kattaa kotitehtävän kohdan D.
	 */

	static String fileName = "Sanakirja.txt";

	public static void main(String[] args) {

		Scanner lukija = new Scanner(System.in);

		// haetaan HashMapin tallennettu data ja palautetaan tiedot
		// parit-muuttujaan. Kun ohjelmaa käytetään ekaa kertaa ja ko. tiedostoa ei
		// vielä ole,
		// luodaan uusi (tallennusvaiheessa), jonne sanoja aletaan tallentamaan ja parit-muuttujaan
		// palautetaan
		// tyhjä HashMap. Tiedosto sijaitsee samassa hakemistossa koodin kanssa.
		HashMap<String, String> parit = haeSanatTiedostosta(fileName);

		// kysellään uudet sanat ja niiden käännökset, jotka lisätään HashMappiin.
		kyseleUudetSanat(parit);

		// Kirjoitetaan uusi versio HashMapista talteen.
		tallennetaanTiedostoon(parit);

		System.out.println();
		System.out.print("Sanakirjan sisältö: " + parit.toString());

		System.out.println();
		System.out.println();

		// Kysellään sanojen käännöksiä.
		kyseleKaannoksia(parit);

	}

	private static void kyseleKaannoksia(HashMap<String, String> parit) {
		Scanner lukija = new Scanner(System.in);
		String sana;
		String value;
		do {
			System.out.println("Minkä sanan käännöksen haluat tietää? (tyhjä sana lopettaa) ");
			sana = lukija.nextLine();

			if (sana.isEmpty()) {
				System.out.println("Ohjelma lopetetaan, kiitos käynnistä!");
			} else {
				String arvo = parit.get(sana);
				if (arvo == null) {
					System.out.println("Sanan \"" + sana + "\" käännös on tuntematon!\n");
				} else {

					value = (String) parit.get(sana);
					System.out.println("Sanan \"" + sana + "\" käännös on \"" + value + "\"\n");
				}
			}
		} while (!sana.isEmpty());

	}

	private static void kyseleUudetSanat(HashMap<String, String> parit) {
		String uusiSana;
		String kaannos;
		Scanner lukija = new Scanner(System.in);
		do {
			System.out.print("Sana alkukielellä? (tyhjä lopettaa) ");
			uusiSana = lukija.nextLine();

			if (uusiSana.isEmpty()) {
				break;
			} else

				System.out.print("Sana käännetynä? (tyhjä lopettaa) ");
			kaannos = lukija.nextLine();

			parit.put(uusiSana, kaannos);
		} while (!uusiSana.isEmpty());

	}

	private static void tallennetaanTiedostoon(HashMap<String, String> parit) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {

			Iterator<Entry<String, String>> it = parit.entrySet().iterator();
			while (it.hasNext()) {
				HashMap.Entry<String, String> alkio = (HashMap.Entry<String, String>) it.next();
				String avain = alkio.getKey();
				String arvo = alkio.getValue();
				String sisalto = avain + " = " + arvo;
				bw.write(sisalto);
				bw.newLine();
			
			}
			System.out.println("Data tallennettu tiedostoon: " + fileName);
			bw.close();
		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	private static HashMap<String, String> haeSanatTiedostosta(String fileName) {

		HashMap<String, String> parit = new HashMap<String, String>();
		File tiedosto = new File(fileName);

		try {
			Scanner lukija = new Scanner(tiedosto);
			System.out.println("Lisätään sanoja tiedostoon: " + fileName + "\n");
			while (lukija.hasNextLine()) {
				String rivi = lukija.nextLine();
				String[] sanaJaKaannos = rivi.split(" = ");
				parit.put(sanaJaKaannos[0], sanaJaKaannos[1]);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Tiedostoa ei löytynyt. Tallennusvaiheessa luodaan uusi tiedosto: " + fileName
					+ "\nAloita syöttämällä sanoja tiedostoon.\n");
		}

		return parit;
	}

}
