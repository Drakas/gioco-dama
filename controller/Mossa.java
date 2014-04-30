package controller;
import java.util.*;

// la classe mossa è fatta da due liste una di pedine mangiate e l'altra di pedine toccate e 
// un booleano che è utilizzato in valuta mossa per dire se le pedine di mossa diventano Dame
public class Mossa
{
//istanze	
  // Lista delle caselle toccate. 
  public LinkedList<Casella> caselleToccate;
  // Lista delle caselle mangiate
  public LinkedList<Casella> caselleMangiate;
  // Indica se la mossa termina promuovendo una pedina a dama.
  public boolean fattaDama;
  
 // costruttore 

  public Mossa(Casella partenza)
  {  
    caselleToccate = new LinkedList<Casella>();
    caselleMangiate = new LinkedList<Casella>();
    // la prima casella toccata è quella di partenza
    caselleToccate.addLast(partenza);
    fattaDama = false;
  }

  // Costruisce mossa copiandone un'altra.
   
  public Mossa(Mossa daCopiare)
  {  
    caselleToccate = new LinkedList<Casella>(daCopiare.caselleToccate);
    caselleMangiate = new LinkedList<Casella>(daCopiare.caselleMangiate);
    fattaDama = daCopiare.fattaDama;
  }

// stampa mossa
  public void stampaMossa()
  {
    ListIterator<Casella> iter;
    Casella c;
    System.out.print(" Caselle toccate: ");
    iter = caselleToccate.listIterator();
    while (iter.hasNext())
    {
      c = (Casella)iter.next();
      System.out.print(" (" + c.riga + "," + c.colonna + ")");
    }
//    System.out.println();
    System.out.print(" Caselle mangiate: ");
    iter = caselleMangiate.listIterator();
    while (iter.hasNext())
    {
      c = (Casella)iter.next();
      System.out.print(" (" + c.riga + "," + c.colonna + ")");
    }
    System.out.println();
    
    if (fattaDama) System.out.println("Pedina promossa a dama");
  }
}
