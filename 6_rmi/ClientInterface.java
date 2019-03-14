package tp6_rmi;

import java.rmi.*;

public interface ClientInterface extends Remote
{
	String println(String s) throws RemoteException;
}