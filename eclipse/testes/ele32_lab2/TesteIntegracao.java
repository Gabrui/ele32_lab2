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
	public void test() throws IOException {
		
		Leitor l = new Leitor(new File("../textos/testes/ingles"));
		Escritor e = new Escritor(l.getBinarioCaracterOriginal(), l.compactar(), l.getUltimoCaractere());
		e.escrever(new File("../resultados/testes/inglesCompac"));

		LeitorCompactado lc = new LeitorCompactado(new File("../resultados/testes/inglesCompac"));
		EscritorDescompactado ec = new EscritorDescompactado(lc.getBinarioCaractere(), lc.getListaBits(), lc.getUltimoCaractere());
		ec.escrever(new File("../resultados/testes/ingles"));
		
		String esperado = new String(Files.readAllBytes(Paths.get("../textos/testes/ingles")));
		String resultado = new String(Files.readAllBytes(Paths.get("../resultados/testes/ingles")));
		
		assertEquals(esperado, resultado);
		
	}

}
