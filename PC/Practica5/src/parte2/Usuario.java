package parte2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Usuario {
	private String name;
	private Map<String, String> ficheros;
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
