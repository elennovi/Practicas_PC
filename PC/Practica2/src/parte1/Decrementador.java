package parte1;
import java.lang.Thread;

public class Decrementador extends Thread {
	private VarsCompartidas vars;
	private int N;
	private LockRompeEmpate lock;
	private static final int id = 2;
	
	Decrementador(VarsCompartidas vars, LockRompeEmpate lock, int N){
		this.vars = vars;
		this.lock = lock;
		this.N = N;
	}
	
	public void run() {
		lock.takeLock(id);
		for (int i = 0; i < N; i++)
			vars.decrementa();
		lock.releaseLock(id);
	}
}
