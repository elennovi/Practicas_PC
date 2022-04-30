package parte2;
import java.util.concurrent.atomic.AtomicInteger;

public class LockTicket {
	private volatile int next;
	private AtomicInteger number;
	private volatile int turno;

	public LockTicket() {
		number = new AtomicInteger(1);
		next = 1;
		turno = 0;
	}
	
	public void takeLock() {
		turno = number.getAndIncrement();
		while (turno != next);
	}

	
	public void releaseLock() {
		++next;		
	}

}
