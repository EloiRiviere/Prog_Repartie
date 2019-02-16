package td3;

public class Ex5Waiter extends Thread {
	static boolean cafe = false;
	static Object o = new Object();

	public void run(){
		while(true) {
			try {
				sleep(1500);
			} catch (InterruptedException e) {
				System.out.println("Waiter : Interrompu !");
				return;
			}
			
			synchronized(o) {
				cafe = true;
				System.out.println("Waiter : J'ai servi le café.");
				o.notifyAll();
			}
		}
	}
	
	public static void main(String args[]) {
		Ex5Waiter w = new Ex5Waiter();
		w.start();
		
		for(int i = 0; i<5;) {
			synchronized (o) {
				while(!cafe) {
					System.out.println("Main : Il n'y a pas de café !!!");
					try {
						o.wait();
					} catch (InterruptedException e) {
						System.out.println("Main : Interrompu !");
					}
				}
        i++;
        cafe = false;
        System.out.println("Main : J'ai bu le café.");
			}
		}
		
		System.out.println("Main : J'ai trop bu !");
		w.interrupt();
	}
}
