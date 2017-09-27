package ele32_lab2;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

public class TesteEscritorDescompactado {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() throws IOException {
		LinkedList<Boolean> lista = new LinkedList<>();
		lista.add(false);
		lista.add(false);
		lista.add(false);
		lista.add(false);
		lista.add(true);
		lista.add(false);
		lista.add(true);
		lista.add(false);
		HashMap<Integer, String> binMap = new HashMap<>();
		binMap.put(0, "a");
		binMap.put(1, "b");
		binMap.put(2, "c");
		binMap.put(3, "d");
		String ult = "d";
		EscritorDescompactado e = new EscritorDescompactado(binMap, lista, ult);
		String arquivo = "../resultados/testes/tdes1";
		e.escrever(new File(arquivo));
		
		String resposta = new String(Files.readAllBytes(Paths.get(arquivo)));
		
		assertEquals("abcd", resposta);
	}

}
