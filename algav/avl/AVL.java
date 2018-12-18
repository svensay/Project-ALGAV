package algav.avl;

public class AVL<T extends Comparable<T>> {

    private class AVLNoeud {

        private AVLNoeud filsGauche, filsDroit;
        private AVLNoeud parent;
        private int profondeur;
        private T cle;

        public AVLNoeud(T cle, AVLNoeud parent, AVLNoeud gauche, AVLNoeud droit)
        {
            this.cle = cle;
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
                if (facteur == 2)
                {
                    if (((n.filsGauche.filsGauche == null) ? -1 : n.filsGauche.filsGauche.profondeur) < ((n.filsGauche.filsDroit == null) ? -1 : n.filsGauche.filsDroit.profondeur))
                    {
                        n.filsGauche.rg();
                    }
                    n.rd();
                    break;
                }
                else if (facteur == -2)
                {
                    if (((n.filsDroit.filsGauche == null) ? -1 : n.filsDroit.filsGauche.profondeur) > ((n.filsDroit.filsDroit == null) ? -1 : n.filsDroit.filsDroit.profondeur))
                    {
                        n.filsDroit.rd();
                    }
                    n.rg();
                    break;
                }
                n = n.parent;
            }
        }

        private void rg()
        {
            T copieCle = this.cle;

            AVLNoeud A, C, E;
            A = this.filsGauche;

            C = this.filsDroit.filsGauche;
            E = this.filsDroit.filsDroit;

            this.cle = this.filsDroit.cle;
            this.filsGauche = new AVLNoeud(copieCle, this, A, C);
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
            AVLNoeud A, C, E;
            E = this.filsDroit;
            A = this.filsGauche.filsGauche;
            C = this.filsGauche.filsDroit;

            this.cle = this.filsGauche.cle;
            this.filsDroit = new AVLNoeud(copieCle, this, C, E);
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

        public AVLNoeud ajouter(T cle)
        {
            int comp = this.cle.compareTo(cle);
            if (comp > 0)
            {
                if (this.filsGauche == null)
                {
                    this.filsGauche = new AVLNoeud(cle, this, null, null);
                    this.calculerProfondeur();
                    this.equilibrer();
                    return this.filsGauche;
                }
                else
                {
                    return this.filsGauche.ajouter(cle);
                }
            }
            else if (comp < 0)
            {
                if (this.filsDroit == null)
                {
                    this.filsDroit = new AVLNoeud(cle, this, null, null);
                    this.calculerProfondeur();
                    this.equilibrer();
                    return this.filsDroit;
                }
                else
                {
                    return this.filsDroit.ajouter(cle);
                }
            }
            return null;
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

        public String toString()
        {
            String s = "{" + this.cle + ":g=";
            if (this.filsGauche != null)
            {
                s += this.filsGauche;
            }
            else
            {
                s += "NULL";
            }
            s += ";d=";
            if (this.filsDroit != null)
            {
                s += this.filsDroit;
            }
            else
            {
                s += "NULL";
            }
            return s += "}";
        }
    }

    private int taille;
    private AVLNoeud racine;

    public AVL()
    {
        this.taille = 0;
    }

    public AVL(T cle)
    {
        this.taille = 1;
        this.racine = new AVLNoeud(cle, null, null, null);
    }

    public void ajouter(T cle)
    {
        if (this.taille == 0)
        {
            this.racine = new AVLNoeud(cle, null, null, null);
        }
        else
        {
            this.racine.ajouter(cle);
        }
        this.taille++;
    }

    public boolean rechercher(T cle)
    {
        return this.racine.rechercher(cle);
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
