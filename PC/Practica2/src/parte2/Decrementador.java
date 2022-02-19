package parte2;

public class Decrementador extends Thread {
	private int i;
	private LockRompeEmpate lock;
	private Entero e;
	
	Decrementador(int i, LockRompeEmpate lock, Entero e) {
		this.i = i;
		this.lock = lock;
		this.e = e;
	}
	
	public void run() {
		while (true) {
			lock.takeLock(i);
			System.out.println("Soy el decrementador " + i);
			e.decrementa();
			lock.releaseLock(i);
		}
	}
}
