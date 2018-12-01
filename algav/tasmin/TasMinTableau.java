package algav.tasmin;

import java.util.ArrayList;
import java.util.List;

public class TasMinTableau<T extends Comparable<? super T>> implements ITasMin<T> {

    private ArrayList<T> array;

    public TasMinTableau()
    {
        this.array = new ArrayList<>(16);
    }

    /**
     * Supprime l'élément de clé minimal du TasMin
     *
     * @return tas courant
     */
    @Override
    public TasMinTableau<T> supprMin()
    {
        T last = this.array.remove(this.array.size() - 1);
        this.array.set(0, last);

        this.percolerBas(0);

        return this;
    }

    /**
     * Percoler vers le haut à partir du sommet i
     *
     * @param i sommet
     */
    private void percolerHaut(int id) // pour ajouts
    {
        T cle = this.array.get(id);
        // E((i - 1)/2) -> pere
        int idPere = (id - 1) / 2;
        T pere = this.array.get(idPere);

        // tant que i n'est pas la racine et que son pere est plus petit (ou égal mais ça ne doit pas arriver) que lui
        while (id != 0 && cle.compareTo(pere) == -1)
        {
            this.array.set(id, pere);
            this.array.set(idPere, cle);
            id = idPere;
            idPere = (id - 1) / 2;
            pere = this.array.get(idPere);
        }
    }

    /**
     * Percoler vers le bas à partir du sommet i
     *
     * @param i sommet
     */
    private void percolerBas(int i)
    {
        T cle = this.array.get(i);
        // (2 * i) + 1 -> fils gauche
        // (2 * i) + 2 -> fils droit
        int idGauche = (2 * i) + 1;
        boolean possedeGauche = idGauche < this.array.size();
        int idDroit = (2 * i) + 2;
        boolean possedeDroit = idDroit < this.array.size();

        // tant qu'il y a un fils gauche et qu'on dépasse pas la hauteur du tbl
        while (possedeGauche && i < this.array.size())
        {
            // on prend le plus petit fils
            int idMin = idGauche;
            if (possedeDroit)
            {
                idMin = (this.array.get(idGauche).compareTo(this.array.get(idDroit)) == -1) ? idGauche : idDroit;
            }
            T min = this.array.get(idMin);

            //Si la cle i est plus petite que le min
            if (cle.compareTo(min) == 1)
            {
                // alors on echange
                T tmp = cle;
                this.array.set(i, min);
                this.array.set(idMin, tmp);

                i = idMin;
                cle = this.array.get(i);
                idGauche = (2 * i) + 1;
                idDroit = (2 * i) + 2;
                possedeDroit = idDroit < this.array.size();
                possedeGauche = idGauche < this.array.size();
            }
            else
            {
                break;
            }

        }
    }

    /**
     * Ajoute un élément au tas
     *
     * @param cle noeud a ajouté
     * @return le tas courant
     */
    @Override
    public TasMinTableau<T> ajout(T cle)
    {
        if (this.array.contains(cle))
        {
            return this;
        }
        this.array.add(cle);
        if (this.array.size() > 1)
        {
            this.percolerHaut(this.array.size() - 1);
        }
        return this;
    }

    /**
     * Construit itérativement un tas à partir de la liste cles.
     *
     * @param cles List d'élément à ajouter successivement
     */
    @Override
    public void consIter(List<T> cles)
    {
        this.array = new ArrayList<>(cles);

        int profondeurMax = log2(cles.size()) - 1;
        // Pour chaque niveau de l'arbre (à partir de l'avant dernier)
        // complexite sans percolation : O(profondeurMax * 2^profondeurMax) < O(n)
        while (profondeurMax >= 0)
        {
            // on fait la percolation vers le bas pour chaque sommet de la droite vers la gauche
            for (int i = (2 << profondeurMax) - 1; i >= ((2 << profondeurMax - 1)); i--)
            {
                this.percolerBas(i);
            }
            profondeurMax--;
        }
    }

    /**
     * Fait l'union de ce tas avec le tas t
     *
     * @param tas Tas qui doit s'unir avec celui-ci
     * @return nouveau tas
     */
    @Override
    public TasMinTableau<T> union(ITasMin<T> tas)
    {
        TasMinTableau<T> res = new TasMinTableau<>();
        ArrayList<T> list = new ArrayList<>(this.getListe());
        list.addAll(tas.getListe());

        res.consIter(list);
        return res;
    }

    @Override
    public String toString()
    {
        String s = "";
        for (T e : this.array)
        {
            s += e + " ";
        }
        return s;
    }

    /**
     * Obtenir les clés de l'arbre
     *
     * @return liste des clés
     */
    @Override
    public final List<T> getListe()
    {
        return new ArrayList<>(this.array);
    }

    /**
     * Fonction log2(x)
     *
     * @param nombre
     * @return log en base 2 du nombre
     */
    private int log2(int nombre)
    {
        //return (int) (Math.log(nombre) / Math.log(2));
        // solution ci-dessous 10 fois plus rapide :
        // https://stackoverflow.com/questions/3305059/
        int log = 0;
        if ((nombre & 0xffff0000) != 0)
        {
            nombre >>>= 16;
            log = 16;
        }
        if (nombre >= 256)
        {
            nombre >>>= 8;
            log += 8;
        }
        if (nombre >= 16)
        {
            nombre >>>= 4;
            log += 4;
        }
        if (nombre >= 4)
        {
            nombre >>>= 2;
            log += 2;
        }
        return log + (nombre >>> 1);
    }

    /**
     * Indique sur le tas est vide
     *
     * @return tas vide ?
     */
    @Override
    public boolean estVide()
    {
        return this.array.isEmpty();
    }
}
