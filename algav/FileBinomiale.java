package algav;

import algav.Cle128;

import java.util.*;
public class FileBinomiale {
	List<TournoiBinomial> l;
	public FileBinomiale(List<TournoiBinomial> l){
		this.l = l;
	}

	public boolean estVide(){
		return l.isEmpty();
	}

	public TournoiBinomial minDeg(){
		return l.get(l.size()-1);
	}

	public FileBinomiale reste(){
		return new FileBinomiale(l.subList(0, l.size()-1));
	}

	public FileBinomiale AjoutMin(TournoiBinomial t){
		l.add(t);
		return this;
	}
}