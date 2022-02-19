package parte1;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		// Las variables compartidas para ambos
		VarsCompartidas vars = new VarsCompartidas();
		
		// Creamos los hilos que incrementan y decrementan respectivamente
		Incrementador i = new Incrementador(vars);
		Decrementador d = new Decrementador(vars);
		
		// Ahora hacemos start
		i.start();
		d.start();
		
		// Ahora hacemos join
		i.join();
		d.join();

	}

}
