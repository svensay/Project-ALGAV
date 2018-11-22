import java.util.*;
public class TournoiBinomial{
	byte v;
	List<TournoiBinomial> l;
	public TournoiBinomial(byte v, List<TournoiBinomial> l){
		this.v = v;
		this.l =l;
	}

	boolean EstVide(){
		return l.isEmpty()||v==null;
	}
}