package tp7;

import java.rmi.*;

public interface ClientInterface extends Remote
{
	String println(String s) throws RemoteException;
	void resultat(int n, int x) throws RemoteException;
}