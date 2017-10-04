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
		
		compactarDescompactar(original, compactado, descompactado);
	}
	
	@Test
	public void testeChines() throws IOException {
		String original = "../textos/testes/chines";
		String compactado = "../resultados/testes/chinesCompac";
		String descompactado = "../resultados/testes/chines";
		
		compactarDescompactar(original, compactado, descompactado);
	}
	
	
	@Test
	public void testeAAABComDicMaximo() throws IOException {
		String original = "../textos/testes/aaab";
		String compactado = "../resultados/testes/aabCompacDic3";
		String descompactado = "../resultados/testes/aaabDic3";
		
		compactadorMaximoDicionario(original, compactado, descompactado, 3);
	}
	
	
	@Test
	public void testeAlemaoComDicMaximo() throws IOException {
		String original = "../textos/testes/alemao";
		String compactado = "../resultados/testes/alemaoCompacDic200";
		String descompactado = "../resultados/testes/alemaoDic200";
		
		compactadorMaximoDicionario(original, compactado, descompactado, 200);
	}
	
	@Test
	public void testeChinesComDicMaximo() throws IOException {
		String original = "../textos/testes/chines";
		String compactado = "../resultados/testes/chinesCompacDic5000";
		String descompactado = "../resultados/testes/chinesDic5000";
		
		compactadorMaximoDicionario(original, compactado, descompactado, 5000);
	}

	private void compactadorMaximoDicionario(String original, String compactado, String descompactado, int max)
			throws IOException {
		Leitor l = new Leitor(new File(original));
		l.setTamanhoMaximoDic(max);
		
		LinkedList<Boolean> listaBitsOriginal = l.compactar();
		HashMap<Integer, String> binarioCaractereOriginal = l.getBinarioCaracterOriginal();
		HashMap<Integer, String> binarioCaractereFinalOriginal = l.getBinarioCaractere();
		//System.out.println(listaBitsOriginal.size());
		Escritor e = new Escritor(binarioCaractereOriginal, listaBitsOriginal);
		e.escrever(new File(compactado));

		
		LeitorCompactado lc = new LeitorCompactado(new File(compactado));
		LinkedList<Boolean> listaBits = lc.getListaBits();
		HashMap<Integer, String> binarioCaractere = lc.getBinarioCaractere();
		
		assertEquals(listaBitsOriginal, listaBits);
		assertEquals(binarioCaractereOriginal, binarioCaractere);
		
		EscritorDescompactado ec = new EscritorDescompactado(binarioCaractere, listaBits);
		ec.setTamanhoMaximoDic(max);
		ec.escrever(new File(descompactado));
		HashMap<Integer, String> binarioCaractereFinal = ec.getBinarioCaracter();
		
		//int tam = binarioCaractereFinal.size();
		/*
		for (int i=0; i<tam; i++)
			assertEquals(binarioCaractereFinalOriginal.get(i), binarioCaractereFinal.get(i));
		*/
		assertEquals(binarioCaractereFinalOriginal, binarioCaractereFinal);
		
		String esperado = new String(Files.readAllBytes(Paths.get(original)));
		String resultado = new String(Files.readAllBytes(Paths.get(descompactado)));
		
		assertEquals(esperado, resultado);
	}

	private void compactarDescompactar(String original, String compactado, String descompactado) throws IOException {
		Leitor l = new Leitor(new File(original));
		LinkedList<Boolean> listaBitsOriginal = l.compactar();
		HashMap<Integer, String> binarioCaractereOriginal = l.getBinarioCaracterOriginal();
		HashMap<Integer, String> binarioCaractereFinalOriginal = l.getBinarioCaractere();
		//System.out.println(listaBitsOriginal.size());
		Escritor e = new Escritor(binarioCaractereOriginal, listaBitsOriginal);
		e.escrever(new File(compactado));

		
		LeitorCompactado lc = new LeitorCompactado(new File(compactado));
		LinkedList<Boolean> listaBits = lc.getListaBits();
		HashMap<Integer, String> binarioCaractere = lc.getBinarioCaractere();
		
		assertEquals(listaBitsOriginal, listaBits);
		assertEquals(binarioCaractereOriginal, binarioCaractere);
		
		EscritorDescompactado ec = new EscritorDescompactado(binarioCaractere, listaBits);
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
