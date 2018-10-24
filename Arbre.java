import  java.util.*;
public class Arbre{
	byte val;
	Arbre g;
	Arbre d;
	public Arbre(byte val,Arbre g,Arbre d){
		this.val = val;
		this.g = g;
		this.d = d;
	}	
	public void explorer(){
		LinkedList<Arbre> f = new LinkedList<Arbre>();
		f.add(this);
		while(!f.isEmpty()){
			Arbre tmp = f.poll();
			System.out.print(tmp.val + " ");
			if(tmp.g != null) f.add(tmp.g);
			if(tmp.d != null) f.add(tmp.d);
		}
	}
}