package com.example.doan_giasu.Model;

public class LopHoc {
    private String MaLop;
    private String Title;
    private String HocPhi;
    private String MonHoc;
    private String ThoiGian;

    public LopHoc(String maLop, String title, String hocPhi, String monHoc, String thoiGian, String diaDiem) {
        MaLop = maLop;
        Title = title;
        HocPhi = hocPhi;
        MonHoc = monHoc;
        ThoiGian = thoiGian;
    }

    public String getMaLop() {
        return MaLop;
    }

    public void setMaLop(String maLop) {
        MaLop = maLop;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getHocPhi() {
        return HocPhi;
    }

    public void setHocPhi(String hocPhi) {
        HocPhi = hocPhi;
    }

    public String getMonHoc() {
        return MonHoc;
    }

    public void setMonHoc(String monHoc) {
        MonHoc = monHoc;
    }

    public String getThoiGian() {
        return ThoiGian;
    }

    public void setThoiGian(String thoiGian) {
        ThoiGian = thoiGian;
    }

}
