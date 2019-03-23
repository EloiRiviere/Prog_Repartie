package tp4;

import java.io.Serializable;

public class Donnees implements Serializable
{
	private static final long serialVersionUID = 8778418858108856051L;
	String nom, IP, s;

	public Donnees(String nom, String IP)
	{
		this.nom = nom;
		this.IP = IP;
		this.s = "";
	}
	
	public String getS()
	{
		return this.s;
	}

	public String getNom()
	{
		return this.nom;
	}

	public void setS(String s)
	{
		this.s = s;
	}

	public String toString()
	{
		return nom + " " + IP + " " + s;
	}
}
