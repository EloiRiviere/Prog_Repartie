package td2;

public class Ex1 extends Thread {
	private long n, somme;
	
	public Ex1(long n) {
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
		Ex1 t1 = new Ex1(1000000000l);
		Ex1 t2 = new Ex1(2000000000l);
		Ex1 t3 = new Ex1(3000000000l);
		Ex1 t4 = new Ex1(4000000000l);
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		
		System.out.println("Le thread main est fini !");
	}
}
