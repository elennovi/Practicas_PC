package parte1;

public class LockRompeEmpate {
	private volatile boolean in1;
	private volatile boolean in2;
	private volatile int last;
	
	LockRompeEmpate() {
		in1 = false;
		in2 = false;
	}
	
	public void takeLock(int i) {
		if (i == 1) {
			in1 = true;
			last = 1;
			while(in2 && last == 1);
		}
		else {
			in2 = true;
			last = 2;
			while(in1 && last == 2);
		}
	}
	
	public void releaseLock(int i) {
		if (i == 1)
			in1 = false;
		else
			in2 = false;
	}
}
