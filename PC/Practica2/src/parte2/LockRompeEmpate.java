package parte2;

public class LockRompeEmpate extends Lock{
	private volatile int[] in;
	private volatile int[] last;
	private int N;
	
	LockRompeEmpate(int N) {
		this.N = N;
		in = new int[N + 1];
		last = new int[N + 1];
	}
	
	// El m�todo que implementa el take
	public void takeLock(int i) {
		for (int j = 1; j <= N; j++) {
			in[i - 1] = j;
			in = in;
			last[j - 1] = i;
			last = last;
			for (int k = 1; k < N; k++)
				if (k != i) 
					while(in[k - 1] >= in[i - 1] && last[j - 1] == i);
		}
	}
	
	// El metodo que implementa el release
	public void releaseLock(int i) {
		in[i - 1] = 0;
		in = in;
	}
}
