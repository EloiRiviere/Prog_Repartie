package tp5_client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import tp5_serveur.ThreadServer;
import tp5_serveur.Sort;

public class CombatClient
{
    public static void main(String[] args) throws IOException
    {

        if (args.length != 2)
        {
            System.err.println("Usage: java CombatClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (
            Socket clientSocket = new Socket(hostName, portNumber);
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
        )
        {
        	System.out.print("Entrez votre nom: ");
        	Scanner sc = new Scanner(System.in);
        	String nom = sc.nextLine();

            Magicien magicien = new Magicien(nom);
            System.out.println("envoyé : " + magicien);
            out.writeObject(magicien.toString());
            while(true)
            {
                //jeu
                //envoyer enum sort
                out.writeObject(magicien.jeter_sort());
                //recevoir enum sort
                magicien.add_sort((Sort)in.readObject());
            }
        }
        catch (UnknownHostException e)
        {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        }
        catch (IOException e)
        {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
        catch(ClassNotFoundException e)
        {
          System.err.println("Erreur cast");
          System.exit(1);
        }
    }
}
