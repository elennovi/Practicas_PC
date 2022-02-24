package parte2; 
public class LockBakery extends Lock{
	private volatile int[] turn;
	private int N;
	
	public LockBakery(int N) {
		this.N = N;
		turn = new int[N + 1];
		for(int i = 1; i <= N; ++i)
			turn[i] = 0;
		turn = turn;
	}
	
	public void takeLock(int i) {
		turn[i] = 1;
		turn = turn;
		int max_act = turn[0];
		for(int j = 1; j < N; ++j)
			if(turn[j] > max_act)
				max_act = turn[j];
		turn[i] = max_act + 1;
		turn = turn;
		for(int j = 0; j < N; ++j)
			while(turn[j] != 0 && operadorMayor(turn[i], i, turn[j], j));
	}
	
	public void releaseLock(int i) {
		turn[i] = 0;
		turn = turn;
	}
	
	public boolean operadorMayor(int a1, int a2, int b1, int b2) {
		return (a1 > b1) || (a1 == b1 && a2 < b2); // DUDA a2 </> b2
	}
}
