/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skripsi;

import java.util.Arrays;



/**
 *
 * @author tahajuda
 */
public class Algen {
    
    public static double[][] inisiali(double[][] limit, int pop) {
        double[][] krom = new double[pop][limit.length];

        for (int i = 0; i < krom.length; i++) {
            for (int j = 0; j < krom[0].length; j++) {
                krom[i][j] = util.randrng(limit[j][0], limit[j][1]);
            }
        }
        return krom;
    }
    
    public static double[][] crossver(double [][] krom, double pc){
        double b = Math.round(krom.length * pc);
        int cr = (int) Math.round(b / 2);
        double[][] kros = new double[cr * 2][krom[0].length];

        for (int i = 0; i < cr; i++) {
            int p1, p2;
            p1 = (int) Math.round(util.randrng(0, krom.length-1));
            p2 = (int) Math.round(util.randrng(0, krom.length-1));
            
            for (int j = 0; j < krom[0].length; j++) {
                kros[i][j] = krom[p1][j] + (util.randrng(-0.25, 1.25) * (krom[p2][j] - krom[p1][j]));
                kros[i + cr][j] = krom[p2][j] + (util.randrng(-0.25, 1.25) * (krom[p1][j] - krom[p2][j]));
            }
            Arrays.sort(kros[i]);
            Arrays.sort(kros[i+cr]);
        }
        return kros;
    }
    
    public static double[][] mutasi(double[][] limit, double[][] krom, double pm) {
        int b = krom.length, k = krom[0].length, rng;
        double[][] mut = new double[(int) Math.round(b * pm)][k];
        rng = (int) Math.round(util.randrng(0, k - 1));
        
        for (int i = 0; i < mut.length; i++) {
            int pn = (int) Math.round(util.randrng(0, b - 1));
            for (int j = 0; j < mut[0].length; j++) {
                if (j == rng) {
                    mut[i][j] = krom[pn][j] + (util.randrng(-1, 1) * (limit[j][0] - limit[j][1]));
                } else {
                    mut[i][j] = krom[pn][j];
                }
            }
            Arrays.sort(mut[i]);
        }
        return mut;
    }
    
    public static double[] fitness(double[][] intr, double[][] gab,int[] k,int[] l){
        double[] fit = new double[gab.length];
        double[][] intk;
        for(int i=0;i<gab.length;i++){
            intk = util.ubahhim(intr, gab[i]);
            double[] fc;
            double akur;
            fc = fts.training(intk, l, k);
            akur = fts.affer(fc, k);
            fit[i]=100/akur;
        }
        return fit;
    }
        
    private static int[] elitism(double[] fit,int pop) {
        double[] temp;
        int[] idx = new int[pop];
        temp = new double[fit.length];
        System.arraycopy(fit, 0, temp, 0, fit.length);
        Arrays.sort(temp);
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < fit.length; j++) {
                if (temp[(temp.length - 1) - i] == fit[j]) {
                    idx[i] = j;
                }
            }
            if (i > pop - 2) {
                break;
            }
        }
        return idx;
    }
    
    private static int[] rlltwheel(double[] fit,int pop){
        double[] prc, prcm;
        int[] idx2;
        double sum, temp;
        prc = new double[fit.length];
        prcm = new double[fit.length];
        idx2 = new int[pop];
        sum = 0;
        for (int i = 0; i < fit.length; i++) {
            sum += fit[i];
        }

        temp = 0;
        for (int i = 0; i < prc.length; i++) {
            prc[i] = fit[i] / sum;
            temp += prc[i];
            prcm[i] = temp;
        }
        for (int i = 0; i < idx2.length; i++) {
            double num = Math.random();
            for (int j = 0; j < prcm.length; j++) {
                if (prcm[j] >= num) {
                    idx2[i] = j;
                    break;
                }
            }
        }
        return idx2;
    }
    
    public static int[] seleksi(double[] fit, int pop) {
        int[] idxe, idxr, idxs;
        idxe = Algen.elitism(fit, pop);
        idxr = Algen.rlltwheel(fit, pop);
        idxs = new int[pop];
        for (int i = 0; i < pop; i++) {
            if (i >= Math.round((0.75 * pop))) {
                idxs[i] = idxe[i];
            } else {
                idxs[i] = idxr[i];
            }
        }
        return idxs;
    }
    
    public static double[][]kroms(double[][] gab,int[] idx){
        double[][] kb;
        kb = new double[idx.length][gab[0].length];
        for (int i = 0; i < idx.length; i++) {
            System.arraycopy(gab[idx[i]], 0, kb[i], 0, gab[0].length);
            //System.out.println(Arrays.toString(gab[idx[i]]));
        }
        //System.out.println("=========================================");
        return kb;
    }
    
    public static double[][]kroms(double[][] gab,double[] fit,int[] idx){
        double[][] kb;
        kb = new double[idx.length][gab[0].length];
        for (int i = 0; i < idx.length; i++) {
            System.arraycopy(gab[idx[i]], 0, kb[i], 0, gab[0].length);
            System.out.println(Arrays.toString(gab[idx[i]])+" "+fit[idx[i]]);
        }
        return kb;
    }
    
    public static void fits(double[] fit, int[] idx) {
        System.out.println("=======fitness============");
        for (int i = 0; i < idx.length; i++) {
            System.out.println(i + "=  " + fit[idx[i]]);
        }
    }

    public static int indtrb(double[][] gab, double[] fit, int[] idx) {
        double temp = 0;
        int idtb = 0;
        for (int i = 0; i < idx.length; i++) {
            if (fit[idx[i]] > temp) {
                temp = fit[idx[i]];
                idtb = idx[i];
            }
        }
        System.out.print(idtb + "  ");
        System.out.println(fit[idtb]);
        System.out.println(Arrays.toString(gab[idtb]));
        return idtb;
    }
    
    
    
}
