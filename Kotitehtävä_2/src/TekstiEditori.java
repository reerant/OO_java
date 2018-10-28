import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JToolBar;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.awt.event.InputEvent;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TekstiEditori extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TekstiEditori frame = new TekstiEditori();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Huom. iconit ei välttämättä näy, koska eclipsen classpath ei toiminut ja kaikki iconit on absolute pathin kautta.
	 */
	public TekstiEditori() {
		setTitle("Oma Editori");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		JEditorPane editorPane = new JEditorPane();

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnTiedosto = new JMenu("Tiedosto");
		menuBar.add(mnTiedosto);

		// avaa-toiminnolla voidaan avata editoriin tiedosto tarkasteltavaksi/muokattavaksi.
		
		JMenuItem mntmAvaa = new JMenuItem("Avaa");
		mntmAvaa.setIcon(new ImageIcon("C:\\Users\\reear\\eclipse-workspace\\Kotitehtava_2\\pics\\newFile1.png"));
		mntmAvaa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser valintaikkuna = new JFileChooser();
				valintaikkuna.setApproveButtonText("Avaa tiedosto");
				valintaikkuna.setDialogTitle("Tiedoston valinta");

				valintaikkuna.showOpenDialog(null);
				String rivi = "";
				String uusiTiedosto = valintaikkuna.getSelectedFile().getAbsolutePath();

				try {
					Scanner lukija = null;
					File tiedosto = new File(uusiTiedosto);

					lukija = new Scanner(tiedosto);

					while (lukija.hasNextLine()) {
						rivi += lukija.nextLine() + "\n";
						System.out.println(rivi);

					}
				} catch (FileNotFoundException p) {
					System.out.println("Tiedostoa ei löydy..");

				}
				editorPane.setText(rivi);
			}
		});
		mntmAvaa.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		mnTiedosto.add(mntmAvaa);
		
		// tallenna-toiminto avaa ikkunan, jossa voidaan valita minne tiedosto tallennetaan ja millä nimellä.

		JMenuItem mntmTallenna = new JMenuItem("Tallenna");
		mntmTallenna.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mntmTallenna.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser valintaikkuna = new JFileChooser();
				valintaikkuna.showSaveDialog(null);
				String uusiTiedosto = valintaikkuna.getSelectedFile().getAbsolutePath();
				System.out.println("Kirjoitettava tiedosto: " + uusiTiedosto);

				try {
					PrintWriter writer = new PrintWriter(uusiTiedosto);
					String sisalto = editorPane.getText();

					writer.println(sisalto);

					writer.flush();
					writer.close();
				} catch (Exception e1) {

					System.out.println("Tiedoston tallennuksessa tapahtui virhe!");
					e1.printStackTrace();
				}
			}
		});
		mnTiedosto.add(mntmTallenna);
		
		// lopeta-toimintoa painamalla ponnahtaa esiin ikkuna, jossa vielä varmistetaan haluaako käyttäjä lopettaa ohjelman.
		// ok:lla lopettaa ohjelman ja cancelillä palataan takaisin ohjelmaan.

		JMenuItem mntmLopeta = new JMenuItem("Lopeta");
		mntmLopeta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int lopetus = JOptionPane.showConfirmDialog(TekstiEditori.this, "Haluatko varmasti lopettaa ohjelman?", "Vahvista lopetus", JOptionPane.OK_CANCEL_OPTION);
				if(lopetus == JOptionPane.OK_OPTION) {
					System.exit(0);
				}
			}
		});
		mnTiedosto.add(mntmLopeta);

		JMenuItem mntmSulje = new JMenuItem("Sulje");
		mnTiedosto.add(mntmSulje);

		JMenu mnMuokkaa = new JMenu("Muokkaa");
		menuBar.add(mnMuokkaa);

		// etsi-toiminnolla aukeaa uusi ikkuna, jossa kysytään käyttäjältä etsittävä sana ja sen jälkeen se korostetaan värillä.
		// toiminta antaa tällä hetkellä koko tekstistä vain ensimmäisen sanan, joka vastaa haluttua sanaa. 
		
		JMenuItem mntmEtsi = new JMenuItem("Etsi");
		mntmEtsi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String sisalto = editorPane.getText();
				sisalto = sisalto.toLowerCase();

				String haettava = JOptionPane.showInputDialog(TekstiEditori.this, "Etsi sana:");
				int indeksi = sisalto.indexOf(haettava);
				System.out.println("Indeksi: " + indeksi);

				editorPane.setSelectionColor(Color.CYAN);
				editorPane.setSelectionStart(indeksi);
				editorPane.setSelectionEnd(indeksi + haettava.length());
			}
		});
		mnMuokkaa.add(mntmEtsi);
		
		// korvaa-toiminto pyytää ensin käyttäjää antamaan korvattavan sanan ja sen jälkeen sanan, jolla korvattava sana korvataan. 
		// toiminto etsii kaikki haluttua sanaa vastaavat merkkijonot ja korvaa ne. 

		JMenuItem mntmKorvaa = new JMenuItem("Korvaa");
		mntmKorvaa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sisalto = editorPane.getText();
				sisalto = sisalto.toLowerCase();
				String korvattava = JOptionPane.showInputDialog(TekstiEditori.this, "Anna korvattava sana:");
				String korvaaja = JOptionPane.showInputDialog(TekstiEditori.this, "Anna korvaava sana:");;
				
				String korvaa = sisalto.replaceAll(korvattava,korvaaja);
				editorPane.setText(korvaa);
			}
		});
		mnMuokkaa.add(mntmKorvaa);
		
		// Tietoja ohjelmasta-toiminto avaa uuden ikkunan TietojaTekijasta-luokasta, jossa tekijän tiedot sekä kuva. 

		JMenu mnTietoja = new JMenu("Tietoja");
		menuBar.add(mnTietoja);

		JMenuItem mntmTietojaOhjelmasta = new JMenuItem("Tietoja ohjelmasta");
		mntmTietojaOhjelmasta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TietojaTekijasta frame = new TietojaTekijasta();
				frame.setVisible(true);
			}
		});
		mnTietoja.add(mntmTietojaOhjelmasta);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JToolBar toolBar = new JToolBar();
		contentPane.add(toolBar, BorderLayout.NORTH);

		// Ensimmäinen nappi toolbarissa toimii pikakuvakkeena avaa tiedosto-toiminolle.
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\reear\\eclipse-workspace\\Kotitehtava_2\\pics\\newFile1.png"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser valintaikkuna = new JFileChooser();
				valintaikkuna.setApproveButtonText("Avaa tiedosto");
				valintaikkuna.setDialogTitle("Tiedoston valinta");

				valintaikkuna.showOpenDialog(null);
				String rivi = "";
				String uusiTiedosto = valintaikkuna.getSelectedFile().getAbsolutePath();

				try {
					Scanner lukija = null;
					File tiedosto = new File(uusiTiedosto);

					lukija = new Scanner(tiedosto);

					while (lukija.hasNextLine()) {
						rivi += lukija.nextLine() + "\n";
						System.out.println(rivi);

					}
				} catch (FileNotFoundException p) {
					System.out.println("Tiedostoa ei löydy.");

				}
				editorPane.setText(rivi);

			}
		});
		btnNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		toolBar.add(btnNewButton);
		
		// toinen nappi toolbarissa toimii pikakuvakkeena tiedoston tallennus toiminnolle.

		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setIcon(new ImageIcon("C:\\Users\\reear\\eclipse-workspace\\Kotitehtava_2\\pics\\save2.png"));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser valintaikkuna = new JFileChooser();
				valintaikkuna.showSaveDialog(null);
				String uusiTiedosto = valintaikkuna.getSelectedFile().getAbsolutePath();
				System.out.println("Kirjoitettava tiedosto: " + uusiTiedosto);

				try {
					PrintWriter writer = new PrintWriter(uusiTiedosto);
					String sisalto = editorPane.getText();

					writer.println(sisalto);

					writer.flush();
					writer.close();
				} catch (Exception e1) {

					System.out.println("Tiedoston tallennuksessa tapahtui virhe!");
					e1.printStackTrace();
				}
			}
		});
		toolBar.add(btnNewButton_1);
		
		// kolmas nappi toolbarissa toimii pikakuvakkeena etsi-toiminnolle. 
		// HUOM. etsi toiminto ei jostain syystä korosta värillä etsittyä sanaa, enkä keksinyt syytä sille. 
		// toteutettu samoin kuin JMenuIten "etsi".

		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String sisalto = editorPane.getText();
				sisalto = sisalto.toLowerCase();

				String haettava = JOptionPane.showInputDialog(TekstiEditori.this, "Etsi sana:");
				int indeksi = sisalto.indexOf(haettava);
				System.out.println("Indeksi: " + indeksi);
				System.out.println("pituus: " + haettava.length());

				editorPane.setSelectionColor(Color.YELLOW);
				editorPane.setSelectionStart(indeksi);
				editorPane.setSelectionEnd(indeksi + haettava.length());
			}
		});
		btnNewButton_2.setIcon(new ImageIcon("C:\\Users\\reear\\eclipse-workspace\\Kotitehtava_2\\pics\\search.png"));
		toolBar.add(btnNewButton_2);

		contentPane.add(editorPane, BorderLayout.CENTER);
	}

}
