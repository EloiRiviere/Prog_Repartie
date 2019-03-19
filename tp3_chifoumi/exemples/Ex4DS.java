package td3;

public class Ex4DS {
	long x;
	int id;
	
	public Ex4DS(int i) {
		id = i;
	}

	synchronized void count() {
		System.out.println(Thread.currentThread().getName() + " obtient verrou de x" + id);
		for(long i = 1; i <= (3+id) * 1000000000l; i++)
			x += i;
		System.out.println(Thread.currentThread().getName() + " libère verrou de x" + id);
	}

}
