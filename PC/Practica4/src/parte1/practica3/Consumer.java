package parte1.practica3;

public class Consumer extends Thread implements Almacen {
	private static final int N = 5;
	private Monitor moni;
	
	public Consumer(Monitor moni) {
		this.moni = moni;
	}
	
	public void run() {
		for(int i = 0; i < N; ++i) {
			System.out.println("Extraemos: " + extraer().toString());
		}
	}

	public void almacenar(Producto producto) {}

	public Producto extraer() {
		return moni.extraer();
	}

}
