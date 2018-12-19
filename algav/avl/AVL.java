package algav.avl;

public class AVL<K extends Comparable<K>, V> {

    private class AVLNoeud {

        private AVLNoeud filsGauche, filsDroit;
        private AVLNoeud parent;
        private int profondeur;
        private K cle;
        private V valeur;

        public AVLNoeud(K cle, V valeur, AVLNoeud parent, AVLNoeud gauche, AVLNoeud droit)
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
            K copieCle = this.cle;
            V copieValeur = this.valeur;

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
            K copieCle = this.cle;
            V copieValeur = this.valeur;
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

        public AVLNoeud ajouter(K cle, V valeur)
        {
            int comp = this.cle.compareTo(cle);
            if (comp > 0)
            {
                if (this.filsGauche == null)
                {
                    AVLNoeud gauche = new AVLNoeud(cle, valeur, this, null, null);
                    this.filsGauche = gauche;
                    this.equilibrer();
                    this.calculerProfondeur();
                    return gauche;
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
                    AVLNoeud droit = new AVLNoeud(cle, valeur, this, null, null);
                    this.filsDroit = droit;
                    this.calculerProfondeur();
                    this.equilibrer();
                    return droit;
                }
                else
                {
                    return this.filsDroit.ajouter(cle, valeur);
                }
            }
            return null;
        }

        private AVLNoeud getNoeud(K cle) // Pour recherche et obtenir une valeur
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

    public AVL(K cle, V valeur)
    {
        this.taille = 1;
        this.racine = new AVLNoeud(cle, valeur, null, null, null);
    }

    /**
     * Ajouter un couple Clé Valeur à l'AVL
     *
     * @param cle clé unique à ajouter
     * @param valeur valeur assoicée
     * @return true si le couple a été ajouté, false si clé déjà présente
     */
    public boolean ajouter(K cle, V valeur)
    {
        if (this.taille == 0)
        {
            this.racine = new AVLNoeud(cle, valeur, null, null, null);
            this.taille++;
        }
        else
        {
            if (this.racine.ajouter(cle, valeur) == null)
            {
                return false;
            }
            this.taille++;
        }
        return true;
    }

    /**
     * Rechercher une clé dans l'AVL
     *
     * @param cle clé à rechercher
     * @return true si la clé a été trouvée, sinon false
     */
    public boolean rechercher(K cle)
    {
        return this.racine != null && this.racine.getNoeud(cle) != null;
    }

    /**
     * Obtenir la valeur liée à une clée
     *
     * @param cle
     * @return La valeur correspondante à la clé ou null si la clé n'est pas
     * dans l'AVL
     */
    public V getValeur(K cle)
    {
        AVLNoeud n = this.racine.getNoeud(cle);
        if (n == null)
        {
            return null;
        }
        return n.valeur;
    }

    /**
     * Taille de l'arbre
     *
     * @return taille
     */
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
