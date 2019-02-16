package td3;

public class Ex6Barrier extends Thread {
	static int nt = 6;
	int id;
	static Object o = new Object();
	static int finished;
	
	public Ex6Barrier(int id) {
		this.id = id;
	}
	
	String space(int n) {
		return new String(new char[n]).replace('\0', ' ');
	}

	void race() {
		for(int i=0; i<=5; i++) {
			System.out.println(space(id * 8) + i);
			try {
				sleep((id+3) * 100);
			} catch (InterruptedException e) {}
		}
	}
	
	public void run() {
		race();
			
		synchronized(o) {
			finished++;
			o.notifyAll();
			while(finished != nt) {
				try {
					o.wait();
				} catch (InterruptedException e) {}
			}
		}
		
		race();
	}
	
	public static void main(String[] args) {
		Ex6Barrier t[] = new Ex6Barrier[nt];
		
		for(int i=0; i<nt; i++) {
			t[i] = new Ex6Barrier(i);
			t[i].start();
		}
	}

}
