package pruebitas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
		Map<String, List<Integer>> mapa = new HashMap<String, List<Integer>>();
		List<Integer> l = new ArrayList<Integer>();
		for(int i = 1; i < 4; ++i)
			l.add(i);
		mapa.put("Enenita", l);
		mapa.put("Nenik", l);
		System.out.println(mapa.toString());
	}

}
