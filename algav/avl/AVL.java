package algav.avl;

public class AVL<T extends Comparable<T>> {

    private class AVLNoeud {

        private AVLNoeud filsGauche, filsDroit;
        private final T cle;

        public AVLNoeud(T cle)
        {
            this.cle = cle;
            this.filsDroit = this.filsGauche = null;
        }

        public boolean ajouter(T cle)
        {
            int comp = this.cle.compareTo(cle);
            if (comp > 0)
            {
                if (this.filsGauche == null)
                {
                    this.filsGauche = new AVLNoeud(cle);
                    return true;
                }
                else
                {
                    this.filsGauche.ajouter(cle);
                }
            }
            else if (comp < 0)
            {
                if (this.filsDroit == null)
                {
                    this.filsDroit = new AVLNoeud(cle);
                    return true;
                }
                else
                {
                    this.filsDroit.ajouter(cle);
                }
            }
            return false;
        }

        public boolean rechercher(T cle)
        {
            int comp = this.cle.compareTo(cle);
            if (comp == 0)
            {
                return true;
            }
            if (comp > 0 && this.filsGauche != null)
            {
                return this.filsGauche.rechercher(cle);
            }
            else if (comp < 0 && this.filsDroit != null)
            {
                return this.filsDroit.rechercher(cle);
            }
            else
            {
                return false;
            }
        }

        public String parcoursInfixe()
        {
            String res = this.cle.toString();

            if (this.filsGauche != null)
            {
                res = this.filsGauche.parcoursInfixe() + " " + res;
            }
            if (this.filsDroit != null)
            {
                res = res + " " + this.filsDroit.parcoursInfixe();
            }
            return res;
        }

        private void rg()
        {

        }

        private void rd()
        {

        }

        private void rgd()
        {

        }

        private void rdg()
        {

        }
    }

    private int taille;
    private AVLNoeud racine;

    public AVL(T cle)
    {
        this.taille = 1;
        this.racine = new AVLNoeud(cle);
    }

    public void ajouter(T cle)
    {
        if (this.racine.ajouter(cle))
        {
            this.taille++;
        }
    }

    public boolean rechercher(T cle)
    {
        return this.racine.rechercher(cle);
    }

    public int getTaille()
    {
        return this.taille;
    }

    @Override
    public String toString()
    {
        return this.racine.parcoursInfixe();
    }
}
