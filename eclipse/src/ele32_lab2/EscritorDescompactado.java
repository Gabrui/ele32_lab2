package ele32_lab2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Escreve o arquivo original, fazendo a descompactação.
 *
 */
public class EscritorDescompactado {
	
	private HashMap<Integer, String> binarioCaracterOriginal;
	private HashMap<Integer, String> binarioCaracter;
	private LinkedList<Boolean> listaBits;
	private ListIterator<Boolean> iterador;

	@SuppressWarnings("unchecked")
	public EscritorDescompactado(HashMap<Integer, String> binarioCaracterOriginal, LinkedList<Boolean> listaBits) {
		this.binarioCaracterOriginal = binarioCaracterOriginal;
		this.binarioCaracter = (HashMap<Integer, String>) this.binarioCaracterOriginal.clone();
		this.listaBits = listaBits;
		this.iterador = this.listaBits.listIterator();
	}
	
	/**
	 * Escreve no arquivo o resultado da descompactação, altera o dicionário, não chamar duas vezes.
	 * @param arquivo
	 * @throws IOException
	 */
	public void escrever(File arquivo) throws IOException {
		OutputStreamWriter saida = new OutputStreamWriter(new FileOutputStream(arquivo.getPath()), Principal.CODIFICACAO);
		
		int quant = binarioCaracter.size();
		int endereco = 0;
		String ultimo = "";
		String penultimo;
		
		if (iterador.hasNext()) {
			endereco = lerBits(Leitor.quantosBitsRepresenta(quant-1));
			ultimo = binarioCaracter.get(endereco);
			saida.write(ultimo);
			quant++;
		}
		
		while(iterador.hasNext()) {
			endereco = lerBits(Leitor.quantosBitsRepresenta(quant-1));
			penultimo = ultimo;
			ultimo = binarioCaracter.get(endereco);
			if (ultimo == null) {
				ultimo = penultimo + primeiroChar(penultimo);
				binarioCaracter.put(quant-1, ultimo);
			} else {
				binarioCaracter.put(quant-1, penultimo + primeiroChar(ultimo));
			}
			saida.write(ultimo);
			quant++;
		}
		
		saida.close();
	}
	
	/**
	 * Retorna o primeiro caractere de uma String,
	 * o cuidado a ser tomado é que o primeiro caractere não necessariamente é o primeiro char
	 * e pode ser os dois, já que um char é UTF-16 e caracteres chineses são representados com mais
	 * de um char.
	 * @param s
	 * @return
	 */
	public static String primeiroChar (String s) {
		return new String(Character.toChars(s.codePointAt(0)));
	}

	/**
	 * Lê a quantidade de bits indicada na lista de bits
	 * @param quantosBits
	 * @return Número inteiro lido, correspondente
	 */
	private int lerBits(int quantosBits) {
		int resp = 0;
		while (quantosBits > 0) {
			resp = resp << 1;
			if (iterador.next())
				resp++;
			quantosBits--;
		}
		return resp;
	}

	public HashMap<Integer, String> getBinarioCaracterOriginal() {
		return binarioCaracterOriginal;
	}

	public HashMap<Integer, String> getBinarioCaracter() {
		return binarioCaracter;
	}
}
