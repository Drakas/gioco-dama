package controller;

import java.util.*;

import model.Scacchiera;


public class ValutaMosse
{
  // istanze dell'oggetto
  
  public Gioco gioco;
  // ci serve per decidere tra 2 mosse di ugual priorietà
  protected Random generatoreCasuale;
  // la migior mossa
  public Mossa migliorMossa = null;
  //priorietà
  public int migliorValutaz;
  //miglior contromossa
  protected Mossa peggiorContromossa = null;
  //priorietà contromossa
  protected int peggiorValutaz;
  //mossa simulata
  protected Mossa simulata1 = null;
  //pezzi mangiati dalla mossa
  protected int[] pezziMangiati1 = null;
  //contromossa simulata
  protected Mossa simulata2 = null;
  // pezzi mangiati dalla contromossa
  protected int[] pezziMangiati2 = null;
    
  //costruttore 
  
  public ValutaMosse(Gioco g) 
  {
    gioco = g;
    //inizializzo il valore a random
    generatoreCasuale = new Random();
  }

  // metodi
  
  //salva i pezzi delle caselle nell'array (da lista di caselle ad array di interi)
  protected void salvaPezzi(int[] array, LinkedList<Casella> caselle)
  {
    ListIterator<Casella> iter = caselle.listIterator();
    int i = 0;
    while (iter.hasNext())
    {  array[i++] = gioco.contenuto((Casella)iter.next());  }
  }
  
  // simula la mossa

protected void simula(Mossa m)
  {
    // posso simulare al piu' 2 mosse consecutive memorizzandole
    if (simulata1==null)
    {
      simulata1 = new Mossa(m);
      // se la mossa m ha delle caselle mangiate
      if (m.caselleMangiate!=null)
      {	// memorizzo i pezzi mangiati dalle caselle su pezzi mangiati1
        pezziMangiati1 = new int[m.caselleMangiate.size()];
        salvaPezzi(pezziMangiati1, m.caselleMangiate);
      }
    }
    // controllo le contromosse della mossa m (vedi sopra)
    else if (simulata2==null)
    {

      simulata2 = new Mossa(m);
      if (m.caselleMangiate!=null)
      {
        pezziMangiati2 = new int[m.caselleMangiate.size()];
        salvaPezzi(pezziMangiati2, m.caselleMangiate);
      }
    }
    else return; 
    
    gioco.esegui(m);
  
  }
  
  //torna alla situazione di prima
  protected void disfaSimulazione()
  {
    Mossa m;
    // posso disfare, in ordine inverso, solo le mosse simulate 
    // controllo quale mossa voglio disfare
    if (simulata2!=null)  m = simulata2;
    else if (simulata1!=null)  m = simulata1;
    else return; 
    Casella c0 = (Casella)m.caselleToccate.getFirst();
    Casella c1 = (Casella)m.caselleToccate.getLast();
    int pezzo = gioco.contenuto(c1.riga,c1.colonna);
    //controllo se è diventata dama in tal caso torna pedina
    if (m.fattaDama) pezzo = gioco.declassataPedina(pezzo);
    // metto in c0 la prima casella toccata da m e in c1 vuota
    gioco.metti(c0,pezzo);
    gioco.metti(c1,Scacchiera.VUOTA);
    int i = 0;
    ListIterator<Casella> iter = m.caselleMangiate.listIterator();
    while (iter.hasNext())
    {
      Casella c = (Casella)iter.next();
      if (simulata1==m) gioco.metti(c,pezziMangiati1[i++]);
      else gioco.metti(c,pezziMangiati2[i++]);
    }
     
    // posso disfare, in ordine inverso, solo le mosse simulate 
    if (simulata2!=null)  simulata2 = null;
    else if (simulata1!=null)  simulata1 = null;
//  gioco.stampaScacchiera("DOPO DISFA SIMULAZIONE");
  }

