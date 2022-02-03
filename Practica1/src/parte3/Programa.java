package parte3;

public class Programa {
	private static final int N = 3;
	private static final int[][] m1 = {{1,2,3},{4,5,6},{7,8,9}};
	private static final int [][] m2 = {{9,8,7},{6,5,4},{3,2,1}};
	
	public static void main(String[] args) throws InterruptedException {
		// Creamos la matriz compartida
		Compartida c = new Compartida(N);
		
		// Creamos el array de hilos
		Hilo[] hilos = new Hilo[N];
		
		// Creamos los hilos
		for(int i = 0; i < N; ++i) {
			hilos[i] = new Hilo(m1, m2, c, i);
		}
		
		// Ejecutamos los hilos
		for(int i = 0; i < N; ++i) {
			hilos[i].start();
		}
		
		// Esperamos a que hayan terminado todos
		for(int i = 0; i < N; ++i) {
			hilos[i].join();
		}
		
		// Mostramos el valor final de la matriz compartida
		System.out.println("El valor de la matriz es:");
		c.mostrar();
	}
}
