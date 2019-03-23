package tp5_serveur;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;

public class ThreadServer extends Thread {
	private Socket clientSocket;
	private String nom;
	private boolean visible = true;
	private int pv = 10;
	private ArrayList<Sort> sorts = new ArrayList<>();
	ObjectOutputStream out;
	ObjectInputStream in;

	public ThreadServer(Socket clientSocket) {
		this.clientSocket = clientSocket;
		try {
			clientSocket.setSoTimeout(10000);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	synchronized int getPv() {
		return pv;
	}
	
	public String toString() {
		return nom;
	}
	
	public synchronized boolean isVisible() {
		return visible;
	}

	public synchronized void setVisible(boolean x) {
		visible = x;
	}

	void blesser(int npv) {
		synchronized(this) {
			pv -= npv;
			if(pv < 0)
				pv = 0;
		}
		
		//CombatServer.print(nom + " perd " + npv +"pv");
		
		if(pv == 0) {
			//System.out.println(nom + " est mort !");
            CombatServer.remove(getNom());
			this.interrupt();
		}
	}
	
	void soigner(int npv) {
		synchronized(this) {
			pv += npv;
		}
		//CombatServer.print(nom + " gagne " + npv +"pv");
	}
	
	Sort conjurer() throws InterruptedException {
		Sort sort = CombatServer.randSort();
		sorts.add(sort);
		//CombatServer.print( nom + " a reçu " + sort + " Il reste " + sorts);
		
		return sort;
	}
	
	void lancer(Sort sort, ThreadServer enemie) throws InterruptedException, IllegalMagicException, IOException {
		if(!sorts.remove(sort))
			throw(new IllegalMagicException("Sort pas dans la liste : " + sort));
		CombatServer.print(nom + " lance " + sort + " vers " + enemie + "\n        Il reste " + sorts );

		switch(sort) {
		case FLECHE_GLACE:
			sleep(3000);
			enemie.blesser(3);
			break;
		case FLECHE_FEU:
			sleep(2400);
			enemie.blesser(2);
			break;
		case FLECHE_EAU:
			sleep(1500);
			enemie.blesser(1);
			break;
		default:
			throw(new IllegalMagicException("Sort inconu avec enemie : " + sort));
		}
		
		write((Object)conjurer());
	}
	
	void lancer(Sort sort) throws InterruptedException, IllegalMagicException, IOException {
		if(!sorts.remove(sort))
			throw(new IllegalMagicException("Sort pas dans la liste : " + sort));

		CombatServer.print(nom + " lance " + sort + "\n        Il reste " + sorts );

		switch(sort) {
		case GUERISON_EAU:
				sleep(1000);
				soigner(1);
			break;
		case SPHERE_FEU:
			sleep(2900);
			for(String mag : CombatServer.magiciens.keySet())
				if(!mag.equals(nom))
					CombatServer.getMagicien(mag).blesser(2);			
			break;
		case SPHERE_GLACE:
			sleep(2200);
			for(String mag : CombatServer.magiciens.keySet())
				if(!mag.equals(nom))
					CombatServer.getMagicien(mag).blesser(1);			
			break;
		case INVISIBLE:
			setVisible(false);
			sleep(5000);
			setVisible(true);
			CombatServer.print(nom + " est de nouveau visible");
			break;
		default:
			throw new IllegalMagicException("Sort inconu");
		} 

		write((Object)conjurer());
	}
	
	synchronized String getNom() {
		return nom;
	}
	
	void write(Object o) throws IOException {
		out.writeObject(o);
	}
	
	public void run() {
		try {
			out = new ObjectOutputStream(clientSocket.getOutputStream());
	        in = new ObjectInputStream(clientSocket.getInputStream());

			synchronized (this) {
				nom = ((String) in.readObject() );
				if(nom.length() > 8)
					nom = nom.substring(0,8);
				notifyAll();
			}
			
			while(true) {
				synchronized(CombatServer.class) {
					if(CombatServer.started)
						break;
					else
						CombatServer.class.wait();
				}
			}

			for(int i = 0; i < 5; i++)
				write(conjurer());
			
			Object o;
            while (!CombatServer.finished()) {
            	o = in.readObject();
            	if(o instanceof Sort) {
            		Sort sort = (Sort) o;
            		if(sort.hasEnemy()) {
            			String enemy = (String) in.readObject();
            			ThreadServer x = CombatServer.getMagicien(enemy);
            			if(x != null)
            				lancer(sort, x);
            		}
            		else {
            			lancer(sort);
            		}
            	}
            	else if(o instanceof String) {
            		String s = (String) o;
            		if(s.equals("regarder")) {
            			ArrayList<String> list = new ArrayList<>();
            			for(String m :CombatServer.magiciens.keySet()) {
            				if(CombatServer.getMagicien(m).isVisible())
            					list.add(m);
            			}
            			Collections.shuffle(list);
            			out.writeObject(list);
            		}
            		else
            			throw new IllegalMagicException("Reçu un String inconu : " + s);
            	}
            	else
            		throw new IllegalMagicException("Reçu un objet inconu : " + o);
            }
	    } catch (InterruptedException e) {
            CombatServer.print("Le magicien " + getNom() + " est mort !");
            CombatServer.remove(getNom());
	    }
		catch (Exception e) {
			CombatServer.print("Le magicien " + getNom() + " a été pulvérisé !");
			CombatServer.print(e.getMessage());
            e.printStackTrace();
            CombatServer.remove(getNom());
		} finally {
			try {
				out.close();
				in.close();
				clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
