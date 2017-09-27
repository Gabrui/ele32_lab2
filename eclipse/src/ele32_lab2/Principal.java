package ele32_lab2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Principal {
	public static final String CODIFICACAO = "UTF-8";
	
	HashMap<Integer, Integer> binarioCaracter;	
	HashMap<Integer, Integer> caractereBinario;

	public static void main(String[] args) {
		// Abre todos os arquivos dessa pasta
		File[] files = new File("../textos/").listFiles();
		
		for (File file : files) {
			//read_file(file);
		}
	}
	
	

}
