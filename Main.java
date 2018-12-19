import algav.tasmin.ITasMin;
import algav.tasmin.TasMinArbre;
import algav.tasmin.TasMinTableau;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings({"unchecked","deprecation","rawtypes"})
public class Main {

    static HashMap<String, List<String>> fichiers = new HashMap<>();
    static int nbJeux = 5;
    static int nbDonnees[] =
    {
        100, 200, 500, 1000, 5000, 10000, 20000
    };

    public static void main(String[] args)
    {
        String chemin = "/home/maxime/Bureau/Project-ALGAV/cles_alea/";

        System.out.println("ConstIter");
        afficherTableauCSV(calculerPerfConstIterTasMin(chemin, TasMinArbre.class));
        afficherTableauCSV(calculerPerfConstIterTasMin(chemin, TasMinTableau.class));

        System.out.println();
        System.out.println("union");
        afficherTableauCSV(calculerPerfUnionTasMin(chemin, TasMinArbre.class));
        afficherTableauCSV(calculerPerfUnionTasMin(chemin, TasMinTableau.class));

        System.out.println();
        System.out.println("Ajout");
        afficherTableauCSV(calculerPerfAjoutTasMin(chemin, TasMinArbre.class));
        afficherTableauCSV(calculerPerfAjoutTasMin(chemin, TasMinTableau.class));

        System.out.println();
        System.out.println("SupprMin :");
        afficherTableauCSV(calculerPerfSupprMinTasMin(chemin, TasMinArbre.class));
        afficherTableauCSV(calculerPerfSupprMinTasMin(chemin, TasMinTableau.class));
    }

    public static long[] calculerPerfConstIterTasMin(String chemin, Class<? extends ITasMin> c)
    {
        int k = 0;
        long res[] = new long[nbDonnees.length];
        try
        {
            for (int nb : nbDonnees)
            {
                long temps = 0;
                for (int i = 1; i <= nbJeux; i++)
                {
                    ITasMin<String> tas = (ITasMin<String>) c.newInstance();

                    long start = System.nanoTime();
                    tas.consIter(getLignesFichier(chemin + "jeu_" + i + "_nb_cles_" + nb + ".txt"));
                    temps += System.nanoTime() - start;
                }
                res[k] = temps / nbJeux;
                k++;
            }
        } catch (InstantiationException | IllegalAccessException ex)
        {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
        return res;
    }

    public static long[] calculerPerfSupprMinTasMin(String chemin, Class<? extends ITasMin> c)
    {
        int k = 0;
        long res[] = new long[nbDonnees.length];
        try
        {
            for (int nb : nbDonnees)
            {
                long temps = 0;
                for (int i = 1; i <= nbJeux; i++)
                {
                    ITasMin<String> tas = (ITasMin<String>) c.newInstance();
                    tas.consIter(getLignesFichier(chemin + "jeu_" + i + "_nb_cles_" + nb + ".txt"));

                    long start = System.nanoTime();
                    while (!tas.estVide())
                    {
                        tas.supprMin();
                    }
                    temps += System.nanoTime() - start;
                }
                res[k] = temps / nbJeux;
                k++;
            }
        } catch (InstantiationException | IllegalAccessException ex)
        {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
        return res;
    }

    public static long[] calculerPerfAjoutTasMin(String chemin, Class<? extends ITasMin> c)
    {
        int k = 0;
        long res[] = new long[nbDonnees.length];
        try
        {
            for (int nb : nbDonnees)
            {
                long temps = 0;
                for (int i = 1; i <= nbJeux; i++)
                {
                    ITasMin<String> tas = (ITasMin<String>) c.newInstance();
                    List<String> liste = getLignesFichier(chemin + "jeu_" + i + "_nb_cles_" + nb + ".txt");

                    long start = System.nanoTime();
                    for (String cle : liste)
                    {
                        tas.ajout(cle);
                    }
                    temps += System.nanoTime() - start;
                }
                res[k] = temps / nbJeux;
                k++;
            }
        } catch (InstantiationException | IllegalAccessException ex)
        {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
        return res;
    }

    public static long[] calculerPerfUnionTasMin(String chemin, Class<? extends ITasMin> c)
    {
        int k = 0;
        long res[] = new long[nbDonnees.length];
        try
        {
            for (int nb : nbDonnees)
            {
                long temps = 0;
                ITasMin<String> tas = (ITasMin<String>) c.newInstance();
                for (int i = 1; i <= nbJeux; i++)
                {
                    ITasMin<String> tmpTas = (ITasMin<String>) c.newInstance();
                    tmpTas.consIter(getLignesFichier(chemin + "jeu_" + i + "_nb_cles_" + nb + ".txt"));

                    long start = System.nanoTime();
                    tas = tas.union(tmpTas);
                    temps += System.nanoTime() - start;
                }
                res[k] = temps / nbJeux;
                k++;
            }
        } catch (InstantiationException | IllegalAccessException ex)
        {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
        return res;
    }

    public static List<String> getLignesFichier(String path)
    {
        if (fichiers.containsKey(path))
        {
            return fichiers.get(path);
        }
        ArrayList<String> res = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path)))
        {
            String s;
            while ((s = br.readLine()) != null)
            {
                res.add(s);
            }
        } catch (IOException e)
        {
            System.out.println("IOException pour " + path);
            System.out.println(e.getMessage());
            System.exit(0);
        }
        fichiers.put(path, res);
        return res;
    }

    public static void afficherTableauCSV(long tab[])
    {
        for (long i : tab)
        {
            System.out.print(i + ";");
        }
        System.out.println();
    }
}
