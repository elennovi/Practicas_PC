package parte2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	// El nombre del cliente
	private String name;
	// La lista de ficheros y su contenido
	private Map<String, String> ficheros;
	// Su IP
	private String ipv4;
	
	public Usuario(String name, Map<String, String> ficheros, String ipv4) {
		this.name = name;
		this.ficheros = ficheros;
		this.ipv4 = ipv4;
	}
	
	public String getInfoFile(String filename){
		return ficheros.get(filename);
	}
	
	public List<String> getFilenames() {
		List<String> filenames = new ArrayList<String>();
		for (String filename: ficheros.keySet())
			filenames.add(filename);
		return filenames;
	}
	
	public String getName() {
		return name;
	}
	
	public String getIP() {
		return ipv4;
	}
	
}
