package parte2;

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
			extraer();
			c.getEmpty().release();
		}
	}
	// El consumidor no almacena
	public void almacenar(Producto producto) {}

	public Producto extraer() {
		// Imprimimos el producto extraido
		System.out.println("Extraido " + c.getProducto().toString());
		// Extraermos el producto del almacen
		Producto aux = c.getProducto();
		// Ponemos a null el almacen porque ya no hay productos
		c.setProducto(null);
		return aux;
	}

}
