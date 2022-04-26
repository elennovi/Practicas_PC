
package parte2;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class LockTicket extends Lock {
	private int N;
	private volatile int next;
	private AtomicInteger number;
	private AtomicIntegerArray turn;

	public LockTicket(int N) {
		this.N = N;
		turn = new AtomicIntegerArray(N + 1);
		number = new AtomicInteger(1);
		next = 1;
		for(int i = 1; i <= this.N; ++i)
			turn.set(i, 0);
	}
	
	public void takeLock(int i) {
		turn.set(i, number.getAndIncrement());
		while(turn.get(i) != next);
	}

	
	public void releaseLock(int i) {
		++next;		
	}

}
