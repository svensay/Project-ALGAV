
import algav.Cle128;
import algav.FileBinomiale;
import algav.TournoiBinomial;
import algav.tasmin.ITasMin;
import algav.tasmin.TasMinArbre;
import algav.tasmin.TasMinTableau;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
public class Main {

	static HashMap<String, List<Cle128>> fichiers = new HashMap<>();
	static int nbJeux = 5;
	static int nbDonnees[] = { 100, 200, 500, 1000, 5000, 10000, 20000, 50000 };

	public static void main(String[] args) {
		String chemin = args[0];

		// Courbe pour comparer les TasMinArbre et TasMinTableau
		writeTime(calculerPerfConstIterTasMin(chemin, TasMinArbre.class),
				calculerPerfConstIterTasMin(chemin, TasMinTableau.class),
				"averageConsIterTasMinArbreTasMinTableau.csv");
		writeTime(calculerPerfUnionTasMin(chemin, TasMinArbre.class),
				calculerPerfUnionTasMin(chemin, TasMinTableau.class), "averageUnionTasMinArbreTasMinTableau.csv");

		// Courbe pour comparer les TasMin(la meilleur structure) et File Binomiale.
		writeTime(calculerPerfSupprMinFileBinomiale(chemin, FileBinomiale.class),
				calculerPerfSupprMinTasMin(chemin, TasMinTableau.class), "averageSupprMinFileBinomialeTasMin.csv");

		writeTime(calculerPerfAjoutFileBinomiale(chemin, FileBinomiale.class),
				calculerPerfAjoutTasMin(chemin, TasMinTableau.class), "averageAjoutFileBinomialeTasMin.csv");

		writeTime(calculerPerfConstIterFileBinomiale(chemin, FileBinomiale.class),
				calculerPerfConstIterTasMin(chemin, TasMinTableau.class), "averageConsIterFileBinomialeTasMin.csv");

		writeTime(calculerPerfUnionFileBinomiale(chemin, FileBinomiale.class),
				calculerPerfUnionTasMin(chemin, TasMinTableau.class), "averageUnionFileBinomialeTasMin.csv");

	}

	/**
	 * Calcule la moyenne de temps d'execution de consIter pour les TasMin
	 * 
	 * @param chemin Le dossier qui contient le jeu de donnés
	 * @param c      La class avec la quelle on veut calcule le temps d'execution
	 * @return le tableau de la moyenne de temps
	 */
	public static long[] calculerPerfConstIterTasMin(String chemin, Class<? extends ITasMin> c) {
		int k = 0;
		long res[] = new long[nbDonnees.length];
		try {
			for (int nb : nbDonnees) {
				long temps = 0;
				for (int i = 1; i <= nbJeux; i++) {
					ITasMin<Cle128> tas = (ITasMin<Cle128>) c.newInstance();

					long start = System.nanoTime() / 1000;
					tas.consIter(getLignesFichier(chemin + "jeu_" + i + "_nb_cles_" + nb + ".txt"));
					temps += (System.nanoTime() / 1000) - start;
				}
				res[k] = temps / nbJeux;
				k++;
			}
		} catch (InstantiationException | IllegalAccessException ex) {
			System.out.println(ex.getMessage());
			System.exit(0);
		}
		return res;
	}

	public static long[] calculerPerfSupprMinTasMin(String chemin, Class<? extends ITasMin> c) {
		int k = 0;
		long res[] = new long[nbDonnees.length];
		try {
			for (int nb : nbDonnees) {
				long temps = 0;
				for (int i = 1; i <= nbJeux; i++) {
					ITasMin<Cle128> tas = (ITasMin<Cle128>) c.newInstance();
					tas.consIter(getLignesFichier(chemin + "jeu_" + i + "_nb_cles_" + nb + ".txt"));

					long start = System.nanoTime() / 1000;
					while (!tas.estVide()) {
						tas.supprMin();
					}
					temps += (System.nanoTime() / 1000) - start;
				}
				res[k] = temps / nbJeux;
				k++;
			}
		} catch (InstantiationException | IllegalAccessException ex) {
			System.out.println(ex.getMessage());
			System.exit(0);
		}
		return res;
	}

