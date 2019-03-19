package tp6;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Serveur extends UnicastRemoteObject implements ServeurInterface
{
	private static final long serialVersionUID = 4387951679834818249L;
	ArrayList<ClientInterface> clients = new ArrayList<>();
	int count = 0;

	protected Serveur() throws RemoteException
	{
		super();
		/*
		try
		{
			Runtime runtime = new Runtime.getRuntime();
			runtime.exec("export ClassPath=.; rmiregistry &");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		*/
	}

	public synchronized void register(ClientInterface client) throws RemoteException
	{
		clients.add(client);
		System.out.println("Un client a enregistré. Ça fait " + clients.size());
	}

	public synchronized void unregister(ClientInterface client) throws RemoteException
	{
		clients.remove(client);
		System.out.println("Un client a quitté. Ça fait " + clients.size());
	}

	public synchronized String println(ClientInterface c, String s) throws RemoteException
	{
		System.out.println(s);

		for(ClientInterface client : clients)
		{
			if(!client.equals(c))
			{
				client.println(s);
			}
		}

		return "Le serveur a imprimé " + s;
	}

	public static void main(String[] args) throws RemoteException, MalformedURLException
	{
		Serveur r = new Serveur();
		System.out.println("Serveur crée.");
		Naming.rebind("MonServeur", r);
		System.out.println("Serveur accessible publiquement.");
	}
}
