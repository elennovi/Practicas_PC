package parte1;
import java.lang.Thread;

public class Decrementador extends Thread {
	VarsCompartidas vars;
	
	Decrementador(VarsCompartidas vars){
		this.vars = vars;
	}
	
	public void run() {
		while(true) {
			// El proceso dos ha entrado a ejecutar
			vars.setIn2(true);
			vars.setLast(2);
			// Await
			while(vars.isIn1() && vars.getLast() == 2);
			// Entrada a la seccion critica
			vars.decrementa();
			// Salida de la seccion critica
			vars.setIn2(false);
		}
	}
}
