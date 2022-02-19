package parte1;

public class VarsCompartidas {
	private volatile boolean in1;
	private volatile boolean in2;
	private volatile int last;
	private int entero;
	
	VarsCompartidas(){
		setIn1(false);
		setIn2(false);
		setLast(0);
		entero = 0;
	}

	public boolean isIn1() {
		return in1;
	}

	public void setIn1(boolean in1) {
		this.in1 = in1;
	}

	public boolean isIn2() {
		return in2;
	}

	public void setIn2(boolean in2) {
		this.in2 = in2;
	}

	public int getLast() {
		return last;
	}

	public void setLast(int last) {
		this.last = last;
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
