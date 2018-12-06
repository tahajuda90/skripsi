/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skripsi;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tahajuda
 */
public class koneksi {

    public  Connection kondb() {
        Connection hub=null;
        try {
            hub = DriverManager.getConnection("jdbc:mysql://localhost:3306/data_skripsi", "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(koneksi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hub;
    }

    public ResultSet getvaldb(Connection hub,String Quer) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = hub.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(koneksi.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            rs = stmt.executeQuery(Quer);
        } catch (SQLException ex) {
            Logger.getLogger(koneksi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    

}
