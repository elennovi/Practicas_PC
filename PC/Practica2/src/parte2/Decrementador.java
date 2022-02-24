package parte2;

public class Decrementador extends Thread {
	private int i;
	private Lock lock;
	private Entero e;
	private int N;
	
	Decrementador(int i, Lock lock, Entero e, int N) {
		this.i = i;
		this.lock = lock;
		this.e = e;
		this.N = N;
	}
	
	public void run() {
		lock.takeLock(i);
		for (int j = 0; j < N; j++) {
			System.out.println("Soy el decrementador " + i);
			e.decrementa();
		}
		lock.releaseLock(i);
	}
}