	public static long[] calculerPerfAjoutTasMin(String chemin, Class<? extends ITasMin> c) {
		int k = 0;
		long res[] = new long[nbDonnees.length];
		try {
			for (int nb : nbDonnees) {
				long temps = 0;
				for (int i = 1; i <= nbJeux; i++) {
					ITasMin<Cle128> tas = (ITasMin<Cle128>) c.newInstance();
					List<Cle128> liste = getLignesFichier(chemin + "jeu_" + i + "_nb_cles_" + nb + ".txt");

					long start = System.nanoTime() / 1000;
					for (Cle128 cle : liste) {
						tas.ajout(cle);
					}
					temps += (System.nanoTime() / 1000) - start;
				}
				res[k] = temps / nbJeux;
				k++;
			}
		} catch (InstantiationException | IllegalAccessException ex) {
			System.out.println(ex.getMessage());
			System.exit(0);
		}
		return res;
	}

	/**
	 * Calcule la moyenne de temps d'execution de union pour les TasMin
	 * 
	 * @param chemin Le dossier qui contient le jeu de donnés
	 * @param c      La class avec la quelle on veut calcule le temps d'execution
	 * @return le tableau de la moyenne de temps
	 */
	public static long[] calculerPerfUnionTasMin(String chemin, Class<? extends ITasMin> c) {
		int k = 0;
		long res[] = new long[nbDonnees.length];
		try {
			for (int nb : nbDonnees) {
				long temps = 0;
				ITasMin<Cle128> tas = (ITasMin<Cle128>) c.newInstance();
				for (int i = 1; i <= nbJeux; i++) {
					ITasMin<Cle128> tmpTas = (ITasMin<Cle128>) c.newInstance();
					tmpTas.consIter(getLignesFichier(chemin + "jeu_" + i + "_nb_cles_" + nb + ".txt"));

					long start = System.nanoTime() / 1000;
					tas = tas.union(tmpTas);
					temps += (System.nanoTime() / 1000) - start;
				}
				res[k] = temps / nbJeux;
				k++;
			}
		} catch (InstantiationException | IllegalAccessException ex) {
			System.out.println(ex.getMessage());
			System.exit(0);
		}
		return res;
	}

	/**
	 * Calcule la moyenne de temps d'execution de consIter pour les FileBinomiale.
	 * 
	 * @param chemin Le dossier qui contient le jeu de donnés
	 * @param c      La class avec la quelle on veut calcule le temps d'execution
	 * @return le tableau de la moyenne de temps
	 */
	public static long[] calculerPerfConstIterFileBinomiale(String chemin, Class<?> c) {
		int k = 0;
		long res[] = new long[nbDonnees.length];
		try {
			for (int nb : nbDonnees) {
				long temps = 0;
				for (int i = 1; i <= nbJeux; i++) {
					FileBinomiale<Cle128> file = (FileBinomiale<Cle128>) c.newInstance();

					long start = System.nanoTime() / 1000;
					file.consIter(getLignesFichier(chemin + "jeu_" + i + "_nb_cles_" + nb + ".txt"));
					temps += (System.nanoTime() / 1000) - start;
				}
				res[k] = temps / nbJeux;
				k++;
			}
		} catch (InstantiationException | IllegalAccessException ex) {
			System.out.println(ex.getMessage());
			System.exit(0);
		}
		return res;
	}

	public static long[] calculerPerfSupprMinFileBinomiale(String chemin, Class<?> c) {
		int k = 0;
		long res[] = new long[nbDonnees.length];
		try {
			for (int nb : nbDonnees) {
				long temps = 0;
				for (int i = 1; i <= nbJeux; i++) {
					FileBinomiale<Cle128> file = (FileBinomiale<Cle128>) c.newInstance();
					file.consIter(getLignesFichier(chemin + "jeu_" + i + "_nb_cles_" + nb + ".txt"));

					long start = System.nanoTime() / 1000;
					while (!file.estVide()) {
						file.supprMin();
					}
					temps += (System.nanoTime() / 1000) - start;
				}
				res[k] = temps / nbJeux;
				k++;
			}
		} catch (InstantiationException | IllegalAccessException ex) {
			System.out.println(ex.getMessage());
			System.exit(0);
		}
		return res;
	}

