package parte1.practica2;

public class Decrementador extends Thread{
	private Monitor moni;
	private int N;
	
	Decrementador(Monitor moni, int N) {
		this.moni = moni;
		this.N = N;
	}
	
	public void run() {
		for (int j = 0; j < N; j++)
			moni.decrementar();
	}
}
