package parte2;

public class Producto {
	private int id, i;
	
	public Producto(int id, int i) {
		this.id = id;
		this.i = i;
	}
	
	public String toString() {
		return "Producto " + id + "." + i;
	}
}