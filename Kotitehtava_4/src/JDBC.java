import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

// Tässä luokassa suoritetaan kaikki tietokantaan tehtävät muutokset sekä lisäykset ja poistot. 
public class JDBC {

	static final String DB_URL = "jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7268407";
	static final String USER = "sql7268407";
	static final String PASS = "YqQTwDXJic";

	// Tämä hoitaa uuden hlö olion lisäämisen tietokannan tauluun.
	// Konsoliin tulostetaan kun yhteys tietokantaan on luotu sekä kun tieto
	// on tallennettu onnistuneesti. 
	
	public static void tallennaHlo(Henkilo hlo) {

		int id = hlo.getId();
		String etunimi = hlo.getEtunimi();
		String sukunimi = hlo.getSukunimi();
		String tyonAloitus = hlo.getTyonAloitus();
		String tyonLopetus = hlo.getTyonLopetus();

		Connection conn = null;
		Statement stmt = null;
		try {

			System.out.println("Yhteys luotu tietokantaan.");

			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			String sql = "INSERT INTO Henkilöstö (HenkilöID, Etunimi, Sukunimi, TyönAloitus, TyönLopetus) values (?,?,?,?,?)";

			PreparedStatement preparedStmt = conn.prepareStatement(sql);

			preparedStmt.setInt(1, id);
			preparedStmt.setString(2, etunimi);
			preparedStmt.setString(3, sukunimi);
			preparedStmt.setString(4, tyonAloitus);
			// Mikäli uutta hlöä lisättäessä jätetään työn lopetuskenttä tyhjäksi, 
			// asetetaan tietokantaan oletuksena pvm 0000-00-00.
			if(tyonLopetus.length() > 0) {
				preparedStmt.setString(5, tyonLopetus);
			}
			else {
				preparedStmt.setString(5, "0000-00-00");
				
			}

			preparedStmt.execute();

			System.out.println("Tieto tallennettu tietokantaan.");

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {

			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

	}
	
	// Toiminto poistaa halutun rivin tietokannasta. 
	// Poisto suoritetaan parametrina tulleen henkilöID perusteella.
	// Konsoliin tulostetaan kun yhteys tietokantaan on luotu sekä kun 
	// on poisto on tehty onnistuneesti.
	
	public static void poistaHlo(int id) {
		Connection conn = null;
		Statement stmt = null;
		try {

			System.out.println("Yhteys luotu tietokantaan.");

			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			String sql = "DELETE FROM Henkilöstö WHERE HenkilöID = ?";

			PreparedStatement preparedStmt = conn.prepareStatement(sql);

			preparedStmt.setInt(1, id);
			preparedStmt.execute();

			System.out.println("Tieto poistettua tietokannasta.");

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {

			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}

	// Toiminnolla muokataan henkilön työsuhteen päättymispvm.
	// Tieto tuodaan parametrina hlo oliossa, jossa on uusi päättymispvm sekä 
	// kohdennetaan se oikeaan riviin HenkilöID perusteella. 
	// Konsoliin tulostetaan kun yhteys tietokantaan on luotu sekä kun 
	// on tiedon päivitys on tehty onnistuneesti.
	public static void muokkaaHlo(Henkilo hlo, int id) {
		Connection conn = null;
		Statement stmt = null;
		try {

			System.out.println("Yhteys luotu tietokantaan.");

			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			String sql = "UPDATE Henkilöstö SET TyönLopetus = ? WHERE HenkilöID = ?";

			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, hlo.getTyonLopetus());
			preparedStmt.setInt(2, id);
			preparedStmt.execute();

			System.out.println("Tieto päivitetty tietokantaan.");

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			

			e.printStackTrace();
		} finally {

			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}
}
