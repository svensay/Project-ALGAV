import java.util.*;
public class TournoiBinomial{
	byte v; //cle128
	List<TournoiBinomial> l;
	public TournoiBinomial(byte v, List<TournoiBinomial> l){
		this.v = v;
		this.l =l;
	}

	public boolean EstVide(){
		return l.isEmpty()||v==null;
	}

	public int Degre(){
		return 0;
	}

	public TournoiBinomial Union2Tid(TournoiBinomial t){
		return null;
	}

	public FileBinomiale Decapite(){
		return null;
	}

	public FileBinomiale File(){
		return null;
	}
}