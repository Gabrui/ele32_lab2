package ele32_lab2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Responsável por escrever um arquivo em binário, com o cabeçalho correto
 */
public class Escritor {

	private HashMap<Integer, String> binarioCaracterOriginal;
	private LinkedList<Boolean> listaBits;
	private String ultimoCaractere;
	
	public Escritor(HashMap<Integer, String> binarioCaracterOriginal, LinkedList<Boolean> listaBits,
			String ultimoCaractere) {
		this.binarioCaracterOriginal = binarioCaracterOriginal;
		this.listaBits = listaBits;
		this.ultimoCaractere = ultimoCaractere;
	}
	
	/**
	 * -------- CABEÇALHO
	 * Primeiro escreve um int que representa a quantidade de bytes que a lista de simbolos ocupa,
	 * 		isto é, todos os simbolos em ordem crescente e o último símbolo.
	 * depois um byte que representa a quantidade de bits mortos no final do arquivo.
	 * os símbolos em ordem crescente, começando de 0,
	 * o último síbolo,
	 * ---------
	 * a sequência de bits.
	 * @param arquivo
	 * @throws IOException 
	 */
	public void escrever(File arquivo) throws IOException {
		FileOutputStream saida = new FileOutputStream(arquivo.getPath());
		
		// Escreve a quantidade de bytes que os simbolos ocupam
		int quantTipos = binarioCaracterOriginal.size();
		int quantBytes = 0;
		for (int i = 0; i<quantTipos; i++)
			quantBytes += binarioCaracterOriginal.get(i).getBytes(Charset.forName(Principal.CODIFICACAO)).length;
		quantBytes += ultimoCaractere.getBytes(Charset.forName(Principal.CODIFICACAO)).length;
		saida.write(inteiroParaBytes(quantBytes));
		
		// Escreve a quantidade de bits
		int quantBits = listaBits.size();
		int resto = quantBits % 8;
		int qB = quantBits - resto;
		saida.write(resto);
		
		// Escreve os simbolos em ordem crescente a partir de zero
		for (int i = 0; i<quantTipos; i++)
			saida.write(binarioCaracterOriginal.get(i).getBytes(Charset.forName(Principal.CODIFICACAO)));
		
		// Escreve o último símbolo
		saida.write(ultimoCaractere.getBytes(Charset.forName(Principal.CODIFICACAO)));
		
		// Escreve os bits
		for (int i = 0; i<qB; i+=8) {
			byte a = (byte) (
					((listaBits.get(i  )?1:0) << 7) |
					((listaBits.get(i+1)?1:0) << 6) |
					((listaBits.get(i+2)?1:0) << 5) |
					((listaBits.get(i+3)?1:0) << 4) |
					((listaBits.get(i+4)?1:0) << 3) |
					((listaBits.get(i+5)?1:0) << 2) |
					((listaBits.get(i+6)?1:0) << 1) |
					 (listaBits.get(i+7)?1:0));
			saida.write(a);
		}
		if (resto > 0) {
			byte a = 0;
			for (int i = 0; i<resto; i++) {
				a |= ((listaBits.get(i)?1:0) << (7-i));
			}
			saida.write(a);
		}
		saida.close();
	}
	
	
	public static byte[] inteiroParaBytes (int valor) {
	    return new byte[] {
	            (byte) (valor >> 24),
	            (byte) (valor >> 16),
	            (byte) (valor >> 8 ),
	            (byte)  valor };
	}
}
