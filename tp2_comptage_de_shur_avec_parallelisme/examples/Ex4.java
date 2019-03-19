package td2;

public class Ex4 extends Thread {
	long n, somme;
	
	public Ex4(long n) {
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
		Ex4 t1 = new Ex4(1000000000l);
		Ex4 t2 = new Ex4(2000000000l);
		Ex4 t3 = new Ex4(3000000000l);
		Ex4 t4 = new Ex4(4000000000l);
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		
		try {
			t1.join();
			t2.join();
			t3.join();
			t4.join();
			System.out.println("t1 à calculé " + t1.somme);
			System.out.println("t2 à calculé " + t2.somme);
			System.out.println("t3 à calculé " + t3.somme);
			System.out.println("t4 à calculé " + t4.somme);
		} catch (InterruptedException e) {
			System.out.println("Je ne veux plus attendre !");
			return;
		}
		System.out.println("Les autres threads sont finis !");
	}
}
