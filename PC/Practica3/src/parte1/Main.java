package parte1;
import java.util.concurrent.Semaphore;

public class Main {
	
	private static final int M = 500;
	private static final int N = 10;

	public static void main(String[] args) throws InterruptedException{
		// Array para los threads de los dos tipos
		Incrementador[] icrem = new Incrementador[M];
		Decrementador[] dcrem = new Decrementador[M];
		
		// Solo necesitamos un semáforo
		Semaphore sem = new Semaphore(1);
		
		// El entero sobre el que trabajan los hilos
		Entero e = new Entero();
		
		// Creación de cada uno de los incrementadores y decrementadores
		for(int i = 0; i < M; ++i) {
			icrem[i] = new Incrementador(sem, e, N);
			dcrem[i] = new Decrementador(sem, e, N);
		}
		
		// Start de los hilos
		for(int i = 0; i < M; ++i) {
			icrem[i].start();
			dcrem[i].start();
		}
		
		// Que se esperen los hilos
		for(int i = 0; i < M; ++i) {
			icrem[i].join();
			dcrem[i].join();
		}
		
		// Imprimimos el valor final de la variable
		System.out.println("El valor final de la variable es: " + e.getValue());
	}

}
