package parte2.v2;

public class Productor extends Thread implements Almacen{
	private static final int N = 5;
	private int productos;
	private MiMonitor moni;
	private int id;

	public Productor(MiMonitor moni, int productos, int id) {
		this.moni = moni;
		this.productos = productos;
		this.id = id;
	}
	
	public void run() {
		for(int i = 0; i < N; ++i) {
			Product[] arr = new Product[productos];
			for(int j = 0; j < productos; ++j)
				arr[j] = new Product(id, j);
			almacenar(arr);
		}
	}
	
	public void extraer(int p) {}
	
	public void almacenar(Product[] prods) {
		moni.deposit(prods);
	}
}
