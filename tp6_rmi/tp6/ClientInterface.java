package tp6;

import java.rmi.*;

public interface ClientInterface extends Remote
{
	String println(String s) throws RemoteException;
}
