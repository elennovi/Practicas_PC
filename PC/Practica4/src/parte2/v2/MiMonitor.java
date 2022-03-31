package parte2.v2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MiMonitor {
	private Product[] prods;
	private int ini, fin;
	private int K; // El tamaño del buffer
	private int contador;
	private final Lock l = new ReentrantLock();
	private final Condition cFull = l.newCondition();
	private final Condition cEmpty = l.newCondition();
	
	public MiMonitor(int K) {
		prods = new Product[K];
		this.K = K;
		ini = 0;
		fin = 0;
		contador = 0;
	}
	
	public void deposit(Product[] data) {
		l.lock();
		while(K - contador < data.length) // Si no queda espacio, esperamos
			try {
				cEmpty.await();
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
		cFull.signal();
		l.unlock();
	}
	
	public void fetch(int N) {
		l.lock();
		while(contador < N) // Esperamos a que haya tantos productos como los que queremos
			try {
				cFull.await();
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
		cEmpty.signal();
		l.unlock();
	}
}
