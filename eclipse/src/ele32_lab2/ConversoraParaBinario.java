package ele32_lab2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedList;

public class ConversoraParaBinario {
	
	public static void main(String[] args) throws IOException {

		File[] arquivos = new File("../../Antes/").listFiles();
		for (File arquivo : arquivos) {
			OutputStreamWriter saida = new OutputStreamWriter(new FileOutputStream(
					"../../AntesBinarios/"+arquivo.getName()), Principal.CODIFICACAO);
			LinkedList<Boolean> lista = LeitorCompactado.lerBits(new FileInputStream(arquivo.getPath()), 0);
			for (boolean b : lista)
				saida.write(b?"1":"0");
			saida.close();
		}
		
	}

}
