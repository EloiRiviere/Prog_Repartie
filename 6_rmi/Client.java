package tp6_rmi;

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

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException, InterruptedException {
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
		System.out.print("Entrez votre nom: ");
		String nom = scanner.nextLine();
		while(true)
		{
			String s = scanner.nextLine();
			if(s.equals("")) break;
			/*String reponse = */serveur.println(client,"[" + nom + "]: " + s);
			//System.out.println(reponse);
		}
		
		serveur.unregister(client);
		UnicastRemoteObject.unexportObject(client, true);
	}
}