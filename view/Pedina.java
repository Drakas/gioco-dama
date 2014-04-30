package view;

import javax.swing.ImageIcon;

import model.Scacchiera;



public class Pedina extends Casella {

	
	public Pedina(int valore) {
		
		if(valore==1)
		{
			this.setIcon(Scacchiera.damaPokemon ? new ImageIcon("src/img/pokemon_pedinaB.png") : new ImageIcon("src/img/pedinaB.png"));
		}
		else if(valore==2){
			this.setIcon(Scacchiera.damaPokemon ? new ImageIcon("src/img/pokemon_pedinaN.png") : new ImageIcon("src/img/pedinaN.png"));	
		}
		else if(valore==3){
			this.setIcon(Scacchiera.damaPokemon ? new ImageIcon("src/img/pokemon_damaB.png") : new ImageIcon("src/img/damaB.png"));	
		}
		else if(valore==4){
			this.setIcon(Scacchiera.damaPokemon ? new ImageIcon("src/img/pokemon_damaN.png") : new ImageIcon("src/img/damaN.png"));
		}		
		
		//this.addActionListener(this);
	}




}
