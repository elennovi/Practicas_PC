package parte2.v1;

public class MiMonitor {
	private Product[] prods;
	private int ini, fin;
	private int K; // El tamaño del buffer
	private int contador;
	
	public MiMonitor(int K) {
		prods = new Product[K];
		this.K = K;
		ini = 0;
		fin = 0;
		contador = 0;
	}
	
	public synchronized void deposit(Product[] data) {
		while(K - contador < data.length) // Si no queda espacio, esperamos
			try {
				wait(); // DUDA: EL WAIT DÓNDE VA
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		// Almacenamos productos en el buffer
		for(int i = 0; i < data.length; ++i) {
			prods[fin] = data[i];
			fin = (fin + 1) % K;
			++contador;
		}
		System.out.println("Almacenamos " + data.length + " productos");
		// Mandamos una señal para indicar que ya se pueden consumir productos
		notifyAll();
	}
	
	public synchronized void fetch(int N) {
		//System.out.println("Cont : " + contador + " N: " + N);
		while(contador < N) // Esperamos a que haya tantos productos como los que queremos
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		Product[] data = new Product[N];
		// Extraemos los productos
		for(int i = 0; i < N; ++i) {
			data[i] = prods[ini];
			ini = (ini + 1) % K;
			--contador;
		}
		System.out.println("Extraemos " + N + " productos");
		// Mandamos una señal para indicar que ya se pueden almacenar productos
		notifyAll();
	}
}
