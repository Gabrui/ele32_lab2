package ele32_lab2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Entropia {
	/**
	 * Recebe como parâmetro de execução o diretório de onde vai ler os arquivos
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// File[] files = new File(args[0]).listFiles(); // Se não quiser rodar no terminal passa a pasta aqui
		File[] files = new File("../textos/biblia").listFiles();
		System.out.println("Linguaguem & Quantidade & Tipos & Entropia & Condicional \\\\");
		for (File file : files) {
			HashMap<Integer, HashMap<Integer, Integer>> frequency = new HashMap<Integer, HashMap<Integer, Integer>>();
			HashMap<Integer, Integer> frequencyT = new HashMap<Integer, Integer>();
			int frequency_total;
			float entropy_total;
			float entropy_conditional;
			
			read_file(file, frequency);
			//System.out.println(file.getName());
			
			frequency_total = calculate_frequency(frequency, frequencyT);
			//System.out.println("Amount of chars: "+frequency_total);
			//System.out.println("Types of chars: "+frequencyT.size());
			
			entropy_total = calculate_entropy_total(frequencyT, frequency_total);
			//System.out.println("Entropy: " + entropy_total);
			
			entropy_conditional = calculate_entropy_condicional(frequency, frequencyT, frequency_total);
			//System.out.println("Conditional Entropy: " + entropy_conditional);
			System.out.println(file.getName() +" & " + 
							   frequency_total+" & " +
							   frequencyT.size()+" & " +
							   String.format("%.02f", entropy_total)+" & " +
							   //String.format("%.02f", entropy_total*frequency_total)+" & " +
							   //String.format("%.02f", entropy_total-entropy_conditional)+" & " +
							   String.format("%.02f", entropy_conditional)+" \\\\");
		}
		lerVersiculo19();
	}


	private static float calculate_entropy_condicional(HashMap<Integer, HashMap<Integer, Integer>> frequency,
			HashMap<Integer, Integer> frequencyT, int frequency_total) {
		float entropy_conditional;
		entropy_conditional = 0;
		for (int i : frequency.keySet())
			for (int j : frequency.get(i).keySet()) {
				float p_previous_letter = ((float) frequencyT.get(j))/frequency_total;
				float p_conditional = ((float) frequency.get(i).get(j))/frequencyT.get(j);
				entropy_conditional -= p_previous_letter*p_conditional*Math.log(p_conditional)/Math.log(2);
			}
		return entropy_conditional;
	}


	private static float calculate_entropy_total(HashMap<Integer, Integer> frequencyT, int frequency_total) {
		float entropy_total = 0;
		for (int i : frequencyT.keySet()) {
			float p = ((float) frequencyT.get(i))/frequency_total;
			entropy_total -=  p*Math.log(p)/Math.log(2);
		}
		return entropy_total;
	}


	private static int calculate_frequency(HashMap<Integer, HashMap<Integer, Integer>> frequency,
			HashMap<Integer, Integer> frequencyT) {
		int frequency_total = 0;
		for (int i: frequency.keySet()) {
			int tmp = 0;
			for (int j : frequency.get(i).keySet())
				tmp += frequency.get(i).get(j);
			frequency_total += tmp;
			frequencyT.put(i, tmp);
		}
		return frequency_total;
	}
	

	private static void read_file(File file, HashMap<Integer, HashMap<Integer, Integer>> frequency)
			throws IOException {
		FileInputStream ins = new FileInputStream(file.getPath());
		InputStreamReader input = new InputStreamReader(ins, "UTF-8");
		int previous_letter = input.read(); 
		int letter = previous_letter;
		do {
			if (!frequency.containsKey(letter))
				frequency.put(letter, new HashMap<Integer, Integer>());
			int previous_value;
			if (frequency.get(letter).containsKey(previous_letter))
				previous_value = frequency.get(letter).get(previous_letter);
			else
				previous_value = 0;
			frequency.get(letter).put(previous_letter, previous_value + 1);
			previous_letter = letter;
			letter = input.read();
		} while (letter != -1);
		input.close();
	}
	
	/**
	 * Usado para imprimir na tela os primeiros versículos
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void lerVersiculo19() throws FileNotFoundException, IOException {
		File[] files = new File("textos/Bible/").listFiles();
		System.out.println("Linguagem & Tradução \\\\");
		System.out.println("\\hline \\hline");
		for (File file : files) {
			FileReader ins = new FileReader(file.getPath());
			BufferedReader input = new BufferedReader(ins);
			String ver = "";
			for (int i = 0; i<19; i++)
				ver = input.readLine();
			System.out.println(file.getName() +" & " + 
					   ver+" \\\\");
			input.close();
		}
	}

}
