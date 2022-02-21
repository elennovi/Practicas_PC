package parte1;

public class VarsCompartidas {
	private int entero;
	
	VarsCompartidas(){
		entero = 0;
	}
	
	public int getEntero() {
		return entero;
	}
	
	public void incrementa() {
		entero++;
		System.out.println("Incremento a " + entero);
	}
	
	public void decrementa() {
		entero--;
		System.out.println("Decremento a " + entero);
	}
}
