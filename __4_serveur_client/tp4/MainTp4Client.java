package tp4;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MainTp4Client
{
	String nom, IP;

	public String getNom()
	{
		return this.nom;
	}

	public String getIP()
	{
		return this.IP;
	}

	private void setNom(String nom)
	{
		this.nom = nom;
	}

	private void setIP(String IP)
	{
		this.IP = IP;
	}

	public static void main(String[] args) throws IOException
	{

      if (args.length != 1)
      {
          System.err.println("Usage: java MainTp4Client <port number>");
          System.exit(1);
      }

    	Scanner sc = new Scanner(System.in);
    	System.out.print("Entrez votre ip: ");
    	String hostName = sc.nextLine();
    	System.out.print("Entrez votre nom: ");
    	String nom = sc.nextLine();
			sc.close();

			int portNumber = Integer.parseInt(args[0]);

      try
      (
          Socket objSocket = new Socket(hostName, portNumber);
          ObjectOutputStream out = new ObjectOutputStream(objSocket.getOutputStream());
          ObjectInputStream in = new ObjectInputStream(objSocket.getInputStream());
      )
      {
				Donnees d = new Donnees(nom,hostName);
				d.setS("Bonjour !");
				out.writeObject(d);

				EnvoiMessage thread_envoi = new EnvoiMessage(nom,hostName,out);
				ReceptionMessage thread_reception = new ReceptionMessage(in);
				thread_envoi.join();
				thread_reception.interrupt();
      	System.exit(0);
      }
      catch (UnknownHostException e)
      {
          System.err.println("Erreur pour le nom d'hôte : " + hostName);
          System.exit(1);
      }
			catch(InterruptedException e2)
			{
				System.err.println("Le thread client a été interrompu.");
			}
		}
}
