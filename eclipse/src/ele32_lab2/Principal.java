package ele32_lab2;

import java.io.File;
import java.io.IOException;

public class Principal {
	public static final String CODIFICACAO = "UTF-8";
	

	public static void main(String[] args) throws IOException {

		// Compactando
		File[] arquivos = new File("../textos/misc/").listFiles();
		for (File arquivo : arquivos) {
			System.out.println("Compactando: "+arquivo.getName());
			Leitor l = new Leitor(arquivo);
			Escritor e = new Escritor(l.getBinarioCaracterOriginal(), l.compactar(), l.getUltimoCaractere());
			e.escrever(new File("../resultados/miscCompactado/"+arquivo.getName()));
		}
		
		// Descompactando
		arquivos = new File("../resultados/miscCompactado/").listFiles();
		for (File arquivo : arquivos) {
			System.out.println("Descompactando: "+arquivo.getName());
			LeitorCompactado l = new LeitorCompactado(arquivo);
			EscritorDescompactado e = new EscritorDescompactado(l.getBinarioCaractere(), l.getListaBits(), l.getUltimoCaractere());
			e.escrever(new File("../resultados/misc/"+arquivo.getName()));
		}
	}
	
	

}
