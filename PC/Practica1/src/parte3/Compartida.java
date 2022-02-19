package parte3;

public class Compartida {
	private int[][] m;
	private int N;
	
	Compartida(int N) {
		this.N = N;
		m = new int[N][N];
	}
	
	public int getN() {
		return N;
	}
	
	public int getValue(int i, int j) {
		return m[i][j];
	}
	
	public void modifica(int value, int i, int j) {
		m[i][j] = value;
	}
	
	public void mostrar() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				System.out.print(m[i][j] + " ");
			}
			System.out.println();
		}
	}
}
