package com.example.doan_giasu.Adapter;

public class GiaSu {

    private String Ten;
    private String Email;
    private  String NamSinh;
    private String DiaChi;
    private int SDT;
    //private String Gioitinh;

    //Contructor thông tin cá nhân đối chiếu bên thông tin cá nhân
    public GiaSu(String ten, String email, String namSinh, String diaChi, String gioitinh) {
        this.Ten = ten;
        this.Email = email;
        this. NamSinh = namSinh;
        this. DiaChi = diaChi;
        this.SDT = SDT;
        //this.Gioitinh = gioitinh;
    }
    public GiaSu(){
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getNamSinh() {
        return NamSinh;
    }

    public void setNamSinh(String namSinh) {
        NamSinh = namSinh;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public int getSDT() {
        return SDT;
    }

    public void setSDT(int SDT) {
        this.SDT = SDT;
    }

//    public String getGioitinh() {
//        return Gioitinh;
//    }

//    public void setGioitinh(String gioitinh) {
//        Gioitinh = gioitinh;
//    }
}
