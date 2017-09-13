package ele32_lab2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

public class Leitor {
	
	private HashMap<Integer, String> binarioCaracter;	
	private HashMap<String, Integer> caractereBinario;
	private int contadorBinario;
	private File arquivo;
	
	public Leitor(File arquivo) throws IOException {
		this.arquivo = arquivo;
		binarioCaracter = new HashMap<Integer, String>();
		caractereBinario = new HashMap<String, Integer>();
		contadorBinario = 0;
		inicializarDics();
	}
	
	
	private void inicializarDics() throws IOException {
		FileInputStream ins = new FileInputStream(arquivo.getPath());
		InputStreamReader input = new InputStreamReader(ins, "UTF-8");
		int letraInt = input.read();
		String letra = "";
		while (letraInt != -1) {
			letra = new String(Character.toChars(letraInt));
			if (!caractereBinario.containsKey(letra)) {
				acrescentaString(letra);
			}
			letraInt = input.read(); 
		}
		input.close();
	}


	private void acrescentaString(String letra) {
		caractereBinario.put(letra, contadorBinario);
		binarioCaracter.put(contadorBinario++, letra);
	}
	
	public Boolean[] compactar() throws IOException {
		LinkedList<Boolean> lista = new LinkedList<Boolean>();
		
		FileInputStream ins = new FileInputStream(arquivo.getPath());
		InputStreamReader input = new InputStreamReader(ins, "UTF-8");
		int letraInt = input.read();
		String contido = "";
		String aumentado = "";
		while (letraInt != -1) {
			aumentado = new String(Character.toChars(letraInt));
			while (caractereBinario.containsKey(aumentado)) {
				contido = aumentado;
				letraInt = input.read();
				aumentado += new String(Character.toChars(letraInt));
			}
			acrescentaString(aumentado);
			lista.addAll(escreveBinario(caractereBinario.get(contido), 
					     quantosBitsRepresenta(contadorBinario)));
			letraInt = input.read();
		}
		input.close();
		Boolean[] retorno = new Boolean[lista.size()];
		return retorno;
	}
	
	
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
	
	public static int quantosBitsRepresenta(int numero) {
		if (numero == 0)
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
	public HashMap<Integer, Integer> getBinarioCaractere() {
		return (HashMap<Integer, Integer>) binarioCaracter.clone();
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<Integer, Integer> getCaractereBinario() {
		return (HashMap<Integer, Integer>) caractereBinario.clone();
	}

}
