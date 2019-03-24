package tp7;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Client extends UnicastRemoteObject implements ClientInterface {
	private static final long serialVersionUID = 3377127794011722830L;

	public Client() throws RemoteException {
		super();
	}

	public String println(String s) throws RemoteException {
		System.out.println(s);
		return "Le client a imprimé " + s;
	}

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException, InterruptedException
	{
		if (args.length != 1) {
			System.err.println("Usage: Client <serveur>");
			return;
		}
		
		String url = new String("rmi://" + args[0] + "/MonServeur");
		ServeurInterface serveur =  (ServeurInterface) Naming.lookup(url);
		System.out.println("J'ai trouvé le serveur à " + url);
		
		Client client = new Client();
		serveur.register(client);
		System.out.println("Je suis enregistré."); 


		Scanner scanner = new Scanner(System.in);
		System.out.print("Entrez le nombre: ");
		String nombre = scanner.nextLine();
		while(true)
		{
			/*String reponse = */serveur.calculer(Integer.parseInt(nombre),client);
			System.out.println("J’ai envoyé " + nombre + " au serveur.");
			nombre = scanner.nextLine();
			if(nombre.equals("")) break;
		}
		
		serveur.unregister(client);
		UnicastRemoteObject.unexportObject(client, true);
	}

	public void resultat(int n, int x) throws RemoteException
	{
		System.out.println("Le calcul pour n = " + n + " est " + x + ".");
	}
}