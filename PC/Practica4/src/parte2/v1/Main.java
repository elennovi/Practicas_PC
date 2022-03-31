package parte2.v1;

public class Main {
	
	private static final int N_CONS = 4;
	private static final int N_PROD = 1;
	private static final int K = 5;
	private static final int ARBITRARIO = 3;

	public static void main(String[] args) throws InterruptedException {
		// Creamos los arrays de los hilos consumidor y productor
		Consumidor[] cons = new Consumidor[N_CONS];
		Productor[] prods = new Productor[N_PROD];
	
		// Creamos el monitor
		MiMonitor moni = new MiMonitor(K);
		
		// Inicializamos los arrays de productores y consumidores
		for(int i = 0; i < N_CONS; ++i) {
			cons[i] = new Consumidor(moni, ARBITRARIO);
		}
		for(int i = 0; i < N_PROD; ++i) {
			prods[i] = new Productor(moni, ARBITRARIO, i);
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
		System.out.println("Esto es todo, esto es todo, esto es todo amigos");

	}

}
