package parte1.practica2;

public class Incrementador extends Thread{
	private Monitor moni;
	private int N;
	
	public Incrementador(Monitor moni, int N) {
		this.moni = moni;
		this.N = N;
	}
	
	public void run() {
		for (int j = 0; j < N; j++)
			moni.incrementar();
	}
}
