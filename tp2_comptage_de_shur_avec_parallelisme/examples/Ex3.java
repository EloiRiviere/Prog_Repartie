package td2;

public class Ex3 extends Thread {
	private long n, somme;
	
	public Ex3(long n) {
		this.n = n;
	}
	
	public void run() {
		System.out.println("Thread commence pour n = " + n);
		calcul();
		System.out.println("La somme des entiers de 1 à " + n + " est " + somme);
	}
	
	void calcul() {
		somme = 0;
		
		for(long i = 1; i <= n; i++)
			somme += i;
	}

	public static void main(String[] args) {
		int nt = 20;  // nombre de threads
		Ex3 t[] = new Ex3[nt];
		
		for(int i = 0; i < nt; i++)
			t[i] = new Ex3(i+1000000000l);
		
		for(int i = 0; i < nt; i++)
			t[i].start();
		
		System.out.println("Le thread main est fini !");
	}
}
