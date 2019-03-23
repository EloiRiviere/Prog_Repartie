package tp4;

import java.io.*;
import java.net.*;

public class ReceptionMessage extends Thread
{
  ObjectInputStream in;
  Donnees d;

  public ReceptionMessage(ObjectInputStream in)
  {
    this.in = in;
  }

  public void run()
  {
    while(true)
    {
      try{d = (Donnees) in.readObject();}
      catch(IOException e){e.printStackTrace();}
      catch (ClassNotFoundException e){e.printStackTrace();}
      System.out.println(d.getNom() + " : " + d.getS());
    }
  }
}
