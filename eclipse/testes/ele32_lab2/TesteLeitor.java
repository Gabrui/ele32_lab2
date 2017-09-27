package ele32_lab2;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

public class TesteLeitor {

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testeNulo() throws IOException {
		Leitor l = new Leitor(new File("../textos/testeNulo"));
		assertEquals(0, l.getContadorBinario());
	}

	@Test
	public void testaLeituraSimples() throws IOException {
		Leitor l = new Leitor(new File("../textos/teste"));
		assertEquals(10, l.getContadorBinario());
		assertEquals(10, l.getBinarioCaractere().size());
		assertEquals(10, l.getCaractereBinario().size());
		assertEquals("a", l.getBinarioCaractere().get(0));
		assertEquals("e", l.getBinarioCaractere().get(4));
		assertEquals(3, (int) l.getCaractereBinario().get("d"));
	}
	
	@Test
	public void testaRepresentacaoBinariaUnitaria() {
		assertEquals(1, Leitor.quantosBitsRepresenta(0));
		assertEquals(1, Leitor.quantosBitsRepresenta(1));
	}
	
	@Test
	public void testaRepresentacaoBinaria() {
		assertEquals(2, Leitor.quantosBitsRepresenta(2));
		assertEquals(2, Leitor.quantosBitsRepresenta(3));
		assertEquals(4, Leitor.quantosBitsRepresenta(15));
	}
	
	@Test
	public void testaEscritaBinariaNula() {
		LinkedList<Boolean> l = Leitor.escreveBinario(0, 6);
		assertEquals(false, l.get(0));
		assertEquals(false, l.get(4));
		assertEquals(6, l.size());
	}
	
	@Test
	public void testaEscritaBinaria() {
		LinkedList<Boolean> l = Leitor.escreveBinario(3, 2);
		assertEquals(true, l.get(0));
		assertEquals(true, l.get(1));
		assertEquals(2, l.size());
		l = Leitor.escreveBinario(6, 4);
		assertEquals(false, l.get(0));
		assertEquals(true, l.get(1));
		assertEquals(true, l.get(2));
		assertEquals(false, l.get(3));
		assertEquals(4, l.size());
	}
	
	@Test
	public void testaCompactacaoUnitaria() throws IOException {
		Leitor l = new Leitor(new File("../textos/testeUnitario"));
		assertEquals(1, l.getContadorBinario());
		
		LinkedList<Boolean> res = l.compactar();
		assertEquals(0, res.size());
		assertEquals("a", l.getUltimoCaractere());
	}
	
	
	@Test
	public void testaCompactacaoDupla() throws IOException {
		Leitor l = new Leitor(new File("../textos/testeDuplo"));
		assertEquals(2, l.getContadorBinario());
		
		LinkedList<Boolean> res = l.compactar();
		assertEquals(1, res.size());
		assertEquals(false, res.get(0));
		assertEquals("b", l.getUltimoCaractere());
	}
	
	
	@Test
	public void testaCompactacaoABCD() throws IOException {
		Leitor l = new Leitor(new File("../textos/testeABCD"));
		assertEquals(4, l.getContadorBinario());
		
		LinkedList<Boolean> res = l.compactar();
		assertEquals(8, res.size());
		assertEquals(false, res.get(0));
		assertEquals(false, res.get(1));
		assertEquals(false, res.get(2));
		assertEquals(false, res.get(3));
		assertEquals(true , res.get(4));
		assertEquals(false, res.get(3));
		assertEquals(true , res.get(4));
		assertEquals(false, res.get(3));
		
		assertEquals("d", l.getUltimoCaractere());
	}

}
