package ele32_lab2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Lê o arquivo original, a ser compactado, gerando seu hashmap e lista de
 * bits (booleans)
 */
public class Leitor {
	
	private HashMap<Integer, String> binarioCaracter;	
	private HashMap<String, Integer> caractereBinario;
	private HashMap<Integer, String> binarioCaracterOriginal;	
	private HashMap<String, Integer> caractereBinarioOriginal;
	private int tamanhoMaximoDic = Integer.MAX_VALUE;
	private int contadorBinario;


	private File arquivo;
	
	/**
	 * Já inicializa o dicionário (hashmap)
	 * @param arquivo
	 * @throws IOException
	 */
	public Leitor(File arquivo) throws IOException {
		this.arquivo = arquivo;
		binarioCaracter = new HashMap<Integer, String>();
		caractereBinario = new HashMap<String, Integer>();
		contadorBinario = 0;
		inicializarDics();
	}
	
	public void setTamanhoMaximoDic(int maximo) {
		this.tamanhoMaximoDic = maximo;
	}
	
	// Faz uma leitura inicial do arquivo, para saber quais caracteres ele tem
	private void inicializarDics() throws IOException {
		FileInputStream ins = new FileInputStream(arquivo.getPath());
		InputStreamReader input = new InputStreamReader(ins, Principal.CODIFICACAO);
		int letraInt = input.read();
		String letra = "";
		while (letraInt != -1) {
			letra = new String(Character.toChars(letraInt));
			if (!caractereBinario.containsKey(letra)) {
				acrescentaString(letra);
			}
			letraInt = input.read(); 
		}
		caractereBinarioOriginal = getCaractereBinario();
		binarioCaracterOriginal = getBinarioCaractere();
		input.close();
	}


	private void acrescentaString(String letra) {
		caractereBinario.put(letra, contadorBinario);
		binarioCaracter.put(contadorBinario++, letra);
	}
	
	
	/**
	 * Altera o hashmap, não chamar duas vezes.
	 * @return Uma lista de booleans que representam os bits
	 * @throws IOException
	 */
	public LinkedList<Boolean> compactar() throws IOException {
		LinkedList<Boolean> lista = new LinkedList<Boolean>();
		//HashMap<String, Integer> caractereBinario = getBinarioCaractere();
		FileInputStream ins = new FileInputStream(arquivo.getPath());
		InputStreamReader input = new InputStreamReader(ins, Principal.CODIFICACAO);
		int letraInt = input.read();
		String contido = "";
		String ultimaLetraLida = "";
		String aumentado = "";
		while (letraInt != -1) {
			ultimaLetraLida = new String(Character.toChars(letraInt));
			aumentado = ultimaLetraLida;
			contido = aumentado;
			while (caractereBinario.containsKey(aumentado)) {
				contido = aumentado;
				letraInt = input.read();
				if (letraInt == -1)
					break;
				ultimaLetraLida = new String(Character.toChars(letraInt));
				aumentado += ultimaLetraLida;
			}
			lista.addAll(escreveBinario(caractereBinario.get(contido), 
				     quantosBitsRepresenta(contadorBinario-1)));
			if (letraInt != -1)
				acrescentaString(aumentado);
			if (contadorBinario >= tamanhoMaximoDic)
				reiniciaDicionarios();
		}
		input.close();
		
		return lista;
	}
	
	private void reiniciaDicionarios() {
		caractereBinario = getCaractereBinarioOriginal();
		binarioCaracter = getBinarioCaracterOriginal();
		contadorBinario = caractereBinario.size();
	}

