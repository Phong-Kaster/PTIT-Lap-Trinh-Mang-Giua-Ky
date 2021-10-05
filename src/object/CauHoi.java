/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

/**
 *
 * @author ngdanghau
 */
public class CauHoi {
    private int macauhoi;
    private String noidung;
    private String a;
    private String b;
    private String c;
    private String d;

    public int getMacauhoi() {
        return macauhoi;
    }

    public void setMacauhoi(int macauhoi) {
        this.macauhoi = macauhoi;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getA() {
        return a;
    }

    public void setA(String A) {
        this.a = A;
    }

    public String getB() {
        return b;
    }

    public void setB(String B) {
        this.b = B;
    }

    public String getC() {
        return c;
    }

    public void setC(String C) {
        this.c = C;
    }

    public String getD() {
        return d;
    }

    public void setD(String D) {
        this.d = D;
    }
    
    
    @Override
    public String toString() {
            return "Student [macauhoi=" + macauhoi + ", noidung=" + noidung + "]";
    }
    
}
