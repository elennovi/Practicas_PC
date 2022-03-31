package parte2.v2;

public class Consumidor extends Thread implements Almacen{
	private static final int N = 5;
	private int productos;
	private MiMonitor moni;

	public Consumidor(MiMonitor moni, int productos) {
		this.moni = moni;
		this.productos = productos;
	}
	
	public void run() {
		for(int i = 0; i < N; ++i)
			extraer(productos);
	}
	
	public void almacenar(Product[] productos) {}
	
	public void extraer(int p) {
		moni.fetch(p);
	}
	

}
