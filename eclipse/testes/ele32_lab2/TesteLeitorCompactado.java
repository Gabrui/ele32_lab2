package ele32_lab2;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

public class TesteLeitorCompactado {

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testaLeituraInteiro() throws IOException {
		FileInputStream ins = new FileInputStream("../resultados/testes/chinesCompac");
		assertEquals(9463, LeitorCompactado.leInteiro(ins));
		ins.close();
	}

	@Test
	public void testeGeral() throws IOException {
		String arquivo = "../resultados/testes/tler1";
		LeitorCompactado l = new LeitorCompactado(new File(arquivo));
		
		assertEquals(4, l.getContadorBinario());
		
		LinkedList<Boolean> res = l.getListaBits();
		assertEquals(8, res.size());
		assertEquals(false, res.get(0));
		assertEquals(false, res.get(1));
		assertEquals(false, res.get(2));
		assertEquals(false, res.get(3));
		assertEquals(true , res.get(4));
		assertEquals(false, res.get(3));
		assertEquals(true , res.get(4));
		assertEquals(false, res.get(3));
		
		assertEquals(4, l.getBinarioCaractere().size());
		assertEquals(4, l.getCaractereBinario().size());
		assertEquals("a", l.getBinarioCaractere().get(0));
		assertEquals("b", l.getBinarioCaractere().get(1));
		assertEquals("c", l.getBinarioCaractere().get(2));
		assertEquals("d", l.getBinarioCaractere().get(3));
	}

}
