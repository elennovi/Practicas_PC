package parte2;
import java.util.concurrent.Semaphore;

public class Compartidas {
	private volatile Producto p;
	private Semaphore empty, full;
	
	public Compartidas(Semaphore empty, Semaphore full) {
		this.empty = empty;
		this.full = full;
	}
	
	public Semaphore getEmpty() {
		return empty;
	}
	
	public Semaphore getFull() {
		return full;
	}
	
	public Producto getProducto() {
		return p;
	}
	
	public void setProducto(Producto p) {
		this.p = p;
	}
}
