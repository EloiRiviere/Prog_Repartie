package tp4;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class MainTp4Serveur extends Thread
{
	Socket clientSocket;
	// Donnees d = new Donnees("","");
	Donnees d;
	static ArrayList<ObjectOutputStream> clients = new ArrayList<>();
    public MainTp4Serveur(Socket clientSocket)
    {
        this.clientSocket = clientSocket;
    }

    public void run()
    {
        try
        (
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
        )
        {
					clients.add(out);//ajout client ! gérer pas doublons
        	Object o;
            while ((o = in.readObject()) != null)
            {
                if(o instanceof Donnees)
                {
                    d = (Donnees) o;
                    System.out.println(d.getNom() + " : " + d.getS());
										for(ObjectOutputStream client:clients)
										{
											if(client!=out)
											{
												client.writeObject(d);
											}
										}
                }
            }
        }
        catch (IOException e)
        {
            System.out.println(d.getNom() + " s'est déconnecté.");
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                clientSocket.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException
    {
        if (args.length != 1)
        {
            System.err.println("Usage: java MainTp4Serveur <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        try
        (
            ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));
        )
        {
            while(true)
            {
            	MainTp4Serveur server = new MainTp4Serveur(serverSocket.accept());
                server.start();
            }
        }
        catch (IOException e)
        {
            System.out.println("Erreur d'écoute sur le port " + portNumber);
            System.out.println(e.getMessage());
        }
    }
}
