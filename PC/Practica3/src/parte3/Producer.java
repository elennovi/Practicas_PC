package parte3;

public class Producer extends Thread implements Almacen {
	private Compartidas c;
	private int id;
	private static final int N = 10;
	
	public Producer(Compartidas c, int id) {
		this.c = c;
		this.id = id;
	}

	public void run() {
		for(int i = 0; i < N; ++i) {
			try {
				c.getEmpty().acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			almacenar(new Producto(id, i));
			c.getFull().release();
		}
	}
		
	@Override
	public void almacenar(Producto producto) {
		try {
			c.getP().acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Imprimimos el producto que estmos almacenando
		System.out.println("Almacenando " + producto);
		// Ponemos en el almacen el nuevo producto
		c.setProducto(producto);
		c.getP().release();
	}

	@Override
	public Producto extraer() {return null;}
}
