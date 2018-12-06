/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skripsi;

/**
 *
 * @author tahajuda
 */
public class fts {
    
    public static int[] vardat(int[] dat) {
        int[] vard = new int[dat.length - 1];
        for (int i = 0; i < vard.length; i++) {
            vard[i] = dat[i + 1] - dat[i];
        }
        return vard;
    }
        
    private static double universe (int[] vard){
        double u=(util.max(vard)+10)+Math.abs(util.min(vard)-10);
        return u;
    }
    
    public static double[][] intrvl(int[] vard, int jmlh) {
        double[][] intr = new double[jmlh][2];
        double rng = fts.universe(vard) / jmlh;

        double temp = util.min(vard) - 10;
        for (int i = 0; i < intr.length; i++) {
            intr[i][0] = temp;
            temp = temp + rng;
            intr[i][1] = temp;
        }
        return intr;
    }
    
    private static int[] fzfks(int[] ars2, double[][] intk){
        int[] hi;
        hi = new int[ars2.length];
        for (int i = 0; i < ars2.length; i++) {
            for (int j = 0; j < intk.length; j++) {
                if (ars2[i] > intk[j][0] && ars2[i] <= intk[j][1]) {
                    hi[i] = j;
                }
            }
        }
        return hi;
    }
    
    private static int[][] flrflrg(int[] f, int lintrvl) {
        int[][] flr, flrg;
        flr = new int[f.length - 1][2];
        flrg = new int[lintrvl][lintrvl];
        for (int i = 0; i < f.length - 1; i++) {
            flr[i][0] = f[i];
            flr[i][1] = f[i + 1];
        }
        for (int i = 0; i < flr.length; i++) {
            int x = flr[i][0];
            int y = flr[i][1];
            flrg[x][y] = flr[i][1] + 1;
        }
        return flrg;
    }
   
    private static double[] mfunction(int[][] fg, int findx){
        double der[][];
        der = new double[fg.length][fg.length];

        for (int i = 0; i < der.length; i++) {
            for (int j = 0; j < der[0].length; j++) {
                if (i == j) {
                    der[i][j] = 1;
                } else if (i - j == 1) {
                    der[i][j] = 0.5;
                } else if (j - i == 1) {
                    der[i][j] = 0.5;
                }
            }
        }
        double[] temp = new double[der[0].length];
        for (int i = 0; i < fg[0].length; i++) {
            if (fg[findx][i] != 0) {
                for (int j = 0; j < der[0].length; j++) {
                    temp[j] = Math.max(der[i][j], temp[j]);

                }
            }
        }
        return temp;
    }
    
    private static double dfuzzy(double []der, double[][] intk){
        double hs;
        int st = 0, en = 0, m1 = 0, m2 = 0, idx = 0;
        for (int i = 0; i < der.length; i++) {
            if (der[i] == 1) {
                m1 = m1 + 1;
                idx = i;
            } else if (der[i] == 0.5) {
                m2 = m2 + 1;
                idx = i;
            }
        }
        for (int j = 0; j < der.length; j++) {
            if (der[j] == 1) {
                st = st + 1;
                en = j;
                if (j == der.length - 1) {
                    break;
                } else if (der[j + 1] != 1) {
                    break;
                }
            }
        }        
        if (st > 1) {
            hs = (intk[(en + 1) - st][0] + intk[en][1]) / 2;

        } else if (m2 == 1 && m1 == 1) {
            hs = (((intk[idx][0] + intk[idx][1]) / 2) * der[idx]);

        } else if (m2 == 0 && m1 == 0) {
            hs = (((intk[idx][0] + intk[idx][1]) / 2) * der[idx]);

        } else {
            double adh1 = 0, adh2 = 0;
            for (int i = 0; i < der.length; i++) {
                adh1 = adh1 + (((intk[i][0] + intk[i][1]) / 2) * der[i]);
                adh2 = adh2 + der[i];
            }
            hs = adh1 / adh2;
        }
           
        return hs;
    }
    
    public static double [] training(double[][] intk,int[] l,int[] k){
        double rg;
        int[] f;
        double[] fc, der;

        f = fts.fzfks(l, intk);
        int[][] fg = fts.flrflrg(f, intk.length);
        fc = new double[k.length];
        for (int i = 1; i < fc.length; i++) {
            int findx = f[i - 1];
            //System.out.println(f[i - 1]);
            der = fts.mfunction(fg, findx);
            rg = fts.dfuzzy(der, intk);
            fc[i] = rg + k[i - 1];
        }
        return fc;
    }
    
      public static double [] training2(double[][] intk,int[] l,int[] k){
        double rg;
        int[] f;
        double[] fc, der;

        f = fts.fzfks(l, intk);
        int[][] fg = fts.flrflrg(f, intk.length);
        fc = new double[k.length+1];
        for (int i = 1; i < fc.length-1; i++) {
            int findx = f[i-1];
            //System.out.println(f[i - 1]);
            der = fts.mfunction(fg, findx);
            rg = fts.dfuzzy(der, intk);
            fc[i] = rg + k[i-1];
        }
        fc[fc.length-1]=fts.dfuzzy(fts.mfunction(fg, f[f.length-1]), intk)+k[k.length-1];
        return fc;
    }
    
    public static double affer(double[] fc,int[] k){
        double temp, akur;

        temp = 0;
        for (int i = 0; i < fc.length; i++) {
            temp = Math.abs((fc[i] - k[i]) / k[i]) + temp;
        }
        akur = (temp / k.length) * 100;
        return akur;
    }
}
