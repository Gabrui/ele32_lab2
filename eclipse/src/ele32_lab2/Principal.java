package ele32_lab2;

import java.io.File;
import java.io.IOException;

public class Principal {
	public static final String CODIFICACAO = "UTF-8";


	public static void main(String[] args) throws IOException {

		long tStartTotal = System.currentTimeMillis();
		// Compactando
		File[] arquivos = new File("../../Antes/").listFiles();
		for (File arquivo : arquivos) {
			System.out.println("Compactando: "+arquivo.getName());
			long tStartCompactacao = System.currentTimeMillis();
			Leitor l = new Leitor(arquivo);
			Escritor e = new Escritor(l.getBinarioCaracterOriginal(), l.compactar());
			e.escrever(new File("../../Compactados/"+arquivo.getName()));
			long tEndCompactacao = System.currentTimeMillis();
			long tDeltaCompactacao = tEndCompactacao - tStartCompactacao;
			double elapsedSecondsCompactacao = tDeltaCompactacao / 1000.0;
			System.out.println("Duracao de compactacao do idioma " + arquivo.getName() + ": " + elapsedSecondsCompactacao);
		}

		// Descompactando
		arquivos = new File("../../Compactados/").listFiles();
		for (File arquivo : arquivos) {
			System.out.println("Descompactando: "+arquivo.getName());
			long tStartDescompactacao = System.currentTimeMillis();
			LeitorCompactado l = new LeitorCompactado(arquivo);
			EscritorDescompactado e = new EscritorDescompactado(l.getBinarioCaractere(), l.getListaBits());
			e.escrever(new File("../../Descompactados/"+arquivo.getName()));
			long tEndDescompactacao = System.currentTimeMillis();
			long tDeltaDescompactacao = tEndDescompactacao - tStartDescompactacao;
			double elapsedSecondsDescompactacao = tDeltaDescompactacao / 1000.0;
			System.out.println("Duracao de descompactacao do idioma " + arquivo.getName() + ": " + elapsedSecondsDescompactacao);
		}

		long tEndTotal = System.currentTimeMillis();
		long tDeltaTotal = tEndTotal - tStartTotal;
		double elapsedSecondsTotal = tDeltaTotal / 1000.0;
		System.out.println("Duracao total: " + elapsedSecondsTotal);
	}



}
