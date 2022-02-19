package parte3;
import java.lang.Thread;

public class Hilo extends Thread{
	private int[][] m1;
	private int[][] m2;
	Compartida c;
	private int fila;
	
	Hilo(int[][] m1, int[][] m2, Compartida c, int fila){
		this.m1 = m1;
		this.m2 = m2;
		this.c = c;
		this.fila = fila;
	}
	
	public void run() {
		for(int col = 0; col < c.getN(); ++col) {
			c.modifica(0, fila, col);
			for(int k = 0; k < c.getN(); ++k) {
				c.modifica(c.getValue(fila, col) + m1[fila][k] * m2[k][col], fila, col);
			}
		}
	}
}
