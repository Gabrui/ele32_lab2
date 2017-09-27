package ele32_lab2;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

public class TesteEscritor {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testBitsMultiploDe8() throws IOException {
		LinkedList<Boolean> lista = new LinkedList<>();
		lista.add(false);
		lista.add(false);
		lista.add(false);
		lista.add(false);
		lista.add(true);
		lista.add(false);
		lista.add(true);
		lista.add(false); // São 8 bits, dá certinho
		HashMap<Integer, String> binMap = new HashMap<>();
		binMap.put(0, "a");
		binMap.put(1, "b");
		binMap.put(2, "c");
		binMap.put(3, "d");
		String ult = "d";
		Escritor e = new Escritor(binMap, lista, ult);
		String arquivo = "../resultados/testes/t1";
		e.escrever(new File(arquivo));
		
		byte[] bytes = new byte[11];
		FileInputStream ins = new FileInputStream(arquivo);
		assertEquals(11, ins.read(bytes));
		assertEquals(-1, ins.read());
		assertEquals(0, bytes[0]);
		assertEquals(0, bytes[1]);
		assertEquals(0, bytes[2]);
		assertEquals(5, bytes[3]);
		assertEquals(0, bytes[4]);
		assertEquals((byte)'a', bytes[5]);
		assertEquals((byte)'b', bytes[6]);
		assertEquals((byte)'c', bytes[7]);
		assertEquals((byte)'d', bytes[8]);
		assertEquals((byte)'d', bytes[9]);
		assertEquals(0b00001010, bytes[10]);
		ins.close();
	}
	
	
	@Test
	public void testaBits() throws IOException {
		LinkedList<Boolean> lista = new LinkedList<>();
		lista.add(false);
		lista.add(true);
		lista.add(false);
		HashMap<Integer, String> binMap = new HashMap<>();
		binMap.put(0, "a");
		binMap.put(1, "b");
		String ult = "b";
		Escritor e = new Escritor(binMap, lista, ult);
		String arquivo = "../resultados/testes/t2";
		e.escrever(new File(arquivo));
		
		byte[] bytes = new byte[5+3+1];
		FileInputStream ins = new FileInputStream(arquivo);
		assertEquals(9, ins.read(bytes));
		assertEquals(-1, ins.read());
		assertEquals(0, bytes[0]);
		assertEquals(0, bytes[1]);
		assertEquals(0, bytes[2]);
		assertEquals(3, bytes[3]);
		assertEquals(5, bytes[4]);
		assertEquals((byte)'a', bytes[5]);
		assertEquals((byte)'b', bytes[6]);
		assertEquals((byte)'b', bytes[7]);
		assertEquals(0b01000000, bytes[8]);
		ins.close();
	}
	
	@Test
	public void testaBits2() throws IOException {
		LinkedList<Boolean> lista = new LinkedList<>();
		lista.add(false);
		lista.add(true);
		lista.add(false);
		lista.add(false);
		lista.add(false);
		lista.add(false);
		lista.add(true);
		lista.add(false);
		lista.add(true);
		HashMap<Integer, String> binMap = new HashMap<>();
		binMap.put(0, "a");
		binMap.put(1, "b");
		String ult = "b";
		Escritor e = new Escritor(binMap, lista, ult);
		String arquivo = "../resultados/testes/t2";
		e.escrever(new File(arquivo));
		
		byte[] bytes = new byte[5+3+2];
		FileInputStream ins = new FileInputStream(arquivo);
		assertEquals(10, ins.read(bytes));
		assertEquals(-1, ins.read());
		assertEquals(0, bytes[0]);
		assertEquals(0, bytes[1]);
		assertEquals(0, bytes[2]);
		assertEquals(3, bytes[3]);
		assertEquals(7, bytes[4]);
		assertEquals((byte)'a', bytes[5]);
		assertEquals((byte)'b', bytes[6]);
		assertEquals((byte)'b', bytes[7]);
		assertEquals((byte)0b01000010, bytes[8]);
		assertEquals((byte)0b10000000, bytes[9]);
		ins.close();
	}
	
}
