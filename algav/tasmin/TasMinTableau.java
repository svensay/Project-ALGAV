package algav.tasmin;

import java.util.ArrayList;
import java.util.List;

public class TasMinTableau<T extends Comparable> implements ITasMin<T> {

    private ArrayList<T> array;

    public TasMinTableau()
    {
        this.array = new ArrayList<>(8);
    }

    /**
     * Supprime l'élément de clé minimal du TasMin
     *
     * @return tas courant
     */
    @Override
    public TasMinTableau supprMin()
    {
        T last = this.array.remove(this.array.size() - 1);
        this.array.set(0, last);

        //TODO:trier la tête du tas
        /*
        int i = 0;
        while(((2*i)+1) < this.array.size() && ((2*i)+2) < this.array.size() && i < this.array.size())
        {
            if(last.compareTo(this.array.get((2*i)+1)) == -1)//last inferieur au fils gauche
            {
                this.array.set(i,this.array.get((2*i)+1));
                i = (2*i)+1;
            }
            else if(this.array.get((2*i)+2).compareTo(last) == -1)//fils droit inferieur a last
            {
                this.array.set(i,this.array.get((2*i)+2));
                i = (2*i)+2;
            }else{
                break;
            }
        }
        this.array.set(i,last);
        */


        return this;
    }

    /**
     * Ajoute un élément au tas
     *
     * @param cle noeud a ajouté
     * @return le tas courant
     */
    @Override
    public TasMinTableau ajout(T cle)
    {
        if (this.array.contains(cle))
        {
            return this;
        }

        this.array.add(cle);
        if (this.array.size() > 1)
        {
            int taille = this.array.size() - 1;

            while (taille > 0 && cle.compareTo(this.array.get((taille - 1) / 2)) == -1)
            {
                this.array.set(taille, this.array.get((taille - 1) / 2));
                taille = (taille - 1) / 2;
            }
            this.array.set(taille, cle);
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
        this.array = new ArrayList<>(8);
        cles.forEach((cle) ->
        {
            this.ajout(cle);
        });

    }

    /**
     * Fait l'union de ce tas avec le tas t
     *
     * @param tas Tas qui doit s'unir avec celui-ci
     * @return
     */
    @Override
    public TasMinTableau union(ITasMin tas)
    {
        tas.getListe().forEach((cle) ->
        {
            tas.ajout(cle);
        });
        return this;
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

    @Override
    public final List<T> getListe()
    {
        return new ArrayList<>(this.array);
    }
}
