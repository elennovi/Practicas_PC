package parte3;
import java.util.concurrent.Semaphore;

import parte3.Compartidas;
import parte3.Consumer;
import parte3.Producer;

public class Main {
	
	private static final int N_CONS = 100;
	private static final int N_PROD = 100;
	private static final int K = 10;

	public static void main(String[] args) throws InterruptedException {
		// Creamos los arrays de los hilos consumidor y productor
		Consumer[] cons = new Consumer[N_CONS];
		Producer[] prods = new Producer[N_PROD];
	
		// Creamos las variables compartidas y los semáforos
		Semaphore empty = new Semaphore(K);
		Semaphore full = new Semaphore(0);
		Semaphore mutex_p = new Semaphore(1);
		Semaphore mutex_c = new Semaphore(1);
		Compartidas c = new Compartidas(empty, full, mutex_p, mutex_c, K);
		
		// Inicializamos los arrays de productores y consumidores
		for(int i = 0; i < N_CONS; ++i) {
			cons[i] = new Consumer(c);
		}
		for(int i = 0; i < N_PROD; ++i) {
			prods[i] = new Producer(c, i);
		}
		
		// Llamamos al método start de cada hilo
		for(int i = 0; i < N_CONS; ++i) {
			cons[i].start();
		}
		for(int i = 0; i < N_PROD; ++i) {
			prods[i].start();
		}
		
		// Hacemos que se esperen los hilos
		for(int i = 0; i < N_CONS; ++i) {
			cons[i].join();
		}
		for(int i = 0; i < N_PROD; ++i) {
			prods[i].join();
		}

	}

}
