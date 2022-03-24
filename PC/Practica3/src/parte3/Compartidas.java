package parte3;
import java.util.concurrent.Semaphore;

public class Compartidas {
	private volatile Producto[] prods;
	private Semaphore empty, full, mutex_p, mutex_c;
	private volatile int ini, fin;
	private int K;
	
	public Compartidas(Semaphore empty, Semaphore full, Semaphore mutex_p, Semaphore mutex_c, int K) {
		prods = new Producto[K];
		this.empty = empty;
		this.full = full;
		this.mutex_c = mutex_c;
		this.mutex_p = mutex_p;
		this.K = K;
		ini = 0;
		fin = 0;
	}
	
	public Semaphore getP() {
		return mutex_p;
	}
	
	public Semaphore getC() {
		return mutex_c;
	}
	
	public Semaphore getEmpty() {
		return empty;
	}
	
	public Semaphore getFull() {
		return full;
	}
	
	public Producto getProducto() {
		int aux = ini;
		ini = (ini + 1) % K;
		return prods[aux];
	}
	
	public void setProducto(Producto p) {
		prods[fin] = p;
		fin = (fin + 1) % K;
		prods = prods;
	}
}
