import java.lang.*;

public class main{
	public static boolean inf(byte cle1,byte cle2){
		return cle1 < cle2;
	}
	public static boolean eg(byte cle1,byte cle2){
		return cle1 == cle2;
	}
	public static void main(String[] args) {
		System.out.println("1 inf 7: " + inf((byte)1,(byte)7));
		System.out.println("1 inf 1: " + inf((byte)1,(byte)1));
		System.out.println("8 inf 7: " + inf((byte)8,(byte)7));

		System.out.println("1 eg 7: " + eg((byte)1,(byte)7));
		System.out.println("1 eg 1: " + eg((byte)1,(byte)1));
		System.out.println("8 eg 7: " + eg((byte)8,(byte)7));

		ArbreBinaire a = new ArbreBinaire((byte)2 , 
			new ArbreBinaire((byte)5,
				new ArbreBinaire((byte)10, 
					new ArbreBinaire((byte)12,null,null),
					new ArbreBinaire((byte)15,null,null)),
				new ArbreBinaire((byte)13,
					new ArbreBinaire((byte)14,null,null),
					null)),
			new ArbreBinaire((byte)6,
				new ArbreBinaire((byte)8,null,null),
				new ArbreBinaire((byte)7,null,null)));
		a.explorer();
		System.out.println();
	}
}