	public static long[] calculerPerfAjoutFileBinomiale(String chemin, Class<?> c) {
		int k = 0;
		long res[] = new long[nbDonnees.length];
		try {
			for (int nb : nbDonnees) {
				long temps = 0;
				for (int i = 1; i <= nbJeux; i++) {
					FileBinomiale<Cle128> file = (FileBinomiale<Cle128>) c.newInstance();
					List<Cle128> liste = getLignesFichier(chemin + "jeu_" + i + "_nb_cles_" + nb + ".txt");

					long start = System.nanoTime() / 1000;
					for (Cle128 cle : liste) {
						file.ajout(new TournoiBinomial<>(cle));
					}
					temps += (System.nanoTime() / 1000) - start;
				}
				res[k] = temps / nbJeux;
				k++;
			}
		} catch (InstantiationException | IllegalAccessException ex) {
			System.out.println(ex.getMessage());
			System.exit(0);
		}
		return res;
	}

	/**
	 * Calcule la moyenne de temps d'execution de union pour les FileBinomiale.
	 * 
	 * @param chemin Le dossier qui contient le jeu de donnés
	 * @param c      La class avec la quelle on veut calcule le temps d'execution
	 * @return le tableau de la moyenne de temps
	 */
	public static long[] calculerPerfUnionFileBinomiale(String chemin, Class<?> c) {
		int k = 0;
		long res[] = new long[nbDonnees.length];
		try {
			for (int nb : nbDonnees) {
				long temps = 0;
				FileBinomiale<Cle128> file = (FileBinomiale<Cle128>) c.newInstance();
				for (int i = 1; i <= nbJeux; i++) {
					FileBinomiale<Cle128> tmpFile = (FileBinomiale<Cle128>) c.newInstance();
					tmpFile.consIter(getLignesFichier(chemin + "jeu_" + i + "_nb_cles_" + nb + ".txt"));

					long start = System.nanoTime() / 1000;
					file = file.union(tmpFile);
					temps += (System.nanoTime() / 1000) - start;
				}
				res[k] = temps / nbJeux;
				k++;
			}
		} catch (InstantiationException | IllegalAccessException ex) {
			System.out.println(ex.getMessage());
			System.exit(0);
		}
		return res;
	}

	/**
	 * 
	 * @param path Fichier dont on veut recupérer les données
	 * @return List de Cle218 qui sont les cles dans le fichier path convertie.
	 */
	public static List<Cle128> getLignesFichier(String path) {
		if (fichiers.containsKey(path)) {
			return fichiers.get(path);
		}
		ArrayList<Cle128> res = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String s;
			while ((s = br.readLine()) != null) {
				res.add(new Cle128(s));
			}
			br.close();
		} catch (IOException e) {
			System.out.println("IOException pour " + path);
			System.out.println(e.getMessage());
			System.exit(0);
		}
		fichiers.put(path, res);
		return res;
	}

	public static void afficherTableauCSV(long tab[]) {
		for (long i : tab) {
			System.out.print(i + ";");
		}
		System.out.println();
	}

	/**
	 *
	 * Enregistre les donnees de temps de 2 structure de donnés pour les comparers.
	 *
	 * @param temps1 tableau du temps lors de l'execution de chaque type de
	 *               fichier(indice 0 temps pour 100cle, indice 1 temps pour 200cle
	 *               ..etc) de la premiere structure de donnée.
	 * 
	 * @param temps2 tableau du temps lors de l'execution de chaque type de
	 *               fichier(indice 0 temps pour 100cle, indice 1 temps pour 200cle
	 *               ..etc) de la deuxieme structure de donnée.
	 * 
	 * @param name   Chemin ou on enrengistre les donnees
	 */
	public static void writeTime(long temps1[], long temps2[], String name) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(name));
			for (long i : temps1) {
				bw.write(Long.toString(i));
				bw.write(",");
			}
			bw.newLine();
			for (long i : temps2) {
				bw.write(Long.toString(i));
				bw.write(",");
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
