package tp4;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class EnvoiMessage extends Thread
{
  String nom, hostname, message;
  ObjectOutputStream out;
  Scanner sc;

  public EnvoiMessage(String nom, String hostname, ObjectOutputStream out)
  {
    this.nom = nom;
    this.hostname = hostname;
    this.out = out;
    this.sc = new Scanner(System.in);
  }

  public void run()
  {
    try
    {
      while(true)
      {
        Donnees d = new Donnees(nom, hostname);
        d.setS(sc.nextLine());
        this.out.writeObject(d);
        System.out.println("envoyé : " + d.getS());
        if(d.getS().equals("Bye")) break;
      }
      System.out.println("Vous vous êtes déconnecté.");
    }
    catch (IOException e)
    {
      System.err.println("Couldn't get I/O for the connection to " + hostname);
      System.exit(1);
    }
    catch(Exception e2)
    {
      e2.printStackTrace();
    }
    catch(Error e3)
    {
      e3.printStackTrace();
    }
  }
}
