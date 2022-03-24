package parte1.practica3;

public class Producer extends Thread implements Almacen{
	private static final int N = 5;
	private Monitor moni;
	private int id;
	
	public Producer(Monitor moni, int id) {
		this.moni = moni;
		this.id = id;
	}
	
	public void run() {
		for(int i = 0; i < N; ++i) {
			Producto p = new Producto(id, i);
			System.out.println("Almacenamos: " + p.toString());
			almacenar(p);
		}
	}
	
	public void almacenar(Producto p) {
		moni.almacenar(p);
	}

	public Producto extraer() { return null; }

}
