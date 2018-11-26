package algav.tasmin;

import java.util.List;

public class TasMinArbre<T extends Comparable> implements ITasMin<T> {

    private T index;
    private TasMinArbre gauche, droite;

    public TasMinArbre()
    {
        this.index = null;
        this.gauche = this.droite = null;
    }

    public TasMinArbre(T cle)
    {
        this.index = cle;
        this.gauche = this.droite = null;
    }

    /**
     * Supprime l'élément de clé minimal du TasMin
     */
    public TasMinArbre supprMin()
    {
        return this;
    }

    /**
     * Ajoute un élément au tas
     *
     * @param child noeud a ajouté
     */
    public TasMinArbre ajout(T cle)
    {
        if (this.index == null)
        {
            this.index = cle;
        }
        else
        {
        }

        return this;
    }

    /**
     * Construit itérativement un tas à partir de la list l d'éléments.
     *
     * @param cles List d'élément à ajouter successivement
     */
    public void consIter(List cles)
    {

    }

    /**
     * Fait l'union de ce tas avec le tas t
     *
     * @param tas Tas qui doit s'unir avec celui-ci
     */
    public TasMinArbre union(ITasMin tas)
    {
        return this;
    }

    @Override
    public String toString()
    {
        return "( "
                + this.index
                + ": "
                + ((this.gauche == null) ? "()" : this.gauche)
                + ";"
                + ((this.droite == null) ? "()" : this.droite)
                + " )";
    }

    @Override
    public List<T> getListe()
    {
        return null;
    }

}
