package ele32_lab2;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
	public void testeIngles() throws IOException {
		String original = "../textos/testes/ingles";
		String compactado = "../resultados/testes/inglesCompac";
		String descompactado = "../resultados/testes/ingles";
		
		compactarDescompactar(original, compactado, descompactado);
	}

	private void compactarDescompactar(String original, String compactado, String descompactado) throws IOException {
		Leitor l = new Leitor(new File(original));
		Escritor e = new Escritor(l.getBinarioCaracterOriginal(), l.compactar(), l.getUltimoCaractere());
		e.escrever(new File(compactado));

		LeitorCompactado lc = new LeitorCompactado(new File(compactado));
		EscritorDescompactado ec = new EscritorDescompactado(lc.getBinarioCaractere(), lc.getListaBits(), lc.getUltimoCaractere());
		ec.escrever(new File(descompactado));
		
		String esperado = new String(Files.readAllBytes(Paths.get(original)));
		String resultado = new String(Files.readAllBytes(Paths.get(descompactado)));
		
		assertEquals(esperado, resultado);
	}

}
