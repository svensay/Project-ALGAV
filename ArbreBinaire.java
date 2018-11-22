import  java.util.*;
public class ArbreBinaire{
	byte val;
	ArbreBinaire g;
	ArbreBinaire d;
	public ArbreBinaire(byte val,ArbreBinaire g,ArbreBinaire d){
		this.val = val;
		this.g = g;
		this.d = d;
	}	
	public void explorer(){
		LinkedList<ArbreBinaire> f = new LinkedList<ArbreBinaire>();
		f.add(this);
		while(!f.isEmpty()){
			ArbreBinaire tmp = f.poll();
			System.out.print(tmp.val + " ");
			if(tmp.g != null) f.add(tmp.g);
			if(tmp.d != null) f.add(tmp.d);
		}
	}
}