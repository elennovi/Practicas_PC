package parte2;

import java.util.List;

public class Usuario {
	private String name;
	private List<String> ficheros;
	
	public Usuario(String name, List<String> ficheros) {
		this.name = name;
		this.ficheros = ficheros;
	}
	
	public List<String> getFicheros(){
		return ficheros;
	}
	
	public String getName() {
		return name;
	}
}
