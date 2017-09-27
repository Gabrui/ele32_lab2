package ele32_lab2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class LeitorCompactado {
	
	public void ler (File arquivo) throws IOException {

		FileInputStream ins = new FileInputStream(arquivo.getPath());
		
		// Lê um inteiro que representa a quantidade de bytes
		byte[] inteiro = new byte[4];
		ins.read(inteiro);
		int quantBytes = inteiro[0]<<24 | inteiro[1]<<16 | inteiro[2]<<8 | inteiro[0];
		
		// Lê um byte que é o resto
		int resto = ins.read();
		
		
		
		ins.close();
	}
}
