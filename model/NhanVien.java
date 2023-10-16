/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package asignmantGd1.model;

/**
 *
 * @author macbook
 */
public class NhanVien {
    private String hoTen,ma,email;
    private double luong;
    private int tuoi;

    public NhanVien(String hoTen, String ma, String email, double luong, int tuoi) {
        this.hoTen = hoTen;
        this.ma = ma;
        this.email = email;
        this.luong = luong;
        this.tuoi = tuoi;
    }

    public NhanVien() {
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getLuong() {
        return luong;
    }

    public void setLuong(double luong) {
        this.luong = luong;
    }

    public int getTuoi() {
        return tuoi;
    }

    public void setTuoi(int tuoi) {
        this.tuoi = tuoi;
    }
    
    
    
    
}
