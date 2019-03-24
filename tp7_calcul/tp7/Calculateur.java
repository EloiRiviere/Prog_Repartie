package tp7;

import java.rmi.*;

public class Calculateur extends Thread
{
	int n;
	ClientInterface c;

	public Calculateur(int n, ClientInterface c)
	{
		this.n = n;
		this.c = c;
	}
	public void run()
	{
		try
		{
			c.resultat(n,calc(n));	
		}
		catch(RemoteException e)
		{
			e.printStackTrace();
		}
		
	}

	public int calc(int n)
	{
		int x = 0;
		for(int i = 1; i <= n; i++)
		for(int j = 1; j <= i; j++)
		{
			x += i % 777;
			x = x % 777;
		}
		return x;
	}
}