package ele32_lab2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
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
	private int tamanhoMaximoDic = Integer.MAX_VALUE;
	private int contadorBinario = 0;

	
	public EscritorDescompactado(HashMap<Integer, String> binarioCaracterOriginal, LinkedList<Boolean> listaBits) {
		this.binarioCaracterOriginal = binarioCaracterOriginal;
		this.listaBits = listaBits;
		this.iterador = this.listaBits.listIterator();
	}
	

	public EscritorDescompactado(LinkedList<Boolean> listaBits) {
		this.listaBits = listaBits;
		this.iterador = this.listaBits.listIterator();
	}
	
	public void setTamanhoMaximoDic(int maximo) {
		this.tamanhoMaximoDic = maximo;
	}
	
	@SuppressWarnings("unchecked")
	private String reiniciaDicionarios() {
		this.contadorBinario = binarioCaracterOriginal.size();
		this.binarioCaracter = (HashMap<Integer, String>) this.binarioCaracterOriginal.clone();
		String ultimo = "";
		if (iterador.hasNext()) {
			int endereco = lerBits(Leitor.quantosBitsRepresenta(contadorBinario-1));
			ultimo = binarioCaracter.get(endereco);
			
			if (ultimo == null) {
				System.out.println(endereco);
				System.out.println(contadorBinario-1);
			}
			
			contadorBinario++;
		}
		return ultimo;
	}
	
	/**
	 * Escreve no arquivo o resultado da descompactação, altera o dicionário, não chamar duas vezes.
	 * @param arquivo
	 * @throws IOException
	 */
	public void escrever(File arquivo) throws IOException {
		OutputStreamWriter saida = new OutputStreamWriter(new FileOutputStream(arquivo.getPath()), Principal.CODIFICACAO);
		
		int endereco = 0;
		String ultimo = "";
		String penultimo;
		
		ultimo = reiniciaDicionarios();
		saida.write(ultimo);
		
		while(iterador.hasNext()) {
			if (contadorBinario >= tamanhoMaximoDic) {
				ultimo = reiniciaDicionarios();
				saida.write(ultimo);
			} else {
				endereco = lerBits(Leitor.quantosBitsRepresenta(contadorBinario-1));
				penultimo = ultimo;
				ultimo = binarioCaracter.get(endereco);
				if (ultimo == null) {
					ultimo = penultimo + primeiroChar(penultimo);
					binarioCaracter.put(contadorBinario-1, ultimo);
				} else {
					binarioCaracter.put(contadorBinario-1, penultimo + primeiroChar(ultimo));
				}
				saida.write(ultimo);
				contadorBinario++;
			}
		}
		
		saida.close();
	}
	
	/**
	 * Escreve no arquivo o resultado da descompactação em binário
	 * @param arquivo
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void escreverBinario(File arquivo) throws IOException {
		HashMap<Integer, LinkedList<Boolean>> dic = new HashMap<>();
		dic.put(0, new LinkedList<Boolean>(Arrays.asList(false)));
		dic.put(1, new LinkedList<Boolean>(Arrays.asList(true)));
		
		int quant = dic.size();
		int endereco = 0;
		
		LinkedList<Boolean> penultimo;
		LinkedList<Boolean> ultimo = new LinkedList<>();
		
		LinkedList<Boolean> saida = new LinkedList<>();
		
		if (iterador.hasNext()) {
			endereco = lerBits(Leitor.quantosBitsRepresenta(quant-1));
			ultimo = (LinkedList<Boolean>) dic.get(endereco).clone();
			saida.addAll(ultimo);
			quant++;
		}
		
		while(iterador.hasNext()) {
			endereco = lerBits(Leitor.quantosBitsRepresenta(quant-1));
			penultimo = ultimo;
			ultimo = (LinkedList<Boolean>) dic.get(endereco).clone();
			if (ultimo == null) {
				ultimo = penultimo;
				ultimo.add(penultimo.getFirst());
				dic.put(quant-1, ultimo);
			} else {
				penultimo.add(ultimo.getFirst());
				dic.put(quant-1, penultimo);
			}
			saida.addAll(ultimo);
			quant++;
		}
		
		Escritor.escreveArquivoBinario(new FileOutputStream(arquivo.getPath()), saida);
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
