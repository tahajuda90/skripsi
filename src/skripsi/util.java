/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skripsi;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author tahajuda
 */
public class util {

    private static koneksi kon = new koneksi();
    private static Connection hub = null;
    private static String sql,trgt;
    
    
    //fungsi operasi database
    private static ArrayList datb(ArrayList arr){
        ResultSet rs = null;
        try {
            hub = kon.kondb();
            rs = kon.getvaldb(hub, sql);
            while (rs.next()) {
                arr.add(rs.getInt(trgt));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arr;
    }
    
     private static ArrayList datb2(ArrayList arr){
        ResultSet rs = null;
        try {
            hub = kon.kondb();
            rs = kon.getvaldb(hub, sql);
            while (rs.next()) {
                arr.add(rs.getDouble(trgt));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arr;
    }
    
    private static ArrayList nilaidb() {        
        ArrayList arr = new ArrayList();
        sql="SELECT nilai FROM khl2";
        trgt="nilai";
        arr=datb(arr);
        return arr;
    }

    public static ArrayList nilaidb(String tahun, int bulan) {
        ArrayList arr = new ArrayList();
        sql="SELECT `nilai` FROM `khl2` WHERE `tahun` = " + tahun + " AND `bulan` BETWEEN '1' AND '" + bulan+"'";
        trgt="nilai";
        arr=datb(arr);
        return arr;
    }

    public static ArrayList nilaidb(String tahun) {
        ArrayList arr = new ArrayList();
        sql="SELECT `nilai` FROM `khl2` WHERE `tahun` = " + tahun;
        trgt="nilai";
        arr=datb(arr);
        return arr;
    }
    
    public static ArrayList nilaidb(int id1,int id2) {
        ArrayList arr = new ArrayList();
        sql="SELECT `nilai` FROM `khl2` WHERE `id_khl`BETWEEN "+id1+" AND "+id2;
        trgt="nilai";
        arr=datb(arr);
        return arr;
    }

    public static ArrayList tahundb() {
        ArrayList arr = new ArrayList();
        sql="SELECT DISTINCT tahun FROM khl2 ";
        trgt="tahun";
        arr=datb(arr);
        return arr;
    }
    
    public static int iddb(String thn, int bulan) {
        int id = 0;
        sql = "SELECT `id_khl` FROM `khl2` WHERE `tahun`=" + thn + " AND `bulan`=" + bulan;
        trgt = "id_khl";
        ResultSet rs = null;
        try {
            hub = kon.kondb();
            rs = kon.getvaldb(hub, sql);
            while (rs.next()) {
                id = rs.getInt(trgt);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
    
    public static double[] ujintr(int intr) {
        double[] fit;
        ArrayList arr = new ArrayList();
        sql = "SELECT `fitnes` FROM `pengujian` WHERE `percobaan` <= 25 AND `jum_intrvl` ="+intr;
        trgt = "fitnes";
        arr = datb2(arr);
        fit=datauj(arr);
        return fit;
    }
    
    public static double[] ujirep(double pc,double pm){
        double[] fit;
        ArrayList arr = new ArrayList();
        sql = "SELECT * FROM `pengujian` WHERE `percobaan` BETWEEN 26 AND 525 AND `pc` = " + pc + " AND `pm` = " + pm;
        trgt = "fitnes";
        arr = datb2(arr);
        fit = datauj(arr);
        return fit;
    }
    
    public static double[] ujipop(int pop){
        double[] fit;
        ArrayList arr = new ArrayList();
        sql = "SELECT * FROM `pengujian` WHERE `percobaan` BETWEEN 526 AND 550 AND `popSize` = "+pop;
        trgt = "fitnes";
        arr = datb2(arr);
        fit = datauj(arr);
        return fit;
    }
    
    public static double[] ujiiter(int it){
        double[] fit;
        ArrayList arr = new ArrayList();
        sql = "SELECT * FROM `pengujian` WHERE `percobaan` BETWEEN 551 AND 575 AND `iterasi` = "+it;
        trgt = "fitnes";
        arr = datb2(arr);
        fit = datauj(arr);
        return fit;
    }

    public static void uji(int jh,int pop,int it,double pc,double pm,String krom,double fit) {
        PreparedStatement isi = null;
        String input;
        input = ("INSERT INTO `pengujian`"+
                "(`jum_intrvl`, `popSize`, `iterasi`, `pc`, `pm`, `kromosom`, `fitnes`) VALUES"+
                "(?,?,?,?,?,?,?)");
        try {
        hub = kon.kondb();
        isi = hub.prepareStatement(input);
        isi.setInt(1, jh);
        isi.setInt(2, pop);
        isi.setInt(3, it);
        isi.setDouble(4, pc);
        isi.setDouble(5, pm);
        isi.setString(6, krom);
        isi.setDouble(7, fit);
        isi.executeUpdate();
        isi.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //fungsi pendukung yang berkaitan dengan algoritma
    public static int[] data() {
        ArrayList arr = new ArrayList();
        arr = util.nilaidb();
        int[] k = new int[arr.size()];
        for (int i = 0; i < k.length; i++) {
            k[i] = (int) arr.get(i);
        }
        return k;
    }
    
     public static int[] data(ArrayList arr) {
        int[] k = new int[arr.size()];
        for (int i = 0; i < k.length; i++) {
            k[i] = (int)arr.get(i);
        }
        return k;
    }
    
    private static double[] datauj(ArrayList arr) {
        double[] k = new double[arr.size()];
        for (int i = 0; i < k.length; i++) {
            k[i] = (double) arr.get(i);
        }
        return k;
    }
    
    public static int max(int[] dat) {
        int m=0;
        for(int i=0;i<dat.length;i++){
            if(dat[i]>m){
                m=dat[i];
            }
        }
        return m;
    }

    public static int min(int[] dat) {
        int m=0;
           for(int i=0;i<dat.length;i++){
            if(dat[i]<m){
                m=dat[i];
            }
        }
        return m;
    }

    public static double[][] limit(double[][] intr) {
        double[][] limit = new double[intr.length - 1][2];
        for (int i = 0; i < limit.length; i++) {
            limit[i][0] = (intr[i][0] + intr[i][1]) / 2;
            limit[i][1] = (intr[i + 1][0] + intr[i + 1][1]) / 2;
        }
        return limit;
    }

    public static double randrng(double mn, double mx) {
        double r;
        r = mn + (Math.random() * (mx - mn));
        return r;
    }

    public static void prntmtrx(double[][] x) {

        for (int i = 0; i < x.length; i++) {
            System.out.println(Arrays.toString(x[i]));
        }

    }

    public static void prntmtrx(int[][] x) {

        for (int i = 0; i < x.length; i++) {
            System.out.println(Arrays.toString(x[i]));
        }

    }

    public static double[][] gabung(double[][] krom, double[][] kros, double[][] mut) {

        double[][] gab = new double[krom.length + kros.length + mut.length][krom[0].length];
        int a, b;
        a = krom.length;
        b = krom.length + kros.length;
        for (int i = 0; i < gab.length; i++) {
            if (i < a) {
                System.arraycopy(krom[i], 0, gab[i], 0, krom[0].length);
            } else if ((i > a - 1) && (b > i)) {
                System.arraycopy(kros[i - a], 0, gab[i], 0, krom[0].length);
            } else if (i > b - 1) {
                System.arraycopy(mut[i - b], 0, gab[i], 0, krom[0].length);
            }
        }
        return gab;
    }

    public static double[][] ubahhim(double[][] intr, double[] x) {
        double[][] intk = new double[intr.length][intr[0].length];
        intk[0][0] = intr[0][0];
        for (int i = 0; i < x.length; i++) {
            intk[i][1] = x[i];
            intk[i + 1][0] = intk[i][1];
        }
        intk[intr.length - 1][1] = intr[intr.length - 1][1];
        for(int i=0;i<intk.length;i++){
            Arrays.sort(intk[i]);
        }
        return intk;
    }

}
