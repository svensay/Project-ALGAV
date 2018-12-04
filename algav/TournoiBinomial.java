package algav;

import algav.Cle128;

import java.util.*;
public class TournoiBinomial{
	Cle128 v; //cle128
	List<TournoiBinomial> l;
	public TournoiBinomial(Cle128 v, List<TournoiBinomial> l){
		this.v = v;
		this.l =l;
	}

	public boolean EstVide(){
		return l.isEmpty();
	}

	public int Degre(){
		return l.size();
	}

	public TournoiBinomial Union2Tid(TournoiBinomial t){
		if(this.v.compareTo(t.v) == -1){
			l.add(t);
			return this;
		}
		t.l.add(this);
		return t;
	}

	public FileBinomiale Decapite(){
		return new FileBinomiale(l);
	}

	public FileBinomiale File(){
		List<TournoiBinomial> res = new ArrayList<>();
		res.add(this);
		return new FileBinomiale(res);
	}
}