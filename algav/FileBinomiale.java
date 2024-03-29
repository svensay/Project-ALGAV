package algav;

import java.util.*;

public class FileBinomiale<T extends Comparable<? super T>> {
	List<TournoiBinomial<T>> l;

	public FileBinomiale(List<TournoiBinomial<T>> l) {
		this.l = l;
	}

	public FileBinomiale() {
		this.l = new ArrayList<TournoiBinomial<T>>();
	}

	public boolean estVide() {
		return l.isEmpty();
	}

	public TournoiBinomial<T> minDeg() {
		return l.get(l.size() - 1);
	}

	public FileBinomiale<T> reste() {
		return new FileBinomiale<T>(l.subList(0, l.size() - 1));
	}

	public FileBinomiale<T> AjoutMin(TournoiBinomial<T> t) {
		l.add(t);
		return this;
	}

	/**
	 * 
	 * @param f File a unir avec this
	 * @param t retenue lors de collision entre 2 File de meme degre
	 * @return l'union de this et f
	 */
	public FileBinomiale<T> UFret(FileBinomiale<T> f, TournoiBinomial<T> t) {
		if (t.EstVide()) {
			if (this.estVide()) {
				return f;
			}
			if (f.estVide()) {
				return this;
			}
			TournoiBinomial<T> t1 = this.minDeg();
			TournoiBinomial<T> t2 = f.minDeg();

			if (t1.Degre() < t2.Degre()) {
				return this.reste().union(f).AjoutMin(t1);
			}
			if (t2.Degre() < t1.Degre()) {
				return f.reste().union(this).AjoutMin(t2);
			}
			if (t1.Degre() == t2.Degre()) {
				return this.reste().UFret(f.reste(), t1.Union2Tid(t2));
			}
		} else {
			if (this.estVide()) {
				return t.File().union(f);
			}
			if (f.estVide()) {
				return t.File().union(this);
			}
			TournoiBinomial<T> t1 = this.minDeg();
			TournoiBinomial<T> t2 = f.minDeg();

			if (t.Degre() < t1.Degre() && t.Degre() < t2.Degre()) {
				return this.union(f).AjoutMin(t);
			}
			if (t.Degre() == t1.Degre() && t.Degre() == t2.Degre()) {
				return this.reste().UFret(f.reste(), t1.Union2Tid(t2)).AjoutMin(t);
			}
			if (t.Degre() == t1.Degre() && t.Degre() < t2.Degre()) {
				return this.reste().UFret(f, t1.Union2Tid(t));
			}
			if (t.Degre() == t2.Degre() && t.Degre() < t1.Degre()) {
				return f.reste().UFret(this, t2.Union2Tid(t));
			}
		}
		return new FileBinomiale<T>().ajout(t);
	}

	public FileBinomiale<T> union(FileBinomiale<T> l1) {
		return UFret(l1, new TournoiBinomial<T>());
	}

	/**
	 * 
	 * @param t element a ajouter
	 * @return la file this avec l'element t
	 */
	public FileBinomiale<T> ajout(TournoiBinomial<T> t) {
		FileBinomiale<T> tmp = new FileBinomiale<T>();
		tmp.AjoutMin(t);
		l = this.union(tmp).l;
		return this;
	}

	/**
	 * Cherche l'element minimum de la file et la supprime
	 * 
	 * @return la file avec l'element min supprimé
	 */
	public FileBinomiale<T> supprMin() {
		int min = 0;
		for (int i = 1; i < l.size(); i++) {
			if (l.get(i).v.compareTo(l.get(min).v) < 0) {
				min = i;
			}
		}
		
		FileBinomiale<T> l1 = l.get(min).Decapite();
		l.remove(min);
		l = this.union(l1).l;
		return this;
	}

	/**
	 * Divise la liste de cle, puis unit deux a deux
	 * @param cles liste de cles a ajouter iterativement
	 */
	public void consIter(List<T> cles) {
		List<TournoiBinomial<T>> l_tmp = new ArrayList<>();
		for (T c : cles) {
			l_tmp.add(new TournoiBinomial<T>(c));
		}

		List<TournoiBinomial<T>> res = new ArrayList<>();
		
		if (l_tmp.size() % 2 == 1) {
			res.add(l_tmp.get(0));
			l_tmp = l_tmp.subList(1, l_tmp.size());
		}

		if ((l_tmp.size() / 2) % 2 == 0) {
			res.add(arrangement(l_tmp));
		} else {
			res.add(arrangement(l_tmp.subList(0, 2)));
			res.add(arrangement(l_tmp.subList(2, l_tmp.size())));
		}
		l = res;
	}

	public TournoiBinomial<T> arrangement(List<TournoiBinomial<T>> l_tmp) {
		if (l_tmp.size() <= 1) {
			return l_tmp.get(0);
		}
		return arrangement_fusion(arrangement(l_tmp.subList(0, l_tmp.size() / 2)),
				arrangement(l_tmp.subList(l_tmp.size() / 2, l_tmp.size())));
	}

	public TournoiBinomial<T> arrangement_fusion(TournoiBinomial<T> t1, TournoiBinomial<T> t2) {
		return t1.Union2Tid(t2);
	}

	public String toString() {
		String str = "";
		if (!l.isEmpty()) {
			for (TournoiBinomial<T> t : l) {
				str += t.toString() + "\n";
			}
		}
		return str;
	}
}