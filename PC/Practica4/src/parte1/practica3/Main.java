package parte1.practica3;

public class Main {

	private static final int N_CONS = 5;
	private static final int N_PROD = 5;
	private static final int K = 5;

	public static void main(String[] args) throws InterruptedException {
		// Creamos los arrays de los hilos consumidor y productor
		Consumer[] cons = new Consumer[N_CONS];
		Producer[] prods = new Producer[N_PROD];
	
		// Creamos el monitor
		Monitor moni = new Monitor(K);
		
		// Inicializamos los arrays de productores y consumidores
		for(int i = 0; i < N_CONS; ++i) {
			cons[i] = new Consumer(moni);
		}
		for(int i = 0; i < N_PROD; ++i) {
			prods[i] = new Producer(moni, i);
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
