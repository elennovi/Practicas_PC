package parte2;

public class Main {
	private static final int M = 100;
	private static final int N = 10;
	
	public static void main(String[] args) throws InterruptedException {
		// El array para los threads de los dos tipos
		Incrementador[] icrem = new Incrementador[M];
		Decrementador[] dcrem = new Decrementador[M];
		
		// El lock
		LockRompeEmpate lock = new LockRompeEmpate(2 * M);
		
		// El entero que se incrementa y se decrementa
		Entero e = new Entero();
		
		// La creacion de cada uno de los incrementadores decrementadores
		int id = 1;
		for (int i = 0; i < M; i++) {
			icrem[i] = new Incrementador(id, lock, e, N);
			++id;
			dcrem[i] = new Decrementador(id, lock, e, N);
			++id;
		}
		
		// Hacemos start de todos los hilos
		for (int i = 0; i < M; i++)  {
			icrem[i].start();
			dcrem[i].start();
		}
		
		// Hacemos el join para que se espere
		for (int i = 0; i < M; i++)  {
			icrem[i].join();
			dcrem[i].join();
		}
		
		// Mostramos el valor final 
		System.out.println("El valor final es: " + e.getValue());
	}

}
