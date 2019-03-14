package tp5_serveur;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CombatServer {
	static boolean started = false;
	static final long startTime = System.nanoTime();

	private static ArrayList<ThreadServer> threads = new ArrayList<>();
	static ConcurrentHashMap<String,ThreadServer> magiciens = new ConcurrentHashMap<>();
	private static ArrayList<Sort> sortsDispo = new ArrayList<>();

	public static Sort randSort() throws InterruptedException {
		Sort ret;
		synchronized(sortsDispo) {
			if(sortsDispo.isEmpty()) {
				final Sort sorts[] = {
						Sort.FLECHE_GLACE, Sort.FLECHE_GLACE,
						Sort.FLECHE_FEU, Sort.FLECHE_FEU, Sort.FLECHE_FEU,
						Sort.FLECHE_EAU, Sort.FLECHE_EAU, Sort.FLECHE_EAU, Sort.FLECHE_EAU,
						Sort.GUERISON_EAU, Sort.GUERISON_EAU, Sort.GUERISON_EAU, Sort.GUERISON_EAU,
						Sort.SPHERE_GLACE, Sort.SPHERE_GLACE,
						Sort.SPHERE_FEU, Sort.SPHERE_FEU,
						Sort.INVISIBLE, Sort.INVISIBLE
					};

				sortsDispo.addAll(Arrays.asList(sorts));
				Collections.shuffle(sortsDispo);
			}

			ret = sortsDispo.get(sortsDispo.size()- 1);
			sortsDispo.remove(sortsDispo.size()- 1);
		}
		return ret;
	}
	
	public static synchronized void print(String msg) {
		String time = Double.toString(((System.nanoTime() - startTime)/10000000l)/100.);
		String head = time + " [";
		for(ThreadServer mag : magiciens.values()) {
			head += mag.getNom() + ":" + mag.getPv() + " ";
		}
		head = head.substring(0, head.length()-1) + "] ";
		String s = head + msg;
		System.out.println(s);
	}
	
	public static synchronized void remove(String nom) {
		magiciens.remove(nom);
	}
	
	public static synchronized boolean finished() {
		if(magiciens.size() == 1)
			for(ThreadServer mag : magiciens.values()) {
				System.out.println(mag.getNom() + " a gagné !");
			}
		return magiciens.size() <= 1;
	}
	public static synchronized ThreadServer getMagicien(String nom) {
		return magiciens.get(nom);
	}
	
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java ServerMultiObj2 <port number>");
            System.exit(1);
        }
        
        int portNumber = Integer.parseInt(args[0]);
        
        try (
            ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));
        ) {
        	System.out.println("Serveur prêt !");
        	try {
        		for(int i=0; i<4; i++) {
        			if(i==2)
        				serverSocket.setSoTimeout(5000);
        			ThreadServer mag = new ThreadServer(serverSocket.accept());
        			mag.start();
        			threads.add(mag);
                	System.out.println("Un magicien est arrivé.");
        		}
        	}catch(SocketTimeoutException e) {
        		System.out.println("On va commencer sans attendre des autres magiciens !");
        	}
        	//serverSocket.setSoTimeout(0);
        	
        	for(ThreadServer mag : threads) {
        		synchronized(mag) {
        			while(mag.getNom() == null) {
        				try {
							mag.wait();
						} catch (InterruptedException e) {
						}
        			}
        			magiciens.put(mag.getNom(), mag);
        		}
        	}
        	System.out.println("C'est parti !");
        	
    		synchronized(CombatServer.class) {
    			started = true;
    			CombatServer.class.notifyAll();
    		}
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
