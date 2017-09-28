package ele32_lab2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PrimitiveIterator.OfInt;


public class LeitorCompactado {
	
	private HashMap<Integer, String> binarioCaracter;	
	private HashMap<String, Integer> caractereBinario;
	private LinkedList<Boolean> listaBits;
	private int contadorBinario;
	
	public LeitorCompactado(File arquivo) throws IOException {
		binarioCaracter = new HashMap<Integer, String>();
		caractereBinario = new HashMap<String, Integer>();
		listaBits = new LinkedList<>();
		contadorBinario = 0;

		FileInputStream ins = new FileInputStream(arquivo.getPath());
		
		// Lê um inteiro que representa a quantidade de bytes
		byte[] inteiro = new byte[4];
		ins.read(inteiro);
		int quantBytes = (inteiro[0]<<24) | (inteiro[1]<<16) | (inteiro[2]<<8) | inteiro[3];

		// Lê um byte que é o resto
		int resto = ins.read();
		
		// Lê o dicionário inicial
		byte[] simbolos = new byte[quantBytes];
		ins.read(simbolos);
		String s = new String(simbolos, Charset.forName(Principal.CODIFICACAO));
		OfInt a = s.codePoints().iterator();
		while (a.hasNext())
			acrescentaString(new String(Character.toChars(a.next())));

		// Gera uma lista de booleans
		int lido = ins.read();
		int ultLido = ins.read();
		while (ultLido != -1) {
			for (int i=7; i>=0; i--)
				listaBits.add( (lido & (1<<i)) != 0);
			lido = ultLido;
			ultLido = ins.read();
		}
		// Processa o último byte
		for (int i=7; i>=resto; i--)
			listaBits.add( (lido & (1<<i)) != 0);
		
		ins.close();
	}
	
	
	private void acrescentaString(String letra) {
		caractereBinario.put(letra, contadorBinario);
		binarioCaracter.put(contadorBinario++, letra);
	}
	
	
	public int getContadorBinario() {
		return contadorBinario;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public HashMap<Integer, String> getBinarioCaractere() {
		return (HashMap<Integer, String>) binarioCaracter.clone();
	}
	
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Integer> getCaractereBinario() {
		return (HashMap<String, Integer>) caractereBinario.clone();
	}
	
	@SuppressWarnings("unchecked")
	public LinkedList<Boolean> getListaBits() {
		return (LinkedList<Boolean>) listaBits.clone();
	}
}
