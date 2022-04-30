package parte2;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class InfoCliente {
	// Flujos de entrada y de salida
	private ObjectInputStream fin;
	private ObjectOutputStream fout;
	// IP del cliente
	private String ipv4;
	
	public InfoCliente(String nombre, String ipv4, ObjectInputStream fin, ObjectOutputStream fout) {
		this.setFin(fin);
		this.setFout(fout);
		this.ipv4 = ipv4;
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

	public String getIP() {
		return ipv4;
	}
	
}