  //conta il numero di pedine in gioco
  public int valuta()
  {
    int pedineBianche = 0;
    int pedineNere = 0;
    int dameBianche = 0;
    int dameNere = 0;
    int r, c;
    //ciclo la scacchiera
    for (r=0; r<Scacchiera.DIM_LATO; r++)
    for (c=0; c<Scacchiera.DIM_LATO; c++)
    {
      if (gioco.contenuto(r,c)==Scacchiera.PEDINA_BIANCA)
         pedineBianche++;
      else if (gioco.contenuto(r,c)==Scacchiera.PEDINA_NERA)
         pedineNere++;
      else if (gioco.contenuto(r,c)==Scacchiera.DAMA_BIANCA)
         dameBianche++;
      else if (gioco.contenuto(r,c)==Scacchiera.DAMA_NERA)
         dameNere++;
    }
//System.out.println("Bianco ha "+pedineBianche+" pedine e "+dameBianche+" dame");
//System.out.println("Nero ha "+pedineNere+" pedine e "+dameNere+" dame");
    int totale = 0;
    // dal punto di vista bianco: vittoria
    if ( (pedineNere==0) && (dameNere==0) )
       totale = 9999;
    // vittoria avversaria
    else if ( (pedineBianche==0) && (dameBianche==0) )
       totale = -9999; 
    // caso normale
    else totale = ( 2*(dameBianche-dameNere)+(pedineBianche-pedineNere) );
    return -totale;
  }

  // controlla che se val è il migiore del val precedente
  protected boolean eNuovaMigliore(int val)
  {
    if (migliorMossa==null) return true;
    if (val > migliorValutaz) return true;
    if (val < migliorValutaz) return false;
    // se valutazione uguale scegli a caso
    return ((generatoreCasuale.nextInt()%2)==0);
  }
  
//controlla che se val è il peggiore del val precedente
  protected boolean eNuovaPeggiore(int val)   
  {
    if (peggiorContromossa==null) return true;
    if (val < peggiorValutaz) return true;
    if (val > peggiorValutaz) return false;
    // se valutazione uguale scegli a caso
    return ((generatoreCasuale.nextInt()%2)==0);
  }
    
  // Calcola e ritorna la mossa valutata migliore per l'intelligenza artificiale 
  //guardando le possibili contromosse.
 
   
  public Mossa mossaMigliore()
  {
    int r1, c1, r2, c2;
    int valutaz;
    migliorMossa = null;
    LinkedList<Mossa> possibili1 = null;
    for (r1=0; r1<Scacchiera.DIM_LATO; r1++)
    for (c1=0; c1<Scacchiera.DIM_LATO; c1++)
    {        // controllo le nere
      if (gioco.colore(gioco.contenuto(r1,c1))==Scacchiera.NERO)
      { // metto in possibili le mosse del suggerisci mosse per la casella ri ci
        possibili1 = gioco.suggerisciMosse(new Casella(r1,c1));
        if (possibili1!=null)
        {
          LinkedList<Mossa> possibili2 = null;
          ListIterator<Mossa> iter1 = possibili1.listIterator();
          while (iter1.hasNext())
          {	//simulo la prima mossa della lista delle possibili
            Mossa m1 = (Mossa)iter1.next();
            simula(m1);
            peggiorContromossa = null;
            for (r2=0; r2<Scacchiera.DIM_LATO; r2++)   
            for (c2=0; c2<Scacchiera.DIM_LATO; c2++)   
            {        // simulo tutte le contromosse per m1
              if (gioco.colore(gioco.contenuto(r2,c2))==Scacchiera.BIANCO)
              {
                possibili2 = gioco.suggerisciMosse(new Casella(r2,c2));
                if (possibili2!=null)
                {
                  ListIterator<Mossa> iter2 = possibili2.listIterator();
                  while (iter2.hasNext())
                  {
                    Mossa m2 = (Mossa)iter2.next();
                    simula(m2);
                    valutaz = valuta();
                    if (eNuovaPeggiore(valutaz))
                    {  peggiorContromossa = m2; peggiorValutaz = valutaz;  }
                    disfaSimulazione();
                  } // fine while scorrimeto possibili2
                } // fine if possibili2
                else
                // no contromosse, il colore ha vinto
                {
                  peggiorValutaz = 9999;
                }
              } // fine se colore opposto
            } // fine for, fine esame possibili contromosse a m1
            if (eNuovaMigliore(peggiorValutaz))
            {  migliorMossa = m1; migliorValutaz = peggiorValutaz;  }
            disfaSimulazione();
          } // fine while scorrimento possibili1
        } // fine if possibili1
      } // fine se colore giusto
    } // fine for, fine esame mosse possibili
    return migliorMossa;
  }
  
}
