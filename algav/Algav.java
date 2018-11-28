package algav;

import algav.tasmin.ITasMin;
import algav.tasmin.TasMinTableau;
import algav.Cle128;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Algav {

    /**
    *   Lit le fichier dans path et l'ajoute dans une structure de TasMinTableau et calcule le temps de ConstIter en nano seconde
    *
    *   @param path Chemin vers le fichier de lecture
    *
    *   @return le temps pour ajouter la liste des cles dans path avec ConstIter
    */
    public static long timeConstIter(String path){
        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            String sCurrentLine;
            List<Cle128> l = new ArrayList<>();
            ITasMin<Cle128> res = new TasMinTableau<>();
            while ((sCurrentLine = br.readLine()) != null) {
                l.add(new Cle128(sCurrentLine));
            }
            long startTime = System.nanoTime();
            res.consIter(l);
            long endTime = System.nanoTime();
            br.close();
            return (endTime - startTime);
        }catch(IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
    *   Compte les lignes du fichier path
    *
    *   @param path chemin du fichier 
    */

    public static int countLine(String path){
        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            int lines = 0;
            while (br.readLine() != null) lines++;
            br.close();
            return lines;
        }catch(IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void writeTime(int temps[],String path){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            bw.write(Integer.toString(100));
            bw.write(",");
            bw.write(Integer.toString(200));
            bw.write(",");
            bw.write(Integer.toString(500));
            bw.write(",");
            bw.write(Integer.toString(1000));
            bw.write(",");
            bw.write(Integer.toString(5000));
            bw.write(",");
            bw.write(Integer.toString(10000));
            bw.write(",");
            bw.write(Integer.toString(20000));
            bw.write(",");
            bw.write(Integer.toString(50000));
            bw.newLine();
            for (int i : temps){
                bw.write(Integer.toString(i/5));
                bw.write(",");
            }
            bw.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
    *   Calcule la moyenne du temps de construction de chaque liste de cle et l'enrengistre dans un fichier .csv
    *   
    *   @param path chemin du dossier contenant les cles
    */
    public static void averageConstIter(String path){
        File folder = new File(path);
        int temps[] = new int[8];
        String route;
        for (File f : folder.listFiles()) {
            route = path + f.getName();
            switch(countLine(route)){
                case 100:
                    temps[0] += timeConstIter(route);
                    break;
                case 200:
                    temps[1] += timeConstIter(route);
                    break;
                case 500:
                    temps[2] += timeConstIter(route);
                    break;
                case 1000:
                    temps[3] += timeConstIter(route);
                    break;
                case 5000:
                    temps[4] += timeConstIter(route);
                    break;
                case 10000:
                    temps[5] += timeConstIter(route);
                    break;
                case 20000:
                    temps[6] += timeConstIter(route);
                    break;
                case 50000:
                    temps[7] += timeConstIter(route);
                    break;
            }
        }
        writeTime(temps,"averageConstIter.csv");
    }

    /**
    *   Lit le fichier dans path et l'ajoute dans une structure de TasMinTableau et calcule le temps de Union en nano seconde avec t
    *
    *   @param t Tas min avec qui on fait l'union
    *   @param path Chemin vers le fichier de lecture
    *
    *   @return le temps d'unir la liste des cles dans path avec t
    */
    public static long timeUnion(ITasMin<Cle128> t,String path){
        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            String sCurrentLine;
            List<Cle128> l = new ArrayList<>();
            ITasMin<Cle128> t2 = new TasMinTableau<>();
            while ((sCurrentLine = br.readLine()) != null) {
                l.add(new Cle128(sCurrentLine));
            }
            t2.consIter(l);
            long startTime = System.nanoTime();
            t.union(t2);
            long endTime = System.nanoTime();
            br.close();
            return (endTime - startTime);
        }catch(IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
    *   Calcule la moyenne du temps de l'union de chaque liste de cle et l'enrengistre dans un fichier .csv
    *   On unit toute les listes de meme nombre de cle entre elle(tous les 100 ensemble, tous les 200 ensemble...etc.)
    *   
    *   @param path chemin du dossier contenant les cles
    */
    public static void averageUnion(String path){
        File folder = new File(path);
        int temps[] = new int[8];
        ITasMin<Cle128> t100 = new TasMinTableau<>();
        ITasMin<Cle128> t200 = new TasMinTableau<>();
        ITasMin<Cle128> t500 = new TasMinTableau<>();
        ITasMin<Cle128> t1000 = new TasMinTableau<>();
        ITasMin<Cle128> t5000 = new TasMinTableau<>();
        ITasMin<Cle128> t10000 = new TasMinTableau<>();
        ITasMin<Cle128> t20000 = new TasMinTableau<>();
        ITasMin<Cle128> t50000 = new TasMinTableau<>();
        String route;
        for (File f : folder.listFiles()) {
            route = path + f.getName();
            switch(countLine(route)){
                case 100:
                    temps[0] += timeUnion(t100,route);
                    break;
                case 200:
                    temps[1] += timeUnion(t200,route);
                    break;
                case 500:
                    temps[2] += timeUnion(t500,route);
                    break;
                case 1000:
                    temps[3] += timeUnion(t1000,route);
                    break;
                case 5000:
                    temps[4] += timeUnion(t5000,route);
                    break;
                case 10000:
                    temps[5] += timeUnion(t10000,route);
                    break;
                case 20000:
                    temps[6] += timeUnion(t20000,route);
                    break;
                case 50000:
                    temps[7] += timeUnion(t50000,route);
                    break;
            }
        }
        writeTime(temps,"averageUnion.csv");

    }

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
        //System.out.println(timeConstIter(args[0]));
        // averageConstIter(args[0]);
        averageUnion(args[0]);
    }

}
