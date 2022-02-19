package parte2;

public class Incrementar extends Thread{
	private Entero e;
	private int N;
	
	Incrementar(Entero e, int N){
		this.e = e;
		this.N = N;
	}
	
	public void run() {
		for (int i = 0; i < N; i++) {
			e.incrementa();
		}		
	}
	
}
