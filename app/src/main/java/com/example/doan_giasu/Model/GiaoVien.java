package com.example.doan_giasu.Model;

public class GiaoVien {
    private String HovaTen;
    private String DiaChi;
    private String NamSinh;
    private String BangCap;
    private String MonDay;
    private String Mota;

    public GiaoVien(String hovaTen, String diaChi, String namSinh, String bangCap, String monDay, String mota) {
        HovaTen = hovaTen;
        DiaChi = diaChi;
        NamSinh = namSinh;
        BangCap = bangCap;
        MonDay = monDay;
        Mota = mota;
    }

    public String getHovaTen() {
        return HovaTen;
    }

    public void setHovaTen(String hovaTen) {
        HovaTen = hovaTen;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getNamSinh() {
        return NamSinh;
    }

    public void setNamSinh(String namSinh) {
        NamSinh = namSinh;
    }

    public String getBangCap() {
        return BangCap;
    }

    public void setBangCap(String bangCap) {
        BangCap = bangCap;
    }

    public String getMonDay() {
        return MonDay;
    }

    public void setMonDay(String monDay) {
        MonDay = monDay;
    }

    public String getMota() {
        return Mota;
    }

    public void setMota(String mota) {
        Mota = mota;
    }
}
