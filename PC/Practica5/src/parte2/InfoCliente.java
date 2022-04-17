package parte2;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class InfoCliente { // DUDA
	private ObjectInputStream fin;
	private ObjectOutputStream fout;
	
	public InfoCliente(String nombre, ObjectInputStream fin, ObjectOutputStream fout) {
		this.setFin(fin);
		this.setFout(fout);
	}

	public ObjectInputStream getFin() {
		return fin;
	}

	public void setFin(ObjectInputStream fin) {
		this.fin = fin;
	}

	public ObjectOutputStream getFout() {
		return fout;
	}

	public void setFout(ObjectOutputStream fout) {
		this.fout = fout;
	}
	
}
