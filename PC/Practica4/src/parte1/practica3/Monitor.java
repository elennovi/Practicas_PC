package parte1.practica3;

public class Monitor {
	private Producto[] prods;
	private int ini, fin;
	private int K;
	private int contador;
	
	public Monitor(int K) {
		prods = new Producto[K];
		this.K = K;
		ini = 0;
		fin = 0;
		contador = 0;
	}
	
	public synchronized Producto extraer() {
		// Si el almacen está vacío hay que esperar a que haya algún producto
		while(contador == 0)
			try {
				// Y luego se echa la siesta
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		// Extraemos un producto
		--contador;
		int aux = ini;
		ini = (ini + 1) % K;
		// Notificamos a todos los hilos para que un productor almacene un producto
		notifyAll();
		return prods[aux];
	}
	
	public synchronized void almacenar(Producto p) {
		// Si el almacen está lleno hay que esperar a que se extraiga algún producto
		while(contador == K)
			try {
				// Y nos volvemos a echar la siesta
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		// Almacenamos un producto
		++contador;
		prods[fin] = p;
		fin = (fin + 1) % K;
		// Notificamos a todos los hilos para que un consumidor extraiga un producto
		notifyAll();
	}
}
