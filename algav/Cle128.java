package algav;

import java.math.BigInteger;

public class Cle128 implements Comparable<Cle128> {

    private final BigInteger cle;

    public Cle128(String cle)
    {
        if (cle.startsWith("0x"))
        {
            cle = cle.substring(2);
        }
        this.cle = new BigInteger(cle, 16);
    }

    public static boolean inf(Cle128 cle1, Cle128 cle2)
    {
        return cle1.cle.compareTo(cle2.cle) == -1;
    }

    public static boolean eg(Cle128 cle1, Cle128 cle2)
    {
        return cle1.cle.compareTo(cle2.cle) == 0;
    }

    @Override
    public String toString()
    {
        return this.cle.toString(16);
    }

    @Override
    public int compareTo(Cle128 o)
    {
        return this.cle.compareTo(cle);
    }

}
