//Dipendenze classi
package model;
import controller.Casella;

public class Scacchiera
{ // istanze
  public static final int DIM_LATO = 8;
  public static final int VUOTA = 0;
  public static final int PEDINA_BIANCA = 1;
  public static final int PEDINA_NERA = 2;
  public static final int DAMA_BIANCA = 3;
  public static final int DAMA_NERA = 4;
  
  public static final int NON_COLORE = 0;
  public static final int BIANCO = 1;
  public static final int NERO = 2;
  
  public static boolean damaPokemon;
  
  protected int[][] contenutoCaselle;
  
//costruttore  
  public Scacchiera()
  {
    contenutoCaselle = new int[DIM_LATO][DIM_LATO];
    statoIniziale();
  }
  
  // metodi
  // Controlla, date riga e colonna, se una casella e' nera. 
  public boolean eNera(int riga, int colonna)
  {  return ( (riga % 2) == (colonna % 2) );  }

  // Controlla, se una casella e' nera. 
  public boolean eNera(Casella casella)
  {  return eNera(casella.riga,casella.colonna);  }

  // inizio
  public void statoIniziale()
  {
    int r, c;
    for (r=0; r<DIM_LATO; r++)
    for (c=0; c<DIM_LATO; c++)
    {
      if (eNera(r,c))
      {
         if (r<3) // le tre righe in alto
           contenutoCaselle[r][c] = PEDINA_NERA;
         else if (r>4) // le tre righe in basso
           contenutoCaselle[r][c] = PEDINA_BIANCA;
         else contenutoCaselle[r][c] = VUOTA; // le 2 righe centrali
      }
      else contenutoCaselle[r][c] = VUOTA; // caselle bianche
    }
  }

  

  // Ritorna il colore di un pezzo, dato il codice del pezzo. 
  public int colore(int pezzo)
  {
    switch(pezzo)
    {
      case PEDINA_BIANCA: 
      case DAMA_BIANCA: return BIANCO;
      case PEDINA_NERA:
      case DAMA_NERA: return NERO;
    }
    return NON_COLORE;
  }  

  //Controlla se il pezzo dato e' una pedina. 
  public boolean ePedina(int pezzo)
  {  return ( (pezzo==PEDINA_BIANCA) || (pezzo==PEDINA_NERA) );  }


  public int contenuto(int r, int c) {  return contenutoCaselle[r][c];  }

  public int contenuto(Casella cas)
  {  return contenutoCaselle[cas.riga][cas.colonna];  }

  public boolean metti(int r, int c, int pezzo)
  {
    if ( (pezzo>=0) && (pezzo<=4) )
    {  contenutoCaselle[r][c] = pezzo; return true;  }
    else return false;
  }  
  public boolean metti(Casella cas, int pezzo)
  {  return metti(cas.riga, cas.colonna, pezzo);  }

  // Controlla, date riga e colonna, se una casella e' dentro i
  // limiti della scacchiera.
   
  public boolean eDentro(int riga, int colonna)
  {  return ( (riga>=0) && (riga<DIM_LATO) &&
              (colonna>=0) && (colonna<DIM_LATO) );
  }
  
  //
  public boolean eDentro(Casella c)  {  return eDentro(c.riga,c.colonna);  }

 
  public boolean bordoOpposto(Casella c, int col)
  {
    switch (col)
    {
      case NERO: return ( c.riga==(DIM_LATO-1) );
      case BIANCO: return ( c.riga==0 );
    }
    return false;
  }
  
  
  public int promossaDama(int pezzo)
  {
    switch (pezzo)
    {
      case PEDINA_NERA: case DAMA_NERA: return DAMA_NERA;
      case PEDINA_BIANCA: case DAMA_BIANCA: return DAMA_BIANCA;
    }
    return VUOTA;
  }

  
  public int declassataPedina(int pezzo)
  {
    switch (pezzo)
    {
      case PEDINA_NERA: case DAMA_NERA: return PEDINA_NERA;
      case PEDINA_BIANCA: case DAMA_BIANCA: return PEDINA_BIANCA;
    }
    return VUOTA;
  }

}