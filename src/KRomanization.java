import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
/*
 * ***HANGUL ROMANIZATOR***
 * 
 * Ce programme "romanise" le hangul. Il nécessite donc l'entrée d'une phrase en hangul.
 * 
 * Le hangul est l’alphabet officiel du coréen. Il comprend 40 lettres, appelées "jamos", divisées en plusieurs 
 * catégories. 
 * >>> L’unité graphique est la syllabe et non le phonème (on ne peut pas écrire les jamos 
 * indépendamment sauf pour des raisons didactiques). 
 * - Wikipédia (https://en.wikipedia.org/wiki/Hangul)
 * 
 * La romanisation est le fait de transcrire une écriture non-latine (ici, le hangul) vers l'alphabet romain/latin.
 * - Wikipédia (https://en.wikipedia.org/wiki/Romanization)
 * 
 * Pour les besoins du programme, les jamos ont été regroupés sous les étiquettes suivantes : 
 * initiales : consonnes + consonnes doubles
 * mediales : voyelles + voyelles composées
 * finales : consonnes + consonnes composées 
 * 
 * >>> Une syllabe coréene est un bloc unicode composé d'une initiale (facultative), d'une mediale (obligatoire) 
 * et d'une finale (facultative).
 *  
 */
public class KRomanization {

	private static Map<Integer, KSyllable> allSyllables = generateAllSyllables();
	private static Map<Integer, String> allInitial = loadRomanization("initiales.txt");
	private static Map<Integer, String> allMedial = loadRomanization("mediales.txt");
	private static Map<Integer, String> allFinal = loadRomanization("finales.txt");
	
	
	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		System.out.println("*** HANGUL ROMANIZER ***");
		String kr = "나는 김치를 많이 좋아합니다 (j'aime beaucoup le kimchi)";
		System.out.println("Demo :\n" + kr + "\ndevient \""+ romanize(kr)+ "\"");
        System.out.println("\nA vous d'essayer ! Entrez une phrase en coréen : ");
        String kr_sentence = input.nextLine();
        
        String romanization = romanize(kr_sentence);
        
        System.out.println(romanization);
        input.close();
        
	}//fin main

	public static String romanize(String kSentence) {
		char[] charArray = kSentence.toCharArray();
        
        String romanization = "";
        
        for (char kr : charArray) {
       
        	KSyllable kSyllable = allSyllables.get((int) kr);
        	if (kSyllable == null) {
        		romanization += kr;
        	} else {
        		int initial = kSyllable.initial;
        		int medial = kSyllable.medial;
        		int finale = kSyllable.finale;
        		
        		romanization += allInitial.get(initial);
        		romanization += allMedial.get(medial);
        		romanization += allFinal.get(finale);
        	} //fin if-else
        		
        }//fin for 
		return romanization;
	}// fin romanize
	
	private static Map<Integer, KSyllable> generateAllSyllables() {
		Map<Integer, KSyllable> result = new HashMap<>();

		for (int initial = 0; initial <= 18; initial++){
		    for (int medial = 0; medial <= 20; medial++){
		    	for (int finale = 0; finale <= 27; finale++){
		    		// voir "https://en.wikipedia.org/wiki/Korean_language_and_computers#Hangul_in_Unicode"
		    		int syllableCode = initial * 588 + medial * 28 + finale + 44032; 
		    		result.put(syllableCode, new KSyllable(initial, medial, finale));
		    	}//fin for finale
		    }//fin for medial
		}// fin for initial
		
		return result;
	}// fin generateAllSyllables 
	
	private static Map<Integer, String> loadRomanization(String fileName) {
		Map<Integer, String> result = new HashMap<>();
	
		try {
			Files.lines(Paths.get(fileName))
			//exp lambda
			.forEach(l ->  {
				String[] split = l.split("=", -1); // "-1" pour garder les blancs (=> chaines vides)
				result.put(Integer.parseInt(split[0]), split[1]);
			});
		} catch (IOException e) {
			// Fichier introuvable!
			System.out.println("Fichier introuvable.");
		}
	      
		return result;
	}//fin loadRomanization

	
}//fin class
