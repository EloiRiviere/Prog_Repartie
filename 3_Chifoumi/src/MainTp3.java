package src;

import java.util.Scanner;

public class MainTp3 extends Thread
{
	/* Variables de classes */
	// objet pour le synchronize
	static Object o = new Object();
	// cadenas pour synchroniser les threads
	static int cadenas;
	// nombre de victoires de chaque joueur
	static int nombre_victoires_j1, nombre_victoires_j2;
	// scanner d'entrée clavier
	static Scanner sc = new Scanner(System.in);

	/* variables d'instance */
	// nom du joueur
	String nom;
	// choix du joueur
	int choix;

  // code à la création d'instance
	public MainTp3()
	{

	}

	// getter de choix d'un joueur
	public int getChoix()
	{
		return choix;
	}

	// getter de nom d'un joueur
	public String getNom()
	{
		return nom;
	}

	// setter de nom d'un joueur
	public void setNom()
	{
		System.out.print("Rentre ton nom: ");
		this.nom = sc.nextLine();
	}

	// setter de choix d'un joueur
	public void choisir()
	{
		System.out.print(nom + ", rentre 0 (pierre), 1 (feuille) ou 2 (ciseaux): ");
		choix = Integer.parseInt(sc.nextLine());
	}

	//code exécuté par les threads
	public void run()
	{
		//synchronisation des threads
		synchronized(o)
		{
			// premier tour, demande du nom du joueur et de son choix
			this.setNom();
			this.choisir();
			// on incrémente le compteur
			cadenas++;

			// si c'est le premier des deux threads à jouer, il doit dormir en attendant que second thread joue
			if(cadenas != 2)
			{
				// on attend que le second thread ait fini de jouer et que le thread principal nous appelle
				try	{	o.wait(); o.wait();	}
				catch (InterruptedException e) { System.out.println("Thread joueur 1 interrompu."); return; }
			}
			// si c'est le second thread à jouer, il doit donner la main au thread principal et attendre que ce dernier le rappelle
			else
			{
				try { o.notifyAll(); o.wait(); }
				catch (InterruptedException e) { System.out.println("Thread joueur 2 interrompu."); return; }
			}

			traitement_thread_joueur();
		}


	}

	// traitement d'un thread joueur
	public void traitement_thread_joueur()
	{
		// boucle de jeu, traitement que fera le thread à chaque nouvel appel du thread principal
		while(true)
		{
			synchronized(o)
			{
				this.choisir();
				// on incrémente le compteur
				cadenas++;

				// si c'est le premier des deux threads à jouer, il doit dormir en attendant que second thread joue
				if(cadenas != 2)
				{
					// on attend que le second thread ait fini de jouer et que le thread principal nous appelle
					try	{	o.wait(); o.wait();	}
					catch (InterruptedException e) { System.out.println("Thread joueur 1 interrompu."); return; }
				}
				// si c'est le second thread à jouer, il doit donner la main au thread principal et attendre que ce dernier le rappelle
				else
				{
					try { o.notifyAll(); o.wait(); }
					catch (InterruptedException e) { System.out.println("Thread joueur 2 interrompu."); return; }
				}
			}
		}
	}


	// méthode main, code du thread principal
	public static void main(String args[])
	{
		// on initialise les variables de classe
		nombre_victoires_j1 = 0;
		nombre_victoires_j2 = 0;
		cadenas = 0;
		// on crée et lance les threads
		MainTp3 joueur1 = new MainTp3();
		MainTp3 joueur2 = new MainTp3();
		joueur1.start();
		joueur2.start();
		int i;
		synchronized(o)
		{
			//on s'endort (laisse les threads choisir nom et choix)
			try { o.wait(); }
			catch(InterruptedException e) { System.out.println("Exception wait"); }
		}

		jeu(joueur1,joueur2);

		if(nombre_victoires_j1 == 3){System.out.println(joueur1.getNom() + " a gagné !");}
		else{System.out.println(joueur2.getNom() + " a gagné !");}

		//on tue les threads
		joueur1.interrupt();
		joueur2.interrupt();
	}

	public static void jeu(MainTp3 joueur1, MainTp3 joueur2)
	{
		// traitement du jeu
		while(true)
		{
			System.out.println("\n" + joueur1.getNom() + " joue " + joueur1.getChoix() + " et " + joueur2.getNom() + " joue " + joueur2.getChoix());

			if(joueur1.getChoix() == joueur2.getChoix())
			{
				System.out.println("Match nul.");
			}
			else if(joueur1.getChoix() == 0)
			{
				if(joueur2.getChoix()== 1)
				{
					nombre_victoires_j2 ++;
					System.out.println(joueur2.getNom() + " a gagné ! " + nombre_victoires_j1 + " points contre " + nombre_victoires_j2 + " points.\n");
				}
				else if(joueur2.getChoix() == 2)
				{
					nombre_victoires_j1 ++;
					System.out.println(joueur1.getNom() + " a gagné ! " + nombre_victoires_j1 + " points contre " + nombre_victoires_j2 + " points.");
				}
			}
			else if(joueur1.getChoix() == 1)
			{
				if(joueur2.getChoix()== 0)
				{
					nombre_victoires_j1 ++;
					System.out.println(joueur1.getNom() + " a gagné ! " + nombre_victoires_j1 + " points contre " + nombre_victoires_j2 + " points.");
				}
				else if(joueur2.getChoix() == 2)
				{
					nombre_victoires_j2 ++;
					System.out.println(joueur2.getNom() + " a gagné ! " + nombre_victoires_j1 + " points contre " + nombre_victoires_j2 + " points.");
				}
			}
			else
			{
				if(joueur2.getChoix()== 0)
				{
					nombre_victoires_j2 ++;
					System.out.println(joueur2.getNom() + " a gagné ! " + nombre_victoires_j1 + " points contre " + nombre_victoires_j2 + " points.");
				}
				else if(joueur2.getChoix() == 1)
				{
					nombre_victoires_j1 ++;
					System.out.println(joueur1.getNom() + " a gagné ! " + nombre_victoires_j1 + " points contre " + nombre_victoires_j2 + " points.");
				}
			}
			System.out.println("\n");

			// si on arrive pas à 3 manches gagnantes pour un des 2 joueurs, le jeu continue
			if(nombre_victoires_j1 < 3 && nombre_victoires_j2 < 3)
			{
				synchronized(o)
				{
					//reset le cadenas
					cadenas = 0;
					//réveille les threads
					o.notifyAll();
					// le thread principal s'endort en attendant l'appel du dernier thread joueur
					try { o.wait(); }
					catch (InterruptedException e) { System.out.println("Exception wait"); }
				}
			}
			// si on arrive à 3 manches gagnantes pour un des 2 joueurs, on sort de la boucle infinie de jeu
			else { break; }
		}
	}
}
