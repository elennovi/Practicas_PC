package parte1.practica2;

public class Monitor {
	private int ent;
	
	public Monitor() {
		setEnt(0);
	}
	
	public synchronized void incrementar() {
		setEnt(getEnt() + 1);
		System.out.println("Incremento a: " + getEnt());
	}
	
	public synchronized void decrementar() {
		setEnt(getEnt() - 1);
		System.out.println("Decremento a: " + getEnt());
	}

	public int getEnt() {
		return ent;
	}

	public void setEnt(int ent) {
		this.ent = ent;
	}
}
