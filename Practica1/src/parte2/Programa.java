package parte2;

public class Programa {
	
	private static final int M = 500;
	private static final int N = 100;
	
	public static void main(String[] args) throws InterruptedException {
		// Creamos el objeto entero
		Entero e = new Entero();
		
		// Creamos los Arrays que guardara los hilos
		Incrementar[] inc = new Incrementar[M];
		Decrementar[] dec = new Decrementar[M];
		
		// Ahora Cremos los incrementadores y los decrementadores
		for (int i = 0; i < M; i++) {
			inc[i] = new Incrementar(e, N);
			dec[i] = new Decrementar(e, N);
		}
		
		// Llamamos a la funcion start para que se comiencen a ejecutar
		for (int i = 0; i < M; i++) {
			inc[i].start();
			dec[i].start();
		}
		
		// Ahora esperamos a que acaben todos ellos
		for (int i = 0; i < M; i++) {
			inc[i].join();
			dec[i].join();
		}
		
		// Mostramos el valor final del objeto entero creado
		System.out.println("El valor final deberia ser 0 pero es " + e.getValue());
	}
}
