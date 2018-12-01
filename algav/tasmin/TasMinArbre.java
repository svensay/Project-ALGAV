package algav.tasmin;

import java.util.ArrayList;
import java.util.List;

public class TasMinArbre<T extends Comparable<? super T>> implements ITasMin<T> {

    private class TasMinNoeud {

        private T cle;
        private final TasMinNoeud pere, precedent;
        private TasMinNoeud gauche, droit, suivant;

        public TasMinNoeud(T cle, TasMinNoeud pere, TasMinNoeud precedent)
        {
            this.cle = cle;
            this.pere = pere;
            this.precedent = precedent;
            this.suivant = this.gauche = this.droit = null;
        }
    }

    private TasMinNoeud racine;
    private TasMinNoeud dernier;
    private int taille;

    public TasMinArbre(T cle)
    {
        this.taille = 1;
        this.racine = new TasMinNoeud(cle, null, null);
        this.dernier = this.racine;
    }

    public TasMinArbre()
    {
        this.taille = 0;
        this.racine = this.dernier = null;
    }

    /**
     * Supprime l'élément de clé minimal du TasMin
     *
     * @return tas courant
     */
    @Override
    public TasMinArbre<T> supprMin()
    {
        if (this.taille < 3)
        {
            switch (this.taille)
            {
                case 2:
                    this.racine = new TasMinNoeud(this.racine.gauche.cle, null, null);
                    break;
                case 1:
                    this.racine = null;
                    break;
                default:
                    return this;
            }
            this.dernier = this.racine;
            this.taille--;
        }
        else
        {
            TasMinNoeud pereDernier = this.dernierPere();

            // On supprime le dernier noeud de l'arbre
            TasMinNoeud dernier;
            if ((this.taille & 1) == 0) // à gauche
            {
                dernier = pereDernier.gauche;
                pereDernier.gauche = null;
            }
            else // à droite
            {
                dernier = pereDernier.droit;
                pereDernier.droit = null;
            }
            this.taille--;

            // On met à jour les références this.dernier et (avant-dernier).suivant
            pereDernier = this.dernierPere();
            TasMinNoeud avantDernier = ((this.taille & 1) == 0) ? pereDernier.gauche : pereDernier.droit;
            assert (dernier == avantDernier.suivant); // pour être sûr de pas casser l'arbre ...
            if (avantDernier != null)
            {
                avantDernier.suivant = null;
                this.dernier = dernier;
            }

            // On met à jour la clé de la racine avant de percoler vers le bas
            this.racine.cle = dernier.cle;
            this.percolerBas(this.racine);
        }

        return this;
    }

    /**
     * Percoler vers le haut à partir d'un noeud
     *
     * @param n noeud
     */
    private void percolerHaut(TasMinNoeud noeud) // pour ajouts
    {
        TasMinNoeud pere = noeud.pere;

        // tant que i n'est pas la racine et que son pere est plus petit (ou égal mais ça ne doit pas arriver) que lui
        while (pere != null && noeud.cle.compareTo(pere.cle) == -1)
        {
            T tmp = noeud.cle;
            noeud.cle = pere.cle;
            pere.cle = tmp;

            noeud = pere;
            pere = noeud.pere;
        }
    }

    /**
     * Percoler vers le bas à partir d'un noeud
     *
     * @param noeud noeud
     */
    private void percolerBas(TasMinNoeud noeud)
    {
        // tant qu'il y a un fils gauche
        while (noeud.gauche != null)
        {
            // on prend le plus petit fils
            TasMinNoeud min = noeud.gauche;
            if (noeud.droit != null && noeud.droit.cle.compareTo(noeud.gauche.cle) == -1)
            {
                min = noeud.droit;
            }

            //Si la cle du noeud est plus grande que celle de min
            if (noeud.cle.compareTo(min.cle) == 1)
            {
                // alors on echange
                T tmp = noeud.cle;
                noeud.cle = min.cle;
                min.cle = tmp;

                noeud = min;
            }
            else
            {
                break;
            }
        }
    }

