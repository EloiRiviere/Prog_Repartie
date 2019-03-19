package td2;

public class Ex2 implements Runnable {
	private long n, somme;
	
	public Ex2(long n) {
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
		Ex2 e1 = new Ex2(1000000000l);
		Thread t1 = new Thread(e1);
		Thread t2 = new Thread(new Ex2(2000000000l));
		Thread t3 = new Thread(new Ex2(3000000000l));
		Thread t4 = new Thread(new Ex2(4000000000l));
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		
		System.out.println("Le thread main est fini !");
	}
}
