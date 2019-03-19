package td3;

public class Ex4Main extends Thread {
	static Ex4DS x1 = new Ex4DS(1);
	static Ex4DS x2 = new Ex4DS(2);
	
	public void run() {
		for(int k = 0; k<3; k++) {
			System.out.println(getName() + " demande verrou de x1");
			x1.count();
			
			System.out.println(getName() + " demande verrou de x2");
			x2.count();
		}
	}

	public static void main(String argsp[]) {
		Ex4Main t1 = new Ex4Main();
		Ex4Main t2 = new Ex4Main();
		
		t1.start();
		t2.start();
		System.out.println("Le thread main est fini !");
	}

}
