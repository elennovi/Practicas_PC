package parte2;

public class Decrementar extends Thread{
	private Entero e;
	private int N;
	
	Decrementar(Entero e, int N){
		this.e = e;
		this.N = N;
	}
	
	public void run() {
		for (int i = 0; i < N; i++) {
			e.decrementa();
		}
	}
}
