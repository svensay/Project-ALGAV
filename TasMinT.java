import java.util.*;
public class TasMinT{
	List<Byte> tab;
	public TasMinT(List<Byte> tab){
		this.tab = tab;
	}

	/**
	* Echange les valeurs de x et y
	*/
	public void swap(byte x, byte y){
		byte tmp = x;
		x = y;
		y = tmp;
	}
	
	/**
	*	Supprime l'élément de clé minimal du TasMin
	*/
	public void SupprMin(){}

	/**
	*	Ajoute un élément au tas
	*/
	public void Ajout(byte b){}

	/**
	*	Construit itérativement un tas à partir de la list l d'éléments.
	*	@param l 
	* 			List d'élément à ajouter successivement
	*/
	public void ConsIter(List l){}

	/**
	*	Fait l'union de ce tas avec le tas t
	*	@param t
	*			Tas qui doit s'unir avec celui-ci
	*/
	public void Union(TasMinA t){}
}