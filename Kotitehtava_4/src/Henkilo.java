
public class Henkilo {

	// Luodaan Henkilo niminen luokka, ja sille tarvittavat getterit ja setterit, sekä
	// konstruktorit ja toString.

	private int id;
	private String etunimi;
	private String sukunimi;
	private String tyonAloitus;
	private String tyonLopetus;

	public Henkilo(int id, String etunimi, String sukunimi, String tyonAloitus, String tyonLopetus) {
		super();
		this.id = id;
		this.etunimi = etunimi;
		this.sukunimi = sukunimi;
		this.tyonAloitus = tyonAloitus;
		this.tyonLopetus = tyonLopetus;
	}

	public Henkilo(String text, String text2, String text3, String text4, String text5) {

	}

	public Henkilo(String tyonLopetus2) {
		this.tyonLopetus = tyonLopetus2;
	}

	@Override
	public String toString() {
		return "Henkilo [id=" + id + ", etunimi=" + etunimi + ", sukunimi=" + sukunimi + ", tyonAloitus=" + tyonAloitus
				+ ", tyonLopetus=" + tyonLopetus + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEtunimi() {
		return etunimi;
	}

	public void setEtunimi(String etunimi) {
		this.etunimi = etunimi;
	}

	public String getSukunimi() {
		return sukunimi;
	}

	public void setSukunimi(String sukunimi) {
		this.sukunimi = sukunimi;
	}

	public String getTyonAloitus() {
		return tyonAloitus;
	}

	public void setTyonAloitus(String tyonAloitus) {
		this.tyonAloitus = tyonAloitus;
	}

	public String getTyonLopetus() {
		return tyonLopetus;
	}

	public void setTyonLopetus(String tyonLopetus) {
		this.tyonLopetus = tyonLopetus;
	}

}
