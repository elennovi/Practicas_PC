package parte2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MiMonitor {
	// El numero de escritores y el numero de lectores
	private int nw = 0, nr = 0;
	private final ReentrantLock l = new ReentrantLock();
	private final Condition okToRead = l.newCondition();
	private final Condition okToWrite = l.newCondition();
	
	public void requestRead() {
		l.lock();
		while (nw > 0)
			try {
				okToRead.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		nr++;
		l.unlock();
	}
	
	public void releaseRead() {
		l.lock();
		nr--;
		if (nr == 0) okToWrite.signal();
		l.unlock();
	}
	
	public void requestWrite() {
		l.lock();
		while(nr > 0 || nw > 0)
			try {
				okToWrite.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		nw++;
		l.unlock();
	}
	
	public void releaseWrite() {
		l.lock();
		nw--;
		okToWrite.signal();
		okToRead.signalAll();
		l.unlock();
	}
}
