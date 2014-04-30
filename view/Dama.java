//dipendenze classi
package view;

//dipendenze utilizzate di Java
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;

import model.Scacchiera;
import controller.Gioco;
import controller.Mossa;

public class Dama extends JFrame {
	//istanze

	protected Point click1 = null;
	protected Point click2 = null;
	protected JButton p1 = null;
	protected JButton p2 = null;
	protected Gioco dash;

	//costruttore
	public Dama(Gioco gioco) {
		super("Progetto Dama:  Andrea Saviozzi-Giovanni Menegozzo");
		dash = gioco;

				
		// Imposto la grafica
		this.setLayout(new GridLayout(8, 8));
		setResizable(false);
		showBoard(-1,-1);
		setSize(720, 720);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Centro la finestra
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
		this.setLocation(x, y);
	}
	
	//metodi
	
	// Metodo che genera i bottoni e quindi la grafica che assumerà la dama.
	 
	private void showBoard(int xiniziale, int yiniziale) {
		//controlla se il primo click corrisponde a una pedina del player
		boolean trovata=false;
		
		
		//inizio scansione scacchiera
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				
				//Vuol dire che ho chiamato lo showBoard con l'intento di visuallizare le mosse suggerite
				if (xiniziale != -1) {
					trovata=false;
					//lista delle mosse della casella (xiniziale yinizliale)
					LinkedList<Mossa> mossePedina = dash.suggerisciMosse(new controller.Casella(xiniziale, yiniziale));
					// Controllo se la mossa è fattibile e memorizzo l'ultima casella raggiunta in casella
						for (int i = 0; i < mossePedina.size(); i++) {
						Mossa pop = (Mossa) mossePedina.get(i);
						controller.Casella casella =  (controller.Casella) pop.caselleToccate.getLast();
						// Se stiamo iterando una delle caselle appartenenti a caselle toccate di pop
							if (casella.riga == y && casella.colonna == x) {
								if (dash.mangiataObbligatoria(xiniziale, yiniziale, casella.riga, casella.colonna)) 
								trovata = true;
							}
							
						    
				        }
				}//Fine IF
				
			final Casella p;
			//se la casella x,y corrisponde a una casella suggerita verrà colorata altrimenti
			//gli viene assegnata la pedina corrispondente
				if(trovata){
					p = generaPedina(dash.contenuto(y, x), 0, 0, true);
				}
				else{
					p = generaPedina(dash.contenuto(y, x), x, y, false);
				}
				
				p.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {

						//primo click, se ho cliccato su una pedina del player
						if (dash.colore(dash.contenuto(p.getPosizione().x,p.getPosizione().y)) == Scacchiera.BIANCO) 
						{
							click1 = p.getPosizione();
							System.out.println("primo click: " + click1);
							// Distruggo e ridisegno la grafica
							getContentPane().removeAll();
							//passo alla showBoard il primo click
							showBoard(p.getPosizione().x,p.getPosizione().y);
							invalidate();
							validate();
						} 
						else if (click1 != null) {
							//Secondo click
							click2 = p.getPosizione();
							System.out.println("secondo click: " + click2);
							String messaggio = dash.provaMossaGiocatore(click1.x, click1.y, click2.x, click2.y);
						
							
							// Resetto i click
							click1 = null;
							click2 = null;

							// Distruggo e ridisegno la grafica
							getContentPane().removeAll();
							showBoard(-1,-1);
							invalidate();
							validate();

							if (messaggio != null)
								stampaMessaggio(messaggio);

						} //Fine secondo click
					}
				});

				// Aggiungo il bottone
				add(p);
			}
		}
	}

	private Casella generaPedina(int valore, int x, int y, boolean colore) {
		// Genero le pedine
		if (valore >= 1 && valore <= 4 && colore == false)
			return new Pedina(valore);
		
		else if(colore == true){
			Pedina pedColorata = new Pedina(valore);
			pedColorata.setBorder(new LineBorder(Color.RED, 4));
			return pedColorata;
		}
		
		if (valore == 0) {
			if (dash.eNera(x, y)) {
				return new Vuota(Color.gray);
			}
		}

		// Penso a colorare la parte centrale
		if (y == 3)
			if (x % 2 == 1)
				return new Vuota(Color.gray);
		// Penso a colorare la parte centrale
		if (y == 4)
			if (x % 2 == 0)
				return new Vuota(Color.gray);

		// Di default tutto è bianco
		return new Vuota();
	}

	//Metodo che si preoccupa di stampare il messaggio passatogli per parametro.
	public void stampaMessaggio(String s) {
		Component frame = null;
		JOptionPane.showMessageDialog(frame, s);
	}
	
	//Metodo voi che avvia il gioco
	public static void avviaGioco(){
		new Dama(new Gioco()).setVisible(true);
	}

	public static void main(String[] args) {
	
		//Lancio la schermata iniziale
		JFrame window = new SchermataIniziale();
        window.setVisible(true);

		
	}

}
