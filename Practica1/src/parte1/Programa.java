package parte1;
public class Programa {
	
	private static final int N = 10;
	public static void main(String[] args) throws InterruptedException {
		Hilo[] hilos = new Hilo[N];
		// Creamos los N hilos
		for (int i = 0; i < N; i++) {
			hilos[i] = new Hilo(i * 100);
		}
		// Llamamos al run de todos
		for (int i = 0; i < N; i++) {
			hilos[i].start();
		}
		// Ahora hacemos que espere a que acaben todos los hilos
		for (int i = 0; i < N; i++) {
			hilos[i].join();
		}
		// Ahora indicamos que han acabado de ejecutarse todos los hilos
		System.out.println("Ya han acabado todos los hilos");
	}

}
