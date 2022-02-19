package parte2;

public class Incrementador extends Thread {
	private int i;
	private LockRompeEmpate lock;
	private Entero e;
	
	Incrementador(int i, LockRompeEmpate lock, Entero e) {
		this.i = i;
		this.lock = lock;
		this.e = e;
	}
	
	public void run() {
		while (true) {
			lock.takeLock(i);
			System.out.println("Soy el incrementador " + i);
			e.incrementa();
			lock.releaseLock(i);
		}
	}
}
