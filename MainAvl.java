import algav.Cle128;
import algav.avl.AVL;
import algav.md5.MD5;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainAvl {

    public static void main(String[] args)
    {
        List<String> shakespeare = getLignesShakespeare(args[0]);

        long start = System.nanoTime();
        List<String> unique = listeUniqueShakespeare(shakespeare);
        long end1 = System.nanoTime() - start;

        List<String> test = new ArrayList<>();

        start = System.nanoTime();
        for (String mot : shakespeare)
        {
            if (!test.contains(mot))
            {
                test.add(mot);
            }
        }
        long end2 = System.nanoTime() - start;

        System.out.println("Temps création liste unique avec AVL : " + end1 + "ns");
        System.out.println("Temps création liste unique sans AVL : " + end2 + "ns");
        chercherCollisions(shakespeare);

    }

    public static List<String> getLignesShakespeare(String path)
    {
        File folder = new File(path);
        String[] tab = folder.list();
        List<String> shakespeare = new ArrayList();
        for (String fichier : tab)
        {
            try (BufferedReader br = new BufferedReader(new FileReader(path + fichier)))
            {
                String ligne;
                while ((ligne = br.readLine()) != null)
                {
                    shakespeare.add(ligne);
                }
            } catch (IOException e)
            {
                e.printStackTrace();
                System.exit(0);
            }
            break;
        }
        return shakespeare;
    }

    public static List<String> listeUniqueShakespeare(List<String> s)
    {
        List<String> mots = new ArrayList();
        AVL<String, Void> avl = new AVL<>();

        int c = 0;
        int cc = 0;
        for (String mot : s)
        {
            if (avl.ajouter(mot, null))
            {
                mots.add(mot);
            }
        }
        return mots;
    }

    public static void chercherCollisions(List<String> mots)
    {
        AVL<Cle128, String> avl = new AVL<>();
        int col = 0;
        for (String mot : mots)
        {
            Cle128 md5 = new Cle128(MD5.get(mot));
            if (avl.rechercher(md5) && !avl.getValeur(md5).equals(mot))
            {
                System.out.println("Collision : " + mot + " et " + avl.getValeur(md5));
                col++;
            }
            else
            {
                avl.ajouter(md5, mot);
            }
        }
        System.out.println("Nombre de collisions : " + col);
    }

}