    /**
     * Retourne le père de la dernière feuille de l'arbre
     *
     * @return dernier père
     */
    private TasMinNoeud dernierPere()
    {
        int profondeur = this.log2(this.taille) - 1;
        TasMinNoeud dernierPere = this.racine;
        // tant qu'on est pas à l'avant dernier niveau de l'arbre (niveau avant les feuilles)
        while (profondeur > 0)
        {
            /* en se basant sur la décomposition binaire de la taille (et donc id du noeud),
            on sait où il se trouve dans l'arbre.
            Si le digit est 0 alors on va à gauche, sinon on va à droite
             */
            if ((this.taille & (1 << profondeur)) == 0)
            {
                dernierPere = dernierPere.gauche;
            }
            else
            {
                dernierPere = dernierPere.droit;
            }
            profondeur--;
        }

        return dernierPere;
    }

    /**
     * Ajoute un élément au tas
     *
     * cout <= O(2log(n) )
     *
     * @param cle noeud a ajouté
     * @return le tas courant
     */
    @Override
    public TasMinArbre<T> ajout(T cle)
    {
        this.ajoutSansPercoler(cle);
        this.percolerHaut(this.dernier);

        return this;
    }

    /**
     * Ajoute un élément au tas sans percoler (donc l'ordre des éléments
     * correspond à l'ordre d'insertion)
     *
     * O(log(n))
     *
     * @param cle noeuf à ajouter
     */
    public void ajoutSansPercoler(T cle)
    {
        if (this.dernier == null)
        {
            this.taille++;
            this.racine = new TasMinNoeud(cle, null, null);
            this.dernier = this.racine;
        }
        else
        {
            this.taille++;
            TasMinNoeud pere = this.dernierPere();

            TasMinNoeud fils = new TasMinNoeud(cle, pere, this.dernier);

            // On insert le nouveau fils
            if ((this.taille & 1) == 0) // à gauche
            {
                pere.gauche = fils;
            }
            else // à droite
            {
                pere.droit = fils;
            }

            // mise à jour référence du dernier
            this.dernier.suivant = fils;
            this.dernier = fils;
        }
    }

    /**
     * Construit itérativement un tas à partir de la liste cles.
     *
     * @param cles List d'élément à ajouter successivement
     */
    @Override
    public void consIter(List<T> cles)
    {
        cles.forEach((cle) ->
        {
            this.ajoutSansPercoler(cle);
        });

        int profondeurMax = this.log2(this.taille) - 1;

        // On cherche le noeud le plus à droite de l'arbre au dernier niveau complet
        // complexité O(profondeur du niveau) ?
        TasMinNoeud noeud = this.racine;
        while (profondeurMax > 0)
        {
            noeud = noeud.droit;
            profondeurMax--;
        }

        // on fait une percolation vers le bas à partir du noeud trouvé et on remonte
        // jusqu'à la racine en utilisant le noeud précédent !!
        while (noeud != this.racine) // complexité < O(n) car on fait pas tous les noeuds
        {
            this.percolerBas(noeud);
            noeud = noeud.precedent;
        }
        this.percolerBas(this.racine);

    }

    /**
     * Fait l'union de ce tas avec le tas t
     *
     * @param tas Tas qui doit s'unir avec celui-ci
     * @return nouveau tas
     */
    @Override
    public TasMinArbre union(ITasMin<T> tas)
    {
        TasMinArbre<T> res = new TasMinArbre<>();
        ArrayList<T> list = new ArrayList<>(this.getListe());
        list.addAll(tas.getListe());

        res.consIter(list);
        return res;
    }

    @Override
    public String toString()
    {
        String s = "";
        for (T e : this.getListe())
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
    public List<T> getListe()
    {
        ArrayList<T> res = new ArrayList<>();
        TasMinNoeud noeud = this.racine;
        while (noeud != null)
        {
            res.add(noeud.cle);
            noeud = noeud.suivant;
        }
        return res;
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
        return this.taille == 0;
    }
}
