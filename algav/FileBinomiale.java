package algav;

import java.util.*;
public class FileBinomiale<T extends Comparable<? super T>> {
	List<TournoiBinomial<T>> l;

	public FileBinomiale(List<TournoiBinomial<T>> l){
		this.l = l;
	}
	public FileBinomiale(){
		this.l = new ArrayList<TournoiBinomial<T>>();
	}

	public boolean estVide(){
		return l.isEmpty();
	}

	public TournoiBinomial<T> minDeg(){
		return l.get(l.size()-1);
	}

	public FileBinomiale<T> reste(){
		return new FileBinomiale<T>(l.subList(0, l.size()-1));
	}

	public FileBinomiale<T> AjoutMin(TournoiBinomial<T> t){
		l.add(t);
		return this;
	}
	public FileBinomiale<T> UFret(FileBinomiale<T> f,TournoiBinomial<T> t){
		if(t.EstVide()){
			if(this.estVide()){
				return f;
			}
			if(f.estVide()){
				return this;
			}
			TournoiBinomial<T> t1 = this.minDeg();
			TournoiBinomial<T> t2 = f.minDeg();
			if(t1.Degre() < t2.Degre()){
				return this.reste().Union(f).AjoutMin(t1);
			}
			if(t2.Degre() < t1.Degre()){
				return f.reste().Union(this).AjoutMin(t2);
			}
			if(t1.Degre() == t2.Degre()){
				return this.reste().UFret(f.reste(),t1.Union2Tid(t2));
			}
		}else{
			if(this.estVide()){
				return t.File().Union(f);
			}
			if(f.estVide()){
				return t.File().Union(this);
			}
			TournoiBinomial<T> t1 = this.minDeg();
			TournoiBinomial<T> t2 = f.minDeg();
			if(t.Degre() < t1.Degre() && t.Degre() < t2.Degre()){
				return this.Union(f).AjoutMin(t);
			}
			if(t.Degre() == t1.Degre() && t.Degre() == t2.Degre()){
				return this.reste().UFret(f.reste(),t1.Union2Tid(t2)).AjoutMin(t);
			}
			if(t.Degre() == t1.Degre() && t.Degre() < t2.Degre()){
				return this.reste().UFret(f,t1.Union2Tid(t));
			}
			if(t.Degre() == t2.Degre() && t.Degre() < t1.Degre()){
				return f.reste().UFret(this,t2.Union2Tid(t));
			}
		}
		return null;
	}

	public FileBinomiale<T> Union(FileBinomiale<T> l){
		return UFret(l,new TournoiBinomial<T>());
	}

	public void Ajout(TournoiBinomial<T> t){
		FileBinomiale<T> tmp = new FileBinomiale<T>();
		tmp.AjoutMin(t);
		l = this.Union(tmp).l;
	}

	public void SupprMin(){
		int min = 0;
		for (int i = 1; i < l.size();i++) {
			if(l.get(i).v.compareTo(l.get(min).v) < 0){
				min = i;
			}
		}
		FileBinomiale<T> l1 = l.get(min).Decapite();
		l.remove(min);
		l = this.Union(l1).l;
	}
	
	public void ConstIter(List<T> cles) {
		for (T c : cles) {
			TournoiBinomial<T> tmp = new TournoiBinomial<T>(c);
			this.Ajout(tmp);
		}
	}
	public String toString() {
		String str = "";
		for (TournoiBinomial<T> t : l) {
			str += t.toString() + "\n";
		}
		return str;
	}
}