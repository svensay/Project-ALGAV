package algav;

import java.util.*;

public class TournoiBinomial<T extends Comparable<? super T>>{

	T v; //cle128
	LinkedList<TournoiBinomial<T>> fils;

	public TournoiBinomial(T v){
		this.v = v;
		this.fils = new LinkedList<>();
	}
	public TournoiBinomial(){
		this.v = null;
		this.fils = new LinkedList<>();
	}
	public boolean EstVide(){
		return fils.isEmpty() && v == null;
	}

	public int Degre(){
		return fils.size();
	}

	public TournoiBinomial<T> Union2Tid(TournoiBinomial<T> t){
		if(this.v.compareTo(t.v) < 0){
			fils.addFirst(t);
			return this;
		}
		t.fils.addFirst(this);
		return t;
	}

	public FileBinomiale<T> Decapite(){
		return new FileBinomiale<T>(fils);
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
			if(!t.fils.isEmpty()) {
				for (TournoiBinomial<T> tb : t.fils) {
					file.add(tb);
				}
			}
		}
		return str;
	}
}