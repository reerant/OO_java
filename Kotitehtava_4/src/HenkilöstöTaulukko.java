import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class HenkilöstöTaulukko extends JFrame {
// Luodaan käyttöliittymä henkilöstötaulukkoa varten.
// Ikkunassa näytetään tietokannasta tuodun henkilöstötaulun tiedot
// JTablessa ja ikkunaan lisätyillä napeilla saa tehtyä muokkauksia tietokantaan. 
// HUOM, kun tekee lisäyksen, poiston tai muokkauksen, täytyy painaa päivitä-nappia,
// jotta taulukkonäkymä päivittyisi. 
	
	private JPanel contentPane;
	static JTable table;
	static ArrayList<Object[]> data = new ArrayList<Object[]>();

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HenkilöstöTaulukko frame = new HenkilöstöTaulukko();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public HenkilöstöTaulukko() {
		setTitle("Henkil\u00F6st\u00F6");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 300);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("Tiedosto");
		menuBar.add(mnNewMenu);
		
		// Tiedosto -Lopeta painike menuvalikossa.
		// Aukeaa uusi ikkuna, jossa varmistetaan lopetus. 
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Lopeta");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int lopetus = JOptionPane.showConfirmDialog(HenkilöstöTaulukko.this,
						"Haluatko varmasti lopettaa ohjelman?", "Vahvista lopetus", JOptionPane.OK_CANCEL_OPTION);
				if (lopetus == JOptionPane.OK_OPTION) {
					System.exit(0);
				}
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		// Taulukon sarakkeiden nimet.
		JScrollPane scrollPane = new JScrollPane();
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("HenkilöID");
		model.addColumn("Etunimi");
		model.addColumn("Sukunimi");
		model.addColumn("Työsuhteen aloitus");
		model.addColumn("Työsuhteen lopetus");
		
		// Muodostetaan yhteys tietokantaan.
		try {

			Connection con = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7268407",
					"sql7268407", "YqQTwDXJic");

			Statement stmt = con.createStatement();
			
		//Valitaan koko tietokantataulu ja lisätään taulun joka rivi data muuttujaan. 
			ResultSet rs = stmt.executeQuery("SELECT * FROM Henkilöstö");

			while (rs.next()) {
				data.add(new Object[] { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5) });
			}
			con.close();
	

		} catch (Exception e) {
			System.out.println(e);
		}

		//Lisätään data muuttujasta tiedot käyttöliittymän Jtable-ikkunaan. 
		for (int i = 0; i < data.size(); i++) {
			model.addRow(data.get(i));
		}

		JTable table = new JTable(model);
		scrollPane.setViewportView(table);
		contentPane.add(scrollPane, BorderLayout.CENTER);

		// Ikkunan alaosaan Jpaneliin lisätään napit eri toimintoja varten, joita ovat:
		// Lisää uusi hlö, Päivitä taulukko, Muokkaa lopetuspvm ja Poista hlö.
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnNewButton_2 = new JButton("Lis\u00E4\u00E4 uusi henkil\u00F6 ");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lisaaHlo();
			}

		});
		panel.add(btnNewButton_2);

		JButton btnNewButton_1 = new JButton("P\u00E4ivit\u00E4 taulukko");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paivitaTaulukko(model);

			}

		});
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_3 = new JButton("Muokkaa lopetuspvm");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				muokkaaTietoja(table);
				
			}

		
		});
		panel.add(btnNewButton_3);
		
				JButton btnNewButton = new JButton("Poista henkil\u00F6");
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// Varmistetaan vielä erikseen halutaako ko. rivi poistaa taulusta. 
						int lopetus = JOptionPane.showConfirmDialog(HenkilöstöTaulukko.this,
								"Haluatko varmasti poistaa henkilön/henkilöt?", "Vahvista poisto", JOptionPane.OK_CANCEL_OPTION);
						if (lopetus == JOptionPane.OK_OPTION) {
							poistaHlo(table);
						}
					}

				});
				panel.add(btnNewButton);
	}
	
	// ao. toiminnon avulla lisätään uusi hlö tietokantaan. 
	// Nappia painamalla aukeaa uusi ikkuna, johon hlön tiedot syötetään.
	
	private void lisaaHlo() {
		JTextField henkiloID = new JTextField(10);
		JTextField etunimi = new JTextField(10);
		JTextField sukunimi = new JTextField(10);
		JTextField aloitus = new JTextField(10);
		JTextField lopetus = new JTextField(10);

		JPanel myPanel = new JPanel();

		myPanel.add(new JLabel("HenkilöID:"));
		myPanel.add(henkiloID);

		myPanel.add(new JLabel("Etunimi:"));
		myPanel.add(etunimi);

		myPanel.add(new JLabel("Sukunimi"));
		myPanel.add(sukunimi);

		myPanel.add(new JLabel("Työsuhteen aloitus (vvvv-kk-pp):"));
		myPanel.add(aloitus);

		myPanel.add(new JLabel("Työsuhteen lopetus (vvvv-kk-pp):"));
		myPanel.add(lopetus);

		int result = JOptionPane.showConfirmDialog(null, myPanel, "Lisää uuden henkilön tiedot:",
				JOptionPane.OK_CANCEL_OPTION);

		if (result == JOptionPane.OK_OPTION) {

			String id = henkiloID.getText();
			String enimi = etunimi.getText();
			String snimi = sukunimi.getText();
			String tyonAloitus = aloitus.getText();
			String tyonLopetus = lopetus.getText();

			Henkilo hlo = new Henkilo(Integer.parseInt(id), enimi, snimi, tyonAloitus, tyonLopetus);
			
			// Tallennus tietokantaan hoidetaan JDBC.java luokassa. 
			JDBC.tallennaHlo(hlo);

		}

	}
	
	// ao. toiminnolla päivitetään taulukko.
	
	private void paivitaTaulukko(DefaultTableModel model) {
		// Luodaan uusi arraylist ja otetaan yhteys tietokantaan, josta tiedot haetaan uudelleen. 
		ArrayList<Object[]> uusiData = new ArrayList<Object[]>();
		try {

			Connection con = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7268407",
					"sql7268407", "YqQTwDXJic");

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM Henkilöstö");
			//Henkilöstötaulun tiedot lisätään uusiData muuttujaan 
			while (rs.next()) {
				uusiData.add(new Object[] { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5) });
			}
			con.close();

		} catch (Exception ex) {
			System.out.println(ex);
		}
		// tyhjennetään nykyinen näkymä JTable ikkunasta.
		model.setRowCount(0);
		
		// Lisätään uuData muuttujan sisältö, eli päivitetyn Henkilöstötaulun tiedot ikkunaan. 
		for (int i = 0; i < uusiData.size(); i++) {
			model.addRow(uusiData.get(i));
		}

	}
	
	//ao. toiminto poistaa halutun rivin/rivit tietokannasta. 
	private void poistaHlo(JTable table) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int[] rivit = table.getSelectedRows();
		for (int i = 0; i < rivit.length; i++) {
			String id = model.getValueAt(rivit[i],0).toString();
			// poisto suoritetaan loppuun JDBC.java luokassa. 
			// poistettavan rivin henkilöstöID:n arvo välitetään parametrina. 
			JDBC.poistaHlo(Integer.parseInt(id));
		}

	}
	
	//ao. toiminnolla voidaan muokata työsuhteen päättymispvm.
	// Nappia painamalla aukeaa uusi ikkuna, johon haluttu pvm syötetään. 
	private void muokkaaTietoja(JTable table) {
		
		JTextField lopetus = new JTextField(10);

		JPanel myPanel = new JPanel();
		
		myPanel.add(new JLabel("Työsuhteen lopetuspvm (vvvv-kk-pp):"));
		myPanel.add(lopetus);

		int result = JOptionPane.showConfirmDialog(null, myPanel, "Muokkaa tietoja:",
				JOptionPane.OK_CANCEL_OPTION);
		
		if (result == JOptionPane.OK_OPTION) {

			String tyonLopetus = lopetus.getText();
			
			Henkilo hlo = new Henkilo(tyonLopetus);
			
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			int[] rivit = table.getSelectedRows();
			for (int i = 0; i < rivit.length; i++) {
				String id = model.getValueAt(rivit[i],0).toString();
				// Tieto päivitetään tietokantaan  JDBC.java luokassa. 
				// Muokkattavan rivin id sekä uusi lopetuspvm hlo-oliossa 
				// välitetään parametreina.
				
				JDBC.muokkaaHlo(hlo,Integer.parseInt(id));
		
			}

			
		}
		
	}
}
