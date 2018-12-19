package algav.avl;

public class AVL<T extends Comparable<T>, U> {

    private class AVLNoeud {

        private AVLNoeud filsGauche, filsDroit;
        private AVLNoeud parent;
        private int profondeur;
        private T cle;
        private U valeur;

        public AVLNoeud(T cle, U valeur, AVLNoeud parent, AVLNoeud gauche, AVLNoeud droit)
        {
            this.cle = cle;
            this.valeur = valeur;
            this.parent = parent;
            this.filsDroit = droit;
            this.filsGauche = gauche;

            int max = -1;
            if (gauche != null)
            {
                max = gauche.profondeur;
            }
            if (droit != null && droit.profondeur > max)
            {
                max = droit.profondeur;
            }

            this.profondeur = 1 + max;
        }

        private void calculerProfondeur()
        {
            AVLNoeud noeud = this;
            while (noeud != null)
            {
                int profondeurG = 0;
                int profondeurD = 0;
                if (noeud.filsGauche != null)
                {
                    profondeurG = noeud.filsGauche.profondeur;
                }
                if (noeud.filsDroit != null)
                {
                    profondeurD = noeud.filsDroit.profondeur;
                }

                noeud.profondeur = 1 + ((profondeurG > profondeurD) ? profondeurG : profondeurD);
                noeud = noeud.parent;
            }
        }

        private void equilibrer()
        {
            AVLNoeud n = this;
            int facteur;
            while (n != null)
            {
                facteur = ((n.filsGauche != null) ? n.filsGauche.profondeur : -1) - ((n.filsDroit != null) ? n.filsDroit.profondeur : -1);
                if (facteur > 1)
                {
                    if (((n.filsGauche.filsGauche == null) ? -1 : n.filsGauche.filsGauche.profondeur) < ((n.filsGauche.filsDroit == null) ? -1 : n.filsGauche.filsDroit.profondeur))
                    {
                        n.filsGauche.rg();
                    }
                    n.rd();
                    break;
                }
                else if (facteur < -1)
                {
                    if (((n.filsDroit.filsGauche == null) ? -1 : n.filsDroit.filsGauche.profondeur) > ((n.filsDroit.filsDroit == null) ? -1 : n.filsDroit.filsDroit.profondeur))
                    {
                        n.filsDroit.rd();
                    }
                    n.rg();
                    break;
                }
                else
                {
                    n = n.parent;
                }
            }
        }

        private void rg()
        {
            T copieCle = this.cle;
            U copieValeur = this.valeur;

            AVLNoeud A, C, E;
            A = this.filsGauche;

            C = this.filsDroit.filsGauche;
            E = this.filsDroit.filsDroit;

            this.cle = this.filsDroit.cle;
            this.valeur = this.filsDroit.valeur;
            this.filsGauche = new AVLNoeud(copieCle, copieValeur, this, A, C);
            this.filsDroit = E;
            if (E != null)
            {
                E.parent = this;
            }
            if (A != null)
            {
                A.parent = this.filsGauche;
            }
            if (C != null)
            {
                C.parent = this.filsGauche;
            }

            if (this.filsGauche != null)
            {
                this.filsGauche.calculerProfondeur();
            }
            if (this.filsDroit != null)
            {
                this.filsDroit.calculerProfondeur();
            }
            this.calculerProfondeur();

        }

        private void rd()
        {
            T copieCle = this.cle;
            U copieValeur = this.valeur;
            AVLNoeud A, C, E;
            E = this.filsDroit;
            A = this.filsGauche.filsGauche;
            C = this.filsGauche.filsDroit;

            this.cle = this.filsGauche.cle;
            this.valeur = this.filsGauche.valeur;
            this.filsDroit = new AVLNoeud(copieCle, copieValeur, this, C, E);
            this.filsGauche = A;
            if (E != null)
            {
                E.parent = this.filsDroit;
            }
            if (A != null)
            {
                A.parent = this;
            }
            if (C != null)
            {
                C.parent = this.filsDroit;
            }

            if (this.filsGauche != null)
            {
                this.filsGauche.calculerProfondeur();
            }
            if (this.filsDroit != null)
            {
                this.filsDroit.calculerProfondeur();
            }
            this.calculerProfondeur();

        }

        public AVLNoeud ajouter(T cle, U valeur)
        {
            int comp = this.cle.compareTo(cle);
            if (comp > 0)
            {
                if (this.filsGauche == null)
                {
                    this.filsGauche = new AVLNoeud(cle, valeur, this, null, null);
                    this.equilibrer();
                    this.calculerProfondeur();
                    return this.filsGauche;
                }
                else
                {
                    return this.filsGauche.ajouter(cle, valeur);
                }
            }
            else if (comp < 0)
            {
                if (this.filsDroit == null)
                {
                    this.filsDroit = new AVLNoeud(cle, valeur, this, null, null);
                    this.calculerProfondeur();
                    this.equilibrer();
                    return this.filsDroit;
                }
                else
                {
                    return this.filsDroit.ajouter(cle, valeur);
                }
            }
            return null;
        }

        private AVLNoeud getNoeud(T cle) // Pour recherche
        {
            int comp = this.cle.compareTo(cle);
            if (comp == 0)
            {
                return this;
            }
            if (comp > 0 && this.filsGauche != null)
            {
                return this.filsGauche.getNoeud(cle);
            }
            else if (comp < 0 && this.filsDroit != null)
            {
                return this.filsDroit.getNoeud(cle);
            }
            return null;

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
    }

    private int taille;
    private AVLNoeud racine;

    public AVL()
    {
        this.taille = 0;
    }

    public AVL(T cle, U valeur)
    {
        this.taille = 1;
        this.racine = new AVLNoeud(cle, valeur, null, null, null);
    }

    public void ajouter(T cle, U valeur)
    {
        if (this.taille == 0)
        {
            this.racine = new AVLNoeud(cle, valeur, null, null, null);
        }
        else
        {
            this.racine.ajouter(cle, valeur);
        }
        this.taille++;
    }

    public boolean rechercher(T cle)
    {
        return this.racine != null && this.racine.getNoeud(cle) != null;
    }

    public U getValeur(T cle)
    {
        AVLNoeud n = this.racine.getNoeud(cle);
        if (n == null)
        {
            return null;
        }
        return n.valeur;
    }

    public int getTaille()
    {
        return this.taille;
    }

    public String getArbreString()
    {
        return this.racine.toString();
    }

    @Override
    public String toString()
    {
        return this.racine.parcoursInfixe();
    }
}
