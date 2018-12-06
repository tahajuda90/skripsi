/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skripsi;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author tahajuda
 */
public class Skripsi {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        int[] k = util.data();
        int[] l = fts.vardat(k);
        double[][] intr = fts.intrvl(l, 7);
        double[] krom ={-659661.8190605823, -337501.20558110304, -4380.501510010567, 340908.78622582945, 431152.4086169435, 777171.3937155532}; 
        double fc[]=fts.training(util.ubahhim(intr, krom),l,k);
        //System.out.println(Arrays.toString(fc)+"  "+fc.length);
        
        //utama();
        //ujiiter();        
//       hintr();
//        System.out.println("==================================");
//        hrep();
//        System.out.println("==================================");
//        hpop();
//        System.out.println("============================");
//        hiter();

    }
    
    public static void utama() {
        Thread iterasi = new Thread() {
            public void run() {
                int pop, jh, it,interupt=0;
                double pc, pm;
                jh = 7;
                pop = 1300;
                it = 100;
                pc = 0.9;
                pm = 0.5;
                int[] k, l, id;
                id = null;
                double[][] intr, lmt, krom, kros, mut, gab;
                gab = null;
                double[] fit;
                fit = null;
                k = util.data();
                l = fts.vardat(k);
                intr = fts.intrvl(l, jh);
                lmt = util.limit(intr);
                krom = Algen.inisiali(lmt, pop);
                //util.prntmtrx(krom);
                //System.out.println("================kromm awal=================");

                for (int i = 0; i < it; i++) {
                    kros = Algen.crossver(krom, pc);
                    mut = Algen.mutasi(lmt, krom, pm);
                    gab = util.gabung(krom, kros, mut);
                    fit = Algen.fitness(intr, gab, k, l);
                    id = Algen.seleksi(fit, pop);
                    krom = Algen.kroms(gab, id);
                    if (interupt == 25) {
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                        }
                        interupt=0;
                    }
                  interupt++;
                }
                //System.out.println("================kromosom==============");
                //util.prntmtrx(krom);
                //Algen.fits(fit, id);
                //System.out.println("====================================");
                int idtk;
                idtk = Algen.indtrb(gab, fit, id);
                //util.uji(jh, pop, it, pc, pm, Arrays.toString(gab[idtk]), fit[idtk]);
            }
        }; iterasi.start();
    }
    
    public static void utama(int pop, int jh, int it, double pc, double pm) {
        Thread iterasi = new Thread() {
            public void run() {
                int interupt=0;
                int[] k, l, id;
                id = null;
                double[][] intr, lmt, krom, kros, mut, gab;
                gab = null;
                double[] fit;
                fit = null;
                k = util.data();
                l = fts.vardat(k);
                intr = fts.intrvl(l, jh);
                lmt = util.limit(intr);
                krom = Algen.inisiali(lmt, pop);
                //util.prntmtrx(krom);
                //System.out.println("================kromm awal=================");

                for (int i = 0; i < it; i++) {
                    kros = Algen.crossver(krom, pc);
                    mut = Algen.mutasi(lmt, krom, pm);
                    gab = util.gabung(krom, kros, mut);
                    fit = Algen.fitness(intr, gab, k, l);
                    id = Algen.seleksi(fit, pop);
                    krom = Algen.kroms(gab, id);
                     if (interupt == 50) {
                        try {
                            Thread.sleep(20000);
                        } catch (InterruptedException e) {
                        }
                        interupt=0;
                    }
                  interupt++;
                    //Algen.fits(fit, id);
                    //System.out.println("================" + i + "================");
                }
                //util.prntmtrx(krom);
                //Algen.fits(fit, id);
                System.out.println("====================================");
                int idtk;
                idtk = Algen.indtrb(gab, fit, id);
                util.uji(jh, pop, it, pc, pm, Arrays.toString(gab[idtk]), fit[idtk]);
            }
        };
        iterasi.start();
    }
    
    public static void hintr() {
        int[] ji = { 6, 7, 8, 9, 10};
        double[] intr;
        for (int i = 0; i < ji.length; i++) {
            System.out.print("interval : "+ji[i] + " ");
            intr = decfor(util.ujintr(ji[i]));
            System.out.print(Arrays.toString(intr) + "  ");
            rata(intr);
            System.out.println("");
        }
    }
    
    public static void hrep(){
    double[] prob = {0.1, 0.3, 0.5, 0.7, 0.9};
        double[] intr;
        for(int i=0;i<prob.length;i++){
            System.out.println("pc ="+prob[i]);
            for(int j=0;j<prob.length;j++){
                System.out.print("pm ="+prob[j]);
                intr=decfor(util.ujirep(prob[i], prob[j]));
                System.out.print(" "+Arrays.toString(intr));
                rata(intr);
                System.out.println("");
            }        
        }
    }
    
    public static void hpop(){
        int[] pop = {50,300,550,800,1050};
        double[] intr;
        for (int i = 0; i < pop.length; i++) {
            System.out.print("populasi : "+pop[i] + " ");
            intr = decfor(util.ujipop(pop[i]));
            System.out.print(Arrays.toString(intr) + "  ");
            rata(intr);
            System.out.println("");
        }
    }
    
    public static void hiter(){
        int[] it = {100,150,200,250,300};
        double[] intr;
        for (int i = 0; i < it.length; i++) {
            System.out.print("iterasi : "+it[i] + " ");
            intr = decfor(util.ujiiter(it[i]));
            System.out.print(Arrays.toString(intr) + "  ");
            rata(intr);
            System.out.println("");
        }
    }
    
    public static void rata(double[] itnr){
        double a=0;        
        for(int i=0;i<itnr.length;i++){
            a+=itnr[i];
        }
        a=a/itnr.length;
        System.out.print(" "+a);
    }
    
    public static void ujiint(){
        int[] itv = {6, 7, 8, 9, 10};
        for (int i = 0; i < itv.length; i++) {
            utama(50, itv[i], 100, 0.6, 0.5);
        }
    }
    
    public static void ujirep(){
        double[] prob = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1};
        for (int i = 0; i < prob.length; i++) {
            for (int j = 0; j < prob.length; j++) {
                utama(50, 7, 100, prob[i], prob[j]);
            }
        }
    }

    public static void ujipop(){
        int[] pop = {50,300,550,800,1050};
        for (int i = 0; i < pop.length; i++) {
                utama(pop[i], 7, 100, 0.9, 0.5);
        }
    }
    
    public static void ujiiter() {
        int[] iter = {100,150,200,250,300};
        for (int i = 0; i < iter.length; i++) {
            utama(1050, 7, iter[i], 0.9, 0.5);
        }
    }
    
    public static double[] decfor(double[] mtrx) {
        double[] berub = new double[mtrx.length];
        for (int i = 0; i < mtrx.length; i++) {
            berub[i] = Math.round(mtrx[i]*10000.0)/10000.0;
        }
        return berub;
    }
}
