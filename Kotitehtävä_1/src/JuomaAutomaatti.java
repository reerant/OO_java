import java.util.Scanner;

public class JuomaAutomaatti {

	private int teeta;
	private int kahvia;
	private int kaakaota;

	public JuomaAutomaatti() {
		this.teeta = 50;
		this.kahvia = 50;
		this.kaakaota = 50;
	}

	public String toString() {

		return "Kahvia jäljellä: " + this.getKahvia() + " yksikköä. Teetä jäljellä:  " + this.getTeeta()
				+ " yksikköä. Kaakaota jäljellä: " + this.getKaakaota() + " yksikköä.\n";

	}

	public int getTeeta() {
		return teeta;
	}

	public void setTeeta(int teeta) {
		this.teeta = teeta;
	}

	public int getKahvia() {
		return kahvia;
	}

	public void setKahvia(int kahvia) {
		this.kahvia = kahvia;
	}

	public int getKaakaota() {
		return kaakaota;
	}

	public void setKaakaota(int kaakaota) {
		this.kaakaota = kaakaota;
	}

	public void valmistaKahvi() {
		if (kahvia >= 10) {
			kahvia -= 10;
			System.out.println("Odota hetki, kahvisi valmistuu.");
		} else {
			System.out.println(
					"Kahvi on valitettavasti loppunut. Täytä säiliö valmistaaksesi uuden juoman tai valitse toinen tuote.");

		}
	}

	public void valmistaTee() {
		if (teeta >= 10) {
			teeta -= 10;
			System.out.println("Odota hetki, teesi valmistuu.");

		} else {
			System.out.println(
					"Tee on valitettavasti loppunut. Täytä säiliö valmistaaksesi uuden juoman tai valitse toinen tuote.");

		}

	}

	public void valmistaKaakao() {
		if (kaakaota >= 10) {
			kaakaota -= 10;
			System.out.println("Odota hetki, kaakaosi valmistuu.");

		} else {
			System.out.println(
					"Kaakao on valitettavasti loppunut. Täytä säiliö valmistaaksesi uuden juoman tai valitse toinen tuote.");

		}

	}

	public boolean onnistuuko() {
		int luku = (int) (Math.random() * 100 + 1);
		return luku > 25;

	}

	public static void main(String[] args) {

		JuomaAutomaatti omaKone = new JuomaAutomaatti();
		Scanner lukija = new Scanner(System.in);
		int valinta = 0;

		do {
			System.out.println("********JUOMA-AUTOMAATTI********");
			System.out.println();
			System.out.println("Ole hyvä ja valitse haluamasi juoma. \nValitsemalla 'Lopeta', ohjelma päättyy.");
			System.out.println();
			System.out.println("1. Kahvi\n2. Tee\n3. Kaakao\n4. Lopeta");
			System.out.println();
			System.out.println("********************************");
			valinta = lukija.nextInt();

			if (valinta == 4) {
				System.out.println("Ohjelma lopetetaan.");
				break;
			}
			if (valinta > 4) {
				System.out.println("Tuntematon valinta. Ole hyvä ja yritä uudelleen.");
				System.out.println(omaKone);
				continue;
			}

			if (!omaKone.onnistuuko()) {
				System.out.println(
						"Hups! Tapahtui virhe - juoman teko ei onnistu. Kiitos kuitenkin maksustasi.");
				System.exit(0);
			}
			
			if (valinta == 1) {
				omaKone.valmistaKahvi();
				System.out.println(omaKone);
			} else if (valinta == 2) {
				omaKone.valmistaTee();
				System.out.println(omaKone);
			} else if (valinta == 3) {
				omaKone.valmistaKaakao();
				System.out.println(omaKone);
			} 
		} while (valinta != 4);
	}

}