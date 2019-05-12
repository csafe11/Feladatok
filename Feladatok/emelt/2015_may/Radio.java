import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Radio {
	
	public static void main(String[] args) throws IOException {
		var file = Files.readAllLines(Paths.get("veetel.txt"));
		
		List<Feljegyzes> feljegyzesek = new ArrayList<>();
		for(int k = 0; k < file.size(); k += 2) {
			feljegyzesek.add(new Feljegyzes(file.get(k).split(" "), file.get(k + 1)));
		}
		
		System.out.println("2.Feladat\nEls� feljegyz�: " + feljegyzesek.get(0).radioAmator + ", utols�: " + feljegyzesek.get(feljegyzesek.size() - 1).radioAmator);
		System.out.println("3.Feladat");
		
		for(Feljegyzes feljegyzes : feljegyzesek) {
			if(feljegyzes.adat.contains("farkas")) {
				System.out.println("Nap: " + feljegyzes.nap + ", feljegyzo: " + feljegyzes.radioAmator);
			}
		}
		
		System.out.println("4.Feladat");
		for(int k = 1; k < 12; ++k) {
			int napiSzam = 0;
			for(Feljegyzes feljegyzes : feljegyzesek) {
				if(feljegyzes.nap == k) {
					++napiSzam;
				}
			}
			System.out.println(k + ". napon lev� feljegyz�sek sz�ma: " + napiSzam);
		}
		
		try(PrintWriter output = new PrintWriter("adaas.txt")){
			for(int k = 1; k < 12; ++k) {
				char[] felj = null;
				for(Feljegyzes feljegyzes : feljegyzesek) {
					if(feljegyzes.nap == k) {
						if(felj == null) {
							felj = feljegyzes.adat.toCharArray();
						}else {
							char[] nextData = feljegyzes.adat.toCharArray();
							
							for(int charIndex = 0; charIndex < felj.length; ++charIndex) {
								if(felj[charIndex] == '#' && Character.isLetter(nextData[charIndex])) {
									felj[charIndex] = nextData[charIndex];
								}
							}
						}
					}
				}
				output.println(new String(felj));
			}
		}
		
		System.out.println("7.Feladat\n�rj be 1 napot (1-11) �s 1 megfigyel� sorsz�m�t!");
		try(Scanner input = new Scanner(System.in)){
			int readNap = input.nextInt(), readMegfigyelo = input.nextInt(), egyedszam = 0;
			
			boolean voltIlyen = false;
			for(Feljegyzes feljegyzes : feljegyzesek) {
				if(feljegyzes.nap == readNap && feljegyzes.radioAmator == readMegfigyelo) {
					voltIlyen = true;
					egyedszam += feljegyzes.gyerekekSzama;
					egyedszam += feljegyzes.szulokSzama;
				}
			}
			
			if(voltIlyen) {
				if(egyedszam == 0) {
					System.out.println("Nem hat�rozhat� meg");
				}else {
					System.out.println(egyedszam);
			}}else {
				System.out.println("Nem volt ilyen feljegyz�s");
			}
		}
	}
	
	private static boolean szame(char[] szo) {
		boolean valasz = true;
		for(int i = 1; i < szo.length; ++i) {
			if(szo[i] < '0' || szo[i] > '9') {
				valasz = false;
			}
		}
		
		return valasz;
	}
	
	static class Feljegyzes{
		int nap, radioAmator, szulokSzama, gyerekekSzama;
		String adat;
		
		public Feljegyzes(String[] data1, String data2) {
			nap = Integer.parseInt(data1[0]);
			radioAmator = Integer.parseInt(data1[1]);
			
			if(data2.contains("/")){
				char first = data2.charAt(0), second = data2.charAt(2);
				if(first != '#') {
					szulokSzama = Character.getNumericValue(first);
				}
				if(second != '#') {
					Character.getNumericValue(second);
				}
				
				adat = data2.substring(3);
			}else {
				adat = data2;
				gyerekekSzama = 0;
				szulokSzama = 0;
			}
		}
	}
}