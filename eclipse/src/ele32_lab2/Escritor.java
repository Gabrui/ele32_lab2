package ele32_lab2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;


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
		System.out.println("escrevendo cabeçalho");
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
		saida.write((8-resto)%8); // É a quantidade de bits inúteis
		
		// Escreve os simbolos em ordem crescente a partir de zero
		for (int i = 0; i<quantTipos; i++)
			saida.write(binarioCaracterOriginal.get(i).getBytes(Charset.forName(Principal.CODIFICACAO)));
		
		// Escreve o último símbolo
		saida.write(ultimoCaractere.getBytes(Charset.forName(Principal.CODIFICACAO)));
		System.out.println("escrevendo bits");
		// Escreve os bits
		// TODO otimizar isso
		int qBytes = qB/8;
		int a = 0;
		byte[] bufferSaida = new byte[qBytes];
		ListIterator<Boolean> iterador = listaBits.listIterator();
		for (int i = 0, j=0; i<qBytes; i++) {
			j = 8;
			a = 0;
			while (j > 0) {
				a = a << 1;
				if (iterador.next())
					a++;
				j--;
			}
			bufferSaida[i] = (byte) a;
		}
		System.out.println("pronto");
		saida.write(bufferSaida);
		if (resto > 0) {
			a = 0;
			for (int i = 0; i<resto; i++) {
				a = (a | ((listaBits.get(qB+i)?1:0) << (7-i)));
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
