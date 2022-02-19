package parte1;
import java.lang.Thread;

public class Hilo extends Thread{
	private long T;
	
	// Constructor que utiliza el valor predeterminado para los milisegundos
	Hilo() {
		this(1000);
	}
	// Constructor que recibe una cantidad de milisegundos de entrada
	Hilo(long T){
		this.T = T;
	}
	
	public void run() {
		// Conseguimos nuestro ID de proceso y lo mostramos por pantalla
		System.out.println("Mi ID es: " + super.getId());
		// Ahora esperamos una cantidad determinada de segundos
		try {
			super.sleep(T);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// Conseguimos nuestro ID de proceso y lo mostramos por pantalla al terminar su ejecución
		System.out.println("Mi ID es: " + super.getId());
	}
}
