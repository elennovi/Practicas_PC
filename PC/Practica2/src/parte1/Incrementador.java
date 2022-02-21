package parte1;
import java.lang.Thread;

public class Incrementador extends Thread {
	private VarsCompartidas vars;
	private int N;
	private LockRompeEmpate lock;
	private static final int id = 1;
	
	Incrementador(VarsCompartidas vars, LockRompeEmpate lock, int N){
		this.vars = vars;
		this.N = N;
		this.lock = lock;
	}
	
	public void run() {
		lock.takeLock(id);
		for (int i = 0; i < N; i++)
			vars.incrementa();
		lock.releaseLock(id);
	}
}
