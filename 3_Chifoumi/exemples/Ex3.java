package td3;

public class Ex3 extends Thread {
	static Object o1 = new Object(); // Verrou pour x1
	static long x1 = 0;
	static Object o2 = new Object(); // Verrou pour x2
	static long x2 = 0;
	
	public void run() {
		for(int k = 0; k<3; k++) {
			System.out.println(getName() + " demande verrou o1");
			synchronized(o1) {
				System.out.println(getName() + " obtient verrou o1");
				for(long i = 1; i <= 4000000000l; i++)
					x1 += i;
				System.out.println(getName() + " libère verrou o1");
			}
		
			System.out.println("                                   "
					+ getName() + " demande verrou o2");
			synchronized(o2) {
				System.out.println("                                   "
						+ getName() + " obtient verrou o2");
				for(long i = 1; i <= 5000000000l; i++)
					x2 += i;
				System.out.println("                                   " 
						+ getName() + " libère verrou o2");
			}
		}
	}
	

	public static void main(String[] args) {
		Ex3 t1 = new Ex3();
		Ex3 t2 = new Ex3();
		
		t1.start();
		t2.start();
		System.out.println("Le thread main est fini !");
	}
}
