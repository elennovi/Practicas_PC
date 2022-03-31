package parte2.v1;

public class Product {
	private int id, i;
	
	public Product(int id, int i) {
		this.id = id;
		this.i = i;
	}
	
	public String toString() {
		return "Producto " + id + "." + i;
	}
}