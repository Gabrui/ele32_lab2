package ele32_lab2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PrimitiveIterator.OfInt;

/**
 * Lê um arquivo compactado (binário), gerando o hashmap primitivo original
 * e uma lista de booleans (bits)
 */
public class LeitorCompactado {
	
	private HashMap<Integer, String> binarioCaracter;	
	private HashMap<String, Integer> caractereBinario;
	private LinkedList<Boolean> listaBits;
	private int contadorBinario;
	private boolean representaBinario = false;
	
	public LeitorCompactado(File arquivo) throws IOException {
		binarioCaracter = new HashMap<Integer, String>();
		caractereBinario = new HashMap<String, Integer>();
		
		contadorBinario = 0;

		FileInputStream ins = new FileInputStream(arquivo.getPath());
		
		// Lê um inteiro que representa a quantidade de bytes
		int quantBytes = leInteiro(ins);

		// Lê um byte que é o resto
		int resto = ins.read();
		
		if (quantBytes != 0) {
		// Lê o dicionário inicial
			byte[] simbolos = new byte[quantBytes];
			ins.read(simbolos);
			String s = new String(simbolos, Charset.forName(Principal.CODIFICACAO));
			OfInt a = s.codePoints().iterator();
			while (a.hasNext())
				acrescentaString(new String(Character.toChars(a.next())));
		} else {
			representaBinario = true;
			acrescentaString("0");
			acrescentaString("1");
		}
		
		
		listaBits = lerBits(ins, resto);
		
	}


	public static int leInteiro(FileInputStream ins) throws IOException {
		byte[] inteiro = new byte[4];
		ins.read(inteiro);
		return ByteBuffer.wrap(inteiro).getInt();
	}


	/**
	 * Lê sequenciamente bits e fecha o arquivo.
	 * @param ins
	 * @param resto O resto de bits em um arquivo, varia de 0 a 7 bits.
	 * @return A lista de bits lida
	 * @throws IOException
	 */
	public static LinkedList<Boolean> lerBits(FileInputStream ins, int resto) throws IOException {
		LinkedList<Boolean> listaBits = new LinkedList<>();
		
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
		
		return listaBits;
	}
	
	
	private void acrescentaString(String letra) {
		caractereBinario.put(letra, contadorBinario);
		binarioCaracter.put(contadorBinario++, letra);
	}
	
	
	public int getContadorBinario() {
		return contadorBinario;
	}
	
	public boolean isRepresentacaoBinaria() {
		return representaBinario;
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
