package view;
import model.Scacchiera;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


class SchermataIniziale extends JFrame {

	SchermataIniziale() {
		
		//Layout principale
		JPanel borderl = new JPanel();
		borderl.setLayout(new BorderLayout());
		
		//Bottone per selezionare la dama normale
		JButton damaNormale = new JButton("Dama Normale");
		damaNormale.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Scacchiera.damaPokemon=false;
				Dama.avviaGioco();
				dispose();
			}
		});
		
		//Bottone per selezionare la dama pokemon
		JButton damaPokemon = new JButton("Dama Pokemon");
		damaPokemon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Scacchiera.damaPokemon=true;
				Dama.avviaGioco();
				dispose();
			}
		});
		
		//Layout bottoni
		JPanel zonaBottoni = new JPanel();
		zonaBottoni.setLayout(new FlowLayout(FlowLayout.CENTER));
		zonaBottoni.add(damaNormale);
		zonaBottoni.add(damaPokemon);

		//Layout titolo
		JPanel sopra = new JPanel();
		sopra.setLayout(new BoxLayout(sopra, BoxLayout.Y_AXIS));

		//Titolo
		JTextPane presentazione = new JTextPane();
		presentazione.setText("\n-- Progetto UniVr - Dama Italiana -- \n\n"
				+ "Di Andrea Saviozzi e Giovanni Menegozzo \n\n\n"
				+ "Il gioco segue le regole della Dama Italiana e permette al giocatore di"
				+ "giocare contro la CPU, ovvero l'intelligenza artificiale. \n"
				+ "E' possibile scegliere la variante di come sarà visualizzata la damiera. \n \n"
				+ "Seleziona la Damiera desiderata:\n");

		presentazione.setEditable(false);

		//Centro il testo all'interno di presentazione
		StyledDocument doc = presentazione.getStyledDocument();
		SimpleAttributeSet center1 = new SimpleAttributeSet();
		StyleConstants.setAlignment(center1, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center1, false);

		sopra.add(presentazione);

		borderl.add(sopra, BorderLayout.NORTH);
		borderl.add(zonaBottoni, BorderLayout.SOUTH);

		//Inizializzo la finestra..
		setContentPane(borderl);
		setTitle("Schermata Iniziale");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		pack();

		//.. e la centro
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
		this.setLocation(x, y);
	}
}