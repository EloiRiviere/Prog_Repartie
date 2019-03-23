package tp5_client;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

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

          System.out.println("envoyé : " + nom);
          out.writeObject(nom);

          Magicien magicien = new Magicien(nom);

          for(int i=0; i<5; i++)
          {
            magicien.add_sort((Sort) in.readObject());
          }

          while(true)
          {
            //jeu
            //envoyer enum sort
            Sort sort_jete = magicien.jeter_sort();
            if(sort_jete.hasEnemy())
            {
              out.writeObject("regarder");
              List<String> ennemis = (ArrayList<String>) in.readObject();
              for(String ennemi : ennemis)
              {
                if(!ennemi.equals(nom))
                {
                  out.writeObject(sort_jete);
                  out.writeObject(ennemi);
                  System.out.println("Vous avez jeté le sort " + sort_jete + " sur " + ennemi);
                  //recevoir enum sort
                  Sort sort_recupere = (Sort)in.readObject();
                  magicien.add_sort(sort_recupere);
                  System.out.println("Vous avez récupéré le sort " + sort_recupere);
                  break;
                }
              }
            }
            else
            {
              out.writeObject(sort_jete);
              System.out.println("Vous avez jeté le sort " + sort_jete);
              Sort sort_recupere = (Sort)in.readObject();
              magicien.add_sort(sort_recupere);
              System.out.println("Vous avez récupéré le sort " + sort_recupere);
            }
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
