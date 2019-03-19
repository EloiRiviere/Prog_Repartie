package td2;

public class Ex5 implements Runnable {
	private long n, somme;
	
	public Ex5(long n) {
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
		Ex5 e1 = new Ex5(1000000000l);
		Thread t1 = new Thread(e1);
		Ex5 e2 = new Ex5(2000000000l);
		Thread t2 = new Thread(e2);
		Ex5 e3 = new Ex5(3000000000l);
		Thread t3 = new Thread(e3);
		Ex5 e4 = new Ex5(4000000000l);
		Thread t4 = new Thread(e4);
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		
		try {
			t1.join();
			t2.join();
			t3.join();
			t4.join();
			System.out.println("t1 à calculé " + e1.somme);
			System.out.println("t2 à calculé " + e2.somme);
			System.out.println("t3 à calculé " + e3.somme);
			System.out.println("t4 à calculé " + e4.somme);
		} catch (InterruptedException e) {
			System.out.println("Je ne veux plus attendre !");
			return;
		}
		System.out.println("Les autres threads sont finis !");
	}
}
