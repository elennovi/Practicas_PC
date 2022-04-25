package parte2.mensajes;

import java.io.Serializable;

public abstract class Mensaje implements Serializable{
	private static final long serialVersionUID = 1L;
	
	// Atributos: tipo, origen, destino, pero lo más importante, camino
	private int tipo;
	private String origen;
	private String destino;
	
	public Mensaje(int tipo, String origen, String destino) {
		this.tipo = tipo;
		this.origen = origen;
		this.destino = destino;
	}
	
	public int getTipo() {
		return tipo;
	}
	
	public String getOrigen() {
		return origen;
	}
	
	public String getDestino() {
		return destino;
	}
}
