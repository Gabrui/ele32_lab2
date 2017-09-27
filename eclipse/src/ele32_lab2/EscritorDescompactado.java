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
	private String ultimoCaractere;
	private ListIterator<Boolean> iterador;

	@SuppressWarnings("unchecked")
	public EscritorDescompactado(HashMap<Integer, String> binarioCaracterOriginal, LinkedList<Boolean> listaBits,
			String ultimoCaractere) {
		this.binarioCaracterOriginal = binarioCaracterOriginal;
		this.binarioCaracter = (HashMap<Integer, String>) this.binarioCaracterOriginal.clone();
		this.listaBits = listaBits;
		this.ultimoCaractere = ultimoCaractere;
		this.iterador = this.listaBits.listIterator();
	}
	
	/**
	 * Escreve no arquivo o resultado da descompactação
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
				ultimo = penultimo + new String(Character.toChars(penultimo.codePointAt(0)));
				binarioCaracter.put(quant-1, ultimo);
			} else {
				binarioCaracter.put(quant-1, penultimo + ultimo);
			}
			saida.write(ultimo);
			quant++;
		}
		
		saida.write(ultimoCaractere);
		saida.close();
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
}
