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

public class TesteIntegracao {

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testeAAAB() throws IOException {
		String original = "../textos/testes/aaab";
		String compactado = "../resultados/testes/aabCompac";
		String descompactado = "../resultados/testes/aaab";
		
		compactarDescompactar(original, compactado, descompactado);
	}
	
	@Test
	public void testeABCD() throws IOException {
		String original = "../textos/testes/abcd";
		String compactado = "../resultados/testes/abcdCompac";
		String descompactado = "../resultados/testes/abcd";
		
		compactarDescompactar(original, compactado, descompactado);
	}
	

	@Test
	public void testeAeB() throws IOException {
		String original = "../textos/testes/abs";
		String compactado = "../resultados/testes/absCompac";
		String descompactado = "../resultados/testes/abs";
		
		compactarDescompactar(original, compactado, descompactado);
	}
	
	
	@Test
	public void testeIngles() throws IOException {
		String original = "../textos/testes/ingles";
		String compactado = "../resultados/testes/inglesCompac";
		String descompactado = "../resultados/testes/ingles";
		
		compactarDescompactar(original, compactado, descompactado);
	}
	
	@Test
	public void testeAlemao() throws IOException {
		String original = "../textos/testes/alemao";
		String compactado = "../resultados/testes/alemaoCompac";
		String descompactado = "../resultados/testes/alemao";
		
		//compactarDescompactar(original, compactado, descompactado);
	}

	private void compactarDescompactar(String original, String compactado, String descompactado) throws IOException {
		Leitor l = new Leitor(new File(original));
		LinkedList<Boolean> listaBitsOriginal = l.compactar();
		HashMap<Integer, String> binarioCaractereOriginal = l.getBinarioCaracterOriginal();
		HashMap<Integer, String> binarioCaractereFinalOriginal = l.getBinarioCaractere();
		String ultimoCaractereOriginal = l.getUltimoCaractere();
		System.out.println(listaBitsOriginal.size());
		Escritor e = new Escritor(binarioCaractereOriginal, listaBitsOriginal, ultimoCaractereOriginal);
		e.escrever(new File(compactado));

		
		LeitorCompactado lc = new LeitorCompactado(new File(compactado));
		LinkedList<Boolean> listaBits = lc.getListaBits();
		HashMap<Integer, String> binarioCaractere = lc.getBinarioCaractere();
		String ultimoCaractere = lc.getUltimoCaractere();
		
		assertEquals(listaBitsOriginal, listaBits);
		assertEquals(binarioCaractereOriginal, binarioCaractere);
		assertEquals(ultimoCaractereOriginal, ultimoCaractere);
		
		EscritorDescompactado ec = new EscritorDescompactado(binarioCaractere, listaBits, ultimoCaractere);
		ec.escrever(new File(descompactado));
		HashMap<Integer, String> binarioCaractereFinal = ec.getBinarioCaracter();
		
		int tam = binarioCaractereFinal.size();
		for (int i=0; i<tam; i++)
			assertEquals(binarioCaractereFinalOriginal.get(i), binarioCaractereFinal.get(i));
		//assertEquals(binarioCaractereFinalOriginal, binarioCaractereFinal);
		
		String esperado = new String(Files.readAllBytes(Paths.get(original)));
		String resultado = new String(Files.readAllBytes(Paths.get(descompactado)));
		
		assertEquals(esperado, resultado);
	}

}
