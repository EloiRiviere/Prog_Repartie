package td3;

public class Ex1 extends Thread {
	String s;
	
	public Ex1(String s) {
		this.s = s;
	}
	
	public void run() {
		for(int k = 0; k<10; k++) {
			System.out.print("J'");
			System.out.print("aime ");
			System.out.print("bien ");
			System.out.print("les ");
			System.out.println(s);
		}
	}

	public static void main(String[] args) {
		Ex1 t1 = new Ex1("harricots");
		Ex1 t2 = new Ex1("oranges");

		t1.start();
		t2.start();
	}

}
