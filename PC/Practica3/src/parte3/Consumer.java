package parte3;

public class Consumer extends Thread implements Almacen{
	
	private Compartidas c;
	private static final int N = 10;
	
	public Consumer(Compartidas c) {
		this.c = c;
	}
	public void run() {
		for(int i = 0; i < N; ++i) {
			try {
				c.getFull().acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Imprimimos el producto extraido
			System.out.println("Extraido " + extraer().toString());
			c.getC().release();
			c.getEmpty().release();
		}
	}
	// El consumidor no almacena
	public void almacenar(Producto producto) {}

	public Producto extraer() {
		try {
			c.getC().acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Extraermos el producto del almacen
		return c.getProducto();
	}

}
