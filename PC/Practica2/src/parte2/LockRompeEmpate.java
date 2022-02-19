package parte2;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class LockRompeEmpate {
	private AtomicIntegerArray in;
	private AtomicIntegerArray last;
	private int N;
	
	LockRompeEmpate(int N) {
		in = new AtomicIntegerArray(N);
		last = new AtomicIntegerArray(N);
		this.N = N;
	}
	
	// El método que implementa el take
	public void takeLock(int i) {
		for (int j = 1; j < N; j++) {
			in.set(i - 1, j);
			last.set(j - 1, i);
			for (int k = 1; k < N; k++)
				if (k != i) 
					while(in.get(k - 1) >= in.get(i - 1) && last.get(j - 1) == i);
		}
	}
	
	// El metodo que implementa el release
	public void releaseLock(int i) {
		in.set(i - 1, 0);
	}
}
