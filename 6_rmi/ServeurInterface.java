package tp6_rmi;

import java.rmi.*;

public interface ServeurInterface extends Remote
{
	String println(ClientInterface c, String s) throws RemoteException;
	void register(ClientInterface client) throws RemoteException;
	void unregister(ClientInterface client) throws RemoteException;
}