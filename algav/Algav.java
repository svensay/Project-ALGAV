package algav;

import algav.tasmin.ITasMin;
import algav.tasmin.TasMinTableau;
import algav.tasmin.TasMinArbre;
import algav.Cle128;
import algav.FileBinomiale;
//import algav.md5.MD5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Algav {
	/**
	 * Differentier les strucutre entre Tableau et Arbre
	 */
	private static final boolean isTab = true;

	/**
	 * Lit le fichier dans path et l'ajoute dans une structure de ITasMin et calcule
	 * le temps de ConstIter en nano seconde
	 *
	 * @param path Chemin vers le fichier de lecture
	 *
	 * @return le temps pour ajouter la liste des cles dans path avec ConstIter
	 */
	public static long timeConstIterTasMin(String path) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String sCurrentLine;
			List<Cle128> l = new ArrayList<>();
			ITasMin<Cle128> res;
			if (isTab) {
				res = new TasMinTableau<>();
			} else {
				res = new TasMinArbre<>();
			}
			while ((sCurrentLine = br.readLine()) != null) {
				l.add(new Cle128(sCurrentLine));
			}
			long startTime = System.nanoTime();
			res.consIter(l);
			long endTime = System.nanoTime();
			br.close();
			return (endTime - startTime);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * Lit le fichier dans path et l'ajoute dans une structure de File Binomiale et
	 * calcule le temps de ConstIter en nano seconde
	 *
	 * @param path Chemin vers le fichier de lecture
	 *
	 * @return le temps pour ajouter la liste des cles dans path avec ConstIter
	 */
	public static long timeConstIterFileBinomiale(String path) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String sCurrentLine;
			List<Cle128> l = new ArrayList<>();
			FileBinomiale<Cle128> res = new FileBinomiale<>();
			while ((sCurrentLine = br.readLine()) != null) {
				l.add(new Cle128(sCurrentLine));
			}
			long startTime = System.nanoTime();
			res.consIter(l);
			long endTime = System.nanoTime();
			br.close();
			return (endTime - startTime);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * Compte les lignes du fichier path
	 *
	 * @param path chemin du fichier
	 */

	public static int countLine(String path) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			int lines = 0;
			while (br.readLine() != null)
				lines++;
			br.close();
			return lines;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 *
	 * Enregistre les donnees de temps par rapport au nombre de vle dans un fichier
	 * path
	 *
	 * @param temps tableau du temps lors de l'execution de chaque type de
	 *              fichier(indice 0 temps pour 100cle, indice 1 temps pour 200cle
	 *              ..etc)
	 * @param path  Chemin ou on enrengistre les donnees
	 */
	public static void writeTime(int temps[], String path) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(path));
			bw.write(Integer.toString(100));
			bw.write(",");
			bw.write(Integer.toString(200));
			bw.write(",");
			bw.write(Integer.toString(500));
			bw.write(",");
			bw.write(Integer.toString(1000));
			bw.write(",");
			bw.write(Integer.toString(5000));
			bw.write(",");
			bw.write(Integer.toString(10000));
			bw.write(",");
			bw.write(Integer.toString(20000));
			bw.write(",");
			bw.write(Integer.toString(50000));
			bw.newLine();
			for (int i : temps) {
				bw.write(Integer.toString(i / 5));
				bw.write(",");
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Calcule la moyenne du temps de construction de chaque liste de cle et
	 * l'enrengistre dans un fichier .csv
	 * 
	 * @param path chemin du dossier contenant les cles
	 */
	public static void averageConstIterTasMin(String path) {
		File folder = new File(path);
		int temps[] = new int[8];
		String route;
		for (File f : folder.listFiles()) {
			route = path + f.getName();
			switch (countLine(route)) {
			case 100:
				temps[0] += timeConstIterTasMin(route);
				break;
			case 200:
				temps[1] += timeConstIterTasMin(route);
				break;
			case 500:
				temps[2] += timeConstIterTasMin(route);
				break;
			case 1000:
				temps[3] += timeConstIterTasMin(route);
				break;
			case 5000:
				temps[4] += timeConstIterTasMin(route);
				break;
			case 10000:
				temps[5] += timeConstIterTasMin(route);
				break;
			case 20000:
				temps[6] += timeConstIterTasMin(route);
				break;
			case 50000:
				temps[7] += timeConstIterTasMin(route);
				break;
			}
		}
		writeTime(temps, "averageConstIterTasMin.csv");
	}

	/**
	 * Calcule la moyenne du temps de construction de chaque liste de cle et
	 * l'enrengistre dans un fichier .csv
	 * 
	 * @param path chemin du dossier contenant les cles
	 */
	public static void averageConstIterFileBinomiale(String path) {
		File folder = new File(path);
		int temps[] = new int[8];
		String route;
		for (File f : folder.listFiles()) {
			route = path + f.getName();
			switch (countLine(route)) {
			case 100:
				temps[0] += timeConstIterFileBinomiale(route);
				break;
			case 200:
				temps[1] += timeConstIterFileBinomiale(route);
				break;
			case 500:
				temps[2] += timeConstIterFileBinomiale(route);
				break;
			case 1000:
				temps[3] += timeConstIterFileBinomiale(route);
				break;
			case 5000:
				temps[4] += timeConstIterFileBinomiale(route);
				break;
			case 10000:
				temps[5] += timeConstIterFileBinomiale(route);
				break;
			case 20000:
				temps[6] += timeConstIterFileBinomiale(route);
				break;
			case 50000:
				temps[7] += timeConstIterFileBinomiale(route);
				break;
			}
		}
		writeTime(temps, "averageConstIterFileBinomiale.csv");
	}

	/**
	 * Lit le fichier dans path et l'ajoute dans une structure de ITasMin et calcule
	 * le temps de Union en nano seconde avec t
	 *
	 * @param t    Tas min avec qui on fait l'union
	 * @param path Chemin vers le fichier de lecture
	 *
	 * @return le temps d'unir la liste des cles dans path avec t
	 */
	public static long timeUnionTasMin(ITasMin<Cle128> t, String path) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String sCurrentLine;
			List<Cle128> l = new ArrayList<>();
			ITasMin<Cle128> t2;
			if (isTab) {
				t2 = new TasMinTableau<>();
			} else {
				t2 = new TasMinArbre<>();
			}
			while ((sCurrentLine = br.readLine()) != null) {
				l.add(new Cle128(sCurrentLine));
			}
			t2.consIter(l);
			long startTime = System.nanoTime();
			t.union(t2);
			long endTime = System.nanoTime();
			br.close();
			return (endTime - startTime);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * Lit le fichier dans path et l'ajoute dans une structure d'une FileBinomiale
	 * et calcule le temps de Union en nano seconde avec t
	 *
	 * @param l1   File Binomiale avec qui on fait l'union
	 * @param path Chemin vers le fichier de lecture
	 *
	 * @return le temps d'unir la liste des cles dans path avec t
	 */
	public static long timeUnionFileBinomiale(FileBinomiale<Cle128> l1, String path) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String sCurrentLine;
			List<Cle128> l = new ArrayList<>();
			FileBinomiale<Cle128> l2 = new FileBinomiale<>();
			while ((sCurrentLine = br.readLine()) != null) {
				l.add(new Cle128(sCurrentLine));
			}
			l2.consIter(l);
			long startTime = System.nanoTime();
			l1.union(l2);
			long endTime = System.nanoTime();
			br.close();
			return (endTime - startTime);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * Calcule la moyenne du temps de l'union de chaque liste de cle et
	 * l'enrengistre dans un fichier .csv On unit toute les listes de meme nombre de
	 * cle entre elle(tous les 100 ensemble, tous les 200 ensemble...etc.)
	 * 
	 * @param path chemin du dossier contenant les cles
	 */
	public static void averageUnionTasMin(String path) {
		File folder = new File(path);
		int temps[] = new int[8];
		ITasMin<Cle128> t100;
		ITasMin<Cle128> t200;
		ITasMin<Cle128> t500;
		ITasMin<Cle128> t1000;
		ITasMin<Cle128> t5000;
		ITasMin<Cle128> t10000;
		ITasMin<Cle128> t20000;
		ITasMin<Cle128> t50000;
		if (isTab) {
			t100 = new TasMinTableau<>();
			t200 = new TasMinTableau<>();
			t500 = new TasMinTableau<>();
			t1000 = new TasMinTableau<>();
			t5000 = new TasMinTableau<>();
			t10000 = new TasMinTableau<>();
			t20000 = new TasMinTableau<>();
			t50000 = new TasMinTableau<>();
		} else {
			t100 = new TasMinArbre<>();
			t200 = new TasMinArbre<>();
			t500 = new TasMinArbre<>();
			t1000 = new TasMinArbre<>();
			t5000 = new TasMinArbre<>();
			t10000 = new TasMinArbre<>();
			t20000 = new TasMinArbre<>();
			t50000 = new TasMinArbre<>();
		}
		String route;
		for (File f : folder.listFiles()) {
			route = path + f.getName();
			switch (countLine(route)) {
			case 100:
				temps[0] += timeUnionTasMin(t100, route);
				break;
			case 200:
				temps[1] += timeUnionTasMin(t200, route);
				break;
			case 500:
				temps[2] += timeUnionTasMin(t500, route);
				break;
			case 1000:
				temps[3] += timeUnionTasMin(t1000, route);
				break;
			case 5000:
				temps[4] += timeUnionTasMin(t5000, route);
				break;
			case 10000:
				temps[5] += timeUnionTasMin(t10000, route);
				break;
			case 20000:
				temps[6] += timeUnionTasMin(t20000, route);
				break;
			case 50000:
				temps[7] += timeUnionTasMin(t50000, route);
				break;
			}
		}
		writeTime(temps, "averageUnionTasMin.csv");
	}

	/**
	 * Calcule la moyenne du temps de l'union de chaque liste de cle et
	 * l'enrengistre dans un fichier .csv On unit toute les listes de meme nombre de
	 * cle entre elle(tous les 100 ensemble, tous les 200 ensemble...etc.)
	 * 
	 * @param path chemin du dossier contenant les cles
	 */
	public static void averageUnionFileBinomiale(String path) {
		File folder = new File(path);
		int temps[] = new int[8];
		FileBinomiale<Cle128> t100;
		FileBinomiale<Cle128> t200;
		FileBinomiale<Cle128> t500;
		FileBinomiale<Cle128> t1000;
		FileBinomiale<Cle128> t5000;
		FileBinomiale<Cle128> t10000;
		FileBinomiale<Cle128> t20000;
		FileBinomiale<Cle128> t50000;
		t100 = new FileBinomiale<>();
		t200 = new FileBinomiale<>();
		t500 = new FileBinomiale<>();
		t1000 = new FileBinomiale<>();
		t5000 = new FileBinomiale<>();
		t10000 = new FileBinomiale<>();
		t20000 = new FileBinomiale<>();
		t50000 = new FileBinomiale<>();
		String route;
		for (File f : folder.listFiles()) {
			route = path + f.getName();
			switch (countLine(route)) {
			case 100:
				temps[0] += timeUnionFileBinomiale(t100, route);
				break;
			case 200:
				temps[1] += timeUnionFileBinomiale(t200, route);
				break;
			case 500:
				temps[2] += timeUnionFileBinomiale(t500, route);
				break;
			case 1000:
				temps[3] += timeUnionFileBinomiale(t1000, route);
				break;
			case 5000:
				temps[4] += timeUnionFileBinomiale(t5000, route);
				break;
			case 10000:
				temps[5] += timeUnionFileBinomiale(t10000, route);
				break;
			case 20000:
				temps[6] += timeUnionFileBinomiale(t20000, route);
				break;
			case 50000:
				temps[7] += timeUnionFileBinomiale(t50000, route);
				break;
			}
		}
		writeTime(temps, "averageUnionFileBinomiale.csv");
	}

	public static List<String> listShak(String path) {
		File folder = new File(path);
		String[] tab = folder.list();
		Arrays.sort(tab);
		String route;
		List<String> l = new ArrayList<>();
		try {
			for (String str : tab) {
				route = path + str;
				BufferedReader br = new BufferedReader(new FileReader(route));
				String sCurrentLine;
				while ((sCurrentLine = br.readLine()) != null) {
					if(!l.contains(sCurrentLine)) {
						l.add(sCurrentLine);	
					}
				}
				br.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return l;
	}

	public static void main(String[] args) {
		Cle128 test1 = new Cle128("0x9c1f03a0d9cf510f2765bd0f226ff5dc");
		Cle128 test2 = new Cle128("0x9c1f03a0d9cf510f2765bd0f226ff5dc");

		Cle128 test3 = new Cle128("0x10fd1015413104a2f26018d0ab77a729");
		Cle128 test4 = new Cle128("0x10fd1015413104a2f26018d0ab77a728");
		Cle128 test6 = new Cle128("0x10fd1015413104a2f26018d0ab77a728");
		// Cle128 test5 = new Cle128("0x10fd1015413104a2f26018d0ab77a727");

		System.out.println(test4 == test6);

		/*
		 * ITasMin<Integer> test = new TasMinTableau<>(); test.ajout(2) .ajout(5)
		 * .ajout(6) .ajout(10) .ajout(13) .ajout(8) .ajout(7) .ajout(12) .ajout(15)
		 * .ajout(15) .ajout(15) .ajout(14);
		 */
		ITasMin<Cle128> test = new TasMinTableau<>();
		test.ajout(test1).ajout(test4).ajout(test3).ajout(test2);
		System.out.println(test);

		test.supprMin();
		System.out.println(test);
		// System.out.println(timeConstIter(args[0]));
		// averageConstIterTasMin(args[0]);
		// averageUnionTasMin(args[0]);
		// averageConstIterFileBinomiale(args[0]);
		// averageUnionFileBinomiale(args[0]);
		// System.out.println(MD5.get("Wikipedia, l'encyclopedie libre et gratuite"));
		List<String> l = listShak(args[0]);
		for (String str : l) {
			System.out.print(str + " ");
		}
	}

}