	/**
	 * Compacta utilizando um dicionário em binário
	 * @return Uma lista de booleans que representam os bits
	 * @throws FileNotFoundException 
	 * @throws IOException
	 */
	public LinkedList<Boolean> compactarBinario() throws IOException {
		LinkedList<Boolean> compactados = new LinkedList<Boolean>();
		LinkedList<Boolean> entrada = LeitorCompactado.lerBits(
				new FileInputStream(arquivo.getPath()), 0);
		HashMap<LinkedList<Boolean>, Integer> dic = new HashMap<>();
		dic.put(new LinkedList<Boolean>(Arrays.asList(false)), 0);
		dic.put(new LinkedList<Boolean>(Arrays.asList(true)), 1);
		
		Iterator<Boolean> i = entrada.iterator();
		boolean ultimoBitLido;
		LinkedList<Boolean> contido;
		LinkedList<Boolean> aumentado;
		while (i.hasNext()) {
			ultimoBitLido = i.next();
			aumentado = new LinkedList<Boolean>(Arrays.asList(ultimoBitLido));
			contido = new LinkedList<Boolean>();
			while (dic.containsKey(aumentado)) {
				contido.add(ultimoBitLido);
				if (i.hasNext()) {
					ultimoBitLido = i.next();
					aumentado.add(ultimoBitLido);
				}
			}
			compactados.addAll(escreveBinario(dic.get(contido), 
				     quantosBitsRepresenta(dic.size()-1)));
			if (i.hasNext())
				dic.put(aumentado, dic.size());
		}
		
		return compactados;
	}
	
	/**
	 * Compacta utilizando um dicionário em binário
	 * @return Uma lista de booleans que representam os bits
	 * @throws IOException
	 */
	/*
	private LinkedList<Object> compactarObjetos(HashMap<LinkedList<Object>, Integer> dicOriginal, ) {
		LinkedList<Boolean> compactados = new LinkedList<Boolean>();
		LinkedList<Boolean> entrada = LeitorCompactado.lerBits(
				new FileInputStream(arquivo.getPath()), 0);
		HashMap<LinkedList<Boolean>, Integer> dic = new HashMap<>();
		dic.put(new LinkedList<Boolean>(Arrays.asList(false)), 0);
		dic.put(new LinkedList<Boolean>(Arrays.asList(true)), 1);
		
		Iterator<Boolean> i = entrada.iterator();
		boolean ultimoBitLido;
		LinkedList<Boolean> contido;
		LinkedList<Boolean> aumentado;
		while (i.hasNext()) {
			ultimoBitLido = i.next();
			aumentado = new LinkedList<Boolean>(Arrays.asList(ultimoBitLido));
			contido = new LinkedList<Boolean>();
			while (dic.containsKey(aumentado)) {
				contido.add(ultimoBitLido);
				if (i.hasNext()) {
					ultimoBitLido = i.next();
					aumentado.add(ultimoBitLido);
				}
			}
			compactados.addAll(escreveBinario(dic.get(contido), 
				     quantosBitsRepresenta(dic.size()-1)));
			if (i.hasNext())
				dic.put(aumentado, dic.size());
		}
		
		return compactados;
	}*/
	
	
	/**
	 * Converte inteiro para binário (lista de booleans)
	 * @param numero Número a ser convertido
	 * @param qBits Quantidade de bits a ser escrita
	 * @return Uma lista de booleans representando os bits do número em binário
	 */
	public static LinkedList<Boolean> escreveBinario(int numero, int qBits) {
		LinkedList<Boolean> lista = new LinkedList<Boolean>();
		int mascara = 1 << (qBits - 1);
		while (qBits > 0) {
			lista.add((mascara & numero) != 0);
			numero = numero << 1;
			qBits--;
		}
		return lista;
	}
	
	
	/**
	 * Calcula o menor número possível de bits para se representar o inteiro
	 * @param numero Inteiro a ser representado
	 * @return Quantidade de bits mínima para representar o número
	 */
	public static int quantosBitsRepresenta(int numero) {
		if (numero <= 0)
			return 1;
		int qBits = 0;
		for (int num = numero; num > 0; qBits++) {
			num = num >> 1;
		}
		return qBits;
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
	public HashMap<Integer, String> getBinarioCaracterOriginal() {
		return (HashMap<Integer, String>) binarioCaracterOriginal.clone();
	}


	@SuppressWarnings("unchecked")
	public HashMap<String, Integer> getCaractereBinarioOriginal() {
		return (HashMap<String, Integer>)  caractereBinarioOriginal.clone();
	}

}
