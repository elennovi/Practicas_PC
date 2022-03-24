package parte1;
import java.util.concurrent.Semaphore;

public class Decrementador extends Thread {
	private Semaphore sem;
	private Entero e;
	private int N;
	
	Decrementador(Semaphore sem, Entero e, int N) {
		this.sem = sem;
		this.e = e;
		this.N = N;
	}
	
	public void run() {
		try {
			sem.acquire();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (int j = 0; j < N; j++) {
			e.decrementa();
		}
		sem.release();
	}
}