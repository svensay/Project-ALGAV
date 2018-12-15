package algav;

import java.util.*;

public class TournoiBinomial<T extends Comparable<? super T>>{

	T v; //cle128
	LinkedList<TournoiBinomial<T>> l;

	public TournoiBinomial(T v){
		this.v = v;
		this.l = new LinkedList<>();
	}
	public TournoiBinomial(){
		this.v = null;
		this.l = new LinkedList<>();
	}
	public boolean EstVide(){
		return l.isEmpty() && v == null;
	}

	public int Degre(){
		return l.size();
	}

	public TournoiBinomial<T> Union2Tid(TournoiBinomial<T> t){
		if(this.v.compareTo(t.v) < 0){
			l.addFirst(t);
			return this;
		}
		t.l.addFirst(this);
		return t;
	}

	public FileBinomiale<T> Decapite(){
		return new FileBinomiale<T>(l);
	}

	public FileBinomiale<T> File(){
		List<TournoiBinomial<T>> res = new ArrayList<>();
		res.add(this);
		return new FileBinomiale<T>(res);
	}
	
	public String toString() {
		LinkedList<TournoiBinomial<T>> file = new LinkedList<>();
		file.add(this);
		TournoiBinomial<T> t;
		String str = "";
		while(!file.isEmpty()) {
			t = file.pop();
			str += t.v + " ";
			if(!t.l.isEmpty()) {
				for (TournoiBinomial<T> tb : t.l) {
					file.add(tb);
				}
			}
		}
		return str;
	}
}