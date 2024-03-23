package com.example.doan_giasu.Model;

public class LopHoc {
    private String Email;
    private String Title;
    private String DiaDiemDay;
    private String NgayBatDau;
    private String GioMoiBuoi;
    private String MonHoc;
    private String GioiTinhHocVien;
    private String HocPhi;
    private String SoBuoiTrongTuan;
    private String GioiTinh;
    private String HocPhiTheo;
    private String MoTaChiTiet;
    private String ID;

    public LopHoc() {
        // Hàm khởi tạo mặc định, cần thiết cho Firebase
    }

    public LopHoc(String email, String title, String diaDiemDay, String ngayBatDau, String gioMoiBuoi, String monHoc, String gioiTinhHocVien, String hocPhi, String soBuoiTrongTuan, String gioiTinh, String hocPhiTheo, String moTaChiTiet) {
        Email = email;
        Title = title;
        DiaDiemDay = diaDiemDay;
        NgayBatDau = ngayBatDau;
        GioMoiBuoi = gioMoiBuoi;
        MonHoc = monHoc;
        GioiTinhHocVien = gioiTinhHocVien;
        HocPhi = hocPhi;
        SoBuoiTrongTuan = soBuoiTrongTuan;
        GioiTinh = gioiTinh;
        HocPhiTheo = hocPhiTheo;
        MoTaChiTiet = moTaChiTiet;
    }
    // Các phương thức getter và setter

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDiaDiemDay() {
        return DiaDiemDay;
    }

    public void setDiaDiemDay(String diaDiemDay) {
        DiaDiemDay = diaDiemDay;
    }

    public String getNgayBatDau() {
        return NgayBatDau;
    }

    public void setNgayBatDau(String ngayBatDau) {
        NgayBatDau = ngayBatDau;
    }

    public String getGioMoiBuoi() {
        return GioMoiBuoi;
    }

    public void setGioMoiBuoi(String gioMoiBuoi) {
        GioMoiBuoi = gioMoiBuoi;
    }

    public String getMonHoc() {
        return MonHoc;
    }

    public void setMonHoc(String monHoc) {
        MonHoc = monHoc;
    }

    public String getGioiTinhHocVien() {
        return GioiTinhHocVien;
    }

    public void setGioiTinhHocVien(String gioiTinhHocVien) {
        GioiTinhHocVien = gioiTinhHocVien;
    }

    public String getHocPhi() {
        return HocPhi;
    }

    public void setHocPhi(String hocPhi) {
        HocPhi = hocPhi;
    }

    public String getSoBuoiTrongTuan() {
        return SoBuoiTrongTuan;
    }

    public void setSoBuoiTrongTuan(String soBuoiTrongTuan) {
        SoBuoiTrongTuan = soBuoiTrongTuan;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        GioiTinh = gioiTinh;
    }

    public String getHocPhiTheo() {
        return HocPhiTheo;
    }

    public void setHocPhiTheo(String hocPhiTheo) {
        HocPhiTheo = hocPhiTheo;
    }

    public String getMoTaChiTiet() {
        return MoTaChiTiet;
    }

    public void setMoTaChiTiet(String moTaChiTiet) {
        MoTaChiTiet = moTaChiTiet;
    }
    public String getID() {
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }
}
