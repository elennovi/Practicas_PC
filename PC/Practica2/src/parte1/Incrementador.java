package parte1;
import java.lang.Thread;

public class Incrementador extends Thread {
	VarsCompartidas vars;
	
	Incrementador(VarsCompartidas vars){
		this.vars = vars;
	}
	
	public void run() {
		while(true) {
			// Ha entrado el proceso 1 a ejecución
			vars.setIn1(true);
			vars.setLast(1);
			// Await
			while(vars.isIn2() && vars.getLast() == 1);
			// Entrada a la seccion critica
			vars.incrementa();
			// Salimos de la seccion critica
			vars.setIn1(false);
		}
	}
}
