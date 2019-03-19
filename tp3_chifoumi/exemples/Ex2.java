package td3;

public class Ex2 extends Thread {
	String s;
	static Object o = new Object();
	
	public Ex2(String s) {
		this.s = s;
	}
	
	public void run() {
		for(int k = 0; k<10; k++) {
			synchronized(o) {
				System.out.print("J'");
				System.out.print("aime ");
				System.out.print("bien ");
				System.out.print("les ");
				System.out.println(s);
			}
		}
	}

	public static void main(String[] args) {
		Ex2 t1 = new Ex2("harricots");
		Ex2 t2 = new Ex2("oranges");

		t1.start();
		t2.start();
	}
}

