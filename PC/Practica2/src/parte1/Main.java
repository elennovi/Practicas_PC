package parte1;

public class Main {
	
	private static final int N = 10;
	
	public static void main(String[] args) throws InterruptedException {
		// Las variables compartidas para ambos
		VarsCompartidas vars = new VarsCompartidas();
		
		// Creamos un lock con los metodos que implementan la entrada y salida a 
		// la seccion critica
		LockRompeEmpate lock = new LockRompeEmpate();
		
		// Creamos los hilos que incrementan y decrementan respectivamente
		Incrementador i = new Incrementador(vars, lock, N);
		Decrementador d = new Decrementador(vars, lock, N);
		
		// Ahora hacemos start
		i.start();
		d.start();
		
		// Ahora hacemos join
		i.join();
		d.join();
		
		// Mostramos el valor final de la variable compartida entero
		System.out.println("El valor final es: " + vars.getEntero());

	}

}
