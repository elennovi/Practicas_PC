package parte2;

public class Entero {
	private int n;
	
	Entero() {
		n = 0;
	}
	
	public void incrementa() {
		n++;
		System.out.println("Incremento a " + n);
	}
	
	public void decrementa() {
		n--;
		System.out.println("Decremento a " + n);
	}
	
	public int getValue() {
		return n;
	}
}

