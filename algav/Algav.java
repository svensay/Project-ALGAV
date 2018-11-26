package algav;

import algav.tasmin.ITasMin;
import algav.tasmin.TasMinTableau;
import algav.Cle128;

public class Algav {

    public static void main(String[] args)
    {
        Cle128 test1 = new Cle128("0x9c1f03a0d9cf510f2765bd0f226ff5dc");
        Cle128 test2 = new Cle128("0x9c1f03a0d9cf510f2765bd0f226ff5dc");

        Cle128 test3 = new Cle128("0x10fd1015413104a2f26018d0ab77a729");
        Cle128 test4 = new Cle128("0x10fd1015413104a2f26018d0ab77a728");
        Cle128 test6 = new Cle128("0x10fd1015413104a2f26018d0ab77a728");
        Cle128 test5 = new Cle128("0x10fd1015413104a2f26018d0ab77a727");

        System.out.println(test4 == test6);

        /*ITasMin<Integer> test = new TasMinTableau<>();
        test.ajout(2)
                .ajout(5)
                .ajout(6)
                .ajout(10)
                .ajout(13)
                .ajout(8)
                .ajout(7)
                .ajout(12)
                .ajout(15)
                .ajout(15)
                .ajout(15)
                .ajout(14);*/
        ITasMin<Cle128> test = new TasMinTableau<>();
        test.ajout(test1).ajout(test4).ajout(test3).ajout(test2);
        System.out.println(test);

        test.supprMin();
        System.out.println(test);

    }

}
