package tp5_client;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

import tp5_serveur.CombatServer;
import tp5_serveur.Sort;

public class Magicien implements Serializable
{
	String nom;
	List<Sort> sorts;
	int pointsVie;
	private static final long serialVersionUID = 6548789532654875445L;

	public Magicien(String nom)
	{
		this.nom = nom;
		this.pointsVie = 10;
		this.sorts = new ArrayList<Sort>();
		try
		{
			for(int i=0; i<5; i++)
			{
				this.sorts.add(CombatServer.randSort());
			}
		}
		catch (InterruptedException e)
        {
          e.printStackTrace();
        }
	}

	public Sort jeter_sort()
	{
		Sort s = this.sorts.get(0);
		this.sorts.remove(0);
		return s;
	}

	public void add_sort(Sort sort)
	{
		this.sorts.add(sort);
	}

	@Override
	public String toString()
	{
		//return "[" + this.nom + "] ";
		return this.nom;
	}
}
