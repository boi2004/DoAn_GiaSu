package com.example.doan_giasu.Model;

public class GiaSu {
    private String monHoc;
    private String ngheNghiep;
    private String thanhPho;
    private String hoVaTen;
    private String diaChi;
    private String namSinh;
    private String email;
    private String gioiThieuBanThan;
    private String soDienThoai;
    private String truongDaHoc;
    private String namTotNghiep;
    private String avatarUrl; // Trường lưu trữ đường dẫn đến ảnh đại diện của gia sư trên Firebase Storage hoặc máy chủ

    public GiaSu() {
        // Hàm khởi tạo mặc định, cần thiết cho Firebase
    }

    public GiaSu(String monHoc, String ngheNghiep, String thanhPho, String hoVaTen, String diaChi, String namSinh, String email, String gioiThieuBanThan, String soDienThoai, String truongDaHoc, String namTotNghiep ,String avatarUrl) {
        this.monHoc = monHoc;
        this.ngheNghiep = ngheNghiep;
        this.thanhPho = thanhPho;
        this.hoVaTen = hoVaTen;
        this.diaChi = diaChi;
        this.namSinh = namSinh;
        this.email = email;
        this.gioiThieuBanThan = gioiThieuBanThan;
        this.soDienThoai = soDienThoai;
        this.truongDaHoc = truongDaHoc;
        this.namTotNghiep = namTotNghiep;
        this.avatarUrl = avatarUrl;
    }
    public GiaSu(String monHoc, String ngheNghiep, String thanhPho, String hoVaTen, String diaChi, String namSinh, String email, String gioiThieuBanThan, String soDienThoai, String truongDaHoc, String namTotNghiep ) {
        this.monHoc = monHoc;
        this.ngheNghiep = ngheNghiep;
        this.thanhPho = thanhPho;
        this.hoVaTen = hoVaTen;
        this.diaChi = diaChi;
        this.namSinh = namSinh;
        this.email = email;
        this.gioiThieuBanThan = gioiThieuBanThan;
        this.soDienThoai = soDienThoai;
        this.truongDaHoc = truongDaHoc;
        this.namTotNghiep = namTotNghiep;
    }
    public GiaSu(String monHoc, String ngheNghiep, String hoVaTen, String diaChi, String namSinh) {
        this.monHoc = monHoc;
        this.ngheNghiep = ngheNghiep;
        this.hoVaTen = hoVaTen;
        this.diaChi = diaChi;
        this.namSinh = namSinh;
    }

    // Các phương thức getter và setter
    public String getMonHoc() {
        return monHoc;
    }

    public void setMonHoc(String monHoc) {
        this.monHoc = monHoc;
    }

    public String getNgheNghiep() {
        return ngheNghiep;
    }

    public void setNgheNghiep(String ngheNghiep) {
        this.ngheNghiep = ngheNghiep;
    }

    public String getThanhPho() {
        return thanhPho;
    }

    public void setThanhPho(String thanhPho) {
        this.thanhPho = thanhPho;
    }

    public String getHoVaTen() {
        return hoVaTen;
    }

    public void setHoVaTen(String hoVaTen) {
        this.hoVaTen = hoVaTen;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(String namSinh) {
        this.namSinh = namSinh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGioiThieuBanThan() {
        return gioiThieuBanThan;
    }

    public void setGioiThieuBanThan(String gioiThieuBanThan) {
        this.gioiThieuBanThan = gioiThieuBanThan;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getTruongDaHoc() {
        return truongDaHoc;
    }

    public void setTruongDaHoc(String truongDaHoc) {
        this.truongDaHoc = truongDaHoc;
    }

    public String getNamTotNghiep() {
        return namTotNghiep;
    }

    public void setNamTotNghiep(String namTotNghiep) {
        this.namTotNghiep = namTotNghiep;
    }
    // Getter và setter cho trường avatarUrl
    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}

